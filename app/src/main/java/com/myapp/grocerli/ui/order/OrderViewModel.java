package com.myapp.grocerli.ui.order;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.myapp.grocerli.data.OrderItem;
import com.myapp.grocerli.repository.ProductRepository;

import java.util.List;

public class OrderViewModel extends ViewModel {
    LiveData<List<OrderItem>> orderList;
    @ViewModelInject
    public OrderViewModel(ProductRepository productRepository){
        orderList = productRepository.getAllOrderItem();

    }
}
