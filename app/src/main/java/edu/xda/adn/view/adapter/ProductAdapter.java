package edu.xda.adn.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.activity.LoginActivity;
import edu.xda.adn.view.activity.MainActivity;
import edu.xda.adn.view.fragment.ProductFragment;
import edu.xda.adn.model.Product;
import edu.xda.adn.viewmodel.ProductController;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    private List<Product> productList;
    private Context context;

    private ProductController productController = new ProductController();

    public ProductAdapter() {
    }
    public ProductAdapter(Context context,List<Product> productList) {
        this.context = context;
        this.productList = productList;
  }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        Product product = productList.get(position);

        // Hiển thị thông tin sản phẩm trong ViewHolder
//        holder.imageViewProduct.setImageURI(product.getImage());
        holder.edProductName.setText(product.getTenDoUong());
        holder.edProductPrice.setText("" + product.getGia());

        // Xử lý sự kiện nút sửa và xóa ở đây (có thể sử dụng Interface để giao tiếp với Activity hoặc Fragment)
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Xử lý sự kiện khi nút sửa được nhấn
                Product newProduct = new Product();
                newProduct.setTenDoUong(holder.edProductName.getText().toString());
                newProduct.setGia(Integer.parseInt(holder.edProductPrice.getText().toString()));
                updateProductFromDatabase(product.getMaDoUong(), newProduct);
                openFragment(new ProductFragment());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProductFromDatabase(product.getMaDoUong());
                openFragment(new ProductFragment());
            }
        });

    }

    private void openFragment(Fragment fragment) {
        try{
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_product, fragment); // R.id.fragment_container là ID của container để đặt Fragment
            transaction.addToBackStack(null);
            transaction.commit();
        }catch (Exception e){
            Log.e("error-open", e.getMessage());
        }

    }
    private void deleteProductFromDatabase(int productId) {
        productController.deleteProduct(productId);
    }

    private void updateProductFromDatabase(int productId, Product product) {
        productController.updateProduct(productId, product);
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
        EditText edProductName;
        EditText edProductPrice;
        Button buttonEdit;
        Button buttonDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            edProductName = itemView.findViewById(R.id.edProductName);
            edProductPrice = itemView.findViewById(R.id.edProductPrice);
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
