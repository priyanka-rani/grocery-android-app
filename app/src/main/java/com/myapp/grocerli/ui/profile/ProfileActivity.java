package com.myapp.grocerli.ui.profile;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.myapp.grocerli.R;
import com.myapp.grocerli.Utilities;
import com.myapp.grocerli.databinding.ActivityProfileBinding;
import com.myapp.grocerli.ui.main.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setViewModel(profileViewModel);
        binding.setLifecycleOwner(this);
        profileViewModel.getUpdateResponse().observe(this, success -> {
            Utilities.INSTANCE.hideKeyboard(ProfileActivity.this);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.profile_update)
                    .setMessage(success?R.string.profile_update_message:R.string.profile_update_failed)
                    .setPositiveButton(android.R.string.ok,null).show();
        });
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }
}