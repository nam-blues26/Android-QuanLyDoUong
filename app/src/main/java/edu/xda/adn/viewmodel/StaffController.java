package edu.xda.adn.viewmodel;

import android.util.Log;

import java.util.List;

import edu.xda.adn.model.Product;
import edu.xda.adn.model.Staff;
import edu.xda.adn.service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffController {

    public interface StaffCallback {
        void onSuccessList(List<Staff> staff);
        void onFailure(String errorMessage);
    }
    public void getStaffs(StaffController.StaffCallback callback) {
        Call<List<Staff>> call = ApiClient.getInstance().getMyApi().getStaffs();
        call.enqueue(new Callback<List<Staff>>() {
            @Override
            public void onResponse(Call<List<Staff>> call, Response<List<Staff>> response) {

                List<Staff> staffList = response.body();
                callback.onSuccessList(staffList);

            }

            @Override
            public void onFailure(Call<List<Staff>> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra");
                Log.e("error", t.getMessage());
            }
        });
    }

    public void addStaff(Staff staff){
        Call<Boolean> call = ApiClient.getInstance().getMyApi().addProduct(staff);
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

    public void updateStaff(int maNV, Staff staff){
        Call<Boolean> call = ApiClient.getInstance().getMyApi().updateStaff(maNV,staff);
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

    public void deleteStaff(int maNV){
        Call<Boolean> call = ApiClient.getInstance().getMyApi().deleteStaff(maNV);
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
