package com.dona.apkpenjualankosmetik.pembeli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.dona.apkpenjualankosmetik.session.PrefSetting;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailKosmetik extends AppCompatActivity {

    EditText edtKodeKos, edtNamaKos, edtDeskripsiKos, edtJenisKos, edtHargaKos, edtJumlahKos;
    ImageView imgGambar;
    Button btnTambahKekeranjang;

    String strKodeKos, strNamaKos, strDeskripsiKos, strJenisKos, strHargaKos, strGambar, _id;


    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kosmetik);

        mRequest = Volley.newRequestQueue(this);

        edtKodeKos = (EditText) findViewById(R.id.edtKodeKos);
        edtNamaKos = (EditText) findViewById(R.id.edtNamaKos);
        edtDeskripsiKos = (EditText) findViewById(R.id.edtDeskripsiKos);
        edtJenisKos = (EditText) findViewById(R.id.edtJenisKos);
        edtHargaKos = (EditText) findViewById(R.id.edtHargaKos);
        edtJumlahKos = (EditText) findViewById(R.id.edtJumlahkos);

        imgGambar = (ImageView) findViewById(R.id.gambar);

        btnTambahKekeranjang = (Button) findViewById(R.id.tambahKekeranjang);

        Intent i = getIntent();
        strKodeKos = i.getStringExtra("kodeKos");
        strNamaKos = i.getStringExtra("namaKos");
        strDeskripsiKos = i.getStringExtra("deskripsiKos");
        strJenisKos = i.getStringExtra("jenisKos");
        strHargaKos = i.getStringExtra("hargaKos");
        strGambar = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        final String usernameUser = PrefSetting.userName;

        edtKodeKos.setText(strKodeKos);
        edtNamaKos.setText(strNamaKos);
        edtDeskripsiKos.setText(strDeskripsiKos);
        edtJenisKos.setText(strJenisKos);
        edtHargaKos.setText(strHargaKos);
        Picasso.get().load(BaseURL.baseURL + "gambar/" + strGambar)
                .into(imgGambar);

        btnTambahKekeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datastrUserName = usernameUser;
                String datastrKodeKos = strKodeKos;
                String datastrNamaKos = strNamaKos;
                String datastrDeskripsiKos = strDeskripsiKos;
                String datastrJenisKos = strJenisKos;
                String datastrHargaKos = strHargaKos;
                String datastrJumlahKos = edtJumlahKos.getText().toString();
                if (datastrJumlahKos.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "jumlah tiket kosong nih", Toast.LENGTH_LONG).show();
                } else {
                    TambahKekeranjang(datastrUserName, datastrKodeKos, datastrNamaKos, datastrDeskripsiKos, datastrJenisKos, datastrHargaKos, datastrJumlahKos);
                }
            }
        });
    }

    public void TambahKekeranjang(String datastrUserName, String datastrKodeKos, String datastrNamaKos, String datastrDeskripsiKos, String datastrJenisKos, String datastrHargaKos, String datastrJumlahKos){
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", datastrUserName);
        params.put("kodeKosmetik", datastrKodeKos);
        params.put("namaKosmetik", datastrNamaKos);
        params.put("deskripsiKosmetik", datastrDeskripsiKos);
        params.put("jenisKosmetik", datastrJenisKos);
        params.put("hargaKosmetik", datastrHargaKos);
        params.put("jumlahKosmetik", datastrJumlahKos);

//        System.out.println("DATA"+datastrUserName);

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.insertCart, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // input data
                        System.out.println("DATA = "+response);

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status = jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), "berhasil tambah Kosmetik", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(DetailKosmetik.this, DataKosmetikActivity.class);
                                startActivity(i);
                                finish();
                            }else{
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
            }
        });
        mRequest.add(req);
    }
}