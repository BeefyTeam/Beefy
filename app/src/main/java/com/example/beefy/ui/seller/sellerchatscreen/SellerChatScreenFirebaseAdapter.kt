package com.example.beefy.ui.seller.sellerchatscreen

import android.text.format.DateUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beefy.R
import com.example.beefy.databinding.MessageItemBinding
import com.example.beefy.ui.buyer.buyerchatscreen.FirebaseMessageAdapter
import com.example.beefy.utils.Message
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class SellerChatScreenFirebaseAdapter(
    options: FirebaseRecyclerOptions<Message>,
    private val currentUserId: String?, private val otherUserId: String?
) : FirebaseRecyclerAdapter<Message, SellerChatScreenFirebaseAdapter.ViewHolder>(options){

    inner class ViewHolder(private val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.tvMessage.text = item.text
            setTextColor(item.senderId, binding.tvMessage, binding.messageItemLinearLayout)
            if (item.timestamp != null) {
                binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Message) {
        if((model.senderId.equals(currentUserId) && model.receiverId.equals(otherUserId)) || (model.receiverId.equals(currentUserId) && model.senderId.equals(otherUserId) )){
            holder.bind(model)
        }else{
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
    }


    private fun setTextColor(id: String?, textView: TextView, linearLayout: LinearLayout) {
        if (currentUserId == id && id != null) {
            textView.setBackgroundResource(R.drawable.rounded_message_blue)
            linearLayout.gravity = Gravity.END
        } else {
            textView.setBackgroundResource(R.drawable.rounded_message_yellow)
        }
    }

}