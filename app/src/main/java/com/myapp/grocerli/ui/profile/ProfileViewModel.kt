package com.myapp.grocerli.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.myapp.grocerli.Utilities
import com.myapp.grocerli.data.Profile
import com.myapp.grocerli.repository.ProfileRepository

class ProfileViewModel @ViewModelInject constructor(private val profileRepository: ProfileRepository) : ViewModel() {
    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var contact = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var pass = MutableLiveData<String>()
    var category = MutableLiveData<String>()
    val profileLiveData by lazy {
        profileRepository.getUser()
    }
    private val _updateProfile = MutableLiveData<Profile>()
    val updateResponse = _updateProfile.switchMap {
        liveData {
            val result = profileRepository.updateUser(it)
            emit(result>0)
        }
    }
/*update click*/
    fun updateUser() {
        val profile = Profile(name.value, email.value, pass.value?.let { Utilities.encodeToBase64(it) })
        profile.id = profileLiveData.value?.id?:-1
        profile.contact = contact.value
        profile.address = address.value
        profile.category = "9"

        _updateProfile.value = profile
    }

    fun enableUpdate(vararg args: String?): Boolean {
        return !args.any { it.isNullOrBlank() }
    }

}