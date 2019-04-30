package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import edu.uark.uarkregisterapp.models.transition.ProductTransition;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.api.ShoppingCart;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.adapters.ShoppingCartListAdapter;



public class ShoppingCartActivity extends AppCompatActivity
{
    private List<Product> products = new ArrayList<>();
    private ShoppingCart shoppingCart;
    private ListView listView;
    private ShoppingCartListAdapter shoppingCartAdapter;

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
        this.shoppingCart = new ShoppingCart();
        this.listView = getShoppingCartListView();
        this.shoppingCartAdapter = new ShoppingCartListAdapter(this, this.products);
        this.listView.setAdapter(this.shoppingCartAdapter);

        //this.products = new ArrayList<>();
        //this.productListAdapter = new ProductListAdapter(this, this.products);

        //this.employeeTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_employee));
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:  // Respond to the action bar's Up/Home button
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addByLookupCode(View view)
    {
        if(!validateInput())
        {
            return;
        }
        String lookup_code = this.getLookupCodeEditText().getText().toString();
        RetrieveProductTask retrieveProductTask = new RetrieveProductTask(lookup_code);
        retrieveProductTask.execute();
        //ApiResponse<Product> product = (new ProductService().getProductByLookupCode(lookup_code));
        //Product t_product = product.getData();
        //shoppingCart.addProduct(t_product);
        //this.products.add(t_product);
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

    private boolean validateInput()
    {
        boolean validInput = true;

        if (StringUtils.isBlank(this.getLookupCodeEditText().getText().toString()))
        {
            this.displayValidationAlert(R.string.alert_dialog_product_lookupcode_empty);
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

    
    //private ProductTransition productTransition;

}

    private class RetrieveProductTask extends AsyncTask<Void, Void, ApiResponse<Product>>
    {
        String lookupcode;
        private AlertDialog loadingProductAlert;

        private RetrieveProductTask(String lookupcode)
        {
            this.lookupcode = lookupcode;
            this.loadingProductAlert = new AlertDialog.Builder(ShoppingCartActivity.this).
                    setMessage(R.string.alert_dialog_product_loading).
                    create();
        }

        @Override
        protected ApiResponse<Product> doInBackground(Void... params)
        {
            ApiResponse<Product> apiResponse = (new ProductService()).getProductByLookupCode(lookupcode);

            if (apiResponse.isValidResponse())
            {
                //products.clear();
                Product t_product = apiResponse.getData();
                //Log.d("Product Lookup", "Success"); // For debugging
                shoppingCart.addProduct(t_product);
                products.add(t_product);
            }

            if (!(apiResponse.isValidResponse()))
                Log.d("Product Lookup", "Failure");

            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Product> apiResponse)
        {
            if (apiResponse.isValidResponse())
            {
                shoppingCartAdapter.notifyDataSetChanged();
            }

            this.loadingProductAlert.dismiss();

            if (!apiResponse.isValidResponse())
            {
                new AlertDialog.Builder(ShoppingCartActivity.this).
                        setMessage(R.string.cart_product_lookup_failure).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().
                        show();
            }
        }
    }
}

