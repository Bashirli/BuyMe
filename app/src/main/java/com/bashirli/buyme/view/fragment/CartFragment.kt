package com.bashirli.buyme.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.buyme.R
import com.bashirli.buyme.adapter.CartAdapter
import com.bashirli.buyme.databinding.FragmentCartBinding
import com.bashirli.buyme.model.Cart
import com.bashirli.buyme.model.CartModel
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.viewmodel.CartMVVM
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    private lateinit var viewModel:CartMVVM
    private var isPaymentSelected:Boolean=false
    private lateinit var adapter:CartAdapter
    private lateinit var dataList: ArrayList<CartModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentCartBinding.bind(view)
        viewModel=ViewModelProvider(requireActivity()).get(CartMVVM::class.java)
        loadData()
        setupButtons()

        onClick()
    }

    private fun onClick(){
        binding.imageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun recyclerEdit(){
     var callback=object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
         override fun onMove(
             recyclerView: RecyclerView,
             viewHolder: RecyclerView.ViewHolder,
             target: RecyclerView.ViewHolder,
         ): Boolean {
             return false
         }

         override fun onChildDraw(
             c: Canvas,
             recyclerView: RecyclerView,
             viewHolder: RecyclerView.ViewHolder,
             dX: Float,
             dY: Float,
             actionState: Int,
             isCurrentlyActive: Boolean
         ) {
             RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                 .addSwipeLeftBackgroundColor(Color.RED)
                 .addSwipeLeftActionIcon(R.drawable.deleteico)
                 .create().decorate()
             super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
         }

         override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
             var position=viewHolder.bindingAdapterPosition
             var deletedData=dataList.get(position)
             var cart= Cart(dataList.get(position).productData.id,
             deletedData.productCount,
             deletedData.productData.title)
             viewModel.removeFromDB(dataList.get(position).productData.id)
             dataList.removeAt(position)
             adapter.notifyItemRemoved(position)
            calculateTotalCost(dataList)
             Snackbar.make(viewHolder.itemView,"Product deleted from cart.",Snackbar.LENGTH_LONG)
                 .setAction("Undo"){
                     viewModel.insertData(cart)
                     dataList.add(position,deletedData)
                     adapter.notifyItemInserted(position)

                 }.show()

         }

     }


        var itemTouchHelper=ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }


    private fun setupButtons(){

        binding.slidingButton.setOnStateChangeListener {
            if(isPaymentSelected){
                binding.slidingButton.visibility=View.GONE
                binding.addSucImage.visibility=View.VISIBLE
                viewModel.clearData()
                val dialog=Dialog(requireContext())
                dialog.setContentView(R.layout.customdialog)
                dialog.window?.attributes?.windowAnimations = R.style.animation;
                dialog.setCancelable(false)
                dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                dialog.show()
                var handler= Handler()
                handler.postDelayed({
                    dialog.dismiss()
                    findNavController().popBackStack()
                },1300)


            }else{
                var alert=AlertDialog.Builder(requireContext())
                alert.setCancelable(false).setTitle(R.string.attention).setMessage("Failed to buy! Select payment type!")
                    .setNegativeButton("Ok"){
                        dialog,which->
                    return@setNegativeButton
                    }.create().show()
            }
        }



        binding.paymentOptions.setOnClickListener {
            if(binding.paymentType.visibility==View.GONE)
            {
                binding.paymentType.visibility=View.VISIBLE
                binding.linearLayout5.visibility=View.GONE
                binding.totalCost.visibility=View.GONE
                binding.buttonIco.setImageResource(R.drawable.bottomarrowico)
            }else{
                binding.paymentType.visibility=View.GONE
                binding.linearLayout5.visibility=View.VISIBLE
                binding.totalCost.visibility=View.VISIBLE
                binding.buttonIco.setImageResource(R.drawable.nextico)
            }
        }


        binding.cashOn.setOnClickListener {
            isPaymentSelected=true
        }
        binding.withCart.setOnClickListener {
            isPaymentSelected=true
        }

    }

    private fun loadData(){
        viewModel.loadData()
        viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                binding.constraintLayout.visibility=View.GONE
                binding.recyclerView.visibility=View.GONE
                binding.progressBarCart.visibility=View.VISIBLE
            }
        }
        viewModel.errorData.observe(viewLifecycleOwner){
            if(it){
                binding.constraintLayout.visibility=View.GONE
                binding.recyclerView.visibility=View.GONE
                binding.progressBarCart.visibility=View.GONE
                Toast.makeText(requireContext(),viewModel.errorMessage,Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.success.observe(viewLifecycleOwner){
            if(it){
            viewModel.favoriteData.observe(viewLifecycleOwner){
                dataList= ArrayList(it)
                calculateTotalCost(dataList)
                recyclerEdit()
                binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
                adapter= CartAdapter(dataList)
                binding.recyclerView.adapter=adapter

                binding.constraintLayout.visibility=View.VISIBLE
                binding.recyclerView.visibility=View.VISIBLE
                binding.progressBarCart.visibility=View.GONE
            }
            }
        }

    }

    private fun calculateTotalCost(arrayList:ArrayList<CartModel>){
      var total:Float=0f
        for(data in arrayList){
            total+=data.productData.price.toFloat()*data.productCount
        }
        binding.totalCost.setText("Total cost : ${total} $")
    }


}