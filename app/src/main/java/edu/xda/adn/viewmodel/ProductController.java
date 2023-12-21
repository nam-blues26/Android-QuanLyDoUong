package edu.xda.adn.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import edu.xda.adn.R;
import edu.xda.adn.model.Category;
import edu.xda.adn.model.Product;
import edu.xda.adn.model.SearchRequest;
import edu.xda.adn.service.ApiClient;
import edu.xda.adn.view.activity.LoginActivity;
import edu.xda.adn.view.adapter.ProductAdapter;
import edu.xda.adn.view.fragment.ProductFragment;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductController {

    public interface ProductCallback {
        void onSuccessList(List<Product> productList);
        void onFailure(String errorMessage);
    }
    public void getProducts(ProductCallback callback) {
        Call<List<Product>> call = ApiClient.getInstance().getMyApi().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                    List<Product> productList = response.body();
                    callback.onSuccessList(productList);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra");
                Log.e("error", t.getMessage());
            }
        });
    }

    public void searchProducts(SearchRequest key, ProductCallback callback) {
        Call<List<Product>> call = ApiClient.getInstance().getMyApi().searchProduct(key);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.i("SearchProduct", response.body().toString());
                List<Product> productList = response.body();
                callback.onSuccessList(productList);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra");
                Log.e("errorSearchProduct", t.getMessage());
            }
        });
    }

    public void addProduct(Product product){
        Call<Boolean> call = ApiClient.getInstance().getMyApi().addProduct(product);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void updateProduct(int productId, Product product){
        Call<Boolean> call = ApiClient.getInstance().getMyApi().updateProduct(productId,product);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void deleteProduct(int productId){
        Call<Boolean> call = ApiClient.getInstance().getMyApi().deleteProduct(productId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

}
