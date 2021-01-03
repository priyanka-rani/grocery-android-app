package com.myapp.grocerli.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.myapp.grocerli.R;
import com.myapp.grocerli.adapters.CartAdapter;
import com.myapp.grocerli.data.CartItem;
import com.myapp.grocerli.databinding.ActivityCartBinding;
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
        CartAdapter adapter = new CartAdapter(new CartAdapter.ItemClickListener() {

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
        cartViewModel.getCartItemListLiveData().observe(this, adapter::submitList);

        binding.btPlaceOrder.setOnClickListener(v -> {
            cartViewModel.insertOrderItem();
            if (cartViewModel.getCartItemListLiveData().getValue().size() > 0) {
                new AlertDialog.Builder(v.getContext()).setTitle(R.string.place_order)
                        .setMessage("Order Placed Successfully!!")
                        .setPositiveButton("OK", (dialog, which) ->{
                            cartViewModel.deleteCartItems();
                            Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                            startActivity(intent);
                            CartActivity.this.finish();
                        } ).show();
            }

        });
        binding.toolBar.setNavigationOnClickListener(v -> onBackPressed());

    }
}