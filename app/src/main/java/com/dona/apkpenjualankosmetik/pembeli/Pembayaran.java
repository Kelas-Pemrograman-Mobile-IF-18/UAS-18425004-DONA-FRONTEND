package com.dona.apkpenjualankosmetik.pembeli;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dona.apkpenjualankosmetik.R;
import com.dona.apkpenjualankosmetik.session.PrefSetting;

public class Pembayaran extends AppCompatActivity {

    EditText edtKodeKos, edtNamaKos, edtDeskripsiKos, edtJenisKos, edtHargaKos, edtJumlahKos, edtJumlahHarga;
    Button bayar;

    String strKodeKos, strNamaKos, strDeskripsiKos, strJenisKos, strHargaKos, strJumlahKos, strJumlahHarga, _id;


    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        mRequest = Volley.newRequestQueue(this);

        edtKodeKos = (EditText) findViewById(R.id.edtKodeKos);
        edtNamaKos = (EditText) findViewById(R.id.edtNamaKos);
        edtDeskripsiKos = (EditText) findViewById(R.id.edtDeskripsiKos);
        edtJenisKos = (EditText) findViewById(R.id.edtJenisKos);
        edtHargaKos = (EditText) findViewById(R.id.edtHargaKos);
        edtJumlahKos = (EditText) findViewById(R.id.edtJumlahKos);
        edtJumlahHarga = (EditText) findViewById(R.id.edtJumlahHarga);

        bayar = (Button) findViewById(R.id.bayar);

        Intent i = getIntent();
        strKodeKos = i.getStringExtra("kodeKos");
        strNamaKos = i.getStringExtra("namaKos");
        strDeskripsiKos = i.getStringExtra("deskripsiKos");
        strJenisKos = i.getStringExtra("jenisKos");
        strHargaKos = i.getStringExtra("hargaKos");
        strJumlahKos = i.getStringExtra("jumlahKos");
        strJumlahHarga = i.getStringExtra("jumlahHarga");
        _id = i.getStringExtra("_id");

        Log.d("inii", strHargaKos+" jum "+ strJumlahKos);

        String usernameUser = PrefSetting.userName;

        edtKodeKos.setText(strKodeKos);
        edtNamaKos.setText(strNamaKos);
        edtDeskripsiKos .setText(strDeskripsiKos);
        edtJenisKos.setText(strJenisKos);
        edtHargaKos.setText(strHargaKos);
        edtJumlahKos.setText(strJumlahKos);
        edtJumlahHarga.setText(strJumlahHarga);
}}