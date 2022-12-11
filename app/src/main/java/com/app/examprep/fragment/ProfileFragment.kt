package com.app.examprep.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.examprep.R
import com.app.examprep.databinding.FragmentProfileBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.bulltradeintraday.app.others.SharedPref
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

const val CHANGE_PROFILE = 101

class ProfileFragment : Fragment(R.layout.fragment_profile) {

     private lateinit var binding : FragmentProfileBinding
     lateinit var viewModel: MainViewModel
     lateinit var myDialog : MyDialog
    private val firebaseStorage = Firebase.storage.reference
    var profilePicUrl = ""
    lateinit var sharedPref: SharedPref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)


        setUI(view)


    }

    private fun setUI(view: View) {


        viewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)


        myDialog = MyDialog(requireContext())

         sharedPref = SharedPref(requireContext())


        if(Constants.curUserData.email.isEmpty()){
            binding.tvEmail.text = "${FirebaseAuth.getInstance().currentUser!!.email}"
        }else{
            binding.tvEmail.text = Constants.curUserData.email
        }

        binding.tvName.text = sharedPref.getUserName()


        if(sharedPref.getProfilePicUrl() != "null"){
            binding.ivProfile.setImageResource(R.drawable.avtar)
        }else{
            Glide.with(requireContext()).load(sharedPref.getProfilePicUrl()).into(binding.ivProfile)
        }



        binding.ivProfile.setOnClickListener {

            showAlertDialog()

        }

        viewModel.userProfileUpdatedLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myDialog.dismissProgressDialog()
            sharedPref.saveProfilePicUrl(profilePicUrl)
            Glide.with(requireContext()).load(sharedPref.getProfilePicUrl()).into(binding.ivProfile)
        })
        viewModel.errorUserProfileUpdatedLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)
        })
    }

    fun showAlertDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Profile Photo")
            .setMessage("Do you want to change your profile photo ?")
            .setPositiveButton("Yes"){  _,_ ->
                openGallery(CHANGE_PROFILE)
            }
            .setNegativeButton("No",null)
            .show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){

            when(requestCode){
                CHANGE_PROFILE -> {
                    data?.data?.let {
                       myDialog.showProgressDialog("Please wait...",this)
                        val id = UUID.randomUUID().toString().substring(0,15)
                        uploadImage(id,it)
                    }
                }
            }

        }
    }


    private fun uploadImage(fileName : String, it : Uri) {

        CoroutineScope(Dispatchers.Main).launch {
            firebaseStorage.child("profileImages/$fileName").putFile(it).addOnSuccessListener {
                firebaseStorage.child("profileImages/$fileName").downloadUrl.addOnSuccessListener {
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.updateProfilePic(it.toString(),sharedPref.getMobileNumber())
                        profilePicUrl = it.toString()
                    }
                }.addOnFailureListener {
                    myDialog.dismissProgressDialog()
                    myDialog.showErrorAlertDialog(it.message)
                }
            }.addOnFailureListener {
                myDialog.dismissProgressDialog()
                myDialog.showErrorAlertDialog(it.message)
            }
        }


    }



}

fun Fragment.openGallery(imgCode : Int) {
    Intent(Intent.ACTION_GET_CONTENT).also {
        it.type = "image/*"
        startActivityForResult(it, imgCode)
    }
}