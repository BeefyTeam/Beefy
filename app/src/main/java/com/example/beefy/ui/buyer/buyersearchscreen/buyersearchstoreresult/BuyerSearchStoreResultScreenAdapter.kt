package com.example.beefy.ui.buyer.buyersearchscreen.buyersearchstoreresult

import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.SearchStoreResponse
import com.example.beefy.databinding.StoreNameWithLocationCardItemBinding
import com.example.beefy.utils.DiffUtil

class BuyerSearchStoreResultScreenAdapter(private val listener : (SearchStoreResponse) -> Unit): RecyclerView.Adapter<BuyerSearchStoreResultScreenAdapter.ViewHolder>() {
    private var items = emptyList<SearchStoreResponse>()
    class ViewHolder(val binding : StoreNameWithLocationCardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item : SearchStoreResponse){
            Glide.with(binding.root.context).load(item.logoToko).into(binding.storeNameWithLocImageView)
            binding.storeNameWithLocTitleTv.setText(item.namaToko)
            binding.storeNameWithLocLocationTv.setText(item.alamatLengkap)
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

    fun setData(data : List<SearchStoreResponse>){
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}