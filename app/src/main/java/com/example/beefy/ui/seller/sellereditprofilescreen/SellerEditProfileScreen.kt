package com.example.beefy.ui.seller.sellereditprofilescreen

import android.app.Activity
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.beefy.R
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.databinding.FragmentSellerEditProfileScreenBinding
import com.example.beefy.utils.Resource
import com.example.beefy.utils.downloadImageAsTempFile
import com.example.beefy.utils.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Calendar


class SellerEditProfileScreen : Fragment() {

    private var _binding : FragmentSellerEditProfileScreenBinding? = null
    private val binding get() = _binding!!

    private var uri : Uri? = null
    private var getFile: File? = null

    private var mHour: Int = 0
    private var mMinute: Int = 0

    private val sellerEditProfileViewModel : SellerEditProfileViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerEditProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sellerEditProfileViewModel.getDetailPenjual()

        validateInput()
        setupEditText()
        setupObserver()
        setupButton()

    }

    private fun setupButton(){
        binding.sellerEditProfileConfirmBtn.setOnClickListener {
            val file = getFile as File
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file_image",
                file.name,
                image
            )

            sellerEditProfileViewModel.confirmEditProfile(
                imagePart,
                binding.sellerEditProfileInfoAddressTIET.text.toString(),
                binding.sellerEditProfileInfoOpenHourTIET.text.toString(),
                binding.sellerEditProfileInfoCloseHourTIET.text.toString(),
                binding.sellerEditProfileInfoPaymentMethodTIET.text.toString(),
                binding.sellerEditProfileInfoAccountTIET.text.toString()
            )
            findNavController().navigate(R.id.action_sellerEditProfileScreen_to_sellerHomeScreen)

        }

        binding.sellerEditProfileUploadProfilePictureUploadBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    setProgressBar(true)
                    startForProfileImageResult.launch(intent)
                }
        }
    }

    private fun setupObserver(){

        sellerEditProfileViewModel.sellerEditPPPenjual.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_sellerEditProfileScreen_to_sellerHomeScreen)
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "sellereditprofile setupobserver: " + it.error, )
                }
            }
        }

        sellerEditProfileViewModel.sellerEditPenjual.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_sellerEditProfileScreen_to_sellerHomeScreen)
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "sellereditprofile setupobserver: " + it.error, )
                }
            }
        }


        sellerEditProfileViewModel.userProfile.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    setLoading(false)
                    setupView(it.data)
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "sellereditprofile setupobserver: " + it.error, )
                }
            }
        }
    }



    private fun setupView(detailPenjualResponse: DetailPenjualResponse){
        val imgUrl = detailPenjualResponse.logoToko.toString()

        lifecycleScope.launch {
            getFile = downloadImageAsTempFile(requireContext(), imgUrl)


        }

        Glide.with(binding.root).load(imgUrl).into(binding.sellerEditProfileUploadProfilePictureImageView)
        binding.sellerEditProfileInfoAddressTIET.setText(detailPenjualResponse.alamatLengkap)
        binding.sellerEditProfileInfoOpenHourTIET.setText(detailPenjualResponse.jamOperasionalBuka)
        binding.sellerEditProfileInfoCloseHourTIET.setText(detailPenjualResponse.jamOperasionalTutup)
        binding.sellerEditProfileInfoPaymentMethodTIET.setText(detailPenjualResponse.metodePembayaran)
        binding.sellerEditProfileInfoAccountTIET.setText(detailPenjualResponse.rekening)

    }

    private fun setLoading(boolean: Boolean){
        if (boolean){
            binding.sellerEditProfileLinearLayout.loadSkeleton()
        }else{
            binding.sellerEditProfileLinearLayout.hideSkeleton()
        }
    }

    private fun setProgressBar(boolean : Boolean){
        if(boolean){
            binding.sellerEditProfileProgressBar.visibility = View.VISIBLE
        } else {
            binding.sellerEditProfileProgressBar.visibility = View.GONE
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                uri = data?.data!!
                Log.e(TAG, "aaa: "+uri, )

                getFile = uriToFile(uri as Uri, requireContext())

                Glide.with(requireContext()).load(uri).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setProgressBar(false)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setProgressBar(false)
                        return false

                    }

                }).into(binding.sellerEditProfileUploadProfilePictureImageView)

                checkEmptyField()
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                setProgressBar(false)
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                setProgressBar(false)
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun validateInput(){
        binding.sellerEditProfileInfoAddressTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerEditProfileInfoAddressTIL.error = if(s.toString().isEmpty()){
                    "Alamat lengkap tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.sellerEditProfileInfoOpenHourTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerEditProfileInfoOpenHourTIL.error = if(s.toString().isEmpty()){
                    "Jam buka operasional tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.sellerEditProfileInfoCloseHourTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerEditProfileInfoCloseHourTIL.error = if(s.toString().isEmpty()){
                    "Jam tutup operasional tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.sellerEditProfileInfoPaymentMethodTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerEditProfileInfoPaymentMethodTIET.error = if(s.toString().isEmpty()){
                    "Metode Pembayaran tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.sellerEditProfileInfoAccountTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerEditProfileInfoAccountTIL.error = if(s.toString().isEmpty()){
                    "Rekening tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })
    }

    private fun checkEmptyField(){
        binding.sellerEditProfileConfirmBtn.isEnabled =
            getFile !=null &&
            binding.sellerEditProfileInfoAddressTIET.text.toString().isNotEmpty() &&
                    binding.sellerEditProfileInfoOpenHourTIET.text.toString().isNotEmpty() &&
                    binding.sellerEditProfileInfoCloseHourTIET.text.toString().isNotEmpty() &&
                    binding.sellerEditProfileInfoPaymentMethodTIET.text.toString().isNotEmpty() &&
                    binding.sellerEditProfileInfoAccountTIET.text.toString().isNotEmpty() &&
                    binding.sellerEditProfileInfoAddressTIL.error == null &&
                    binding.sellerEditProfileInfoOpenHourTIL.error == null &&
                    binding.sellerEditProfileInfoCloseHourTIL.error == null &&
                    binding.sellerEditProfileInfoPaymentMethodTIL.error == null &&
                    binding.sellerEditProfileInfoAccountTIL.error == null
    }

    private fun setupEditText(){
        // Mendapatkan waktu saat ini sebagai default untuk timepicker
        val calendar = Calendar.getInstance()
        mHour = calendar.get(Calendar.HOUR_OF_DAY)
        mMinute = calendar.get(Calendar.MINUTE)
        binding.sellerEditProfileInfoOpenHourTIET.setOnClickListener {
            // Membuat instance dari TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    // Mengubah nilai variabel mHour dan mMinute sesuai dengan waktu yang dipilih
                    mHour = hourOfDay
                    mMinute = minute

                    // Mengubah teks pada TIET openhour dengan waktu yang dipilih
                    binding.sellerEditProfileInfoOpenHourTIET.setText(String.format("%02d:%02d", mHour, mMinute))
                },
                mHour,
                mMinute,
                true
            )

            // Menampilkan timepicker
            timePickerDialog.show()
        }

        binding.sellerEditProfileInfoCloseHourTIET.setOnClickListener {
            // Membuat instance dari TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    // Mengubah nilai variabel mHour dan mMinute sesuai dengan waktu yang dipilih
                    mHour = hourOfDay
                    mMinute = minute

                    // Mengubah teks pada TIET openhour dengan waktu yang dipilih
                    binding.sellerEditProfileInfoCloseHourTIET.setText(String.format("%02d:%02d", mHour, mMinute))
                },
                mHour,
                mMinute,
                true
            )

            // Menampilkan timepicker
            timePickerDialog.show()
        }

//        val paymentMethodItems = listOf("BCA", "Mandiri", "BNI")
//        val adapter = ArrayAdapter(requireContext(), R.layout.payment_method_list_item, paymentMethodItems)
//        (binding.sellerEditProfileInfoPaymentMethodTIET as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}