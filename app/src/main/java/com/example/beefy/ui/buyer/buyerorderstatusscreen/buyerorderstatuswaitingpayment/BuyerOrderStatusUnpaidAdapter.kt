package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuswaitingpayment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.UnpaidOrderResponse
import com.example.beefy.databinding.OrderStatusCardItemBinding
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess.BuyerOrderStatusOnProcessAdapter
import com.example.beefy.utils.DateConverter
import com.example.beefy.utils.DiffUtil

class BuyerOrderStatusUnpaidAdapter(private val listener : (UnpaidOrderResponse) -> Unit) : RecyclerView.Adapter<BuyerOrderStatusUnpaidAdapter.ViewHolder>() {
    private var items = emptyList<UnpaidOrderResponse>()

    class ViewHolder(val binding : OrderStatusCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UnpaidOrderResponse){
            Glide.with(binding.root.context).load(item.FKOrder?.IDBARANG?.gambar).into(binding.orderStatusCardImageView)
            binding.orderStatusCardTitleTv.text = item.FKOrder?.IDBARANG?.namaBarang
            binding.orderStatusCardTotalItemTv.text = item.FKOrder?.totalBarang.toString()
            binding.orderStatusCardDateTv.text = DateConverter(item.FKOrder?.tanggalOrder.toString())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OrderStatusCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item)

        holder.itemView.setOnClickListener { listener(item) }
    }

    fun setData(data : List<UnpaidOrderResponse>){
        val diffUtil = DiffUtil(items,data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}