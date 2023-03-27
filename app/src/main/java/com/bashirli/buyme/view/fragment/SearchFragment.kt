package com.bashirli.buyme.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashirli.buyme.R
import com.bashirli.buyme.adapter.ProductsAdapter
import com.bashirli.buyme.databinding.FragmentSearchBinding
import com.bashirli.buyme.viewmodel.SearchMVVM
import kotlinx.coroutines.*

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:SearchMVVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSearchBinding.bind(view)
        viewModel=ViewModelProvider(requireActivity()).get(SearchMVVM::class.java)
        setSearchChanged()
    }


    private fun setSearchChanged(){
       var job:Job?=null
      binding.searchBar.addTextChangedListener {
          job?.cancel()
         job=lifecycleScope.launch {
             delay(1000)
             it?.let {
                 if(it.toString().isNotEmpty()){
                     //search
                     viewModel.searchData(it.toString())
                     observeSearch()
                 }
             }
         }

      }

    }

    private fun observeSearch(){
        viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                binding.recyclerViewSearch.visibility=View.GONE
                binding.progressBarSearch.visibility=View.VISIBLE

            }
        }
        viewModel.errorData.observe(viewLifecycleOwner){
            if(it){
                binding.recyclerViewSearch.visibility=View.GONE
                binding.progressBarSearch.visibility=View.GONE

            }
        }
        viewModel.success.observe(viewLifecycleOwner){
            if(it){
                binding.recyclerViewSearch.visibility=View.VISIBLE
                binding.progressBarSearch.visibility=View.GONE
            viewModel.resultData.observe(viewLifecycleOwner){
                binding.recyclerViewSearch.layoutManager=LinearLayoutManager(requireContext())
                binding.recyclerViewSearch.adapter=ProductsAdapter(ArrayList(it))
            }
            }
        }
    }


}