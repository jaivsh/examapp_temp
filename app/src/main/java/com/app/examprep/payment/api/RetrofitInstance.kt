package com.app.examprep.payment.api

import com.app.examprep.data.OrderResponseData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://fathomless-lake-53941.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(BackendAPI::class.java)
        }

    }

}