package edu.uark.uarkregisterapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import edu.uark.uarkregisterapp.R;
import edu.uark.uarkregisterapp.models.api.Employee;

public class EmployeeListAdapter extends ArrayAdapter<Employee> {
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            view = inflater.inflate(R.layout.list_view_top_employees, parent, false);
        }

        Employee employee = this.getItem(position);
        if (employee != null) {
            TextView lookupCodeTextView = (TextView) view.findViewById(R.id.list_view_top_employees_UUID);
            if (lookupCodeTextView != null) {
                lookupCodeTextView.setText(employee.getEmployeeId());
            }

            TextView salesTextView = (TextView) view.findViewById(R.id.list_view_top_employees_sales);
            if (salesTextView != null) {
                salesTextView.setText(String.format(Locale.getDefault(), "%d", employee.getSales()));
            }
        }

        return view;
    }

    public EmployeeListAdapter(Context context, List<Employee> employees) {
        super(context, R.layout.list_view_top_employees, employees);
    }
}
