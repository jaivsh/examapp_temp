package com.app.examprep.data

data class PurchaseData(
    var orderId : String = "",
    var userId : String = "",
    var amount : String = "",
    var coursesId : List<String>  = listOf(),
    var date : String = ""
)