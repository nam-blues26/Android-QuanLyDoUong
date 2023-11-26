package edu.xda.adn.viewmodel;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.xda.adn.R;
import edu.xda.adn.model.Product;
import edu.xda.adn.view.activity.LoginActivity;
import edu.xda.adn.view.fragment.ProductFragment;

public class ProductController {

    public ProductController() {
    }



//    public void addProduct(String url, Product product) {
//        try {
//            Context context = ProductFragment.getProductAdapter().getContext();
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, e -> {
//                try {
////                    product.setId(Integer.valueOf(e));
//                    Toast.makeText(ProductFragment.getProductAdapter().getContext(), R.string.add_success_message, Toast.LENGTH_SHORT).show();
//                    LoginActivity.getProducts().add(product);
////                    ProductFragment.getProductAdapter().setArrayList(LoginActivity.getProducts());
//                    ProductFragment.getProductAdapter().notifyDataSetChanged();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }, e -> {
//                Toast.makeText(ProductFragment.getProductAdapter().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<>();
//                    try {
//                        params.put("name", product.getTenDoUong());
//                        params.put("price", String.valueOf(product.getGia()));
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    return params;
//                }
//            };
////            stringRequest.setRetryPolicy(
////                    new DefaultRetryPolicy(5000,
////                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
////            );
//            requestQueue.add(stringRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void updateProduct(String url, Product product, int position) {
//        try {
//            Context context = ProductFragment.getProductAdapter().getContext();
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, e -> {
//                try {
//                    Toast.makeText(ProductFragment.getProductAdapter().getContext(), R.string.update_success_message, Toast.LENGTH_SHORT).show();
////                    LoginActivity.getProducts().get(position).setName(product.getName());
////                    LoginActivity.getProducts().get(position).setPrice(product.getPrice());
//                    ProductFragment.getProductAdapter().notifyDataSetChanged();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }, e -> {
//                Toast.makeText(ProductFragment.getProductAdapter().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<>();
//                    try {
////                        params.put("id", String.valueOf(product.getId()));
////                        params.put("name", product.getName());
////                        params.put("price", String.valueOf(product.getPrice()));
//                        params.put("username", LoginActivity.USERNAME);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    return params;
//                }
//            };
////            stringRequest.setRetryPolicy(
////                    new DefaultRetryPolicy(5000,
////                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
////            );
//            requestQueue.add(stringRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteProduct(String url, ArrayList<Product> products) {
//        try {
//            Context context = ProductFragment.getProductAdapter().getContext();
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, e -> {
//                try {
//                    Toast.makeText(ProductFragment.getProductAdapter().getContext(), R.string.delete_success_message, Toast.LENGTH_SHORT).show();
//                    for (Product product : products) {
//                        LoginActivity.getProducts().remove(product);
//                    }
//                    ProductFragment.getProductAdapter().notifyDataSetChanged();
//                    ProductFragment.getSelectionList().clear();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }, e -> {
//                Toast.makeText(ProductFragment.getProductAdapter().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<>();
//                    try {
//                        String arrayListProduct = new Gson().toJson(products);
//                        params.put("arrayListProduct", arrayListProduct);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    return params;
//                }
//            };
////            stringRequest.setRetryPolicy(
////                    new DefaultRetryPolicy(5000,
////                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
////            );
//            requestQueue.add(stringRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
