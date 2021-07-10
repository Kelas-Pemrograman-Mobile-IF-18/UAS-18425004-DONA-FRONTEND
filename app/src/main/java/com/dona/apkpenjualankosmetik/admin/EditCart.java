package com.dona.apkpenjualankosmetik.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dona.apkpenjualankosmetik.R;
import com.dona.apkpenjualankosmetik.server.BaseURL;

import org.json.JSONException;
import org.json.JSONObject;

public class EditCart extends AppCompatActivity {

    EditText edtKodeKos, edtNamaKos, edtdeskripsiKos, edtJenisKos, edtHargaKos, edtJumlahKos, edtJumlahHarga, edtUserName;
    Button hapusCart;

    String strKodeKos, strNamaKos, strDeskripsiKos, strJenisKos, strHargaKos, strJumlahKos, strJumlahHarga, _id, strUserName;

    private RequestQueue mRequestQueue;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cart);

        mRequestQueue = Volley.newRequestQueue(this);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtKodeKos = (EditText) findViewById(R.id.edtKodeKos);
        edtNamaKos = (EditText) findViewById(R.id.edtNamaKos);
        edtdeskripsiKos = (EditText) findViewById(R.id.edtDeskripsiKos);
        edtJenisKos = (EditText) findViewById(R.id.edtJenisKos);
        edtHargaKos = (EditText) findViewById(R.id.edtHargaKos);
        edtJumlahKos = (EditText) findViewById(R.id.edtJumlahKos);
        edtJumlahHarga = (EditText) findViewById(R.id.edtJumlahHarga);


        hapusCart = (Button) findViewById(R.id.hapus);

        Intent i = getIntent();
        strUserName= i.getStringExtra("userName");
        strKodeKos = i.getStringExtra("kodeKos");
        strNamaKos = i.getStringExtra("namaKos");
        strDeskripsiKos = i.getStringExtra("deskripsiKos");
        strJenisKos = i.getStringExtra("jenisKos");
        strHargaKos = i.getStringExtra("hargaKos");
        strJumlahKos = i.getStringExtra("jumlahKos");
        strJumlahHarga = i.getStringExtra("jumlahHarga");
        _id = i.getStringExtra("_id");

        edtUserName.setText(strUserName);
        edtKodeKos.setText(strKodeKos);
        edtNamaKos.setText(strNamaKos);
        edtdeskripsiKos.setText(strDeskripsiKos);
        edtJenisKos.setText(strJenisKos);
        edtHargaKos.setText(strHargaKos);
        edtJumlahKos.setText(strJumlahKos);
        edtJumlahHarga.setText(strJumlahHarga);

        hapusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCart.this);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Yakin ingin menghapus Pesanan " + strUserName+" ?");

                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        hapusCart();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    public void hapusCart(){

        pDialog = new ProgressDialog(EditCart.this);
        pDialog.setMessage("Mohon Tunggu...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, BaseURL.hapusCart + _id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditCart.this, CartAdmin.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });
        mRequestQueue.add(req);
    }

    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}