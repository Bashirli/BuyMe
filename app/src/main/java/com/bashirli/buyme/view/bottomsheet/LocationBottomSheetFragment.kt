package com.bashirli.buyme.view.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bashirli.buyme.R
import com.bashirli.buyme.databinding.BottomSheetBinding
import com.bashirli.buyme.model.LocationModel
import com.bashirli.buyme.view.fragment.MapsFragmentDirections
import com.bashirli.buyme.viewmodel.LocationMVVM
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationBottomSheetFragment(var latLng: LatLng) : BottomSheetDialogFragment() {

    private lateinit var binding:BottomSheetBinding
    private lateinit var viewModel: LocationMVVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= BottomSheetBinding.bind(view)
        viewModel=ViewModelProvider(requireActivity()).get(LocationMVVM::class.java)
    onClick()

    }

    private fun onClick(){
        binding.saveButton.setOnClickListener {
            if(binding.nameText.text.toString().equals("")||
                    binding.nameText.text.toString().trim().equals("")){
                Toast.makeText(requireContext(),R.string.empty,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                observeData()
            }
        }
    }

    private fun observeData(){
        viewModel.insertLocation(LocationModel(
            binding.nameText.text.toString(),
            latLng.latitude,
            latLng.longitude
        ))
        viewModel.errorData.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(requireContext(),viewModel.errorMessage,Toast.LENGTH_LONG).show()
            }
        }
        viewModel.success.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(MapsFragmentDirections.actionMapsFragmentToMainFragment())
                onDestroyView()
            }
        }
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}