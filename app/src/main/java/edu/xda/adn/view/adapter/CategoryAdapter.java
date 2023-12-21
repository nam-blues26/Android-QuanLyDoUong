package edu.xda.adn.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.model.Bill;
import edu.xda.adn.model.BillDetail;
import edu.xda.adn.model.Category;
import edu.xda.adn.model.Product;
import edu.xda.adn.view.activity.MainActivity;
import edu.xda.adn.view.fragment.CategoryFragment;
import edu.xda.adn.view.fragment.ProductFragment;
import edu.xda.adn.viewmodel.BillController;
import edu.xda.adn.viewmodel.CategoryController;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> categoryList;
    private Context context;

    private CategoryController categoryController = new CategoryController();


    public CategoryAdapter() {
    }

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category_item, parent, false);
        return new CategoryAdapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.edCategoryName.setText(category.getTenLoai().toString());

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Xử lý sự kiện khi nút sửa được nhấn
                Category newCategory = new Category();
                newCategory.setTenLoai(holder.edCategoryName.getText().toString());
                updateCategoryFromDatabase(category.getMaLoai(), newCategory);
                openFragment(new CategoryFragment());
            }
        });

        if (category.getSoLuong() >0){
            holder.buttonDelete.setEnabled(false);
            holder.buttonDelete.setVisibility(View.GONE);
        }else {
            holder.buttonDelete.setEnabled(true);
            holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCategoryFromDatabase(category.getMaLoai());
                    openFragment(new CategoryFragment());
                }
            });
        }



    }

    private void openFragment(Fragment fragment) {
        try{
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_category, fragment); // R.id.fragment_container là ID của container để đặt Fragment
            transaction.addToBackStack(null);
            transaction.commit();
        }catch (Exception e){
            Log.e("error-open", e.getMessage());
        }
    }

    private void deleteCategoryFromDatabase(int categoryId) {
        categoryController.deleteCategory(categoryId);
    }

    private void updateCategoryFromDatabase(int categoryId, Category category) {
        categoryController.updateCategory(categoryId, category);
    }


    @Override
    public int getItemCount() {
        if (categoryList != null & categoryList.size() > 0) {
            return categoryList.size();
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText edCategoryName;
        Button buttonEdit;
        Button buttonDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            edCategoryName = itemView.findViewById(R.id.edCategoryName);
            buttonEdit = itemView.findViewById(R.id.buttonEditCategory);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteCategory);
        }

    }


    public Context getContext() {
        return context;
    }

}
