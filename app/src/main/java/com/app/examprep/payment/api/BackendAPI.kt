package com.app.examprep.payment.api


import com.app.examprep.data.OrderPostDetailsData
import com.app.examprep.data.OrderResponseData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface BackendAPI {


    @POST("getOrderId")
    suspend fun getOrderId(
        @Body
        map : OrderPostDetailsData
    ) : Response<OrderResponseData>

}