package edu.xda.adn.view.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.model.Product;
import edu.xda.adn.model.Staff;
import edu.xda.adn.view.activity.MainActivity;
import edu.xda.adn.view.fragment.ProductFragment;
import edu.xda.adn.view.fragment.StaffFragment;
import edu.xda.adn.viewmodel.ProductController;
import edu.xda.adn.viewmodel.StaffController;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.MyViewHolder>{
    private List<Staff> staffList;
    private Context context;

    private StaffController staffController = new StaffController();

    public StaffAdapter() {
    }

    public StaffAdapter(Context context, List<Staff> staffList) {
        this.context = context;
        this.staffList = staffList;
  }

    @NonNull
    @Override
    public StaffAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_staff_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StaffAdapter.MyViewHolder holder, int position) {
        Staff staff = staffList.get(position);

        // Hiển thị thông tin sản phẩm trong ViewHolder
//        holder.imageViewProduct.setImageURI(product.getImage());
        holder.tvFullName.setText(staff.getHoTen());
        if (staff.isGioiTinh()==true){
            holder.tvGender.setText("Giới tính: Nam" );
        }else {
            holder.tvGender.setText("Giới tính: Nữ");

        }
        holder.tvPhoneNumber.setText(staff.getSdt());
        // Xử lý sự kiện nút sửa và xóa ở đây (có thể sử dụng Interface để giao tiếp với Activity hoặc Fragment)
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogUpdateStaff(staff);

            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStaffFromDatabase(staff.getMaNV());
                openFragment(new StaffFragment());
            }
        });

    }

    private void openFragment(Fragment fragment) {
        try{
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_staff, fragment); // R.id.fragment_container là ID của container để đặt Fragment
            transaction.addToBackStack(null);
            transaction.commit();
        }catch (Exception e){
            Log.e("error-open", e.getMessage());
        }

    }

    private void openDialogUpdateStaff(Staff staff) {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_add_staff);
            final EditText edNameStaff = dialog.findViewById(R.id.edAddFullNameStaff);
            final EditText edPhoneStaff = dialog.findViewById(R.id.edAddPhoneStaff);
            final EditText edAddressStaff = dialog.findViewById(R.id.edAddAddressStaff);
            final EditText edCardStaff = dialog.findViewById(R.id.edAddCardStaff);
            final RadioButton radio_male = dialog.findViewById(R.id.radio_male);
            final RadioButton radio_female = dialog.findViewById(R.id.radio_female);
            edNameStaff.setText(staff.getHoTen());
            edPhoneStaff.setText(staff.getSdt());
            edAddressStaff.setText(staff.getDiaChi());
            edCardStaff.setText(staff.getCccd());
            if (staff.isGioiTinh()){
                radio_male.setChecked(true);
            }else {
                radio_female.setChecked(true);
            }


            Button btnAddNewStaff = dialog.findViewById(R.id.btnAddNewStaff);
            btnAddNewStaff.setOnClickListener(e -> {
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
                    updateStaffFromDatabase(staff.getMaNV(), staff);
                    Toast.makeText(getContext(), "Sửa nhân viên "+staff.getHoTen()+ " thành công", Toast.LENGTH_SHORT).show();
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                dialog.dismiss();
                //reload danh sách đồ uống
                openFragment(new StaffFragment());
            });
            Button btnCancelDialogStaff = dialog.findViewById(R.id.btnCancelDialogStaff);
            btnCancelDialogStaff.setOnClickListener(e -> {
                dialog.dismiss();
                //reload danh sách đồ uống
//                getStaffsFromDatabase();
            });
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
//            Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void deleteStaffFromDatabase(int maNV) {
        staffController.deleteStaff(maNV);
    }

    private void updateStaffFromDatabase(int maNV, Staff staff) {
        staffController.updateStaff(maNV, staff);
    }


    @Override
    public int getItemCount() {
        if (staffList != null & staffList.size() > 0) {
            return staffList.size();
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName;
        TextView tvPhoneNumber;
        TextView tvGender;
        Button buttonEdit;
        Button buttonDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvGender = itemView.findViewById(R.id.tvGender);
            buttonEdit = itemView.findViewById(R.id.buttonEditStaff);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteStaff);
        }

    }



    public Context getContext() {
        return context;
    }

    public void filter(List<Staff> staffs){
        staffList = staffs;
        notifyDataSetChanged();
    }
}
