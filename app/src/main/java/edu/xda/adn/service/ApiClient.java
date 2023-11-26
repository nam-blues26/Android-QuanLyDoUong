package edu.xda.adn.service;

import edu.xda.adn.view.MyString;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = MyString.LOCALHOST; // Điều chỉnh URL của bạn

    private static ApiClient instance = null;
    private ApiService myApi;

    private ApiClient() {
        OkHttpClient.Builder okhttpClientBuilde = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilde.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilde.build())
                .build();
        myApi = retrofit.create(ApiService.class);
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiService getMyApi() {
        return myApi;
    }
}
