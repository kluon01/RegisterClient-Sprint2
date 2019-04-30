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
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.api.ShoppingCart;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.adapters.ShoppingCartListAdapter;



public class ShoppingCartActivity extends AppCompatActivity
{
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<ShoppingCart> shoppingcartproducts = new ArrayList<>();
    private ListView listView;
    private ShoppingCartListAdapter shoppingCartAdapter;
    private TextView TotalView;
    private double runningTotal = 0.0;

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
        this.listView = getShoppingCartListView();
        this.shoppingCartAdapter = new ShoppingCartListAdapter(this, this.products);
        this.listView.setAdapter(this.shoppingCartAdapter);

        TotalView = (TextView) findViewById(R.id.shopping_cart_total);

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
        if(!validateInput())
        {
            return;
        }
        TotalView.setText(Double.toString(runningTotal));
        String lookup_code = this.getLookupCodeEditText().getText().toString();
        RetrieveProductTask retrieveProductTask = new RetrieveProductTask(lookup_code);
        retrieveProductTask.execute();
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
        //this.displayFunctionalityNotAvailableDialog();
        TransactionTask transaction = new TransactionTask();
        transaction.execute();
        this.displayValidationAlert(R.string.alert_dialog_transaction_complete);
        this.startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

    private void addProductToShoppingCart(Product checkproduct)
    {
        boolean found = false;
        for (int x = 0; x < shoppingcartproducts.size(); x++)
        {
            if(shoppingcartproducts.get(x).getProduct().getLookupCode().equals(checkproduct.getLookupCode()))
            {
                shoppingcartproducts.get(x).quantity++;
                found = true;
            }
        }

        runningTotal += checkproduct.getPrice();

        if(!found)
        {
            shoppingcartproducts.add(new ShoppingCart(checkproduct));
        }
        products.add(checkproduct);
    }

    private class TransactionTask extends AsyncTask<Void, Void, ApiResponse<Product>>
    {
        private AlertDialog TransactionAlert;
        private TransactionTask()
        {
            this.TransactionAlert = new AlertDialog.Builder(ShoppingCartActivity.this).
                    setMessage("Failed").
                    create();
        }

        @Override
        protected ApiResponse<Product> doInBackground(Void... params)
        {
            ApiResponse<Product> apiResponse = new ApiResponse<>();

            for (int x = 0; x < shoppingcartproducts.size(); x++)
            {
                ShoppingCart updated_shopproduct = shoppingcartproducts.get(x);
                Product updated_product = shoppingcartproducts.get(x).getProduct();

                updated_product.setCount(updated_product.getCount() - updated_shopproduct.quantity);

                apiResponse = (new ProductService()).updateProduct(updated_product);

                if (apiResponse.isValidResponse())
                {
                    Log.d("Sending", "Success"); // For debugging
                }

                if (!(apiResponse.isValidResponse()))
                {
                    Log.d("Sending", "Failure");
                }
            }

            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Product> apiResponse)
        {
            this.TransactionAlert.dismiss();

            if (!apiResponse.isValidResponse())
            {
                new AlertDialog.Builder(ShoppingCartActivity.this).
                        setMessage("Transaction Failed").
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
                //shoppingcartproducts.add(new ShoppingCart(t_product, findDuplicates(t_product)));
                //products.add(t_product);
                addProductToShoppingCart(t_product);
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

