package com.example.beefy.ui.buyer.buyerhomescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.databinding.BestMeatItemBinding

class BuyerHomeScreenBestMeatAdapter(private val items : ArrayList<String>, private val listener : (String) -> Unit) : RecyclerView.Adapter<BuyerHomeScreenBestMeatAdapter.ViewHolder>() {
    class ViewHolder(val binding : BestMeatItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item : String){
            Glide.with(binding.root.context).load(item).into(binding.carouselImageView)
            binding.carouselTextView.text = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BestMeatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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