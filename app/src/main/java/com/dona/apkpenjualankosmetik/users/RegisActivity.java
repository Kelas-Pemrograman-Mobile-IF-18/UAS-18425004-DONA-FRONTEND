package com.dona.apkpenjualankosmetik.users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dona.apkpenjualankosmetik.R;
import com.dona.apkpenjualankosmetik.server.BaseURL;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisActivity extends AppCompatActivity {

    Button btnLogin;
    NoboButton btnRegis;
    EditText edtUserName, edtNama, edtGmail, edtNoHP, edtPassword;
    ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtGmail = (EditText) findViewById((R.id.edtGmail));
        edtNoHP = (EditText) findViewById(R.id.edtNoHP);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegis = (NoboButton) findViewById(R.id.RegisBtn);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //untuk merubah data di design menjadi string
                String strUserName = edtUserName.getText().toString();
                String strNama = edtNama.getText().toString();
                String strGmail = edtGmail.getText().toString();
                String strNoHP = edtNoHP.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strUserName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong nih", Toast.LENGTH_LONG).show();
                } else if (strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong nih", Toast.LENGTH_LONG).show();
                } else if (strNama.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Lengkap Tidak Boleh Kosong nih", Toast.LENGTH_LONG).show();
                } else if (strGmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Gmail Tidak Boleh Kosong nih", Toast.LENGTH_LONG).show();
                } else if (strNoHP.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nomor Telepon Tidak Boleh Kosong nih", Toast.LENGTH_LONG).show();
                } else {
                    registrasi(strUserName, strNama, strGmail, strNoHP, strPassword);
                }
            }
        });
    }
        public void registrasi(String userName, String namaLengkap, String email, String noTelp, String password){
            //params untuk mencocokan data didatabase dan data di frontendnya
            // Post params to be sent to the server
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("userName", userName);
            params.put("namaLengkap", namaLengkap);
            params.put("email", email);
            params.put("noTelp", noTelp);
            params.put("role", "2");
            params.put("password", password);


            pDialog.setMessage("Mohon Tunggu nih .....");
            showDialog();

            // params dirubah kebentuk json
            try {
                JsonObjectRequest req = new JsonObjectRequest(BaseURL.register, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                hideDialog();
                                //input data ke database
                                try {
                                    String strMsg = response.getString("msg");
                                    boolean status =response.getBoolean("error");
                                    if(status == false){
                                        Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisActivity.this, LoginActivity.class);
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
                        hideDialog();
                    }
                });
                mRequestQueue.add(req);
            } catch (Exception e){
                Log.d("siniii", e.getLocalizedMessage());
            }

        }

//        @Override
        public void BtnLogin(){
            Intent i = new Intent(RegisActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
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