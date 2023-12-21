package edu.xda.adn.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.model.Bill;
import edu.xda.adn.model.Category;
import edu.xda.adn.model.SearchRequest;
import edu.xda.adn.model.Statistical;
import edu.xda.adn.view.adapter.BillAdapter;
import edu.xda.adn.view.adapter.CategoryAdapter;
import edu.xda.adn.view.adapter.CategoryProductAdapter;
import edu.xda.adn.viewmodel.BillController;
import edu.xda.adn.viewmodel.CategoryController;

public class CategoryFragment extends Fragment {


    private RecyclerView recyclerView;
    private CategoryController categoryController = new CategoryController();

    private  CategoryAdapter categoryAdapter;

    private static Toolbar toolbar;

    private static FloatingActionButton btnAddCategory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        init(view);
        createEvent();
        return view;
    }

    private void init(View view) {
        btnAddCategory = view.findViewById(R.id.btnAddCategory);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_category);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            recyclerView = view.findViewById(R.id.rcCategoryList);
            getCategoryFromDatabase();
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void createEvent() {


        btnAddCategory.setOnClickListener(e->{
            openDialogAddCategory();
        });

    }

    private void openDialogAddCategory() {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_add_category);
            final EditText edCategory = dialog.findViewById(R.id.edAddCategory);

            Button btnAddNewProduct = dialog.findViewById(R.id.btnAddNewCategory);
            btnAddNewProduct.setOnClickListener(e -> {
                Category category = new Category();
                category.setTenLoai(edCategory.getText().toString());
                addCategoryFromDatabase(category);
                dialog.dismiss();
                getCategoryFromDatabase();

            });
            Button btnCancelDialog = dialog.findViewById(R.id.btnCancelDialogCategory);
            btnCancelDialog.setOnClickListener(e -> {
                dialog.dismiss();

            });
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume", "invoked");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("OnPause", "invoked");
    }
    // ==============================DATABASE========================
    private void getCategoryFromDatabase() {
        try {
            categoryController.getCategories(new CategoryController.CategoryCallback() {
                @Override
                public void onSuccess(List<Category> categoryList) {
                    categoryAdapter = new CategoryAdapter(getContext(),categoryList);
                    Log.i("listCategory", categoryList.toString());
                    recyclerView.setAdapter(categoryAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(categoryAdapter.getContext(), 1, GridLayoutManager.VERTICAL, true));
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e("errorCategory", errorMessage);
                }
            });
        }catch (Exception e){
            Log.e("error-get-bill", e.getMessage());
        }

    }

    private void addCategoryFromDatabase(Category category) {
        categoryController.addCategory(category);
    }


}
