package com.bashirli.buyme.view.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashirli.buyme.viewmodel.EquipmentMVVM
import com.bashirli.buyme.R
import com.bashirli.buyme.adapter.ImagesAdapter
import com.bashirli.buyme.adapter.ProductsAdapter
import com.bashirli.buyme.databinding.FragmentEquipmentsBinding
import com.bashirli.buyme.model.ProductData


class EquipmentsFragment : Fragment() {

    private lateinit var viewModel: EquipmentMVVM

    private lateinit var binding:FragmentEquipmentsBinding
    private lateinit var adapter:ProductsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_equipments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentEquipmentsBinding.bind(view)
        viewModel=ViewModelProvider(requireActivity()).get(EquipmentMVVM::class.java)
        observeLoad()
        update()
        movingEdit()



    }


    private fun update(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateData()
            binding.swipeRefreshLayout.isRefreshing=false
        }
  /*  binding.linearLayout.setOnClickListener {
        binding.swipeRefreshLayout.isEnabled=true
    }
   binding.recyclerEquipments.addOnScrollListener(object:RecyclerView.OnScrollListener(){

       override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
           super.onScrollStateChanged(recyclerView, newState)
           binding.swipeRefreshLayout.isEnabled=false
       }
   })

   */
    }

    private fun movingEdit(){
        val displayMetrics = DisplayMetrics()
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        binding.recyclerEquipments.minimumHeight=height
        binding.scrollView.setOnScrollChangeListener(object : View.OnScrollChangeListener{
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int,
            ) {

                if(scrollY>binding.mainText.y){
                    var manager=LinearLayoutManager(requireContext())
                    binding.recyclerEquipments.layoutManager=manager
                }else{
                    var manager=object:LinearLayoutManager(requireContext()){
                        override fun canScrollVertically(): Boolean {
                            return false
                        }
                    }
                    binding.recyclerEquipments.layoutManager=manager
                }
            }

        })
    }


    private fun observeLoad(){
        viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                binding.linearLayout.visibility=View.GONE
                binding.progressBar.visibility=View.VISIBLE
            }
        }
        viewModel.errorData.observe(viewLifecycleOwner){
            if(it){
                binding.linearLayout.visibility=View.GONE
                binding.progressBar.visibility=View.GONE
                Toast.makeText(requireContext(),viewModel.errorMessage?:"Error", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.success.observe(viewLifecycleOwner){
            if(it){
                binding.linearLayout.visibility=View.VISIBLE
                binding.progressBar.visibility=View.GONE
                viewModel.responseData.observe(viewLifecycleOwner){myList->
                    var manager=object:LinearLayoutManager(requireContext()){
                        override fun canScrollVertically(): Boolean {
                            return false
                        }
                    }
                    binding.recyclerEquipments.layoutManager=manager
                    adapter=ProductsAdapter(ArrayList(myList))
                    binding.recyclerEquipments.adapter=adapter

                    imageLoader(myList)
                }
            }
        }
    }

    private fun imageLoader(arrayList: List<ProductData>){
        binding.recyclerImages.layoutManager=LinearLayoutManager(
            requireContext()
            ,LinearLayoutManager.HORIZONTAL
            ,false)

        var newImageList=ArrayList<String>()

        for(productData in arrayList){
            for(image in productData.images){
                newImageList.add(image)
            }
        }


        binding.recyclerImages.adapter=ImagesAdapter(newImageList)

    }


}