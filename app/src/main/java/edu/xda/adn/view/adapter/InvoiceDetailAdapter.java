package edu.xda.adn.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.xda.adn.R;
import edu.xda.adn.model.InvoiceDetail;
import edu.xda.adn.view.MyString;

public class InvoiceDetailAdapter extends RecyclerView.Adapter<InvoiceDetailAdapter.MyViewHolder>{
    private ArrayList<InvoiceDetail> arrayList;
    private Context context;
    private ClickListener clickListener;

    public InvoiceDetailAdapter() {
    }

    public InvoiceDetailAdapter(ArrayList<InvoiceDetail> arrayList, Context context, ClickListener clickListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public InvoiceDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_invoice_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat(MyString.PRICE_FORMAT);
        InvoiceDetail invoiceDetail = arrayList.get(position);
        holder.tvProductNameInvoiceDetail.setText(invoiceDetail.getProduct().getTenDoUong());
        holder.tvPriceInvoiceDetail.setText(formatter.format(invoiceDetail.getSinglePrice()));
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView tvProductNameInvoiceDetail, tvPriceInvoiceDetail, tvQuantityInvoiceDetail;
        private ImageView ivCheckDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductNameInvoiceDetail = itemView.findViewById(R.id.tvProductNameInvoiceDetail);
            tvQuantityInvoiceDetail = itemView.findViewById(R.id.tvQuantityInvoiceDetail);
            tvPriceInvoiceDetail = itemView.findViewById(R.id.tvPriceInvoiceDetail);
            ivCheckDelete = itemView.findViewById(R.id.ivCheckDelete);
            ivCheckDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view instanceof ImageView){
                clickListener.onImageViewClick(getAdapterPosition(),view);
            }else{
                clickListener.onItemClick(getAdapterPosition(), view);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onLongItemClick(getAdapterPosition(), view);
            return false;
        }
    }
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onLongItemClick(int position, View v);

        void onImageViewClick(int position, View v);
    }

    public ArrayList<InvoiceDetail> getArrayList() {
        return arrayList;
    }

    public Context getContext() {
        return context;
    }
}

