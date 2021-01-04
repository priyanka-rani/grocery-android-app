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
        /*after update done*/
        profileViewModel.getUpdateResponse().observe(this, success -> {
            Utilities.INSTANCE.hideKeyboard(ProfileActivity.this);
            /*display success or failed*/
            new AlertDialog.Builder(this)
                    .setTitle(R.string.profile_update)
                    .setMessage(success?R.string.profile_update_message:R.string.profile_update_failed)
                    .setPositiveButton(android.R.string.ok,(dialog, which) -> {
                        /*go back to home*/
                        if(success) onBackPressed();
                    }).show();
        });
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());
        /*pre populate the user profile data */
        profileViewModel.getProfileLiveData().observe(this, profile -> {
            profileViewModel.getName().postValue(profile.name);
            profileViewModel.getEmail().postValue(profile.email);
            profileViewModel.getContact().postValue(profile.contact);
            profileViewModel.getAddress().postValue(profile.address);
            profileViewModel.getCategory().postValue(profile.category);
            profileViewModel.getPass().postValue(Utilities.INSTANCE.decodeBase64(profile.password));
        });
    }
}