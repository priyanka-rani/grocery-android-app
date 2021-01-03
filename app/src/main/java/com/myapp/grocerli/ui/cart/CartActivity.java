package com.myapp.grocerli.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.myapp.grocerli.R;
import com.myapp.grocerli.adapters.CartAdapter;
import com.myapp.grocerli.adapters.ProductListAdapter;
import com.myapp.grocerli.data.CartItem;
import com.myapp.grocerli.data.Product;
import com.myapp.grocerli.databinding.ActivityCartBinding;
import com.myapp.grocerli.databinding.ActivityLoginBinding;
import com.myapp.grocerli.ui.login.LoginViewModel;
import com.myapp.grocerli.ui.main.MainActivity;
import com.myapp.grocerli.ui.order.OrderActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding.setViewModel(cartViewModel);
        binding.setLifecycleOwner(this);
        cartViewModel.getCartItemListLiveData().observe(this, cartItems -> {
            CartAdapter adapter = new CartAdapter(cartItems, new CartAdapter.ItemClickListener() {

                @Override
                public void onItemDelete(CartItem cartItem) {
                    cartViewModel.deleteCartItem(cartItem);
                }

                @Override
                public void onItemUpdate(CartItem cartItem) {
                    cartViewModel.updateCartItem(cartItem);
                }
            });
            binding.rvItems.setAdapter(adapter);
        });

        binding.btPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.insertOrderItem();
                if(cartViewModel.getCartItemListLiveData().getValue().size()>0){
                   Toast.makeText(v.getContext(), "Order Placed Successfully!!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                    startActivity(intent);
                    CartActivity.this.finish();
                }

            }
        });
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());

    }
}