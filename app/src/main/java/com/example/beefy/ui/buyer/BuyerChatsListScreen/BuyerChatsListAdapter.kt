package com.example.beefy.ui.buyer.BuyerChatsListScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.databinding.MessageChatListItemBinding
import com.example.beefy.utils.DiffUtil
import com.example.beefy.utils.Message

class BuyerChatsListAdapter(private val listener : (DetailPenjualResponse) -> Unit): RecyclerView.Adapter<BuyerChatsListAdapter.ViewHolder>() {
    private var items = emptyList<DetailPenjualResponse>()


    class ViewHolder(val binding : MessageChatListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item:DetailPenjualResponse){
            Glide.with(binding.root).load(item.logoToko).into(binding.imageView2)
            binding.textView.text = item.namaToko
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MessageChatListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        holder.bind(item)

        holder.itemView.setOnClickListener { listener(item) }

    }

    fun setData(data : List<DetailPenjualResponse>){
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}