package com.example.retrofit_api

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.retrofit_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        //https://meme-api.com/gimme

        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.newMeme.setOnClickListener {
            getData()
        }
    }

    private fun getData() {

        //creating progress bar with message
        val progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetch")
        progressDialog.show()


        //after writing enqueue press ctrl+shift+space and hit enter to import these
        retrofitInstance.apiInterface.getData().enqueue(object : Callback<responseDataClass?> {
            override fun onResponse(
                call: Call<responseDataClass?>,
                response: Response<responseDataClass?>
            ) {
                progressDialog.dismiss()

                binding.memeTitle.text=response.body()?.title
                binding.memeAuthor.text=response.body()?.author
                //fetching meme into image view using glide library
                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.memeImage);


            }

            override fun onFailure(call: Call<responseDataClass?>, t: Throwable) {
                Toast.makeText(this@MainActivity,"${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }
}