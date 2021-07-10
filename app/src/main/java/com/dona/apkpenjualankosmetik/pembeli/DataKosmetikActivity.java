package com.dona.apkpenjualankosmetik.pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.dona.apkpenjualankosmetik.adapter.AdapterKosmetik;
import com.dona.apkpenjualankosmetik.model.ModelKosmetik;
import com.dona.apkpenjualankosmetik.server.BaseURL;
import com.dona.apkpenjualankosmetik.session.PrefSetting;
import com.dona.apkpenjualankosmetik.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataKosmetikActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterKosmetik adapter;
    ListView list;

    ArrayList<ModelKosmetik> newsList = new ArrayList<ModelKosmetik>();
    private RequestQueue mRequestQueue;

//    FloatingActionButton floatingCart;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kosmetik2);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(DataKosmetikActivity.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Kosmetik");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);

        newsList.clear();
        adapter = new AdapterKosmetik(DataKosmetikActivity.this, newsList);
        list.setAdapter(adapter);
        getAllKosmetik();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DataKosmetikActivity.this, HomePembeli.class);
        startActivity(i);
        finish();
    }

    private void getAllKosmetik() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataKosmetik, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data kosmetik = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelKosmetik Kosmetik = new ModelKosmetik();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaKos = jsonObject.getString("namaKosmetik");
                                    final String kodeKos = jsonObject.getString("kodeKosmetik");
                                    final String deskripsiKos = jsonObject.getString("deskripsiKosmetik");
                                    final String jenisKos = jsonObject.getString("jenisKosmetik");
                                    final String hargaKos = jsonObject.getString("hargaKosmetik");
                                    final String gambar = jsonObject.getString("gambar");
                                    Kosmetik.setKodeKosmetik(kodeKos);
                                    Kosmetik.setNamaKosmetik(namaKos);
                                    Kosmetik.setDeskripsiKosmetik(deskripsiKos);
                                    Kosmetik.setJenisKosmetik(jenisKos);
                                    Kosmetik.setHargaKosmetik(hargaKos);
                                    Kosmetik.setGambar(gambar);
                                    Kosmetik.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(DataKosmetikActivity.this, DetailKosmetik.class);
                                            a.putExtra("kodeKos", newsList.get(position).getKodeKosmetik());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaKos", newsList.get(position).getNamaKosmetik());
                                            a.putExtra("deskripsiKos", newsList.get(position).getDeskripsiKosmetik());
                                            a.putExtra("jenisKos", newsList.get(position).getJenisKosmetik());
                                            a.putExtra("hargaKos", newsList.get(position).getHargaKosmetik());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(Kosmetik);
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