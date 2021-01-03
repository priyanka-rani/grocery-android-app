package com.myapp.grocerli.repository

import androidx.lifecycle.map
import com.myapp.grocerli.Utilities
import com.myapp.grocerli.data.Profile
import com.myapp.grocerli.db.ProfileDao
import com.myapp.grocerli.pref.PreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ /**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
@Singleton
class ProfileRepository @Inject constructor(
        private val profileDao: ProfileDao,
        private val preferenceHelper: PreferenceHelper) {
    fun checkLogin(email: String, password: String) = profileDao.getProfile(email).map { profile ->
        if (profile?.password?.let { Utilities.decodeBase64(it) } == password) profile
        else null
    }
    fun getUser()= profileDao.getProfile(preferenceHelper.loggedInId)

    fun loginUser(loggedinId: Int) {
        preferenceHelper.loginUser(loggedinId)
    }

    suspend fun insertUser(profile: Profile) = profileDao.insert(profile)

    suspend fun updateUser(profile: Profile)=
        profileDao.update(profile)

    fun logoutUser() {
        preferenceHelper.logoutUser()

    }
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.

}