package com.app.examprep.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examprep.data.*
import com.app.examprep.others.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.reflect.Field

class MainViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val courseCollection = firestore.collection("course")
    private val materialsCollection = firestore.collection("materials")
    private val usersCollection = firestore.collection("users")
    private val purchasesCollection = firestore.collection("purchases")
    private val testsCollection = firestore.collection("tests")
    private val issuesCollection = firestore.collection("issues")

    private var _userCreatedLive = MutableLiveData<String>()
    var userCreatedLive : LiveData<String> = _userCreatedLive
    private var _errorUserCreatedLive = MutableLiveData<String>()
    var errorUserCreatedLive : LiveData<String> = _errorUserCreatedLive


    fun createUser(userData: UserData) =  viewModelScope.launch(Dispatchers.IO) {
        try {
            usersCollection.document(userData.mobile).get().addOnSuccessListener {
                if (!it.exists()) {
                    usersCollection.document(userData.mobile).set(userData).addOnSuccessListener {
                        _userCreatedLive.postValue("Success")
                    }.addOnFailureListener {
                        _errorUserCreatedLive.postValue(it.message)
                    }
                } else {
                    _userCreatedLive.postValue("Success")
                }
            }.addOnFailureListener {
                _errorUserCreatedLive.postValue(it.message)
            }
        } catch (e: Exception) {
            _errorUserCreatedLive.postValue(e.message)
        }
    }

    private var _userProfileUpdatedLive = MutableLiveData<String>()
    var userProfileUpdatedLive : LiveData<String> = _userProfileUpdatedLive
    private var _errorUserProfileUpdatedLive = MutableLiveData<String>()
    var errorUserProfileUpdatedLive : LiveData<String> = _errorUserProfileUpdatedLive

    fun updateProfilePic(profilePicUrl : String,mobile : String) = viewModelScope.launch(Dispatchers.IO){

        try {
            usersCollection.document(mobile).update(
                "profilePicUrl" , profilePicUrl
            ).addOnSuccessListener {
                _userProfileUpdatedLive.postValue("Updated")
            }.addOnFailureListener {
                _errorUserProfileUpdatedLive.postValue(it.message)
            }

        }catch (e:Exception){
            _errorUserProfileUpdatedLive.postValue(e.message)
        }

    }


    private var _myProfileLive = MutableLiveData<UserData>()
    var myProfileLive : LiveData<UserData> = _myProfileLive
    private var _errorMyProfileLive = MutableLiveData<String>()
    var errorMyProfileLive : LiveData<String> = _errorMyProfileLive
    private var _noProfileLive = MutableLiveData<String>()
    var noProfileLive : LiveData<String> = _noProfileLive

    fun getUserData(mobileNumber : String) = viewModelScope.launch(Dispatchers.IO){

            try {
                val data =
                    usersCollection.document(mobileNumber).get().await()
                        .toObject(UserData::class.java)
                if(data == null){
                    _noProfileLive.postValue("0")
                }else{
                    _noProfileLive.postValue("1")
                }
                data.let {
                    _myProfileLive.postValue(it)
                    Constants.curUserData = it!!
                }

            } catch (e: Exception) {
                _errorMyProfileLive.postValue(e.message)
            }

    }



    private var _courseLive = MutableLiveData<List<CourseData>>()
    var courseLive : MutableLiveData<List<CourseData>> = _courseLive
    private var _errorCourseLive = MutableLiveData<String>()
    var errorCourseLive : LiveData<String> = _errorCourseLive

    fun getCoursesData(type : String) = viewModelScope.launch(Dispatchers.IO){

        try {
            val data =  courseCollection.whereEqualTo("type",type).get().await().toObjects(CourseData::class.java)

            data.let {
                _courseLive.postValue(it)
            }

        }catch (e: Exception){
            _errorCourseLive.postValue(e.message)
        }

    }


    private var _testLive = MutableLiveData<List<ContentData>>()
    var testLive : MutableLiveData<List<ContentData>> = _testLive
    private var _errorTestLive = MutableLiveData<String>()
    var errorTestLive : LiveData<String> = _errorTestLive

    fun getTestData() = viewModelScope.launch(Dispatchers.IO){

        try {
            val data =  testsCollection.get().await().toObjects(ContentData::class.java)

            data.let {
                _testLive.postValue(it)
            }

        }catch (e: Exception){
            _errorTestLive.postValue(e.message)
        }

    }

    private var _materialsLive = MutableLiveData<List<ContentData>>()
    var materialsLive : MutableLiveData<List<ContentData>> = _materialsLive
    private var _errorMaterialsLive = MutableLiveData<String>()
    var errorMaterialsLive : LiveData<String> = _errorMaterialsLive

    fun getMaterialsData() = viewModelScope.launch(Dispatchers.IO){

        try {
            val data =  materialsCollection.get().await().toObjects(ContentData::class.java)

            data.let {
                _materialsLive.postValue(it)
            }

        }catch (e: Exception){
            _errorMaterialsLive.postValue(e.message)
        }

    }


    private var _updatePurchaseLive = MutableLiveData<String>()
    var updatePurchaseLive : LiveData<String> = _updatePurchaseLive
    private var _errorupdatePurchaseLive = MutableLiveData<String>()
    var errorupdatePurchaseLive : LiveData<String> = _errorupdatePurchaseLive

    fun updatePurchase(purchaseData : PurchasHistoryData, courseId : String) = viewModelScope.launch(Dispatchers.IO) {

        try {

            usersCollection.document(Constants.curUserData.mobile).update(
                mapOf(
                    "myMaterials" to FieldValue.arrayUnion(courseId),
                    "purchaseHistory" to FieldValue.arrayUnion(purchaseData)
                )
            ).addOnSuccessListener {
                _updatePurchaseLive.postValue("Added")
            }.addOnFailureListener {
                _errorupdatePurchaseLive.postValue(it.message)
            }
        }catch (e: Exception){
            _errorupdatePurchaseLive.postValue(e.message)
        }

    }

    private var _updateTestLive = MutableLiveData<String>()
    var updateTestLive : LiveData<String> = _updateTestLive
    private var _errorUpdateTestLive = MutableLiveData<String>()
    var errorUpdateTestLive : LiveData<String> = _errorUpdateTestLive

    fun updateTestData(testHistoryData: TestHistoryData) = viewModelScope.launch(Dispatchers.IO) {

        try {

            usersCollection.document(Constants.curUserData.mobile).update(
                mapOf(
                    "testHistory" to FieldValue.arrayUnion(testHistoryData)
                )
            ).addOnSuccessListener {
                _updateTestLive.postValue("Added")
            }.addOnFailureListener {
                _errorUpdateTestLive.postValue(it.message)
            }
        }catch (e: Exception){
            _errorUpdateTestLive.postValue(e.message)
        }

    }

    private var _createPurchaseLive = MutableLiveData<String>()
    var createPurchaseLive : LiveData<String> = _createPurchaseLive
    private var _errorCreatePurchaseLive = MutableLiveData<String>()
    var errorCreatePurchaseLive : LiveData<String> = _errorCreatePurchaseLive

    fun createPurchase(purchaseData : PurchaseData ) = viewModelScope.launch(Dispatchers.IO) {

        try {

            purchasesCollection.document(purchaseData.orderId).set(purchaseData).addOnSuccessListener {
                _createPurchaseLive.postValue("Added")
            }.addOnFailureListener {
                _errorCreatePurchaseLive.postValue(it.message)
            }
        }catch (e: Exception){
            _errorCreatePurchaseLive.postValue(e.message)
        }

    }


    private var _coursesLive = MutableLiveData<List<CourseData>>()
    var coursesLive : MutableLiveData<List<CourseData>> = _coursesLive
    private var _errorCoursesLive = MutableLiveData<String>()
    var errorCoursesLive : LiveData<String> = _errorCoursesLive

    fun getCoursesDataByID(ids : List<String>) = viewModelScope.launch(Dispatchers.IO){

        try {
            val courses = mutableListOf<CourseData>()
            for(id in ids){

                val data =  courseCollection.document(id).get().await().toObject(CourseData::class.java)
               if(data != null){
                   courses.add(data)
               }

            }
                _coursesLive.postValue(courses)

        }catch (e: Exception){
            _errorCoursesLive.postValue(e.message)
        }

    }

    fun submitIssue(issuesData: IssuesData) = viewModelScope.launch(Dispatchers.IO){

        try {

            issuesCollection.document().set(issuesData).addOnSuccessListener {

            }

        }catch (e: Exception){


        }

    }





}