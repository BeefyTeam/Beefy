package com.example.beefy.ui.seller.sellerchatslistscreen

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import com.example.beefy.utils.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject


class SellerChatsListScreen : Fragment() {

    private var _binding : FragmentSellerChatsListScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseDatabase
    private lateinit var messagesRef : DatabaseReference

    private lateinit var adapter: SellerChatsListAdapter

    private lateinit var currentUserId : String

    private val sellerChatsListViewModel : SellerChatsListViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.database
        messagesRef = db.reference.child("messages")
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

        setupAdapter()
        setupObserver()

    }

    private fun setupObserver(){
        sellerChatsListViewModel.getUserId().observe(viewLifecycleOwner){
            currentUserId = it

        }

        sellerChatsListViewModel.buyerChatList.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success-> {
                    setLoading(false)
                    adapter.setData(it.data)
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {

                }

            }
        }
    }

    private fun setupAdapter(){
        adapter = SellerChatsListAdapter{
            val bundle = Bundle().apply {
                putString("currentUserId", currentUserId)
                putString("otherUserId", it.userAccount?.idAccount.toString())
                putString("namaAkunPembeli", it.nama)
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

                if (list.isEmpty()) {
                    setEmptyAnim(true)
                } else {
                    setEmptyAnim(false)
                    Log.e(TAG, "onDataChange: "+ list.distinct(), )
                    sellerChatsListViewModel.getBuyerChatList(list.distinct())
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.sellerChatsListRv.loadSkeleton(R.layout.message_chat_list_item){
                itemCount(4)
            }
        }else{
            binding.sellerChatsListRv.hideSkeleton()
        }
    }

    private fun setEmptyAnim(boolean: Boolean){
        if (boolean){
            binding.sellerChatListEmptyAnim.emptyChatTv.setText("Kamu belum pernah melakukan chat dengan pembeli")
            binding.sellerChatListEmptyAnim.root.visibility = View.VISIBLE
        } else {
            binding.sellerChatListEmptyAnim.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}