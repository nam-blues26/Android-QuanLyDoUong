package edu.xda.adn.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.model.Bill;
import edu.xda.adn.model.Statistical;
import edu.xda.adn.view.activity.MainActivity;
import edu.xda.adn.view.adapter.BillAdapter;
import edu.xda.adn.viewmodel.BillController;

public class BillFragment extends Fragment {

    private RecyclerView recyclerView;
    private BillController billController = new BillController();

    private static BillAdapter billAdapter;

    private static Toolbar toolbar;
    private TextView tv_total_bill;
    private TextView tv_total_revenue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_bill);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            recyclerView = view.findViewById(R.id.rcBillList);
            tv_total_bill = view.findViewById(R.id.tv_total_bill);
            tv_total_revenue = view.findViewById(R.id.tv_total_revenue);
            getBillsFromDatabase();
            getStatisticalFromDatabase();
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

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
// ==============================DATABASE========================
    private void getBillsFromDatabase() {
        try {
            billController.getBills(new BillController.BillCallback() {
                @Override
                public void onSuccessList(List<Bill>bills) {
                    billAdapter = new BillAdapter(getContext(),bills);
                    recyclerView.setAdapter(billAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(billAdapter.getContext(), 1, GridLayoutManager.VERTICAL, true));
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e("error", errorMessage);
                }
            });
        }catch (Exception e){
            Log.e("error-get-bill", e.getMessage());
        }

    }

    private void getStatisticalFromDatabase(){

        billController.getStatistical(new BillController.StatisticalCallback() {
            @Override
            public void onSuccessStatistical(Statistical statistical) {
                Log.i("statistical", statistical.toString());
                tv_total_bill.setText(""+statistical.getSoLuongHD());
                tv_total_revenue.setText(""+statistical.getDoanhThu());

            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("error", errorMessage);
            }
        });
    }


}
