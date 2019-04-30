package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.R;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeClassification;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class MockTransactionActivity extends AppCompatActivity
{
    ArrayList<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_transaction);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    public void saveButtonOnClick(View view)
    {
        /*
        if (!this.validateInput())
        {
            return;
        }
        */
        (new SaveTransactionTask()).execute();
    }


    private class SaveTransactionTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected void onPreExecute() {
            this.savingProductAlert.show();
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            Product product1 = new Product();
            product1.setLookupCode(getProductOneText().getText().toString());
            product1.setCount(Integer.parseInt(getProductOneQuantityText().getText().toString()));

            Product product2 = new Product();
            product2.setLookupCode(getProductTwoText().getText().toString());
            product2.setCount(Integer.parseInt(getProductTwoQuantityText().getText().toString()));

            productList.add(product1);
            productList.add(product2);
            return true;
            /*
            if (productList.size() > 0)
            {
                return (new ProductService()).productTransaction(productList);
            }
            else
            {
                return false;
            }
            */
        }

        @Override
        protected void onPostExecute(Boolean successfulSave) {
            String message;

            savingProductAlert.dismiss();

            if (successfulSave) {
                message = getString(R.string.alert_dialog_product_save_success);
            } else {
                message = getString(R.string.alert_dialog_product_save_failure);
            }

            new AlertDialog.Builder(MockTransactionActivity.this).
                    setMessage(message).
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

        private AlertDialog savingProductAlert;

        private SaveTransactionTask() {
            this.savingProductAlert = new AlertDialog.Builder(MockTransactionActivity.this).
                    setMessage(R.string.alert_dialog_product_save).
                    create();
        }
    }

    private EditText getProductOneText() {
        return (EditText) this.findViewById(R.id.edit_text_product1);
    }

    private EditText getProductOneQuantityText() {
        return (EditText) this.findViewById(R.id.edit_text_product1_quantity);
    }

    private EditText getProductTwoText() {
        return (EditText) this.findViewById(R.id.edit_text_product2);
    }

    private EditText getProductTwoQuantityText() {
        return (EditText) this.findViewById(R.id.edit_text_product2_quantity);
    }
/*
    private boolean validateInput()
    {
        boolean validInput = true;

        if (StringUtils.isBlank(this.getProductOneText().getText().toString()))
        {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_first_name);
            this.getProductOneText().requestFocus();
            validInput = false;
        }
        if (validInput && StringUtils.isBlank(this.getProductOneQuantityText().getText().toString()))
        {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_last_name);
            this.getProductOneQuantityText().requestFocus();
            validInput = false;
        }
        if (validInput && StringUtils.isBlank(this.getProductTwoText().getText().toString()))
        {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_password);
            this.getProductTwoText().requestFocus();
            validInput = false;
        }
        if (validInput && StringUtils.isBlank(this.getProductTwoQuantityText().getText().toString()))
        {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_password_invalid);
            this.getProductTwoQuantityText().requestFocus();
            validInput = false;
        }

        return validInput;
    }
*/
    private void displayValidationAlert(int stringId)
    {
        new AlertDialog.Builder(this)
                .setMessage(stringId)
                .create()
                .show();
    }

    private ProductTransition productTransition;
}
