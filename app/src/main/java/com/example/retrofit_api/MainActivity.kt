package com.example.retrofit_api

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.retrofit_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //https://meme-api.com/gimme



        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        getData()



        binding.newMeme.setOnClickListener {
            getData()
        }

        binding.shareButton.setOnClickListener {
            shareMemeImage()
        }
    }



    private fun getData() {

        //creating progress bar with message
        val progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is being fetched")
        progressDialog.show()


        //after writing enqueue press ctrl+shift+space and hit enter to import these
        retrofitInstance.apiInterface.getData().enqueue(object : Callback<responseDataClass?> {
override fun onResponse(
    call: Call<responseDataClass?>,
    response: Response<responseDataClass?>
) {
    progressDialog.dismiss()

    val responseData = response.body()
    if (responseData != null) {
        binding.memeTitle.text = responseData.title
        binding.memeAuthor.text = responseData.author

        val imageUrl = responseData.url
        //fetching meme into image view using glide library
        Glide.with(this@MainActivity).load(imageUrl).into(binding.memeImage)

        // Set the image URL as a tag to the ImageView
        binding.memeImage.tag = imageUrl
    } else {
        Toast.makeText(this@MainActivity, "Failed to get meme data.", Toast.LENGTH_SHORT).show()
    }
}


            override fun onFailure(call: Call<responseDataClass?>, t: Throwable) {
                Toast.makeText(this@MainActivity,"${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }
    private fun shareMemeImage() {
        val imageUrl = binding.memeImage.tag as? String
        if (imageUrl != null) {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, "Hi, check out this meme: $imageUrl")
            startActivity(Intent.createChooser(i, "Share this meme with"))
        } else {
            Toast.makeText(this, "No meme image to share.", Toast.LENGTH_SHORT).show()
        }


}}







