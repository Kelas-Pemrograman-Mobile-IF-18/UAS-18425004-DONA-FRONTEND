package com.dona.apkpenjualankosmetik.pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dona.apkpenjualankosmetik.R;
import com.dona.apkpenjualankosmetik.adapter.AdapterCart;
import com.dona.apkpenjualankosmetik.model.ModelCart;
import com.dona.apkpenjualankosmetik.server.BaseURL;
import com.dona.apkpenjualankosmetik.session.PrefSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterCart adapter;
    ListView list;

    ArrayList<ModelCart> newsList = new ArrayList<ModelCart>();
    private RequestQueue mRequestQueue;

    String userNameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("Keranjang-Ku");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterCart(Cart.this, newsList);
        list.setAdapter(adapter);
        userNameUser = PrefSetting.userName;
        getAllCart(userNameUser);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Cart.this, HomePembeli.class);
        startActivity(i);
        finish();
    }

    private void getAllCart(String userNameUser) {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataCart + userNameUser, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("Keranjang-Ku = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelCart cart = new ModelCart();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaKos = jsonObject.getString("namaKosmetik");
                                    final String kodeKos = jsonObject.getString("kodeKosmetik");
                                    final String deskripsiKos = jsonObject.getString("deskripsiKosmetik");
                                    final String jenisKos = jsonObject.getString("jenisKosmetik");
                                    final String hargaKos = jsonObject.getString("hargaKosmetik");
                                    final String jumlahKos = jsonObject.getString("jumlahKosmetik");
                                    cart.setKodeKosmetik(kodeKos);
                                    cart.setNamaKosmetik(namaKos);
                                    cart.setDeskripsiKos(deskripsiKos);
                                    cart.setJenisKos(jenisKos);
                                    cart.setHargaKos(hargaKos);
                                    cart.setJumlahKos(jumlahKos);
                                    int a = Integer.parseInt(hargaKos);
                                    int b = Integer.parseInt(jumlahKos);
                                    int ab = a*b;
                                    cart.setJumlahHarga(String.valueOf(ab));
                                    cart.set_id(_id);
                                    Log.d("jenis", jenisKos);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(Cart.this, Pembayaran.class);
                                            a.putExtra("kodeKos", newsList.get(position).getKodeKosmetik());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaKos", newsList.get(position).getNamaKosmetik());
                                            a.putExtra("deskripsiKos", newsList.get(position).getDeskripsiKos());
                                            a.putExtra("jenisKos", newsList.get(position).getJenisKos());
                                            a.putExtra("hargaKos", newsList.get(position).getHargaKos());
                                            a.putExtra("jumlahKos", newsList.get(position).getJumlahKos());
                                            a.putExtra("jumlahHarga", newsList.get(position).getJumlahHarga());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(cart);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}