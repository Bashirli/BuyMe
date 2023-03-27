package com.bashirli.buyme.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bashirli.buyme.R
import com.bashirli.buyme.databinding.ActivityProductBinding
import com.bashirli.buyme.model.Cart
import com.bashirli.buyme.model.ProductData
import com.bashirli.buyme.viewmodel.ProductMVVM
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity: AppCompatActivity() {
    private lateinit var binding:ActivityProductBinding
    private lateinit var productData:ProductData
     lateinit var viewModel:ProductMVVM
    private  var isLiked=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this).get(ProductMVVM::class.java)
         productData=intent.getSerializableExtra("data") as ProductData
        setupProduct()
        setupButtons()



    }

        private fun setupProduct(){
            binding.title.setText(productData.title)
            binding.category.setText(productData.category.name)
            binding.titleAndDescription.setText(productData.title+" - "+productData.description)
            binding.price.setText(productData.price+"$")
            binding.publishedTime.setText(productData.creationAt)
            binding.actualPrice.setText("Actual price"+" : "+productData.price+"$")

            var imagesList=ArrayList<SlideModel>()
            for(url in productData.images){
                imagesList.add(SlideModel(url))
            }
            binding.imageSlider.setImageList(imagesList)

            viewModel.loadFav(productData)
            viewModel.isLiked.observe(this@ProductActivity){
                if(it){
                    binding.likeButton.setImageResource(R.drawable.likeico)
                    isLiked=it
                }else{
                    binding.likeButton.setImageResource(R.drawable.emptylike)
                    isLiked=it
                }
            }


        }


    private fun setupButtons(){

        binding.addToCart.setOnClickListener {
            addToCartProcess()
        }


        binding.increaseButton.setOnClickListener {
            if(binding.numberOf.text.toString().toInt()>=20){
                Toast.makeText(this@ProductActivity,R.string.maxProduct,Toast.LENGTH_SHORT).show()
            }else{
                var number=binding.numberOf.text.toString().toInt()+1
                binding.numberOf.setText(number.toString())
                binding.actualPrice.setText("Actual price"+" : "+productData.price.toFloat()*number+"$")
            }
        }
        binding.decreaseButton.setOnClickListener {
            if(binding.numberOf.text.toString().toInt()>1){
                var number=binding.numberOf.text.toString().toInt()-1
                binding.numberOf.setText(number.toString())
                binding.actualPrice.setText("Actual price"+" : "+productData.price.toFloat()*number+"$")
            }
        }

        binding.goBack.setOnClickListener {
            finish()
        }

        binding.likeButton.setOnClickListener {
            if(isLiked){
                binding.likeButton.setImageResource(R.drawable.emptylike)
                viewModel.deleteFromFavorites(productData)
                isLiked=false
            }else{
                binding.likeButton.setImageResource(R.drawable.likeico)
                viewModel.addToFavorites(productData)
                isLiked=true
            }
        }



    }


    private fun addToCartProcess(){
        val cart=Cart(productData.id,binding.numberOf.text.toString().toInt(),productData.title)
        viewModel.addToCart(cart)
        binding.addToCart.visibility=View.GONE
        viewModel.success.observe(this@ProductActivity){
            if(it){
                Toast.makeText(this@ProductActivity,R.string.addSuc,Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.errorData.observe(this@ProductActivity){
            if(it){
                Toast.makeText(this@ProductActivity,viewModel.errorMessage,Toast.LENGTH_SHORT).show()
                binding.addToCart.visibility=View.VISIBLE
            }
        }

    }


}