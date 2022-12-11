package com.app.examprep

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.examprep.data.IssuesData
import com.app.examprep.data.PurchasHistoryData
import com.app.examprep.data.PurchaseData
import com.app.examprep.databinding.ActivityMainBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.MyDialog
import com.bulltradeintraday.app.others.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var sharePref: SharedPref
    lateinit var myDialog: MyDialog
    lateinit var mainViewModel: MainViewModel
    var userid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val navController = this.findNavController(R.id.main_fragment)
        // Find reference to bottom navigation view
        val navView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)

        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        sharePref = SharedPref(this)
        myDialog = MyDialog(this)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.profileFragment,R.id.purchaseHistoryFragment,R.id.myOrdersFragment,R.id.contactUsFragment,R.id.aboutUsFragment,R.id.privacyPolicyFragment,R.id.tncFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

//        val headerView = navView.getHeaderView(0)
//        val name = headerView.findViewById<TextView>(R.id.tv_name_home)
//        val user_name = headerView.findViewById<TextView>(R.id.logged_user)
//        val profilePic = headerView.findViewById<ImageView>(R.id.imageView)


//        if(sharePref.getUserName() != "null"){
//            name.text = sharePref.getUserName()
//            user_name.text = sharePref.getUserName()
//        }

//        if(sharePref.getProfilePicUrl() != "null"){
//            profilePic.setImageResource(R.drawable.examcorner)
//        }
//
        userid  =   sharePref.getMobileNumber()

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when(destination.id) {

                R.id.loginFragment,R.id.splashScreenFragment,
                R.id.quizFragment,R.id.videoViewFragment,R.id.mobileLoginFragment,
                R.id.registerFragment,R.id.otpFragment, R.id.LandingPageFragment,
                R.id.homeFragment, R.id.favourites, R.id.settings -> hideToolbar()

                else -> showToolbar()

            }
            when(destination.id) {
                R.id.loginFragment,R.id.mobileLoginFragment,R.id.quizFragment,R.id.videoViewFragment,
                R.id.splashScreenFragment,R.id.registerFragment,R.id.otpFragment -> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }

        checkPendingOrders()

    }

    private fun checkPendingOrders() {
        mainViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        val courseData = sharePref.getCourseData()
        val orderId = sharePref.getOrderID()


        if(sharePref.getPaymentError() && courseData != null){
            myDialog.showProgressDialogForActivity("Money Deducted, We are processing the payment...Please wait",this)
            mainViewModel.updatePurchase(PurchasHistoryData(Calendar.getInstance().time.toString(),courseData.courseId,courseData = courseData,money = courseData.price,orderId = orderId),courseData.courseId)
        }

        mainViewModel.createPurchaseLive.observe(this, androidx.lifecycle.Observer {

            sharePref.updatePaymentError(false)
            myDialog.dismissProgressDialog()
            onBackPressed()

        })

        mainViewModel.errorCreatePurchaseLive.observe(this, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)

        })

        mainViewModel.updatePurchaseLive.observe(this, androidx.lifecycle.Observer {


            mainViewModel.createPurchase(
                PurchaseData(orderId,"${FirebaseAuth.getInstance().uid}",courseData!!.price,
                    listOf(courseData.courseId), Calendar.getInstance().time.toString())
            )

        })

        mainViewModel.errorupdatePurchaseLive.observe(this, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)

        })

//        binding.navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
//            showAlertDialogForLogout()
//            true
//        }


    }

    private fun showSubmitIssueDialog() {

        lateinit var dialog : androidx.appcompat.app.AlertDialog

        val customview =  layoutInflater.inflate(R.layout.rv_submit_issue,null,false)


        val edittext = customview.findViewById<EditText>(R.id.ed_issue)
        val submit = customview.findViewById<MaterialButton>(R.id.bt_submit)

        val builder = MaterialAlertDialogBuilder(this)
            .setView(customview)

        submit.setOnClickListener {
            val issues = edittext.text.toString()
            if(issues.isNotEmpty()){
                mainViewModel.submitIssue(IssuesData(issues,userid))
                dialog.dismiss()
            }else{
                Toast.makeText(this, "Please enter issue before submit", Toast.LENGTH_SHORT).show()
            }
        }

        dialog = builder.show()

    }

    override fun onBackPressed() {

        if(navHostFragment.childFragmentManager.backStackEntryCount == 0){
            showAlertDialog()
        }else{
            super.onBackPressed()
        }

    }

    fun showAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Hold on!")
            .setMessage("Are you sure you want to Exit app ?")
            .setPositiveButton("Yes"){  _,_ ->
                finish()
            }
            .setNegativeButton("No",null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> {
                share()
                return true
            }
            R.id.submitissue -> {
                showSubmitIssueDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.options_menu, menu)
//        return true
//    }


    fun hideToolbar(){
        binding.appBarMain.toolbar.visibility = View.GONE
    }

    fun showToolbar(){
        binding.appBarMain.toolbar.visibility = View.VISIBLE
    }

    fun showAlertDialogForLogout() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Logout!")
            .setMessage("Are you sure want to logout ?")
            .setPositiveButton("Yes"){ _,_ ->
                sharePref.setUserAuthStatus(false)
                sharePref.saveUserName("")
                sharePref.saveMobileNumber("")
                sharePref.saveProfilePicUrl("")
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            .setNegativeButton("No",null)
            .show()
    }

    private fun share() {
        val bitmap =  BitmapFactory.decodeResource(resources,
            R.drawable.examcorner)
        val randomString = UUID.randomUUID().toString().substring(0,15)
        try {
            val file = File(this.externalCacheDir, "pic_${randomString}.png")
            val outputStrem = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStrem)
            outputStrem.flush()
            outputStrem.close()
            file.setReadable(true, false)
            val imageuri = FileProvider.getUriForFile(
                this,
                "com.app.examprep.provider",
                file)
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val text =
                "Hey, Please check this app  https://play.google.com/store/apps/details?id=$packageName"
            intent.putExtra(Intent.EXTRA_TEXT,text)
            intent.putExtra(Intent.EXTRA_STREAM,imageuri)
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "share by"))
        }catch (e: Exception){
            Toast.makeText(this,"${e.message}", Toast.LENGTH_SHORT).show()
        }
    }



}