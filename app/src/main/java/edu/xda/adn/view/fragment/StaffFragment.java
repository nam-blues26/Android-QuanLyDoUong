package edu.xda.adn.view.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.model.Category;
import edu.xda.adn.model.Product;
import edu.xda.adn.model.Staff;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.adapter.ProductAdapter;
import edu.xda.adn.view.adapter.StaffAdapter;
import edu.xda.adn.viewmodel.CategoryController;
import edu.xda.adn.viewmodel.ProductController;
import edu.xda.adn.viewmodel.StaffController;


public class StaffFragment extends Fragment {


    private EditText edSearchStaff;
    private static FloatingActionButton btnAddStaff;

    private StaffController staffController = new StaffController();
    private StaffAdapter staffAdapter;
    private RecyclerView recyclerView;
    private static Toolbar toolbar;



    public StaffFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        try {
            init(view);
            createEvent();
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return view;
    }

    private void init(View view) {
//        edSearchStaff = view.findViewById(R.id.edSearchStaff);
        btnAddStaff = view.findViewById(R.id.btnAddStaff);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_staff);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createEvent() {

//        edSearchProduct.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter(editable.toString());
//            }
//        });

        btnAddStaff.setOnClickListener(e -> {
            openDialogAddStaff();
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            recyclerView = view.findViewById(R.id.rcProductList);
            getStaffsFromDatabase();


        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void openDialogAddStaff() {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_add_staff);
            final EditText edNameStaff = dialog.findViewById(R.id.edAddFullNameStaff);
            final EditText edPhoneStaff = dialog.findViewById(R.id.edAddPhoneStaff);
            final EditText edAddressStaff = dialog.findViewById(R.id.edAddAddressStaff);
            final EditText edCardStaff = dialog.findViewById(R.id.edAddCardStaff);
            final RadioButton radio_male = dialog.findViewById(R.id.radio_male);
            final RadioButton radio_female = dialog.findViewById(R.id.radio_female);



            Button btnAddNewStaff = dialog.findViewById(R.id.btnAddNewStaff);
            btnAddNewStaff.setOnClickListener(e -> {
                Staff staff = new Staff();
                String fullName= edNameStaff.getText().toString();
                String phone= edPhoneStaff.getText().toString();
                String address= edAddressStaff.getText().toString();
                String card= edCardStaff.getText().toString();
                Boolean gender;
                if (radio_male.isChecked()){
                     gender = true;
                }else {
                     gender = false;
                }
                staff.setHoTen(fullName);
                staff.setSdt(phone);
                staff.setDiaChi(address);
                staff.setCccd(card);
                staff.setGioiTinh(gender);
                try {
                    addStaffFromDatabase(staff);
                    Toast.makeText(getActivity(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.dismiss();
                //reload data
                getStaffsFromDatabase();


            });
            Button btnCancelDialogStaff = dialog.findViewById(R.id.btnCancelDialogStaff);
            btnCancelDialogStaff.setOnClickListener(e -> {
                dialog.dismiss();
                //reload data
                getStaffsFromDatabase();
            });
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //===================================DATABASE==================
    private void getStaffsFromDatabase() {
        staffController.getStaffs(new StaffController.StaffCallback() {
            @Override
            public void onSuccessList(List<Staff> staffList) {
                staffAdapter = new StaffAdapter(getContext(),staffList);
                recyclerView.setAdapter(staffAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(staffAdapter.getContext(), 1, GridLayoutManager.VERTICAL, true));
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("error", errorMessage);
            }
        });
    }

    private void addStaffFromDatabase(Staff staff) {
        staffController.addStaff(staff);
    }
}