package com.bashirli.buyme.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bashirli.buyme.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageURL(context:Context,url:String){
val options=RequestOptions.placeholderOf(placeHolder(context)).error(R.drawable.ic_launcher_background)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

private fun placeHolder(context: Context):CircularProgressDrawable{
    val circularProgressDrawable=CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth=8f
    circularProgressDrawable.centerRadius=40f
    circularProgressDrawable.start()
    return circularProgressDrawable

}