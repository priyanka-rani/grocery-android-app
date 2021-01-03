package com.myapp.grocerli.ui.profile;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.myapp.grocerli.R;
import com.myapp.grocerli.databinding.ActivityProfileBinding;
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
        profileViewModel.getUpdateResponse().observe(this, unit -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.menu_profile)
                    .setMessage(R.string.profile_update_message)
                    .setPositiveButton(android.R.string.ok,null).show();
        });
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }
}