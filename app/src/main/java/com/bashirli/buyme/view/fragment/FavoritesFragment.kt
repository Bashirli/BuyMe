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
import com.bashirli.buyme.adapter.ProductsAdapter
import com.bashirli.buyme.databinding.FragmentFavoritesBinding
import com.bashirli.buyme.model.FavoritesModel
import com.bashirli.buyme.viewmodel.FavoritesMVVM

class FavoritesFragment : Fragment() {

    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesMVVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFavoritesBinding.bind(view)
        viewModel=ViewModelProvider(requireActivity()).get(FavoritesMVVM::class.java)
        setupData()
        onClick()

    }

    private fun onClick(){
        binding.goBackFav.setOnClickListener { findNavController().popBackStack() }
    }


    private fun setupData(){

        viewModel.loading.observe(viewLifecycleOwner){
            if(it){
            binding.progressBar2.visibility=View.VISIBLE
            binding.recyclerFavorites.visibility=View.GONE
            }
        }
        viewModel.errorData.observe(viewLifecycleOwner){
            if(it){
                binding.progressBar2.visibility=View.GONE
                binding.recyclerFavorites.visibility=View.GONE
                Toast.makeText(requireContext(),viewModel.errorMessage,Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.success.observe(viewLifecycleOwner){
            if(it){
               viewModel.favList.observe(viewLifecycleOwner){myList->
                   binding.recyclerFavorites.layoutManager=LinearLayoutManager(requireContext())
                   binding.recyclerFavorites.adapter=ProductsAdapter(ArrayList(myList))
                   println(myList)
                   binding.progressBar2.visibility=View.GONE
                   binding.recyclerFavorites.visibility=View.VISIBLE
               }
            }
        }

    }

    override fun onResume() {
        super.onResume()
      viewModel.refreshPage()
    }

}