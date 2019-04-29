package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.api.ShoppingCart;
import edu.uark.uarkregisterapp.models.api.services.EmployeeService;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.adapters.ShoppingCartListAdapter;



public class ShoppingCartActivity extends AppCompatActivity
{
    //private List<Product> products;
    private ProductListAdapter productListAdapter;
    private EmployeeTransition employeeTransition;
    private ProductService productService;
    private ShoppingCart shoppingCart;
    private ListView listView;
    private ArrayList<Product> products;
    private ShoppingCartListAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.products = new ArrayList<Product>();
        this.productService = new ProductService();
        this.shoppingCart = new ShoppingCart();
        this.listView = getShoppingCartListView();
        this.productAdapter = new ShoppingCartListAdapter(this, this.products);
        this.listView.setAdapter(this.productAdapter);

        //this.products = new ArrayList<>();
        //this.productListAdapter = new ProductListAdapter(this, this.products);

        //this.employeeTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_employee));
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    public void addByLookupCode(View view)
    {
        this.displayFunctionalityNotAvailableDialog();
        if(!validateInput())
        {
            return;
        }
        String lookup_code = this.getLookupCodeEditText().getText().toString();
        ApiResponse<Product> product = (new ProductService().getProductByLookupCode(lookup_code));
        Product t_product = product.getData();
        shoppingCart.addProduct(t_product);
        this.products.add(t_product);
    }

    private EditText getLookupCodeEditText()
    {
        return (EditText) this.findViewById(R.id.edit_text_shopping_cart_lookup_code);
    }

    private ListView getShoppingCartListView()
    {
        return (ListView) this.findViewById(R.id.list_view_shopping_cart);
    }

    public void completeTransaction(View view)
    {
        this.displayFunctionalityNotAvailableDialog();
    }

    private void displayFunctionalityNotAvailableDialog()
    {
        new AlertDialog.Builder(this).
                setMessage(R.string.alert_dialog_functionality_not_available).
                setPositiveButton(
                        R.string.button_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }
                ).
                create().
                show();
    }

    private boolean validateInput() {
        boolean validInput = true;

        if (StringUtils.isBlank(this.getLookupCodeEditText().getText().toString()))
        {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_first_name);
            this.getLookupCodeEditText().requestFocus();
            validInput = false;
        }

        return validInput;
    }

    private void displayValidationAlert(int stringId)
    {
        new AlertDialog.Builder(this)
                .setMessage(stringId)
                .create()
                .show();
    }

    //private ListView getProductsListView() {
        //return (ListView) this.findViewById(R.id.list_view_products);
    //}
}


