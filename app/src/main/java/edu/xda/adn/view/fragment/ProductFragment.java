package edu.xda.adn.view.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.xda.adn.R;
import edu.xda.adn.model.Product;
import edu.xda.adn.service.ApiClient;
import edu.xda.adn.view.MyAlertDialog;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.activity.LoginActivity;
import edu.xda.adn.view.activity.MainActivity;
import edu.xda.adn.view.adapter.ProductAdapter;
import edu.xda.adn.viewmodel.ProductController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {

    private EditText edSearchProduct;

    private ProductController productController = new ProductController();

    private RecyclerView recyclerView;

    private static ProductAdapter productAdapter;

    private static FloatingActionButton btnAddProduct;

    private static boolean isDeleteState = false;

    private static Toolbar toolbar;

    private static ArrayList<Product> selectionList = new ArrayList<>();

    private static ImageView imDeleteProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        try {
            init(view);
            createEvent();
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return view;
    }

    private void init(View view) {
        imDeleteProduct = view.findViewById(R.id.imDeleteProduct);
        imDeleteProduct.setVisibility(View.INVISIBLE);
        edSearchProduct = view.findViewById(R.id.edSearchProduct);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_product);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createEvent() {

        edSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        btnAddProduct.setOnClickListener(e -> {
            openDialogAddProduct();
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            recyclerView = view.findViewById(R.id.rcProductList);
//  danh sách sản phẩm
            getProductsFromDatabase();

        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void getProductsFromDatabase() {
        // Tạo danh sách sản phẩm giả định
        Call<List<Product>> call = ApiClient.getInstance().getMyApi().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> object = response.body();

                productAdapter = new ProductAdapter(object);
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(productAdapter.getContext(), 1, GridLayoutManager.VERTICAL, true));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Có lỗi xảy ra.", Toast.LENGTH_SHORT).show();
                Log.e("error", t.getMessage());
            }
        });
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

    private void openDialogAddProduct() {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_add_product);
            final EditText edNameProduct = dialog.findViewById(R.id.edNameProductAdd);
            final EditText edPriceProduct = dialog.findViewById(R.id.edPriceProductAdd);
            Button btnAddNewProduct = dialog.findViewById(R.id.btnAddNewProduct);

            btnAddNewProduct.setOnClickListener(e -> {
                if (!checkRegularExpression(MyString.VALIDATE_PRICE_FORMAT, edPriceProduct.getText().toString().trim())) {
                    Toast.makeText(getActivity(), R.string.validate_price, Toast.LENGTH_SHORT).show();
                    return;
                }
                String nameProduct = edNameProduct.getText().toString().trim();
                Integer priceProduct = Integer.valueOf(edPriceProduct.getText().toString().trim());
                Product product = new Product();
                product.setTenDoUong(nameProduct);
                product.setGia(priceProduct);
                product.setTrangThai(true);
//                productController.addProduct(MyString.URL_INSERT_PRODUCT, product);
                dialog.dismiss();
            });
            Button btnCancelDialog = dialog.findViewById(R.id.btnCancelDialog);
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


    public static ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    private void filter(String text) {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product product : LoginActivity.getProducts()) {
            if (product.getTenDoUong().toLowerCase().contains(text.toLowerCase())) {
                productList.add(product);
            }
        }
        productAdapter.filter(productList);
    }

    private boolean checkRegularExpression(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /*
    If state is true, setup fragment delete state, opposite
     */
    public static void setDeleteProductState(boolean state) {
        isDeleteState = state;
        if (isDeleteState) {
            selectionList.clear();
            btnAddProduct.setVisibility(View.INVISIBLE);
            imDeleteProduct.setVisibility(View.VISIBLE);
            toolbar.setTitle(R.string.choose_product_title_toolbar);
        } else {
            btnAddProduct.setVisibility(View.VISIBLE);
            imDeleteProduct.setVisibility(View.INVISIBLE);
            toolbar.setTitle(R.string.title_product);
        }
    }

    public static boolean isIsDeleteState() {
        return isDeleteState;
    }

}
