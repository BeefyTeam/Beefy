package com.example.beefy.ui.buyer.BuyerChatsListScreen

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerChatsListScreenBinding
import com.example.beefy.ui.buyer.buyerchatscreen.FirebaseMessageAdapter
import com.example.beefy.utils.Message
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class BuyerChatsListScreen : Fragment() {

    private var _binding : FragmentBuyerChatsListScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.database
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerChatsListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val messagesRef = db.reference.child("messages")
        val currentUserId = "123"



        val adapter = BuyerChatsListAdapter{

            val bundle = Bundle().apply {
                putString("otherUserId", it)
            }

            findNavController().navigate(R.id.action_buyerChatsListScreen_to_buyerChatScreen, bundle)
        }

        binding.buyerChatsListRv.layoutManager = LinearLayoutManager(requireContext())
        binding.buyerChatsListRv.addItemDecoration(DividerItemDecoration(requireContext(),1 ))
        binding.buyerChatsListRv.adapter = adapter

        val query = messagesRef
            .orderByChild("timestamp")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<String>()

                for (data in snapshot.children) {
                    val value = data.getValue(Message::class.java)

                    val receiverId = data.child("receiverId").value.toString()
                    val senderId = data.child("senderId").value.toString()


                    if(receiverId.equals(currentUserId) ){
                        list.add(value?.senderId.toString())
                    }
                    if(senderId.equals(currentUserId) ){
                        list.add(value?.receiverId.toString())
                    }


                }

                if (list.isEmpty()){
                    Log.e(TAG, "kosong: ", )
                }else{

                    adapter.setData(list.distinct())


                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}