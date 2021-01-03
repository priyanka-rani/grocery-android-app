package com.myapp.grocerli.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.grocerli.data.CartItem;
import com.myapp.grocerli.data.Product;
import com.myapp.grocerli.databinding.ItemCartBinding;
import com.myapp.grocerli.databinding.ItemProductBinding;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartItem> data;
    private ItemClickListener itemClickListener;

    public CartAdapter(List<CartItem> data, ItemClickListener itemClickListener) {
        this.data = data;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext())
                , parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = data.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public CartItem getItem(int pos) {
        return data.get(pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCartBinding binding;

        public ViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.btDelete.setOnClickListener(v -> itemClickListener.onItemDelete(getItem(getAdapterPosition())));
            binding.tilCount.setEndIconOnClickListener(v -> {
                CartItem item = getItem(getAdapterPosition());
                if(item.getCount()<10){
                    item.setCount(item.getCount()+1);
                    itemClickListener.onItemUpdate(item);
                }

            });
            binding.tilCount.setStartIconOnClickListener(v -> {
                CartItem item = getItem(getAdapterPosition());
                if(item.getCount()>1){
                    item.setCount(item.getCount()-1);
                    itemClickListener.onItemUpdate(item);
                }else {
                    itemClickListener.onItemDelete(item);
                }

            });
        }

        public void setData(CartItem cartItem) {
            if (cartItem != null)
                binding.setData(cartItem);
        }
    }

    public interface ItemClickListener {
        void onItemDelete(CartItem cartItem);
        void onItemUpdate(CartItem cartItem);
    }
}
