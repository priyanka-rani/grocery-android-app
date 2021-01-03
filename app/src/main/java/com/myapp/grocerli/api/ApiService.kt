/*
 * Copyright (C) 2017 The Android Open Source Project
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
 */

package com.myapp.grocerli.api

import androidx.lifecycle.LiveData
import com.myapp.grocerli.data.Product
import com.myapp.grocerli.data.Profile
import retrofit2.http.*

/**
 * REST API access points for updating user profile
 */
interface ApiService {

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): LiveData<ApiResponse<Profile>>
    @GET("users/{product}")
    fun getProductCategory(): LiveData<ApiResponse<List<Product>>>

}
