package com.myapp.grocerli.ui.order

import com.myapp.grocerli.data.CartItem
import com.myapp.grocerli.databinding.ItemChildOrderLayoutBinding
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class ChildLimitViewHolder(private val itemChildBinding: ItemChildOrderLayoutBinding)
    : ChildViewHolder(itemChildBinding.root) {
    fun setChildLimitData(data: CartItem?) {
        itemChildBinding.data = data
    }
}