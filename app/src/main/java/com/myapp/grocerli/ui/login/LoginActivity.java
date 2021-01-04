package com.myapp.grocerli.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.myapp.grocerli.R;
import com.myapp.grocerli.Utilities;
import com.myapp.grocerli.databinding.ActivityLoginBinding;
import com.myapp.grocerli.ui.main.MainActivity;
import com.myapp.grocerli.ui.signup.SignupActivity;
import com.myapp.grocerli.utils.AppExecutors;
import com.myapp.grocerli.workers.CartDatabaseWorker;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    @Inject
    AppExecutors appExecutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
        loginViewModel.profileLiveData.observe(this, data -> {
            if (data == null && loginViewModel.email.getValue().equals("test@gmail.com")) {
                appExecutors.diskIO().execute(() -> CartDatabaseWorker.Companion.insertProfile(LoginActivity.this));
            } else {
                if (data != null &&
                        Utilities.INSTANCE.decodeBase64(data.password).equals(loginViewModel.pass.getValue())) {
                    loginViewModel.saveLogin(data.id);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                } else {
                    new Handler().postDelayed(() -> {
                        if (data == null)
                            Snackbar.make(binding.getRoot(), R.string.invalid_email_pass, Snackbar.LENGTH_LONG).show();
                    }, 200);
                }
            }
        });
        binding.btSignUp.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

    }
}