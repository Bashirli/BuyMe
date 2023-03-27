package com.bashirli.buyme.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bashirli.buyme.R
import com.bashirli.buyme.databinding.RecyclerproductBinding
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.util.setImageURL
import com.bashirli.buyme.view.activity.ProductActivity

class ProductsAdapter(var arrayList: ArrayList<ProductData>) : RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {
    class ProductsHolder(var binding:RecyclerproductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
         val binding=RecyclerproductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductsHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {

            holder.binding.constraintRecycler.startAnimation(
                AnimationUtils.loadAnimation(
                    holder.itemView.context,
                    R.anim.animrecycler))

        holder.binding.imageOfProduct.setImageURL(holder.itemView.context,
        arrayList.get(position).images.get(0))
        holder.binding.title.setText(arrayList.get(position).title)
        holder.binding.description.setText(arrayList.get(position).description)
        holder.binding.price.setText(arrayList.get(position).price+"$")

        holder.itemView.setOnClickListener {
            var intent=Intent(holder.itemView.context,ProductActivity::class.java)
            intent.putExtra("data",arrayList.get(position))
            holder.itemView.context.startActivity(intent)
         }

        holder.itemView.setOnLongClickListener {

            return@setOnLongClickListener true
        }


    }
}