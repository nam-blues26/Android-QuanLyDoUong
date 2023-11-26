package edu.xda.adn.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.xda.adn.R;
import edu.xda.adn.model.Invoice;
import edu.xda.adn.model.InvoiceDetail;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.adapter.InvoiceDetailInformationAdapter;
import edu.xda.adn.viewmodel.InvoiceDetailController;

public class InvoiceDetailInformationActivity extends AppCompatActivity {

    private TextView tvNote;

    private TextView tvTotalPrice;

    private RecyclerView recyclerView;

    private InvoiceDetailController invoiceDetailController = new InvoiceDetailController();

    private InvoiceDetailInformationAdapter invoiceDetailInformationAdapter;

    private ArrayList<InvoiceDetail> arrayListInvoiceDetail;

    private DecimalFormat formatter = new DecimalFormat(MyString.PRICE_FORMAT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_invoice);
            Intent intent = getIntent();
            Invoice inv = (Invoice) intent.getSerializableExtra(MyString.INVOICE_INTENT_FORMAT);
            if (inv != null && invoiceDetailController.getAllInvoiceDetails(inv.getId()) != null) {
                arrayListInvoiceDetail = invoiceDetailController.getAllInvoiceDetails(inv.getId());
            }else{
                arrayListInvoiceDetail = new ArrayList<>();
            }
            init();
            tvNote.setText(inv.getNote());
            invoiceDetailInformationAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(invoiceDetailInformationAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(invoiceDetailInformationAdapter.getContext(), 1, GridLayoutManager.VERTICAL, false));
        } catch (Exception e) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void init(){
        tvNote = findViewById(R.id.edNoteTemp);
        tvTotalPrice = findViewById(R.id.tvTotalTemp);
        recyclerView = findViewById(R.id.rcInvoiceDetailListTemp);
        invoiceDetailInformationAdapter = new InvoiceDetailInformationAdapter(arrayListInvoiceDetail, this);
        fillTotalPrice(invoiceDetailInformationAdapter.getArrayList());
    }

    private void fillTotalPrice(ArrayList<InvoiceDetail> invoiceDetails){
        double total = 0.0d;
        for(InvoiceDetail invoiceDetail : invoiceDetails){
            total += invoiceDetail.getQuantity() * invoiceDetail.getSinglePrice();
        }
        tvTotalPrice.setText(formatter.format(total));
    }
}
