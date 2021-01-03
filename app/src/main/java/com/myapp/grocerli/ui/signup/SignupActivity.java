package com.myapp.grocerli.ui.signup;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.myapp.grocerli.R;
import com.myapp.grocerli.databinding.ActivitySignupBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        binding.setViewModel(signupViewModel);
        binding.setLifecycleOwner(this);
        signupViewModel.getSignUpResponse().observe(this, unit -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.sign_up)
                    .setMessage(R.string.signup_success_message)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        onBackPressed();
                    }).show();
        });
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }
}