package edu.xda.adn.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.xda.adn.R;
import edu.xda.adn.model.BillDetail;
import edu.xda.adn.viewmodel.BillController;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.MyViewHolder> {
    private List<BillDetail> billDetails;
    private Context context;

    private edu.xda.adn.viewmodel.BillController BillController = new BillController();

    public BillDetailAdapter() {
    }

    public BillDetailAdapter(Context context, List<BillDetail> billDetails) {
        this.context  = context;
        this.billDetails = billDetails;
    }

    @NonNull
    @Override
    public BillDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bill_detail_information, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BillDetail billDetail = billDetails.get(position);

        holder.tvProductNameBillDetailInformation.setText(billDetail.getTenDoUong());
        holder.tvPriceBillDetailInformation.setText(""+billDetail.getGiaDoUong());
        holder.tvQuantityBillDetailInformation.setText(""+billDetail.getSoLuong());
        holder.tvSizeBillDetailInformation.setText(billDetail.getSize());
    }

    @Override
    public int getItemCount() {
        if (billDetails != null & billDetails.size() > 0) {
            return billDetails.size();
        } else {
            return 0;
        }
    }

    public Context getContext() {
        return context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductNameBillDetailInformation;
        TextView tvPriceBillDetailInformation;
        TextView tvSizeBillDetailInformation;
        TextView tvQuantityBillDetailInformation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductNameBillDetailInformation = itemView.findViewById(R.id.tvProductNameBillDetailInformation);
            tvPriceBillDetailInformation = itemView.findViewById(R.id.tvPriceBillDetailInformation);
            tvSizeBillDetailInformation = itemView.findViewById(R.id.tvSizeBillDetailInformation);
            tvQuantityBillDetailInformation = itemView.findViewById(R.id.tvQuantityBillDetailInformation);
        }

    }

}
