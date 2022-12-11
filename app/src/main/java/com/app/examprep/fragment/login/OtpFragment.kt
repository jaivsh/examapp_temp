package com.app.examprep.fragment.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.app.examprep.R
import com.app.examprep.databinding.FragmentOtpBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.bulltradeintraday.app.others.SharedPref
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.*

class OtpFragment : Fragment(R.layout.fragment_otp) {

    lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks

    lateinit var binding : FragmentOtpBinding
    lateinit var myDialog: MyDialog
    lateinit var viewmodel : MainViewModel
    lateinit var sharedPref: SharedPref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtpBinding.bind(view)

        setUI(view)
    }

    private fun setUI(view: View) {

        viewmodel =
            ViewModelProvider(this).get(MainViewModel::class.java)
        myDialog = MyDialog(requireContext())

        binding.tvLoginClick.setOnClickListener {

            val mobileNumber = Constants.curUserData.mobile

                myDialog.showProgressDialog("Please wait...",this)
                sendOtp("+91$mobileNumber",callbacks)

        }


        binding.cdCreateaccount.setOnClickListener {

            val otp = binding.edMobilenumber.text.toString()

            if(otp.isEmpty()){
                Toast.makeText(requireContext(), "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }else{
                myDialog.showProgressDialog("Please wait...Verifying",this)
                verifyVerficationcode(otp)
            }
        }

        sharedPref = SharedPref(requireContext())

        viewmodel.userCreatedLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myDialog.dismissProgressDialog()
            sharedPref.setUserAuthStatus(true)
            sharedPref.saveUserName(Constants.curUserData.name)
            sharedPref.saveMobileNumber(Constants.curUserData.mobile)
            sharedPref.saveProfilePicUrl(Constants.curUserData.profilePicUrl)
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_otpFragment_to_landingFragment)
        })

        viewmodel.errorUserCreatedLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it,null)
        })

        callbacks  = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //  signUp(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                myDialog.dismissProgressDialog()
                myDialog.showErrorAlertDialog("Verification Failed ${e.message}")
                //     Toast.makeText(requireContext(),"Verification Failed ${e.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Constants.otp = verificationId
                myDialog.dismissProgressDialog()
            }
        }



    }

    fun signUp(credential: PhoneAuthCredential){
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    Constants.curUserData.apply {
                        this.id = "${user?.uid}"
                        this.joinedDate = Calendar.getInstance().timeInMillis.toString()
                    }
                    viewmodel.createUser(Constants.curUserData)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        myDialog.dismissProgressDialog()
                        Toast.makeText(requireContext(),"OTP wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun verifyVerficationcode(code : String){
        val credential = PhoneAuthProvider.getCredential(Constants.otp, code)
        signUp(credential)
    }

}