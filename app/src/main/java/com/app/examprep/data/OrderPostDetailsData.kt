package com.app.examprep.data


data class OrderPostDetailsData(
    var order_id : String,
    var order_amount : Int,
    var customer_details : CustomerDetails,
    var order_note : String
)
