package edu.xda.adn.viewmodel;

import android.os.Build;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.xda.adn.model.Bill;
import edu.xda.adn.model.BillDetail;
import edu.xda.adn.model.Statistical;
import edu.xda.adn.service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillController {

    public interface BillCallback {
        void onSuccessList(List<Bill> Bill);

        void onFailure(String errorMessage);
    }

    public interface BillDetailCallback {
        void onSuccessList(List<BillDetail> Bill);

        void onFailure(String errorMessage);
    }

    public interface StatisticalCallback {
        void onSuccessStatistical(Statistical statistical);

        void onFailure(String errorMessage);
    }

    public void getBills(BillController.BillCallback callback) {
        Call<List<Bill>> call = ApiClient.getInstance().getMyApi().getBills();
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {

                List<Bill> BillList = response.body();
                callback.onSuccessList(BillList);

            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra");
                Log.e("error", t.getMessage());
            }
        });
    }

    public void getBillDetails(int maHDB, BillController.BillDetailCallback callback) {
        Call<List<BillDetail>> call = ApiClient.getInstance().getMyApi().getBillDetails(maHDB);
        call.enqueue(new Callback<List<BillDetail>>() {
            @Override
            public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {

                List<BillDetail> BillList = response.body();
                Log.i("billList", BillList.toString());
                callback.onSuccessList(BillList);

            }

            @Override
            public void onFailure(Call<List<BillDetail>> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra");
                Log.e("error", t.getMessage());
            }
        });
    }

    public void getStatistical(BillController.StatisticalCallback callback) {
        Call<Statistical> call = ApiClient.getInstance().getMyApi().getStatistical();
        call.enqueue(new Callback<Statistical>() {
            @Override
            public void onResponse(Call<Statistical> call, Response<Statistical> response) {

                Statistical statistical = response.body();
                callback.onSuccessStatistical(statistical);
            }

            @Override
            public void onFailure(Call<Statistical> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra");
                Log.e("error", t.getMessage());
            }
        });
    }
}
