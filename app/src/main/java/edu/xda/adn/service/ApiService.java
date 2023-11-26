package edu.xda.adn.service;

import java.util.List;

import edu.xda.adn.model.Product;
import edu.xda.adn.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login.php")
    Call<Boolean> login(@Body User user);

    @GET("danh-sach-do-uong.php") // Thay "products" bằng đường dẫn thực tế của API để lấy danh sách sản phẩm
    Call<List<Product>> getProducts();
}
