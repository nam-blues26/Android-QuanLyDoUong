package edu.xda.adn.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.model.Product;

public class ProductComboBoxAdapter extends ArrayAdapter<Product> {
    private List<Product> productList;

    public ProductComboBoxAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        productList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_combobox, parent, false);
            }
            TextView tvProductName = convertView.findViewById(R.id.tv_product_name);
            Product product = getItem(position);
            tvProductName.setText(product.getTenDoUong());
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                try {
                    List<Product> products = new ArrayList<>();
                    if (charSequence == null || charSequence.length() == 0) {
                        products.addAll(productList);
                    } else {
                        String filter = charSequence.toString().toLowerCase().trim();
                        for (Product product : productList) {
                            if (product.getTenDoUong().toLowerCase().contains(filter)) {
                                products.add(product);
                            }
                        }
                    }
                    filterResults.values = products;
                    filterResults.count = products.size();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                clear();
                addAll((List<Product>) filterResults.values);
                notifyDataSetInvalidated();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Product) resultValue).getTenDoUong();
            }
        };
    }
}
