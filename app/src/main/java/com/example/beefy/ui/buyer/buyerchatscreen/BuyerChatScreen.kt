package com.example.beefy.ui.buyer.buyerchatscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerChatScreenBinding
import com.example.beefy.utils.Message
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.ObservableSnapshotArray
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Date


class BuyerChatScreen : Fragment() {

    private var _binding: FragmentBuyerChatScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseDatabase
    private lateinit var messagesRef : DatabaseReference

    private lateinit var adapter: FirebaseMessageAdapter

    private lateinit var currentUserId: String
    private lateinit var otherUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Firebase.database
        messagesRef = db.reference.child("messages")


        currentUserId = requireArguments().getString("currentUserId").toString()
        otherUserId = requireArguments().getString("otherUserId").toString()
        Log.e(TAG, "otherUserId: " + otherUserId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerChatScreenBinding.inflate(inflater, container, false)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        checkField()
        validateInput()
        setupAdapter()
        setupButton()




    }

    private fun setupAdapter(){
        var linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd = true
        binding.buyerChatRv.layoutManager = linearLayoutManager

        val query = messagesRef
            .orderByChild("timestamp")

//        query.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                for (data in snapshot.children) {
//                    val value = data.getValue(Message::class.java)
//                    Log.e(TAG, "onDataChange: "+value)
//                }
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })


        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .build()

        adapter = FirebaseMessageAdapter(options, currentUserId, otherUserId)
        binding.buyerChatRv.adapter = adapter
    }

    private fun setupButton(){
        binding.buyerSendBtn.setOnClickListener {
            val message = Message(
                id = generateId(),
                text = binding.buyerMessageEditText.text.toString().trim(),
                senderId = currentUserId,
                receiverId = otherUserId,
                timestamp = Date().time
            )
            messagesRef.push().setValue(message)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Pesan berhasil dikirim", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Gagal mengirim pesan", Toast.LENGTH_SHORT)
                        .show()
                }

            binding.buyerMessageEditText.setText("")
        }
    }


    fun generateId(length: Int = 20): String { //ex: bwUIoWNCSQvPZh8xaFuz
        val alphaNumeric = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return alphaNumeric.shuffled().take(length).joinToString("")
    }

    private fun validateInput() {
        binding.buyerMessageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buyerSendBtn.isEnabled = !s.toString().isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                checkField()
            }

        })
    }

    private fun checkField() {
        binding.buyerSendBtn.isEnabled = binding.buyerMessageEditText.text.isNotEmpty()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}