package com.example.beefy.ui.buyer.buyerscanhistoryscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.ScanMeatHistoryResponse
import com.example.beefy.databinding.ScanHistoryItemBinding
import com.example.beefy.utils.DateConverter
import com.example.beefy.utils.DiffUtil

class BuyerScanHistoryAdapter(private val listener : (ScanMeatHistoryResponse) -> Unit) : RecyclerView.Adapter<BuyerScanHistoryAdapter.ViewHolder>() {
    private var items = emptyList<ScanMeatHistoryResponse>()
    class ViewHolder(val binding : ScanHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : ScanMeatHistoryResponse){
            Glide.with(binding.root.context).load(item.gambarUrl).into(binding.scanHistoryItemImageView)
            binding.scanHistoryItemDateTv.text = DateConverter(item.tanggal.toString())
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

    fun setData(data : List<ScanMeatHistoryResponse>){
        val diffUtil = DiffUtil(items,data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }

}