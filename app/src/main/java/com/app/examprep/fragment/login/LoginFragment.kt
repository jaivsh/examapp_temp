package com.app.examprep.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.app.examprep.R
import com.app.examprep.databinding.FragmentLoginBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.bulltradeintraday.app.others.SharedPref
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

const val REQUEST_CODE = 0
class LoginFragment : Fragment(R.layout.fragment_login) {
    
    private lateinit var binding : FragmentLoginBinding
    lateinit var viewmodel : MainViewModel
    lateinit var myProgressDialog : MyDialog
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding = FragmentLoginBinding.bind(view)


        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        myProgressDialog = MyDialog(requireContext())

        binding.cdGooglelogin.setOnClickListener {

//            googleSignIn()
            val sharedPref = SharedPref(requireContext())
            myProgressDialog.dismissProgressDialog()
            myProgressDialog.showProgressDialog("Logging In...",this@LoginFragment)
            sharedPref.setUserAuthStatus(true)
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_loginFragment_to_homeFragment)

        }

        val sharedPref = SharedPref(requireContext())

        viewmodel.userCreatedLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myProgressDialog.dismissProgressDialog()
            sharedPref.setUserAuthStatus(true)
            sharedPref.saveUserName(Constants.curUserData.name)
            sharedPref.saveProfilePicUrl(Constants.curUserData.profilePicUrl)
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_loginFragment_to_homeFragment)
        })

        viewmodel.errorUserCreatedLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myProgressDialog.dismissProgressDialog()
            myProgressDialog.showErrorAlertDialog(it,null)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                account?.let {
                    googleAuthForFirebase(account)
                }
            } catch (e: Exception) {
                myProgressDialog.showErrorAlertDialog(e.message)
            }
        } else{
            myProgressDialog.showErrorAlertDialog("Error requestcode")
        }
    }

    private fun googleSignIn() {

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclient_id))
            .requestEmail()
            .build()
        val signInClient = GoogleSignIn.getClient(requireContext(),options)
        signInClient.signInIntent.also {
            startActivityForResult(it, REQUEST_CODE)
        }

    }

    private fun googleAuthForFirebase(account : GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
        CoroutineScope(Dispatchers.IO).launch {
            firebaseAuth.signInWithCredential(credentials).addOnSuccessListener {
                Constants.curUserData.apply {
                    if (it.user?.isEmailVerified == true) {
                        val email = it.user?.email
                        val displayName = it.user?.displayName ?: "No name"
                        if (email != null) {
                            this.id = email
                            this.name =  displayName
                            this.email = email
                            this.profilePicUrl = "${it.user?.photoUrl}"
                            this.joinedDate = Calendar.getInstance().timeInMillis.toString()
                            viewmodel.createUser(Constants.curUserData)
                            myProgressDialog.showProgressDialog("Logging In...",this@LoginFragment)
                        } else {
                            myProgressDialog.showErrorAlertDialog("Please signin later")
                        }
                    }
                }
            }.addOnFailureListener {
                myProgressDialog.showErrorAlertDialog("${it.message}")
            }
        }
    }

}