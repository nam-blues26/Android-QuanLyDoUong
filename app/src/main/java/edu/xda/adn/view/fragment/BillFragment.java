package edu.xda.adn.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.xda.adn.R;
import edu.xda.adn.model.Invoice;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.activity.InvoiceDetailActivity;
import edu.xda.adn.view.activity.InvoiceDetailInformationActivity;
import edu.xda.adn.view.activity.LoginActivity;
import edu.xda.adn.view.adapter.InvoiceAdapter;
import edu.xda.adn.viewmodel.InvoiceController;

public class BillFragment extends Fragment {

    private EditText edSearchInvoice;

    private FloatingActionButton btnAddInvoice;

    private RecyclerView recyclerView;

    private static InvoiceAdapter invoiceAdapter;

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        try {
            edSearchInvoice = view.findViewById(R.id.edSearchInvoice);
            edSearchInvoice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    filter(editable.toString());
                }
            });
            btnAddInvoice = view.findViewById(R.id.btnAddInvoice);
            btnAddInvoice.setOnClickListener(e -> {
                Intent intent = new Intent(getActivity(), InvoiceDetailActivity.class);
                getActivity().startActivity(intent);
            });
            toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.title_bill);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            recyclerView = view.findViewById(R.id.rcInvoiceList);
            invoiceAdapter = new InvoiceAdapter(LoginActivity.getInvoices(), getActivity(), new InvoiceAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Invoice invoice = invoiceAdapter.getArrayList().get(position);
                    Intent intent = new Intent(getActivity(), InvoiceDetailInformationActivity.class);
                    intent.putExtra(MyString.INVOICE_INTENT_FORMAT, invoice);
                    getActivity().startActivity(intent);
                }

                @Override
                public void onLongItemClick(int position, View v) {

                }
            });
            invoiceAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(invoiceAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(invoiceAdapter.getContext(), 1, GridLayoutManager.VERTICAL, true));
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

    public static ArrayList<Invoice> getArrayList() {
        return LoginActivity.getInvoices();
    }

    public static InvoiceAdapter getInvoiceAdapter() {
        return invoiceAdapter;
    }

    private void filter(String text) {
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        for (Invoice invoice : LoginActivity.getInvoices()) {
            if (invoice.getNote().toLowerCase().contains(text.toLowerCase())) {
                invoiceList.add(invoice);
            }
        }
        invoiceAdapter.filter(invoiceList);
    }

}
