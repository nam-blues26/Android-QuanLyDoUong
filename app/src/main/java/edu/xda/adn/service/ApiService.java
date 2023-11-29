package edu.xda.adn.service;

import java.util.List;

import edu.xda.adn.model.Category;
import edu.xda.adn.model.Product;
import edu.xda.adn.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("login.php")
    Call<Boolean> login(@Body User user);
    @GET("danh-sach-do-uong.php")
    Call<List<Product>> getProducts();
    @GET("danh-sach-loai.php")
    Call<List<Category>> getCategories();
    @POST("them-do-uong.php")
    Call<Boolean> addProduct(@Body Product product);
    @DELETE("xoa-do-uong.php/{productId}")
    Call<Boolean> deleteProduct(@Path("productId") int productId);

    @POST("sua-do-uong.php/{productId}")
    Call<Boolean> updateProduct(@Path("productId") int productId,@Body Product product);

}
