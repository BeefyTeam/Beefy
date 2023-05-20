package com.example.beefy.ui.buyer.buyerhomescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.databinding.BestStoreItemBinding

class BuyerHomeScreenCheckStoreAdapter(private val items : List<String>, private val listener : (String) -> Unit) : RecyclerView.Adapter<BuyerHomeScreenCheckStoreAdapter.ViewHolder>() {
    class ViewHolder(val binding : BestStoreItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String){
            Glide.with(binding.root.context).load("https://images.tokopedia.net/img/cache/215-square/GAnVPX/2023/2/10/c4ad6096-b419-4cb2-b0e1-0f3366950e4e.jpg").into(binding.bestStoreItemImageView)
            binding.bestStoreItemStoreName.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BestStoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }


}