package com.app.examprep.fragment.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.app.examprep.R
import com.app.examprep.databinding.FragmentRegisterBinding
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class RegisterFragment : Fragment(R.layout.fragment_register) {

    lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var binding : FragmentRegisterBinding
    lateinit var myDialog: MyDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

        setUI(view)
    }

    private fun setUI(view: View) {

        myDialog = MyDialog(requireContext())


        binding.tvLoginClick.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_registerFragment_to_mobileLoginFragment)
        }


        binding.cdCreateaccount.setOnClickListener {

            val username = binding.edUsername.text.toString()
            val mobileNumber = binding.edMobilenumber.text.toString()
            val email = binding.edEmail.text.toString()

            if(username.isEmpty() || mobileNumber.isEmpty() && email.isNotEmpty()){
                Toast.makeText(requireContext(), "Please Enter both details", Toast.LENGTH_SHORT).show()
            }else{
                Constants.curUserData.apply {
                    this.name = username
                    this.mobile = mobileNumber
                    this.email = email
                }
                myDialog.showProgressDialog("Please wait...",this)
                sendOtp("+91$mobileNumber",callbacks)
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
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_registerFragment_to_otpFragment)
            }
        }


    }


}
fun Fragment.sendOtp(
    phoneNo: String,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
){

    PhoneAuthProvider.getInstance().verifyPhoneNumber(
        phoneNo,
        60,
        TimeUnit.SECONDS,
        requireActivity(),
        callbacks
    )
}