package edu.xda.adn.viewmodel;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import edu.xda.adn.model.Invoice;
import edu.xda.adn.view.MyString;
import edu.xda.adn.view.activity.LoginActivity;

public class InvoiceController {

    public InvoiceController() {
    }

    public static void getAllInvoicesMain(String url) {
        try {
            JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url, null, e -> {
                for (int i = 0; i < e.length(); i++) {
                    try {
                        SimpleDateFormat formatDate = new SimpleDateFormat(MyString.DATETIME_FORMAT_YEAR_FIRST);
                        JSONObject jo = e.getJSONObject(i);
                        LoginActivity.getInvoicesFromDatabase().add(new Invoice(jo.getInt("id"),
                                formatDate.parse(jo.getString("createdate")),
                                jo.getString("note"),
                                0,
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
}
