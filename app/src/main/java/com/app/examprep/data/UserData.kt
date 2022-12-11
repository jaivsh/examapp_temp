package com.app.examprep.data


data class UserData(
    var id : String = "",
    var email : String = "",
    var joinedDate : String = "",
    var name : String = "",
    var myMaterials : List<String> = listOf(),
    var purchaseHistory  : List<PurchasHistoryData>  = listOf(),
    var profilePicUrl : String = "",
    var mobile : String = "",
    var testHistory : List<TestHistoryData> = listOf()
)
