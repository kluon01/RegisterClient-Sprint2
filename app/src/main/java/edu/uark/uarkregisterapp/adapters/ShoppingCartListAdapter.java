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
import edu.uark.uarkregisterapp.models.api.Product;

public class ShoppingCartListAdapter extends ArrayAdapter<Product> {
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            view = inflater.inflate(R.layout.list_view_shopping_cart, parent, false);
        }

        Product product = this.getItem(position);
        if (product != null) {
            TextView lookupCodeTextView = (TextView) view.findViewById(R.id.list_view_shopping_cart_lookup_code);
            if (lookupCodeTextView != null) {
                lookupCodeTextView.setText(product.getLookupCode());
            }

            TextView priceTextView = (TextView) view.findViewById(R.id.list_view_shopping_cart_price);
            if (priceTextView != null) {
                priceTextView.setText(String.format(Locale.getDefault(), "%d", product.getCost()));
            }

            TextView quantityTextView = (TextView) view.findViewById(R.id.list_view_shopping_cart_quantity);
            if (quantityTextView != null) {
                quantityTextView.setText(String.format(Locale.getDefault(), "%d", product.getSold()));
            }
        }

        return view;
    }

    public ShoppingCartListAdapter(Context context, List<Product> products) {
        super(context, R.layout.list_view_shopping_cart, products);
    }
}