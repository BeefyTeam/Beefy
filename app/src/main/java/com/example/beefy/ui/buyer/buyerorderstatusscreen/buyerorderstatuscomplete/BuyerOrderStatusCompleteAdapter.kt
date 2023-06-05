package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuscomplete

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.BuyerOrderProductResponse
import com.example.beefy.databinding.OrderStatusCardItemBinding
import com.example.beefy.utils.DateConverter
import com.example.beefy.utils.DiffUtil

class BuyerOrderStatusCompleteAdapter(private val listener : (BuyerOrderProductResponse) -> Unit) : RecyclerView.Adapter<BuyerOrderStatusCompleteAdapter.ViewHolder>() {
    private var items = emptyList<BuyerOrderProductResponse>()
    class ViewHolder(val binding : OrderStatusCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BuyerOrderProductResponse){
            Glide.with(binding.root.context).load(item.IDBARANG?.gambar).into(binding.orderStatusCardImageView)
            binding.orderStatusCardTitleTv.text = item.IDBARANG?.namaBarang
            binding.orderStatusCardTotalItemTv.text = item.totalBarang.toString() + " Barang"
            binding.orderStatusCardDateTv.text = DateConverter(item.tanggalOrder.toString())
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

    fun setData(data : List<BuyerOrderProductResponse>){
        val diffUtil = DiffUtil(items,data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }

}