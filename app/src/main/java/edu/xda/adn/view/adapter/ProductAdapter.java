package edu.xda.adn.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.fragment.ProductFragment;
import edu.xda.adn.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    private List<Product> productList;
    private Context context;

    public ProductAdapter() {
    }

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
  }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        Product product = productList.get(position);

        // Hiển thị thông tin sản phẩm trong ViewHolder
//        holder.imageViewProduct.setImageURI(product.getImage());
        holder.textViewProductName.setText(product.getTenDoUong());
        holder.textViewProductPrice.setText("Price: $" + product.getGia());

        // Xử lý sự kiện nút sửa và xóa ở đây (có thể sử dụng Interface để giao tiếp với Activity hoặc Fragment)
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nút sửa được nhấn
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nút xóa được nhấn
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList != null & productList.size() > 0) {
            return productList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduct;
        TextView textViewProductName;
        TextView textViewProductPrice;
        Button buttonEdit;
        Button buttonDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

    }



    public Context getContext() {
        return context;
    }

    public void filter(List<Product> products){
        productList = products;
        notifyDataSetChanged();
    }
}
