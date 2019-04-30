package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeClassification;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        this.employeeTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_employee));
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        this.getEmployeeWelcomeTextView().setText("Welcome " + this.employeeTransition.getFirstName() + " (" + this.employeeTransition.getEmployeeId() + ")!");
    }

    public void beginTransactionButtonOnClick(View view) {
        //this.displayFunctionalityNotAvailableDialog();
        this.startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
    }

    public void productSalesReportButtonOnClick(View view) {
        if(this.employeeTransition.getClassification() == EmployeeClassification.GENERAL_MANAGER || this.employeeTransition.getClassification() == EmployeeClassification.SHIFT_MANAGER) {
            this.startActivity(new Intent(getApplicationContext(), TopProductsActivity.class));
        }
        else
        {
            displayNoAccessDialog(R.string.alert_dialog_no_access_reports);
        }
    }

    public void cashierSalesReportButtonOnClick(View view) {
        if(this.employeeTransition.getClassification() == EmployeeClassification.GENERAL_MANAGER || this.employeeTransition.getClassification() == EmployeeClassification.SHIFT_MANAGER) {
            this.startActivity(new Intent(getApplicationContext(), TopEmployees.class));
        }
        else
        {
            displayNoAccessDialog(R.string.alert_dialog_no_access_reports);
        }
    }

    public void createEmployeeButtonOnClick(View view) {
        if(this.employeeTransition.getClassification() == EmployeeClassification.GENERAL_MANAGER)
        {
            this.startActivity(new Intent(getApplicationContext(), CreateEmployeeActivity.class));
        }
        else
        {
            displayNoAccessDialog(R.string.alert_dialog_no_access_create_employee);
        }
    }

    public void logOutButtonOnClick(View view) {
        this.startActivity(new Intent(getApplicationContext(), LandingActivity.class));
    }

    public void testButtonOnClick(View view) {
        this.startActivity(new Intent(getApplicationContext(), TestActivity.class));
    }

    private TextView getEmployeeWelcomeTextView() {
        return (TextView)this.findViewById(R.id.text_view_employee_welcome);
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

    private void displayNoAccessDialog(int stringID)
    {
        new AlertDialog.Builder(this).
                setMessage(stringID).
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

    private EmployeeTransition employeeTransition;
}
