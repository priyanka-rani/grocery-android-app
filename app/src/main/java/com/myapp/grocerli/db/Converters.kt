/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myapp.grocerli.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myapp.grocerli.data.CartItem
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun stringToCartItemList(data: String?): List<CartItem> {
        if (data == null) {
            return Collections.emptyList()
        }
        val gson = Gson()
        return gson.fromJson(data, object : TypeToken<List<CartItem>>() {}.type)
    }

    @TypeConverter
    fun cartListToString(someObjects: List<CartItem>): String {
        return Gson().toJson(someObjects)
    }
}
