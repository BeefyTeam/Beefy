package com.example.beefy.ui.seller.sellerhomescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.Product
import com.example.beefy.databinding.MeatCardItemBinding
import com.example.beefy.utils.DiffUtil

class SellerHomeAdapter(private val listener : (Product) -> Unit) : RecyclerView.Adapter<SellerHomeAdapter.ViewHolder>() {
    private var items = emptyList<Product>()
    class ViewHolder(val binding : MeatCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item:Product){
            binding.meatCardItemTitleTv.text = item.namaBarang
            binding.meatCardItemPriceTv.text = "Rp"+item.harga.toString()
            Glide.with(binding.root.context).load(item.gambar).into(binding.meatCardItemImageView)
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

    fun setData(data : List<Product>){
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}