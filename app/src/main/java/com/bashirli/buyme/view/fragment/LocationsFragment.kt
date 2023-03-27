package com.bashirli.buyme.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashirli.buyme.R
import com.bashirli.buyme.adapter.LocationAdapter
import com.bashirli.buyme.databinding.FragmentLocationsBinding
import com.bashirli.buyme.viewmodel.LocationMVVM

class LocationsFragment : Fragment() {

    private lateinit var binding:FragmentLocationsBinding
    private lateinit var viewModel: LocationMVVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentLocationsBinding.bind(view)
        viewModel=ViewModelProvider(requireActivity()).get(LocationMVVM::class.java)
        onClicks()
        viewModel.loadLocations()
        observeData()
    }


    private fun onClicks(){
        binding.appCompatButton.setOnClickListener {
            findNavController().navigate(LocationsFragmentDirections.actionLocationsFragmentToMapsFragment())
        }
        binding.goBackLocation.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData(){
        viewModel.errorData.observe(viewLifecycleOwner){
            if(it){
                binding.recyclerViewLocation.visibility=View.GONE
                Toast.makeText(requireContext(),viewModel.errorMessage,Toast.LENGTH_LONG).show()
            }
        }

        viewModel.success.observe(viewLifecycleOwner){
            if(it){
                binding.recyclerViewLocation.visibility=View.VISIBLE
                viewModel.locationList.observe(viewLifecycleOwner){myList->
                    binding.recyclerViewLocation.layoutManager=LinearLayoutManager(requireContext())
                    binding.recyclerViewLocation.adapter=LocationAdapter(ArrayList(myList))
                }

            }
        }


    }



}