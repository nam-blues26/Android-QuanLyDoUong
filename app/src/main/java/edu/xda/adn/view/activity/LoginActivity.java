package edu.xda.adn.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import edu.xda.adn.R;
import edu.xda.adn.model.Product;
import edu.xda.adn.model.User;

import edu.xda.adn.viewmodel.UserController;


public class LoginActivity extends AppCompatActivity {


    public static String USERNAME;

    public static String PASSWORD;

    private UserController userController;
    private static RequestQueue requestQueueCallDataWebService;

    private static ArrayList<Product> products;


    private Button btnSignIn;
    private EditText edUserName, edPassword;

    //khai bao doi tuong dang ky callback
    private ActivityResultLauncher<Intent> callBackForLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        processEvents();

    }

    //khoi tao cac thanh phan
    public void init() {
        try {
            btnSignIn = findViewById(R.id.btnSignIn);
            edUserName = findViewById(R.id.edUserName);
            edPassword = findViewById(R.id.edPassword);
        } catch (Exception ex) {
            Log.e("Init", ex.getMessage());
        }
    }

    //xu ly su kien
    public void processEvents() {
        try {

            //dang ky su kien button login click
            btnSignIn.setOnClickListener(v -> {
                try {
                    String username, password;
                    username = edUserName.getText().toString();
                    password = edPassword.getText().toString();
                    Log.e("Username", username);
                    Log.e("Password", password);
                    if (validateData(username, password)) {
                        User user = new User(username, password);
                        // Khởi tạo UserController
                        userController = new UserController();
                        userController.LoginUser(user, new UserController.LoginCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công!!", Toast.LENGTH_SHORT).show();
                                Intent intent = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    intent = new Intent(LoginActivity.this, MainActivity.class);
                                }
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception ex) {
                    Log.e("btnSignIn Clicked: ", ex.getMessage());
                }

            });
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Co loi xay ra vui long thu lai sau.", Toast.LENGTH_SHORT).show();
            Log.e("Button login clicked: ", ex.getMessage());
        }
    }

    //kiem tra du lieu tren giao dien
    public boolean validateData(String username, String password) {
        if (username == null || username.equals("")) {
            edUserName.setError(getString(R.string.error_message));
            edUserName.requestFocus();
            return false;
        } else if (password == null || password.equals("")) {
            edPassword.setError(getString(R.string.error_password));
            edPassword.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
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

    public static RequestQueue getRequestQueueCallDataWebService() {
        return requestQueueCallDataWebService;
    }

}
