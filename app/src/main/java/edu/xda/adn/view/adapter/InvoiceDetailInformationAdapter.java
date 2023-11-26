package edu.xda.adn.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.xda.adn.R;
import edu.xda.adn.model.InvoiceDetail;

public class InvoiceDetailInformationAdapter extends RecyclerView.Adapter<InvoiceDetailInformationAdapter.MyViewHolder>{
    private ArrayList<InvoiceDetail> arrayList;
    private Context context;

    public InvoiceDetailInformationAdapter() {
    }

    public InvoiceDetailInformationAdapter(ArrayList<InvoiceDetail> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public InvoiceDetailInformationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_invoice_detail_information, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InvoiceDetail invoiceDetail = arrayList.get(position);
        holder.tvProductNameInvoiceDetail.setText(invoiceDetail.getProduct().getTenDoUong());
        holder.tvPriceInvoiceDetail.setText(String.valueOf(invoiceDetail.getSinglePrice()));
        holder.tvQuantityInvoiceDetail.setText(String.valueOf(invoiceDetail.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (arrayList != null & arrayList.size() > 0) {
            return arrayList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvProductNameInvoiceDetail, tvPriceInvoiceDetail, tvQuantityInvoiceDetail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductNameInvoiceDetail = itemView.findViewById(R.id.tvProductNameInvoiceDetailInformation);
            tvQuantityInvoiceDetail = itemView.findViewById(R.id.tvQuantityInvoiceDetailInformation);
            tvPriceInvoiceDetail = itemView.findViewById(R.id.tvPriceInvoiceDetailInformation);
        }
    }

    public ArrayList<InvoiceDetail> getArrayList() {
        return arrayList;
    }

    public Context getContext() {
        return context;
    }
}

