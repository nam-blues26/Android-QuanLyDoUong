package edu.xda.adn.view.adapter;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.model.Bill;
import edu.xda.adn.model.BillDetail;
import edu.xda.adn.model.Category;
import edu.xda.adn.model.Statistical;
import edu.xda.adn.viewmodel.BillController;
import edu.xda.adn.viewmodel.CategoryController;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {
    private List<Bill> BillList;
    private Context context;

    private BillController billController = new BillController();
    private RecyclerView recyclerView;
    private BillDetailAdapter billDetailAdapter;

    public BillAdapter() {
    }

    public BillAdapter(Context context, List<Bill> BillList) {
        this.context = context;
        this.BillList = BillList;
    }

    @NonNull
    @Override
    public BillAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bill_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.MyViewHolder holder, int position) {
        Bill bill = BillList.get(position);

        holder.tvCreateDateBill.setText(bill.getNgayLap().toString());

        holder.tvTotalPriceBill.setText("" + bill.getTongGia());
        holder.tvNoteBill.setText("Số bàn: " + bill.getSoBan());

        holder.billItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddStaff(bill);
            }
        });
    }

    private void openDialogAddStaff(Bill bill) {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_bill_detail);
            final TextView tvNoteBill = dialog.findViewById(R.id.tvNoteBill);
            final TextView tvTotalBill = dialog.findViewById(R.id.tvTotalBill);
            tvNoteBill.setText(bill.getSoBan()+" bàn đã đặt");
            tvTotalBill.setText(""+bill.getTongGia());
            recyclerView = dialog.findViewById(R.id.rcBillDetailList);
            getBillDetailsFromDatabase(bill.getMaHDB());

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
            Log.e("error-open", e.getMessage());
            e.printStackTrace();
        }
    }

    private void getBillDetailsFromDatabase(int maHDB){

        billController.getBillDetails(maHDB,new BillController.BillDetailCallback() {
            @Override
            public void onSuccessList(List<BillDetail> billDetails) {
                Log.i("list-bill",billDetails.toString());
                billDetailAdapter = new BillDetailAdapter(getContext(),billDetails);
                recyclerView.setAdapter(billDetailAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(billDetailAdapter.getContext(), 1, GridLayoutManager.VERTICAL, true));
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("error", errorMessage);
            }
        });
    }



    @Override
    public int getItemCount() {
        if (BillList != null & BillList.size() > 0) {
            return BillList.size();
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCreateDateBill;
        TextView tvTotalPriceBill;
        TextView tvNoteBill;
        View billItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreateDateBill = itemView.findViewById(R.id.tvCreateDateBill);
            tvTotalPriceBill = itemView.findViewById(R.id.tvTotalPriceBill);
            tvNoteBill = itemView.findViewById(R.id.tvNoteBill);
            billItem = itemView.findViewById(R.id.BillItem);
        }

    }


    public Context getContext() {
        return context;
    }

}
