package com.app.examprep.data

data class CourseData(
    val courseId : String = "",
    val courseName : String = "",
    val price : String = "",
    val actualPrice : String = "",
    val courseImg : String = "",
    val lessons : List<LessonData>  = listOf(),
    val type : String = "",
    var status : String = ""
)
data class LessonData(
    val lessonCount : Int = 0,
    val name : String = "",
    val category : String = "",
    val teacher : String = "",
    val status : String = "",
    val content : List<ContentData>  = listOf()
)
data class ContentData(
    val name : String = "",
    val type : String = "",
    val status : String = "",
    val url : String = "",
    val testData : TestData = TestData()
)
data class TestData(
    val id : String = "",
    val questions : List<QuestionData>  = listOf()
)
data class TestHistoryData(
    val id : String = "",
    val questions : List<QuestionData>  = listOf(),
    val date : String = "",
    var name : String = ""
)
data class  QuestionData(
    val questionNo : Int = 0,
    val question : String = "",
    val option1 : String = "",
    val option2 : String = "",
    val option3 : String = "",
    val option4 : String = "",
    val answer : Int = 0,
    var choosenAnswer : Int = 0
)