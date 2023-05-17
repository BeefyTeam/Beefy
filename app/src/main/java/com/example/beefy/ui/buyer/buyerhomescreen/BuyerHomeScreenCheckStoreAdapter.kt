package com.example.beefy.ui.buyer.buyerhomescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beefy.databinding.BestStoreItemBinding

class BuyerHomeScreenCheckStoreAdapter(private val items : List<String>, private val listener : (String) -> Unit) : RecyclerView.Adapter<BuyerHomeScreenCheckStoreAdapter.ViewHolder>() {
    class ViewHolder(val binding : BestStoreItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String){
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