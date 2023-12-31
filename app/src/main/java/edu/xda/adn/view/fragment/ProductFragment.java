package edu.xda.adn.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.xda.adn.R;
import edu.xda.adn.model.Category;
import edu.xda.adn.model.Product;
import edu.xda.adn.model.SearchRequest;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.adapter.CategoryProductAdapter;
import edu.xda.adn.view.adapter.ProductAdapter;
import edu.xda.adn.viewmodel.CategoryController;
import edu.xda.adn.viewmodel.ProductController;

public class ProductFragment extends Fragment   {

    private EditText edSearchProduct;
    private ImageView ivsearchProduct;

    private ProductController productController = new ProductController();
    private CategoryController categoryController = new CategoryController();

    private RecyclerView recyclerView;

    private static ProductAdapter productAdapter;

    private static FloatingActionButton btnAddProduct;

    private static boolean isDeleteState = false;

    private static Toolbar toolbar;


    private Spinner spinnerCategory;
    private CategoryProductAdapter categoryAdapter;

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
        ivsearchProduct=view.findViewById(R.id.ivsearchProduct);
        edSearchProduct = view.findViewById(R.id.edSearchProduct);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_product);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createEvent() {

//        edSearchProduct.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        ivsearchProduct.setOnClickListener(e->{
            SearchRequest searchRequest = new SearchRequest(edSearchProduct.getText().toString());
            getProductsSearchFromDatabase(searchRequest);
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
            getProductsFromDatabase();


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

    private void openDialogAddProduct() {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_add_product);
            final EditText edNameProduct = dialog.findViewById(R.id.edNameProductAdd);
            final EditText edPriceProduct = dialog.findViewById(R.id.edPriceProductAdd);
            Product product = new Product();
            this.spinnerCategory = dialog.findViewById(R.id.spinnerCategory);
            // Bắt sự kiện cho spinnerCategory
            spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // Lấy đối tượng Category được chọn từ Adapter
                    Category selectedCategory = (Category) parentView.getItemAtPosition(position);

                    // Lấy Id của Category từ đối tượng này
                    int selectedCategoryId = selectedCategory.getMaLoai();
                    product.setMaLoai(selectedCategoryId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Xử lý khi không có mục nào được chọn
                    Log.e("SpinnerEvent", "No item selected");
                }
            });

            getCategoriesFromDatabase();

            Button btnAddNewProduct = dialog.findViewById(R.id.btnAddNewProduct);
            btnAddNewProduct.setOnClickListener(e -> {
                if (!checkRegularExpression(MyString.VALIDATE_PRICE_FORMAT, edPriceProduct.getText().toString().trim())) {
                    Toast.makeText(getActivity(), R.string.validate_price, Toast.LENGTH_SHORT).show();
                    return;
                }
                String nameProduct = edNameProduct.getText().toString();
                Integer priceProduct = Integer.valueOf(edPriceProduct.getText().toString().trim());

                product.setTenDoUong(nameProduct);
                product.setGia(priceProduct);
                product.setTrangThai(true);
                // Xử lý sự kiện khi một mục được chọn trong Spinner

                addProductFromDatabase(product);
//                productController.addProduct(MyString.URL_INSERT_PRODUCT, product);
                dialog.dismiss();
                //reload danh sách đồ uống
                getProductsFromDatabase();
            });
            Button btnCancelDialog = dialog.findViewById(R.id.btnCancelDialog);
            btnCancelDialog.setOnClickListener(e -> {
                dialog.dismiss();
                //reload danh sách đồ uống
                getProductsFromDatabase();
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

//    ============================== DATABASE ==================================
    private void getProductsFromDatabase() {
        productController.getProducts(new ProductController.ProductCallback() {
            @Override
            public void onSuccessList(List<Product> productList) {
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(productAdapter.getContext(), 1, GridLayoutManager.VERTICAL, true));
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("error", errorMessage);
            }
        });
    }

    private void getProductsSearchFromDatabase(SearchRequest key) {
        productController.searchProducts(key,new ProductController.ProductCallback() {
            @Override
            public void onSuccessList(List<Product> productList) {
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(productAdapter.getContext(), 1, GridLayoutManager.VERTICAL, true));
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("error", errorMessage);
            }
        });
    }

    private void addProductFromDatabase(Product product) {
        productController.addProduct(product);
    }

    private void getCategoriesFromDatabase(){
        categoryController = new CategoryController();
        categoryController.getCategories(new CategoryController.CategoryCallback() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                Log.e("categoryList", categoryList.toString());
                categoryAdapter = new CategoryProductAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryList);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("error", errorMessage);
            }
        });
    }

}
