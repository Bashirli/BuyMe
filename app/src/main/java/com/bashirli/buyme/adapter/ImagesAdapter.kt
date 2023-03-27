package com.bashirli.buyme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.buyme.databinding.RecyclerimagesBinding
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.util.setImageURL

class ImagesAdapter(var arrayList: ArrayList<String>) : RecyclerView.Adapter<ImagesAdapter.ImagesHolder>(){
    class ImagesHolder(var binding: RecyclerimagesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesHolder {
        val binding= RecyclerimagesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImagesHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ImagesHolder, position: Int) {
    holder.binding.recyclerImageLooker.setImageURL(holder.itemView.context,arrayList.get(position))
    }
}