package com.myapp.grocerli.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.myapp.grocerli.Utilities;
import com.myapp.grocerli.data.Status;
import com.myapp.grocerli.ui.main.MainActivity;
import com.myapp.grocerli.R;
import com.myapp.grocerli.databinding.ActivityLoginBinding;
import com.myapp.grocerli.ui.signup.SignupActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
        loginViewModel.profileLiveData.observe(this, resource -> {
            if (resource != null)
                if (resource.getStatus() == Status.SUCCESS) {
                    if (resource.getData()!= null &&
                            Utilities.INSTANCE.decodeBase64(resource.getData().password).equals(loginViewModel.pass.getValue())) {
                        loginViewModel.saveLogin(resource.getData().id);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
                    }else {
                        new Handler().postDelayed(() -> {
                            if(resource.getData() == null)
                                Snackbar.make(binding.getRoot(),R.string.invalid_email_pass, Snackbar.LENGTH_LONG).show();
                        }, 1000);
                    }


                } else if (resource.getStatus() == Status.ERROR) {
                    Snackbar.make(binding.getRoot(), R.string.invalid_email_pass, Snackbar.LENGTH_LONG).show();

                }
        });
        binding.btSignUp.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

    }
}