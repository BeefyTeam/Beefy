package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.databinding.OrderStatusCardItemBinding
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuscomplete.BuyerOrderStatusCompleteAdapter

class BuyerOrderStatusOnProcessAdapter (private val items : ArrayList<String>, private val listener : (String) -> Unit) : RecyclerView.Adapter<BuyerOrderStatusOnProcessAdapter.ViewHolder>() {
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