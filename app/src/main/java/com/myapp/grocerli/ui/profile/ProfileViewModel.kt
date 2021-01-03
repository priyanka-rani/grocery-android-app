package com.myapp.grocerli.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.myapp.grocerli.data.Profile
import com.myapp.grocerli.repository.ProfileRepository

class ProfileViewModel @ViewModelInject constructor(private val profileRepository: ProfileRepository) : ViewModel() {
    val profile by lazy {
        profileRepository.getUser()
    }
    private val _signup = MutableLiveData<Profile>()
    val updateResponse = _signup.switchMap {
        liveData {
            profileRepository.updateUser(it)
            emit(Unit)
        }
    }

    fun updateUser() {
        _signup.value = profile.value
    }

    fun enableUpdate(vararg args: String?): Boolean {
        return !args.any { it.isNullOrBlank() }
    }

}