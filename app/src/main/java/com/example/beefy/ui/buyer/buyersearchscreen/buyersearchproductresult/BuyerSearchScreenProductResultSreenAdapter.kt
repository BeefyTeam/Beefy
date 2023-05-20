package com.example.beefy.ui.buyer.buyersearchscreen.buyersearchproductresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.databinding.MeatCardItemBinding

class BuyerSearchScreenProductResultSreenAdapter(private val items : ArrayList<String>, private val listener : (String)->Unit): RecyclerView.Adapter<BuyerSearchScreenProductResultSreenAdapter.ViewHolder>() {
    class ViewHolder(val binding: MeatCardItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:String){
            Glide.with(binding.root.context).load(item).into(binding.meatCardItemImageView)
            binding.meatCardItemTitleTv.setText("Sirloin 90gr")
            binding.meatCardItemPriceTv.setText("Rp90.000")
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