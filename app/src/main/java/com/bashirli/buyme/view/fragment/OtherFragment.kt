package com.bashirli.buyme.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.buyme.R
import com.bashirli.buyme.adapter.ProductsAdapter
import com.bashirli.buyme.databinding.FragmentOtherBinding
import com.bashirli.buyme.viewmodel.OtherMVVM

class OtherFragment : Fragment() {

    private lateinit var viewModel:OtherMVVM
    private lateinit var binding:FragmentOtherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentOtherBinding.bind(view)
        viewModel=ViewModelProvider(requireActivity()).get(OtherMVVM::class.java)
        observeData()
        setUpdate()

    }


    private fun setUpdate(){
      binding.swipeRefreshLayoutOther.setOnRefreshListener {
          viewModel.updateData()
          binding.swipeRefreshLayoutOther.isRefreshing=false
      }
    }


    private fun observeData(){
        viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                binding.otherLinearLayout.visibility=View.GONE
                binding.otherProgressBar.visibility=View.VISIBLE
            }
        }

        viewModel.errorData.observe(viewLifecycleOwner){
            if(it){
                binding.otherLinearLayout.visibility=View.GONE
                binding.otherProgressBar.visibility=View.GONE
                Toast.makeText(requireContext(),viewModel.errorMessage,Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.success.observe(viewLifecycleOwner){
            if(it){
                binding.otherLinearLayout.visibility=View.VISIBLE
                binding.otherProgressBar.visibility=View.GONE
                viewModel.responseData.observe(viewLifecycleOwner){myList->
                binding.recyclerOther.layoutManager=LinearLayoutManager(requireContext())
                binding.recyclerOther.adapter=ProductsAdapter(ArrayList(myList))
                }

            }
        }


    }


}