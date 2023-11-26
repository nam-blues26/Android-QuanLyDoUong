package edu.xda.adn.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.xda.adn.R;
import edu.xda.adn.model.Invoice;
import edu.xda.adn.model.InvoiceDetail;
import edu.xda.adn.model.InvoiceDetailDB;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.activity.InvoiceDetailActivity;
import edu.xda.adn.view.activity.LoginActivity;
import edu.xda.adn.view.adapter.InvoiceDetailAdapter;

public class InvoiceDetailController {

    public ArrayList<InvoiceDetail> getAllInvoiceDetails(int id_invoice) {
        ArrayList<InvoiceDetail> invoiceDetailArrayList = new ArrayList<>();
        try {
            for (InvoiceDetail invoiceDetail : LoginActivity.getInvoiceDetails()) {
                if (invoiceDetail.getInvoice().getId() == id_invoice) {
                    invoiceDetailArrayList.add(invoiceDetail);
                }
            }
            if (invoiceDetailArrayList == null) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceDetailArrayList;
    }

    public static void getAllInvoiceDetailsMain(String url) {
        try {
            JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url, null, e -> {
                for (int i = 0; i < e.length(); i++) {
                    try {
                        JSONObject jo = e.getJSONObject(i);
                        LoginActivity.getInvoiceDetailFromDatabase().add(new InvoiceDetailDB(jo.getInt("id_invoice"),
                                jo.getInt("id_product"),
                                jo.getDouble("single_price"),
                                jo.getInt("quantity"),
                                jo.getString("username")));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, e -> {
                Log.e("error", e.getMessage());
            });
//            jsObjRequest.setRetryPolicy(
//                    new DefaultRetryPolicy(5000,
//                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//            );
            LoginActivity.getRequestQueueCallDataWebService().add(jsObjRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addInvoiceDetails(String url, InvoiceDetailAdapter invoiceDetailAdapter, ArrayList<InvoiceDetail> invoiceDetails, Invoice invoice) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, e -> {
                invoice.setId(Integer.valueOf(e));
                double total = 0.0d;
                for (InvoiceDetail invdt : invoiceDetails) {
                    total += invdt.getQuantity() * invdt.getSinglePrice();
                }
                invoice.setTotalPrice(total);
                LoginActivity.getInvoices().add(invoice);
                for (InvoiceDetail invdt : invoiceDetails) {
                    invdt.setInvoice(invoice);
                    LoginActivity.getInvoiceDetails().add(invdt);
                }
                invoiceDetailAdapter.notifyDataSetChanged();
                Toast.makeText(invoiceDetailAdapter.getContext(), R.string.add_success_message, Toast.LENGTH_SHORT).show();
            }, e -> {
                Toast.makeText(invoiceDetailAdapter.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    try {
                        String invoiceDetailList = new Gson().toJson(invoiceDetails);
                        params.put("note", invoice.getNote());
                        params.put("create_date", dateFormat.format(invoice.getCreateDate()));
                        params.put("username", LoginActivity.USERNAME);
                        params.put("arrayList", invoiceDetailList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return params;
                }
            };
//            stringRequest.setRetryPolicy(
//                    new DefaultRetryPolicy(5000,
//                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//            );
            InvoiceDetailActivity.getSentDataWebService().add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
