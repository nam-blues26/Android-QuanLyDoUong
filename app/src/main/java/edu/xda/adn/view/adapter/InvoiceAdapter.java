package edu.xda.adn.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.xda.adn.R;
import edu.xda.adn.model.Invoice;
import edu.xda.adn.view.MyString;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.MyViewHolder>{
    private ArrayList<Invoice> arrayList;
    private Context context;
    private ClickListener clickListener;

    public InvoiceAdapter() {
    }

    public InvoiceAdapter(ArrayList<Invoice> arrayList, Context context, ClickListener clickListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public InvoiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_invoice_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Invoice invoice = arrayList.get(position);
        DateFormat dateFormat = new SimpleDateFormat(MyString.DATETIME_FORMAT_DAY_FIRST);
        DecimalFormat formatter = new DecimalFormat(MyString.PRICE_FORMAT);
        holder.tvCreateDate.setText(dateFormat.format(invoice.getCreateDate()));
        holder.tvTotalPrice.setText(formatter.format(invoice.getTotalPrice()));
        holder.tvNote.setText(invoice.getNote());
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
        private TextView tvCreateDate, tvNote, tvTotalPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreateDate = itemView.findViewById(R.id.tvCreateDateInvoiceItem);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPriceInvoiceItem);
            tvNote = itemView.findViewById(R.id.tvNoteInvoiceItem);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
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
    }

    public ArrayList<Invoice> getArrayList() {
        return arrayList;
    }

    public Context getContext() {
        return context;
    }

    public void filter(ArrayList<Invoice> invoices){
        arrayList = invoices;
        notifyDataSetChanged();
    }
}
