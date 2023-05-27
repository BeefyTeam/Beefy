package com.example.beefy.ui.seller.sellerchatslistscreen

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerChatsListScreenBinding
import com.example.beefy.utils.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SellerChatsListScreen : Fragment() {

    private var _binding : FragmentSellerChatsListScreenBinding? = null
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
        _binding = FragmentSellerChatsListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val messagesRef = db.reference.child("messages")
        val currentUserId = "321"

        val adapter = SellerChatsListAdapter{
            val bundle = Bundle().apply {
                putString("otherUserId", it)
            }

            findNavController().navigate(R.id.action_sellerChatsListScreen_to_sellerChatScreen, bundle)
        }

        binding.sellerChatsListRv.layoutManager = LinearLayoutManager(requireContext())
        binding.sellerChatsListRv.addItemDecoration(DividerItemDecoration(requireContext(),1 ))
        binding.sellerChatsListRv.adapter = adapter

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
                    Log.e(ContentValues.TAG, "kosong: ", )
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