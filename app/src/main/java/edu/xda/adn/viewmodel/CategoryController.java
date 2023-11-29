package edu.xda.adn.viewmodel;

import android.util.Log;

import java.util.List;

import edu.xda.adn.model.Category;
import edu.xda.adn.service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryController {

    public interface CategoryCallback {
        void onSuccess(List<Category> categoryList);
        void onFailure(String errorMessage);
    }

    public void getCategories(CategoryCallback callback) {
        Call<List<Category>> call = ApiClient.getInstance().getMyApi().getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    List<Category> categoryList = response.body();
                    callback.onSuccess(categoryList);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra");
                Log.e("error", t.getMessage());
            }
        });
    }
}

