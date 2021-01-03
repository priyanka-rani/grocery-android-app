package com.myapp.grocerli.ui.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.myapp.grocerli.data.Product;
import com.myapp.grocerli.repository.ProductRepository;
import com.myapp.grocerli.repository.ProfileRepository;

import java.util.List;


public class MainViewModel extends ViewModel {
    private ProductRepository productRepository;
    private ProfileRepository profileRepository;
    public LiveData<Integer> fabCounter;

    @ViewModelInject
    MainViewModel(ProfileRepository profileRepository,
                  ProductRepository productRepository) {
        this.profileRepository = profileRepository;
        this.productRepository = productRepository;
        this.fabCounter = productRepository.getAllCartItemCount();
    }

    public LiveData<List<String>> getProductCategoryList() {
        return productRepository.getProductCategoryList();
    }
    public void insertProductList(){
        profileRepository.insertProductList();
    }
    public void logoutUser(){
        profileRepository.logoutUser();
    }
}