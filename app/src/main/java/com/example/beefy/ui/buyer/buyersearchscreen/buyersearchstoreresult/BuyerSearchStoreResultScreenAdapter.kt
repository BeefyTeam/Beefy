package com.example.beefy.ui.buyer.buyersearchscreen.buyersearchstoreresult

import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.databinding.StoreNameWithLocationCardItemBinding

class BuyerSearchStoreResultScreenAdapter(private val items : ArrayList<String>, private val listener : (String) -> Unit): RecyclerView.Adapter<BuyerSearchStoreResultScreenAdapter.ViewHolder>() {
    class ViewHolder(val binding : StoreNameWithLocationCardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String){
            Glide.with(binding.root.context).load(item).into(binding.storeNameWithLocImageView)
            binding.storeNameWithLocTitleTv.setText("ANugrah bersama")
            binding.storeNameWithLocLocationTv.setText("bandung")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(StoreNameWithLocationCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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