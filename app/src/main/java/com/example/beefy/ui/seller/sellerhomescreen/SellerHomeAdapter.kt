package com.example.beefy.ui.seller.sellerhomescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.databinding.MeatCardItemBinding

class SellerHomeAdapter(private val items : ArrayList<String>, private val listener : (String) -> Unit) : RecyclerView.Adapter<SellerHomeAdapter.ViewHolder>() {
    class ViewHolder(val binding : MeatCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item:String){
            Glide.with(binding.root.context).load(item).into(binding.meatCardItemImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MeatCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }
}