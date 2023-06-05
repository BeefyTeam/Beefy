package com.example.beefy.ui.seller.sellertransactionscreen.sellercompletetransactionscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.SellerOrderProductResponse
import com.example.beefy.databinding.OrderStatusCardItemBinding
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess.BuyerOrderStatusOnProcessAdapter
import com.example.beefy.utils.DateConverter
import com.example.beefy.utils.DiffUtil

class SellerCompleteTransactionAdapter ( private val listener : (SellerOrderProductResponse) -> Unit) : RecyclerView.Adapter<SellerCompleteTransactionAdapter.ViewHolder>() {
    private var items = emptyList<SellerOrderProductResponse>()
    class ViewHolder(val binding : OrderStatusCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: SellerOrderProductResponse){
            binding.orderStatusCardTitleTv.text = items.IDBARANG?.namaBarang
            binding.orderStatusCardTotalItemTv.text = items.totalBarang.toString()
            binding.orderStatusCardDateTv.text = DateConverter(items.tanggalOrder.toString())
            Glide.with(binding.root.context).load(items.IDBARANG?.gambar).into(binding.orderStatusCardImageView)
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

    fun setData(data : List<SellerOrderProductResponse>){
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)

    }
}