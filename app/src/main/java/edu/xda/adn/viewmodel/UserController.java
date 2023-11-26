package edu.xda.adn.viewmodel;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import edu.xda.adn.model.User;
import edu.xda.adn.service.ApiClient;
import edu.xda.adn.service.ApiService;

import edu.xda.adn.view.activity.LoginActivity;
import edu.xda.adn.view.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {

    public interface LoginCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
    public static void LoginUser(User user , LoginCallback loginCallback) {
        Call<Boolean> call = ApiClient.getInstance().getMyApi().login(user);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean object = response.body();
                Log.d("Response", "onResponse: " + object);
                if (object) {
                    loginCallback.onSuccess();
                } else {
                    loginCallback.onFailure("Thông tin đăng nhập sai, mời đăng nhập lại");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                loginCallback.onFailure("Có lỗi xảy ra.");
                Log.e("error", t.getMessage());
            }
        });

    }
}
