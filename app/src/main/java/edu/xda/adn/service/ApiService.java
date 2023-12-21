package edu.xda.adn.service;

import java.util.List;

import edu.xda.adn.model.Bill;
import edu.xda.adn.model.BillDetail;
import edu.xda.adn.model.Category;
import edu.xda.adn.model.Product;
import edu.xda.adn.model.SearchRequest;
import edu.xda.adn.model.Staff;
import edu.xda.adn.model.Statistical;
import edu.xda.adn.model.User;
import okhttp3.RequestBody;
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

    @POST("them-loai.php")
    Call<Boolean> addCategory(@Body Category category);
    @POST("them-do-uong.php")
    Call<Boolean> addProduct(@Body Product product);
    @DELETE("xoa-do-uong.php/{productId}")
    Call<Boolean> deleteProduct(@Path("productId") int productId);

    @POST("tim-kiem-do-uong.php")
    Call<List<Product>> searchProduct(@Body SearchRequest key);

    @POST("tim-kiem-nhan-vien.php")
    Call<List<Staff>> searchStaff(@Body SearchRequest key);
    @POST("sua-do-uong.php/{productId}")
    Call<Boolean> updateProduct(@Path("productId") int productId,@Body Product product);
    @POST("sua-loai.php/{categoryId}")
    Call<Boolean> updateCategory(@Path("categoryId") int categoryId,@Body Category category);

    @DELETE("xoa-loai.php/{categoryId}")
    Call<Boolean> deleteCategory(@Path("categoryId") int categoryId);

    @POST("them-nhan-vien.php")
    Call<Boolean> addProduct(@Body Staff staff);
    @GET("danh-sach-nhan-vien.php")
    Call<List<Staff>> getStaffs();

    @DELETE("xoa-nhan-vien.php/{maNV}")
    Call<Boolean> deleteStaff(@Path("maNV") int maNV);

    @POST("sua-nhan-vien.php/{maNV}")
    Call<Boolean> updateStaff(@Path("maNV") int maNV,@Body Staff staff);

    @GET("danh-sach-hoa-don.php")
    Call<List<Bill>> getBills();

    @GET("chi-tiet-hoa-don.php/{maHDB}")
    Call<List<BillDetail>> getBillDetails(@Path("maHDB") int maHDB);

    @GET("tong-hoa-don.php")
    Call<Statistical> getStatistical();

}
