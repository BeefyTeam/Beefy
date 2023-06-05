package com.example.beefy.ui.buyer.buyerhomescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.TrendingStoreResponse
import com.example.beefy.databinding.BestStoreItemBinding
import com.example.beefy.utils.DiffUtil

class BuyerHomeScreenBestStoreAdapter(private val listener : (TrendingStoreResponse) -> Unit) : RecyclerView.Adapter<BuyerHomeScreenBestStoreAdapter.ViewHolder>() {
    private var items = emptyList<TrendingStoreResponse>()
    class ViewHolder(val binding : BestStoreItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrendingStoreResponse){
            Glide.with(binding.root.context).load(item.logoToko).into(binding.bestStoreItemImageView)
            binding.bestStoreItemStoreName.text = item.namaToko
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

    fun setData(data : List<TrendingStoreResponse>){
        val diffUtil = DiffUtil(items,data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }


}