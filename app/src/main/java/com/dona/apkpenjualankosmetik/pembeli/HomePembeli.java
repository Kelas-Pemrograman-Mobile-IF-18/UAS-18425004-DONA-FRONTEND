package com.dona.apkpenjualankosmetik.pembeli;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.dona.apkpenjualankosmetik.R;
import com.dona.apkpenjualankosmetik.session.PrefSetting;
import com.dona.apkpenjualankosmetik.session.SessionManager;
import com.dona.apkpenjualankosmetik.users.LoginActivity;

public class HomePembeli extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView cardExit, cardDataKos, cardProfile, Card_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(HomePembeli.this);

        prefSetting.isLogin(session, prefs);


        cardExit = (CardView) findViewById(R.id.cardExit);
        cardDataKos = (CardView) findViewById(R.id.cardDataKos);
        cardProfile = (CardView) findViewById(R.id.cardProfile);
        Card_cart = (CardView) findViewById(R.id.Card_Cart);

        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeli.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardDataKos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeli.this, DataKosmetikActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeli.this, ProfilePembeli.class);
                startActivity(i);
                finish();
            }
        });

        Card_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeli.this, Cart.class);
                startActivity(i);
                finish();
            }
        });
    }
}