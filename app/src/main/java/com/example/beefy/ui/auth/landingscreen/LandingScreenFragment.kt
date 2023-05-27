package com.example.beefy.ui.auth.landingscreen

import android.Manifest
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentLandingScreenBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class LandingScreenFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding : FragmentLandingScreenBinding? = null
    private val binding get() = _binding!!

    private val cameraPerms = Manifest.permission.CAMERA
    private val galleryPerms = Manifest.permission.READ_EXTERNAL_STORAGE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLandingScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!hasCameraPerms()){
            requestCameraPerms()
        }
//
//        if(!hasGalleryPerms()){
//            requestGalleryPerms()
//        }


        setupButton()

    }

    private fun setupButton(){
        binding.landingScreenLoginBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_landingScreenFragment_to_loginScreenFragment)
        }

        binding.landingScreenRegisterBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_landingScreenFragment_to_registerConfirmationScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hasCameraPerms() =
        EasyPermissions.hasPermissions(
            requireContext(),
            cameraPerms
        )

    private fun hasGalleryPerms()=
        EasyPermissions.hasPermissions(
            requireContext(),
            galleryPerms
        )

    private fun requestCameraPerms(){
        EasyPermissions.requestPermissions(
            this,
            "Membutuhkan Akses Camera",
            1,
            cameraPerms,
        )
    }


    private fun requestGalleryPerms(){
        EasyPermissions.requestPermissions(
            this,
            "Membutuhkan Akses Gallery",
            2,
            galleryPerms,
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(requireActivity()).build().show()
        } else {
                requestCameraPerms()
        }


    }


}