package com.bashirli.buyme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.buyme.databinding.CartlayoutBinding
import com.bashirli.buyme.databinding.RecyclerimagesBinding
import com.bashirli.buyme.model.CartModel
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.util.setImageURL

class CartAdapter(var arrayList: ArrayList<CartModel>) : RecyclerView.Adapter<CartAdapter.CartHolder>(){
    class CartHolder(var binding: CartlayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding= CartlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
       holder.binding.cartImage.setImageURL(holder.itemView.context,arrayList.get(position).productData.images.get(0))
        holder.binding.titleText.setText(arrayList.get(position).productData.title)
        holder.binding.priceText.setText(arrayList.get(position).productData.price+"$")
        holder.binding.countText.setText("Amount : ${arrayList.get(position).productCount}")
    }
}