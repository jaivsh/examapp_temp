package com.app.examprep.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.examprep.MainActivity
import com.app.examprep.data.*
import com.app.examprep.databinding.ActivityCheckoutBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.app.examprep.payment.api.CheckoutViewmodel
import com.bulltradeintraday.app.others.SharedPref
import com.cashfree.pg.api.CFPaymentGatewayService
import com.cashfree.pg.core.api.CFSession
import com.cashfree.pg.core.api.CFSession.CFSessionBuilder
import java.util.*
import com.cashfree.pg.core.api.CFTheme.CFThemeBuilder
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback
import com.cashfree.pg.core.api.utils.CFErrorResponse
import com.cashfree.pg.ui.api.CFDropCheckoutPayment.CFDropCheckoutPaymentBuilder
import com.google.firebase.auth.FirebaseAuth


class CheckoutActivity : AppCompatActivity(),CFCheckoutResponseCallback {

    private lateinit var binding: ActivityCheckoutBinding
    lateinit var checkoutViewmodel: CheckoutViewmodel
    lateinit var mainViewModel: MainViewModel
    lateinit var myDialog: MyDialog
    lateinit var sharedPref : SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()

    }

    private fun setUI() {

        checkoutViewmodel =
            ViewModelProvider(this).get(CheckoutViewmodel::class.java)

        mainViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)


        sharedPref = SharedPref(this)

        mainViewModel.getUserData(sharedPref.getMobileNumber())



        Constants.curCourse = sharedPref.getCourseData() ?: CourseData()


        myDialog = MyDialog(this)



        val userId = FirebaseAuth.getInstance().currentUser!!.uid


        myDialog.showProgressDialogForActivity("Please wait....",this)

        mainViewModel.myProfileLive.observe(this, androidx.lifecycle.Observer {

            val orderId = UUID.randomUUID().toString().substring(0,15)

            val orderPostData = OrderPostDetailsData(
                orderId,
                Constants.curCourse.price.toInt(),
              //  CustomerDetails(it.email,it.id,it.name,"9940824159")
                CustomerDetails(it.email,"$userId",it.name,it.mobile),
                Constants.curCourse.courseId
            )

            checkoutViewmodel.getOrderID(orderPostData)

        })

        mainViewModel.errorMyProfileLive.observe(this, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)

        })

        checkoutViewmodel.orderCreatedLive.observe(this, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()
            initiatePayment(it)

        })

        checkoutViewmodel.errorOrderCreatedLive.observe(this, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)

        })



        mainViewModel.createPurchaseLive.observe(this, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()
            val myMaterials = Constants.curUserData.myMaterials.toMutableList()
            myMaterials.add(Constants.curCourse.courseId)
            Constants.curUserData.myMaterials = myMaterials
            sharedPref.updatePaymentError(false)

            startActivity(Intent(this,MainActivity::class.java))
            finish()

        })

        mainViewModel.errorCreatePurchaseLive.observe(this, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)

        })

        mainViewModel.updatePurchaseLive.observe(this, androidx.lifecycle.Observer {

            mainViewModel.createPurchase(PurchaseData(Constants.orderId,userId,Constants.amount.toString(),
                listOf(Constants.courseId),Calendar.getInstance().time.toString()))

        })

        mainViewModel.errorupdatePurchaseLive.observe(this, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)

        })


        CFPaymentGatewayService.getInstance().setCheckoutCallback(this)

    }

    fun initiatePayment(orderResponseData: OrderResponseData){

        try {

            val cfSession = CFSessionBuilder()
                .setEnvironment(CFSession.Environment.PRODUCTION)
                .setOrderToken(orderResponseData.order_token)
                .setOrderId(orderResponseData.order_id)
                .build()

            Constants.orderId = orderResponseData.order_id

        /*    val cfPaymentComponent = CFPaymentComponentBuilder()
                .add(CFPaymentComponent.CFPaymentModes.CARD)
                .add(CFPaymentComponent.CFPaymentModes.UPI)
                .build()*/

            val cfTheme = CFThemeBuilder()
                .setNavigationBarBackgroundColor("#D0021B")
                .setNavigationBarTextColor("#FFFFFF")
                .setButtonBackgroundColor("#e7808c")
                .setButtonTextColor("#FFFFFF")
                .setPrimaryTextColor("#000000")
                .setSecondaryTextColor("#000000")
                .build()


            val cfDropCheckoutPayment = CFDropCheckoutPaymentBuilder()
                .setSession(cfSession)
              //  .setCFUIPaymentModes(cfPaymentComponent)
                .setCFNativeCheckoutUITheme(cfTheme)
                .build()


            val gatewayService = CFPaymentGatewayService.getInstance()
            gatewayService.doPayment(this, cfDropCheckoutPayment)

        }catch (e:Exception){
            myDialog.showErrorAlertDialog("Exception : ${e.message}")
        }

    }

    override fun onPaymentVerify(p0: String?) {

        val courseData = sharedPref.getCourseData()  ?: CourseData()
        Constants.courseId = courseData.courseId
        Constants.amount = courseData.price.toInt()
        sharedPref.updatePaymentError(true)
        sharedPref.updateOrderID(Constants.orderId)
        mainViewModel.updatePurchase(PurchasHistoryData(Calendar.getInstance().time.toString(),courseData.courseId,courseData = courseData,money = courseData.price,orderId = Constants.orderId),courseData.courseId)
        myDialog.showProgressDialogForActivity("Please Wait, Payment Processing... don't close your app",this)
     //     Toast.makeText(this,  "Payment Success", Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentFailure(p0: CFErrorResponse?, p1: String?) {

        myDialog.showErrorAlertDialog("ErrorResponse : $p0")

        onBackPressed()

    }

}