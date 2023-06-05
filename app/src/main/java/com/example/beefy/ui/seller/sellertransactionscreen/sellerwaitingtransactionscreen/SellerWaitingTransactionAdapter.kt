package com.example.beefy.ui.seller.sellertransactionscreen.sellerwaitingtransactionscreen

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.PaidOrderResponse
import com.example.beefy.data.response.SellerOrderProductResponse
import com.example.beefy.databinding.OrderStatusCardItemBinding
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess.BuyerOrderStatusOnProcessAdapter
import com.example.beefy.utils.DateConverter
import com.example.beefy.utils.DiffUtil

class SellerWaitingTransactionAdapter (private val listener : (PaidOrderResponse) -> Unit) : RecyclerView.Adapter<SellerWaitingTransactionAdapter.ViewHolder>() {
    private var items = emptyList<PaidOrderResponse>()

    class ViewHolder(val binding : OrderStatusCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(items: PaidOrderResponse){
            binding.orderStatusCardTitleTv.text = items.FKOrder?.IDBARANG?.namaBarang
            binding.orderStatusCardTotalItemTv.text = items.FKOrder?.totalBarang.toString() + " Barang"
            binding.orderStatusCardDateTv.text = DateConverter(items.FKOrder?.tanggalOrder.toString())
            Glide.with(binding.root.context).load(items.FKOrder?.IDBARANG?.gambar).into(binding.orderStatusCardImageView)
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

    fun setData(data : List<PaidOrderResponse>){
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)

    }
}