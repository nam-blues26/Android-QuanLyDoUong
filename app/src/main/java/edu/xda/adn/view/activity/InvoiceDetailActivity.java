package edu.xda.adn.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.xda.adn.R;
import edu.xda.adn.model.Invoice;
import edu.xda.adn.model.InvoiceDetail;
import edu.xda.adn.model.Product;
import edu.xda.adn.view.MyAlertDialog;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.ProductComboBoxAdapter;
import edu.xda.adn.view.adapter.InvoiceDetailAdapter;
import edu.xda.adn.view.fragment.BillFragment;
import edu.xda.adn.viewmodel.InvoiceDetailController;

public class InvoiceDetailActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;

    private EditText edNote;

    private TextView tvTotalPrice;

    private Button btnPayment, btnNewInvoice, btnBackToHome;

    private ImageView btnDeleteAllDetail;

    private InvoiceDetailController invoiceDetailController = new InvoiceDetailController();

    private InvoiceDetailAdapter invoiceDetailAdapter;

    private RecyclerView recyclerView;

    private ArrayList<InvoiceDetail> arrayListInvoiceDetail;

    private static RequestQueue sentDataWebService;

    private DecimalFormat formatter = new DecimalFormat(MyString.PRICE_FORMAT);

    private boolean incomplete = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_invoice);
            Intent intent = getIntent();
            Invoice inv = (Invoice) intent.getSerializableExtra(MyString.INVOICE_INTENT_FORMAT);
            if (inv != null && invoiceDetailController.getAllInvoiceDetails(inv.getId()) != null) {
                arrayListInvoiceDetail = invoiceDetailController.getAllInvoiceDetails(inv.getId());
            } else {
                arrayListInvoiceDetail = new ArrayList<>();
            }
            init();
            createEvent();
            invoiceDetailAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(invoiceDetailAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(invoiceDetailAdapter.getContext(), 1, GridLayoutManager.VERTICAL, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        edNote = findViewById(R.id.edNote);
        tvTotalPrice = findViewById(R.id.tvTotal);
        btnPayment = findViewById(R.id.btnPayment);
        recyclerView = findViewById(R.id.rcInvoiceDetailList);
        autoCompleteTextView = findViewById(R.id.tvSearch_product);
        btnNewInvoice = findViewById(R.id.btnNewInvoice);
        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnDeleteAllDetail = findViewById(R.id.btnDeleteAllDetail);
        btnBackToHome.setVisibility(View.INVISIBLE);
        btnNewInvoice.setVisibility(View.INVISIBLE);
        ProductComboBoxAdapter productComboBoxAdapter = new ProductComboBoxAdapter(this, R.layout.layout_custom_combobox, LoginActivity.getProducts());
        autoCompleteTextView.setAdapter(productComboBoxAdapter);
        autoCompleteTextView.setThreshold(1);
        sentDataWebService = Volley.newRequestQueue(this);
        tvTotalPrice.setText(R.string.value_setup);
    }

    private void createEvent() {

        btnPayment.setOnClickListener(e -> {
            if (invoiceDetailAdapter.getArrayList().size() <= 0) {
                Toast.makeText(this, R.string.invoice_have_not_product, Toast.LENGTH_SHORT).show();
                return;
            }
            Invoice invoice = new Invoice();
            invoice.setNote(edNote.getText().toString());
            invoice.setCreateDate(new Date());
            invoice.setUsername(LoginActivity.USERNAME);
            invoice.setTotalPrice(0);
            invoiceDetailController.addInvoiceDetails(MyString.URL_INSERT_INVOICE_DETAIL, invoiceDetailAdapter, invoiceDetailAdapter.getArrayList(), invoice);
            btnPayment.setVisibility(View.INVISIBLE);
            btnDeleteAllDetail.setEnabled(false);
            btnBackToHome.setVisibility(View.VISIBLE);
            btnNewInvoice.setVisibility(View.VISIBLE);
            setInvoiceState(false);
        });

        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            Product product = (Product) adapterView.getAdapter().getItem(i);
            openDialogAddInvoiceDetail(product);
        });

        btnDeleteAllDetail.setOnClickListener(e -> {
            MyAlertDialog.showAlertDialog(
                    this,
                    MyString.CONFIRM_TITLE,
                    MyString.DELETE_CONFIRM_MESSAGE,
                    MyString.DELETE_NAME_POSITIVE_BUTTON,
                    MyString.DELETE_NAME_NEGATIVE_BUTTON,
                    (dialog, which) -> {
                        invoiceDetailAdapter.getArrayList().clear();
                        invoiceDetailAdapter.notifyDataSetChanged();
                        tvTotalPrice.setText(R.string.value_setup);
                    },
                    (dialog, which) -> {

                    }
            );
        });

        btnNewInvoice.setOnClickListener(e -> {
            edNote.setText("");
            tvTotalPrice.setText(R.string.value_setup);
            btnDeleteAllDetail.setEnabled(true);
            invoiceDetailAdapter.getArrayList().clear();
            invoiceDetailAdapter.notifyDataSetChanged();
            btnPayment.setVisibility(View.VISIBLE);
            btnBackToHome.setVisibility(View.INVISIBLE);
            btnNewInvoice.setVisibility(View.INVISIBLE);
            setInvoiceState(true);
        });

        btnBackToHome.setOnClickListener(e -> {
            Intent intent = new Intent(InvoiceDetailActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        invoiceDetailAdapter = new InvoiceDetailAdapter(arrayListInvoiceDetail, this, new InvoiceDetailAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (incomplete) {
                    openDialogUpdateInvoiceDetail(invoiceDetailAdapter.getArrayList().get(position), position);
                }
            }

            @Override
            public void onLongItemClick(int position, View v) {

            }

            @Override
            public void onImageViewClick(int position, View v) {
                if(incomplete){
                    invoiceDetailAdapter.getArrayList().remove(position);
                    invoiceDetailAdapter.notifyDataSetChanged();
                    fillTotalPrice(invoiceDetailAdapter.getArrayList());
                }
            }
        });
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
        BillFragment.getInvoiceAdapter().notifyDataSetChanged();
        arrayListInvoiceDetail.clear();
        this.finish();
    }

    private void openDialogAddInvoiceDetail(Product product) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_bill_add_qty_product);
            final TextView tvNameProduct = dialog.findViewById(R.id.tvNameProductDialog);
            final EditText edSinglePrice = dialog.findViewById(R.id.edSinglePrice);
            final EditText edQuantityProduct = dialog.findViewById(R.id.edQuantityProduct);

            tvNameProduct.setText(product.getTenDoUong());
            edSinglePrice.setText(String.valueOf(product.getGia()));

            Button btn_AddInvoiceDetail = dialog.findViewById(R.id.btn_AddInvoiceDetail);
            btn_AddInvoiceDetail.setOnClickListener(e -> {
                if (!checkRegularExpression(MyString.VALIDATE_PRICE_FORMAT, edSinglePrice.getText().toString().trim())) {
                    Toast.makeText(this, R.string.validate_price, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkRegularExpression(MyString.VALIDATE_QUANTITY_FORMAT, edQuantityProduct.getText().toString().trim())) {
                    Toast.makeText(this, R.string.validate_quantity, Toast.LENGTH_SHORT).show();
                    return;
                }
                double priceProduct = Double.valueOf(edSinglePrice.getText().toString().trim());
                int quantityProduct = Integer.valueOf(edQuantityProduct.getText().toString().trim());
                InvoiceDetail invoiceDetail = new InvoiceDetail();
                invoiceDetail.setProduct(product);
                invoiceDetail.setSinglePrice(priceProduct);
                invoiceDetail.setQuantity(quantityProduct);
                invoiceDetail.setUsername(LoginActivity.USERNAME);
                invoiceDetailAdapter.getArrayList().add(invoiceDetail);
                invoiceDetailAdapter.notifyDataSetChanged();
                fillTotalPrice(invoiceDetailAdapter.getArrayList());
                dialog.dismiss();
                autoCompleteTextView.setText("");
            });

            Button btn_CancleInvoiceDetail = dialog.findViewById(R.id.btn_CancleInvoiceDetail);
            btn_CancleInvoiceDetail.setOnClickListener(e -> {
                dialog.dismiss();
            });
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

    private void openDialogUpdateInvoiceDetail(InvoiceDetail invoiceDetail, int position) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_bill_update_qty_product);
            final EditText edSinglePrice = dialog.findViewById(R.id.edSinglePriceUpdate);
            final EditText edQuantityProduct = dialog.findViewById(R.id.edQuantityProductUpdate);
            final Button btn_UpdateInvoiceDetail = dialog.findViewById(R.id.btn_UpdateInvoiceDetailUpdate);
            final Button btn_CancleInvoiceDetail = dialog.findViewById(R.id.btn_CancleInvoiceDetailUpdate);
            edQuantityProduct.setText(String.valueOf(invoiceDetail.getQuantity()));
            edSinglePrice.setText(String.valueOf(invoiceDetail.getSinglePrice()));

            btn_UpdateInvoiceDetail.setOnClickListener(e -> {
                if (!checkRegularExpression(MyString.VALIDATE_PRICE_FORMAT, edSinglePrice.getText().toString().trim())) {
                    Toast.makeText(this, R.string.validate_price, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkRegularExpression(MyString.VALIDATE_QUANTITY_FORMAT, edQuantityProduct.getText().toString().trim())) {
                    Toast.makeText(this, R.string.validate_quantity, Toast.LENGTH_SHORT).show();
                    return;
                }
                double priceProduct = Double.valueOf(edSinglePrice.getText().toString().trim());
                int quantityProduct = Integer.valueOf(edQuantityProduct.getText().toString().trim());
                invoiceDetailAdapter.getArrayList().get(position).setSinglePrice(priceProduct);
                invoiceDetailAdapter.getArrayList().get(position).setQuantity(quantityProduct);
                invoiceDetailAdapter.notifyDataSetChanged();
                fillTotalPrice(invoiceDetailAdapter.getArrayList());
                dialog.dismiss();
            });
            btn_CancleInvoiceDetail.setOnClickListener(e -> {
                dialog.dismiss();
            });
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillTotalPrice(ArrayList<InvoiceDetail> invoiceDetails) {
        double total = 0.0d;
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            total += invoiceDetail.getQuantity() * invoiceDetail.getSinglePrice();
        }
        tvTotalPrice.setText(formatter.format(total));
    }

    private boolean checkRegularExpression(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static RequestQueue getSentDataWebService() {
        return sentDataWebService;
    }

    /*
    If invoice is incomplete, set state variable is true
    If invoice is complete, set state variable is false
     */
    private void setInvoiceState(boolean state) {
        incomplete = state;
        edNote.setEnabled(incomplete);
        autoCompleteTextView.setEnabled(incomplete);
        btnDeleteAllDetail.setEnabled(incomplete);
    }
}
