package com.myapp.grocerli.ui.signup

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.myapp.grocerli.data.Profile
import com.myapp.grocerli.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel @ViewModelInject constructor(private val profileRepository: ProfileRepository) : ViewModel() {
    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var contact = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var pass = MutableLiveData<String>()
    var category = MutableLiveData<String>()
    private val _signup = MutableLiveData<Profile>()
    val signUpResponse = _signup.switchMap {
        liveData {
            profileRepository.insertUser(it)
            emit(Unit)
        }
    }

    fun insertUser() {
        val profile = Profile(name.value, email.value, pass.value)
        profile.contact = contact.value
        profile.address = address.value
        profile.category = category.value

        _signup.value = profile
    }

    fun enableRegister(vararg args: String?): Boolean {
        return !args.any { it.isNullOrBlank() }
    }

}