package com.myapp.grocerli.ui.login;

import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.myapp.grocerli.data.Profile;
import com.myapp.grocerli.repository.ProfileRepository;


public class LoginViewModel extends ViewModel {
    private ProfileRepository profileRepository;

    private MutableLiveData<Pair<String, String>> _login = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> pass = new MutableLiveData<>();

    @ViewModelInject
    LoginViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    LiveData<Profile> profileLiveData =
            Transformations.switchMap(_login, input ->
                    profileRepository.checkLogin(input.first, input.second)
            );

    public void loginUser() {
        _login.postValue(Pair.create(email.getValue(), pass.getValue()));
    }

    void saveLogin(int loggedinId) {
        profileRepository.loginUser(loggedinId);
    }


    public boolean enableLogin(String email, String pass) {
        return !(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}