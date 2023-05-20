package com.example.beefy.ui.buyer.buyerscanhistoryscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.databinding.ScanHistoryItemBinding

class BuyerScanHistoryAdapter(private var items : ArrayList<String>, private val listener : (String) -> Unit) : RecyclerView.Adapter<BuyerScanHistoryAdapter.ViewHolder>() {
    class ViewHolder(val binding : ScanHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : String){
            Glide.with(binding.root.context).load(item).into(binding.scanHistoryItemImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ScanHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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