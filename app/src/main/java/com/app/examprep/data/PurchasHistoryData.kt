package com.app.examprep.data

data class PurchasHistoryData(
    var date : String = "",
    var courseID : String = "",
    var money : String = "",
    var orderId : String = "",
    var courseData : CourseData = CourseData()
)