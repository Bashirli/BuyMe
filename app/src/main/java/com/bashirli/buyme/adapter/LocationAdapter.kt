package com.bashirli.buyme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.buyme.databinding.RecyclerlocationBinding
import com.bashirli.buyme.model.LocationModel

class LocationAdapter(var arrayList: ArrayList<LocationModel>) : RecyclerView.Adapter<LocationAdapter.LocationHolder>() {
    class LocationHolder(var binding:RecyclerlocationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
       var binding=RecyclerlocationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LocationHolder(binding)
    }

    override fun getItemCount(): Int {
    return arrayList.size
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.binding.locName.setText(arrayList.get(position).name)
        holder.binding.locDetail.text= "${arrayList.get(position).lat} / ${arrayList.get(position).longit}"

    }



}