package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuspaid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.PaidOrderResponse
import com.example.beefy.data.response.UnpaidOrderResponse
import com.example.beefy.databinding.OrderStatusCardItemBinding
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuswaitingpayment.BuyerOrderStatusUnpaidAdapter
import com.example.beefy.utils.DateConverter
import com.example.beefy.utils.DiffUtil

class BuyerOrderStatusPaidAdapter(private val listener : (PaidOrderResponse) -> Unit) : RecyclerView.Adapter<BuyerOrderStatusPaidAdapter.ViewHolder>() {
    private var items = emptyList<PaidOrderResponse>()

    class ViewHolder(val binding : OrderStatusCardItemBinding) : RecyclerView.ViewHolder(binding.root)  {

        fun bind(item: PaidOrderResponse){
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

    fun setData(data : List<PaidOrderResponse>){
        val diffUtil = DiffUtil(items,data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}