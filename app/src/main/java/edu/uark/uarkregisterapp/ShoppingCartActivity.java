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

import java.util.ArrayList;
import java.util.List;

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;

public class ShoppingCartActivity extends AppCompatActivity
{
    private List<Product> products;
    private ProductListAdapter productListAdapter;
    private EmployeeTransition employeeTransition;
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

    //private ListView getProductsListView() {
        //return (ListView) this.findViewById(R.id.list_view_products);
    //}
}
