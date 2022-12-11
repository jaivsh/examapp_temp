package com.app.examprep.fragment.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.app.examprep.R
import com.app.examprep.databinding.FragmentLoginBinding
import com.app.examprep.databinding.FragmentLoginMobileBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class MobileLoginFragment : Fragment(R.layout.fragment_login_mobile) {


    lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var binding : FragmentLoginMobileBinding
    lateinit var myDialog: MyDialog
    lateinit var viewmodel : MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginMobileBinding.bind(view)

        setUI(view)
    }

    private fun setUI(view: View) {

        myDialog = MyDialog(requireContext())

        viewmodel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        binding.tvLoginClick.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_mobileLoginFragment_to_registerFragment)
        }


        viewmodel.noProfileLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == "0"){
                myDialog.dismissProgressDialog()
                myDialog.showErrorAlertDialog("No Account Found")
            }else{
                val mobileNumber = binding.edMobilenumber.text.toString()
                //sendOtp("+91$mobileNumber",callbacks)
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_mobileLoginFragment_to_otpFragment)

            }
        })




        binding.cdCreateaccount.setOnClickListener {

            val mobileNumber = binding.edMobilenumber.text.toString()

            if(mobileNumber.isEmpty()){
                Toast.makeText(requireContext(), "Please Enter Mobile Number details", Toast.LENGTH_SHORT).show()
            }else{
                //myDialog.showProgressDialog("Please wait...",this)
                viewmodel.getUserData(mobileNumber)
            }
        }

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
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_mobileLoginFragment_to_otpFragment)
            }
        }


    }


}

