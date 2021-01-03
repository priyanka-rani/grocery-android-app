package com.myapp.grocerli;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.myapp.grocerli.pref.PreferenceHelper;
import com.myapp.grocerli.ui.login.LoginActivity;
import com.myapp.grocerli.ui.main.MainActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    @Inject
    PreferenceHelper preferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SplashActivity.this,
                preferenceHelper.isLoggedIn()? MainActivity.class : LoginActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();

    }
}