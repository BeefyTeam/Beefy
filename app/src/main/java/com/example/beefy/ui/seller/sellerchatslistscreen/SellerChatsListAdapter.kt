package com.example.beefy.ui.seller.sellerchatslistscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beefy.databinding.MessageChatListItemBinding
import com.example.beefy.utils.DiffUtil

class SellerChatsListAdapter(private val listener : (String) -> Unit): RecyclerView.Adapter<SellerChatsListAdapter.ViewHolder>() {
    private var items = emptyList<String>()

    inner class ViewHolder(val binding: MessageChatListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item:String){
            binding.textView.text = item
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

    fun setData(data : List<String>){
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}