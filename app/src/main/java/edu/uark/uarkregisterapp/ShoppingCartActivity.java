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



public class ShoppingCartActivity extends AppCompatActivity
{
    private List<Product> products;
    private ProductListAdapter productListAdapter;
    private EmployeeTransition employeeTransition;
    private ProductService productService = new ProductService();
    private ShoppingCart shoppingCart = new ShoppingCart();
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
        if(!validateInput())
        {
            return;
        }
        String lookup_code = this.getLookupCodeEditText().getText().toString();
        ApiResponse<Product> product = (new ProductService().getProductByLookupCode(lookup_code));

    }

    private EditText getLookupCodeEditText()
    {
        return (EditText) this.findViewById(R.id.edit_text_shopping_cart_lookup_code);
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



    // count is a placeholder for how many of each item was sold
// employee_sales is a placeholder for the number of items sold by an Employee
// new_count is a placeholder for how many of each item was sold + DB Number
// new_employee_sales is a placeholder for the number of items sold by an Employee + database number



 /*   private class SaveDatabaseTask extends AsyncTask<Void, Void, Boolean>
    {
        int count = 0;
        int employee_sales = 0;
        int new_count = 0;
        int new_employee_sales= 0;


        protected void onPreExecute ()
    {
        this.savingDatabaseAlert.show();
    }

        protected void updateInfo ()
        {
            Product product_DB = (new Product()).
                    setID(productTransition.getId()).
                    setCount(productTransition.getCount());

            new_count = product_DB.count + count;

            Employee employee_DB = (new Employee()).
                    setEmployeeId(employeeTransition.getEmployeeId()).
                    setSales(employeeTransition.getEmployeeSales());

            new_employee_sales = employee_DB.sales + employee_sales;


        }

        @Override
        protected Boolean doInBackground(Void... params)
    {
        updateInfo();
        Product product = (new Product()).
                setId(productTransition.getId()).
                setCount(Integer.parseInt(new_count.getText().toString()));

        ApiResponse<Product> apiResponse1 = (
                (product.getId().equals(new UUID(0, 0)))
                        ? (new ProductService()).createProduct(product)
                        : (new ProductService()).updateProduct(product)
        );

        Employee employee = (new Employee()).
                setEmployeeId(employeeTransition.getEmployeeId()).
                setSales(Integer.parseInt(new_employee_sales.getText().toString()));

        ApiResponse<Employee> apiResponse2 = (
                (employee.getId().equals(new UUID(0, 0)))
                        ? (new EmployeeService()).createEmployee(employee)
                        : (new EmployeeService()).updateEmployee(employee)
        );

        if (apiResponse1.isValidResponse()) {
            productTransition.setCount(apiResponse.getData().getCount());
        }

        return apiResponse1.isValidResponse();

        if (apiResponse2.isValidResponse()) {
            employeeTransition.setEmployeeSales(apiResponse.getData().getEmployeeSales());
        }

        return apiResponse2.isValidResponse();

    }
        @Override
        protected void onPostExecute(Boolean successfulSave)
        {
            String message1;
            String message2;

            savingProductAlert.dismiss();
            savingEmployeeAlert.dismiss();


            if (successfulSave)
            {
                message1 = getString(R.string.alert_dialog_product_save_success);
                message2 = getString(R.string.alert_dialog_employee_save_success);

            }
            else
            {
                message1 = getString(R.string.alert_dialog_product_save_failure);
                message2 = getString(R.string.alert_dialog_employee_save_failure);

            }

            new AlertDialog.Builder(ShoppingCartActivity.this).
                    setMessage(message1).
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

            new AlertDialog.Builder(ShoppingCartActivity.this).
                    setMessage(message2).
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
        private AlertDialog savingEmployeeAlert;


        private SaveProductTask()
        {
            this.savingProductAlert = new AlertDialog.Builder(ShoppingCartActivity.this).
                    setMessage(R.string.alert_dialog_product_save).
                    create();
        }

        private SaveEmployeeTask()
        {
            this.savingEmployeeAlert = new AlertDialog.Builder(ShoppingCartActivity.this).
                    setMessage(R.string.alert_dialog_employee_save).
                    create();
        }

    }


    private ProductTransition productTransition;
*/
}


