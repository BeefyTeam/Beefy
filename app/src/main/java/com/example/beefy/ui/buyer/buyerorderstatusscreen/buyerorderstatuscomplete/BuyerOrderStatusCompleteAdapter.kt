package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuscomplete

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.databinding.OrderStatusCardItemBinding

class BuyerOrderStatusCompleteAdapter(private val items : ArrayList<String>, private val listener : (String) -> Unit) : RecyclerView.Adapter<BuyerOrderStatusCompleteAdapter.ViewHolder>() {
    class ViewHolder(val binding : OrderStatusCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(items: String){
            Glide.with(binding.root.context).load(items).into(binding.orderStatusCardImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OrderStatusCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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