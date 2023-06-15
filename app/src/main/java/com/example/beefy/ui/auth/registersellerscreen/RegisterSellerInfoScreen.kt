package com.example.beefy.ui.auth.registersellerscreen

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentRegisterSellerInfoScreenBinding
import com.example.beefy.utils.Resource
import org.koin.android.ext.android.inject
import java.util.Calendar


class RegisterSellerInfoScreen : Fragment() {

    private var _binding: FragmentRegisterSellerInfoScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idToko: String

    private val registerSellerViewModel: RegisterSellerViewModel by inject()

    private var mHour: Int = 0
    private var mMinute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idToko = requireArguments().getString("id").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterSellerInfoScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLoading(false)
        checkEmptyField()
        setupEditText()
        validateInput()
        setupObserver()
        setupButton()

    }

    private fun setupObserver() {
        registerSellerViewModel.registerEditPenjual.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> setLoading(true)

                is Resource.Success -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerSellerInfoScreen_to_loginScreenFragment)
                }
            }
        }
    }

    private fun setupButton() {
        binding.registerSellerConfirmDataBtn.setOnClickListener {
            registerSellerViewModel.editPenjual(
                idToko,
                binding.registerSellerInfoAddressTIET.text.toString(),
                binding.registerSellerInfoOpenHourTIET.text.toString(),
                binding.registerSellerInfoCloseHourTIET.text.toString(),
                binding.registerSellerInfoPaymentMethodTIET.text.toString(),
                binding.registerSellerInfoAccountTIET.text.toString()
            )
        }
    }

    private fun validateInput() {
        binding.registerSellerInfoAddressTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerInfoAddressTIL.error = if (s.toString().isEmpty()) {
                    "Alamat lengkap tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerSellerInfoOpenHourTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerInfoOpenHourTIL.error = if (s.toString().isEmpty()) {
                    "Jam buka operasional tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerSellerInfoCloseHourTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerInfoCloseHourTIL.error = if (s.toString().isEmpty()) {
                    "Jam tutup operasional tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerSellerInfoPaymentMethodTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerInfoPaymentMethodTIET.error = if (s.toString().isEmpty()) {
                    "Metode Pembayaran tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerSellerInfoAccountTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerInfoAccountTIL.error = if (s.toString().isEmpty()) {
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

    private fun checkEmptyField() {
        binding.registerSellerConfirmDataBtn.isEnabled =
            binding.registerSellerInfoAddressTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerInfoOpenHourTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerInfoCloseHourTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerInfoPaymentMethodTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerInfoAccountTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerInfoAddressTIL.error == null &&
                    binding.registerSellerInfoOpenHourTIL.error == null &&
                    binding.registerSellerInfoCloseHourTIL.error == null &&
                    binding.registerSellerInfoPaymentMethodTIL.error == null &&
                    binding.registerSellerInfoAccountTIL.error == null
    }

    private fun setupEditText() {
        // Mendapatkan waktu saat ini sebagai default untuk timepicker
        val calendar = Calendar.getInstance()
        mHour = calendar.get(Calendar.HOUR_OF_DAY)
        mMinute = calendar.get(Calendar.MINUTE)
        binding.registerSellerInfoOpenHourTIET.setOnClickListener {
            // Membuat instance dari TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    // Mengubah nilai variabel mHour dan mMinute sesuai dengan waktu yang dipilih
                    mHour = hourOfDay
                    mMinute = minute

                    // Mengubah teks pada TIET openhour dengan waktu yang dipilih
                    binding.registerSellerInfoOpenHourTIET.setText(
                        String.format(
                            "%02d:%02d",
                            mHour,
                            mMinute
                        )
                    )
                },
                mHour,
                mMinute,
                true
            )

            // Menampilkan timepicker
            timePickerDialog.show()
        }

        binding.registerSellerInfoCloseHourTIET.setOnClickListener {
            // Membuat instance dari TimePickerDialog
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    // Mengubah nilai variabel mHour dan mMinute sesuai dengan waktu yang dipilih
                    mHour = hourOfDay
                    mMinute = minute

                    // Mengubah teks pada TIET openhour dengan waktu yang dipilih
                    binding.registerSellerInfoCloseHourTIET.setText(
                        String.format(
                            "%02d:%02d",
                            mHour,
                            mMinute
                        )
                    )
                },
                mHour,
                mMinute,
                true
            )

            // Menampilkan timepicker
            timePickerDialog.show()
        }

        val paymentMethodItems = listOf("BCA", "Mandiri", "BNI")
        val adapter =
            ArrayAdapter(requireContext(), R.layout.payment_method_list_item, paymentMethodItems)
        (binding.registerSellerInfoPaymentMethodTIET as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setLoading(boolean: Boolean) {
        binding.registerSellerInfoProgressbar.visibility = if (
            boolean
        ) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}