package com.myapp.grocerli.ui.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.myapp.grocerli.data.CartItem
import com.myapp.grocerli.data.OrderItem
import com.myapp.grocerli.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel @ViewModelInject internal constructor(private val productRepository: ProductRepository) : ViewModel() {
    private val _login = MutableLiveData<OrderItem>()
    val cartItemListLiveData: LiveData<List<CartItem>> by lazy {
        productRepository.getAllCartItem()
    }

    @JvmField
    var totalPrice= Transformations.map(cartItemListLiveData!!) { input: List<CartItem?>? ->
        var sum = 0.0
        for (cartItem in input!!) {
            sum += cartItem!!.totalPrice!!
        }
        sum
    }
    fun deleteCartItem(cartItem: CartItem?) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteCartItem(cartItem!!)
        }
    }

    fun updateCartItem(cartItem: CartItem?) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.updateCartItem(cartItem!!)
        }
    }

    fun insertOrderItem() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteAllCartItem()
            productRepository.insertOrderItem(OrderItem(totalPrice = totalPrice.value, cartItemList = cartItemListLiveData.value))
        }
    }
}