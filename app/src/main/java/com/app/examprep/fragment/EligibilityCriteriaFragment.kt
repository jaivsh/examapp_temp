package com.app.examprep.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.DatePicker//OnDateChangedListener
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.app.examprep.R
import com.app.examprep.databinding.FragmentEligibilityBinding
import java.util.*
import java.text.SimpleDateFormat
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi


class EligibilityCriteriaFragment: Fragment(R.layout.fragment_eligibility) {

    lateinit var binding: FragmentEligibilityBinding
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEligibilityBinding.bind(view)


        // populating states while the user types input
        val autoPopulateStates: AutoCompleteTextView = binding.eligibilityState
        var states = arrayOf("Andhra Pradesh", "Arunachal Pradesh");
        val adaptData =
            ArrayAdapter(requireActivity(), android.R.layout.simple_dropdown_item_1line, states);
        autoPopulateStates.setAdapter(adaptData);
        autoPopulateStates.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.auto_complete_dropdown,
                null
            )
        )

        // DOB from tutorialkart.com

        val cal = Calendar.getInstance()
        // get the references from layout file
        val textViewDate : TextView = binding.TextViewDate

        textViewDate.setText("Enter DOB");
        textViewDate.setTextColor(Color.parseColor("#a6a6a6"))

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(cal)
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        textViewDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val dpd = DatePickerDialog(requireContext(),
                    R.style.MyDatePickerStyle,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH))
                dpd.show()
                dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#d0021b"))
                dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#d0021b"))
            }

        })




    // populating education qualification while the user types input
            val educationQualification: Spinner = binding.eligibilityEducation
            var education = arrayOf("10th class", "12th class", "Graduation", "Post Graduation");
            val educationData = ArrayAdapter(
                requireActivity(),
                R.layout.qualification_spinner, R.id.eligibilityText, education
            );

//        educationData.setDropDownViewResource(
//            android.R.layout
//                .simple_spinner_dropdown_item);
            educationQualification.setAdapter(educationData);


//       populating category qualification while the user types input
            val category_Qualification: Spinner = binding.category
            var caste_category = arrayOf("General", "OBC", "SC", "ST", "EWS");
            val category_Data = ArrayAdapter(
                requireActivity(),
                // change layout to category_spinner and add image files for caste/category in drawable folder
                R.layout.category_spinner, R.id.eligibilityText, caste_category
            ); // used qualification_spinner layout for category also
//        category_Data.setDropDownViewResource(
//            android.R.layout
//                .simple_spinner_dropdown_item);
            category_Qualification.setAdapter(category_Data);

//      drop down selection for category

            category_Qualification.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
            //  val selectedPosition:TextView = binding.selectedQualification;
            //   selectedPosition.setText(caste_category[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


            // dropdown selection
            educationQualification.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {

                   // val selectedPosition: TextView = binding.selectedQualification;
                 //   selectedPosition.setText(education[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
            setUI(view)


        }

        private fun setUI(view: View) {

            binding.eligibilityStateAction.setOnClickListener {
                callNextStep(view);
            }


        }

        fun callNextStep(view: View) {
            val state: EditText = binding.eligibilityState;
//        val getState:TextView = binding.state;
            val stateValue: String = state.text.toString();
//        val eligibilityAge:EditText = binding.eligibilityAge;
//        getState.setText(stateValue);

//        val eligibilityAge = EligibilityAge();
//        val fragment: Fragment? = supportFragmentManager

        }
        private fun updateDateInView(cal: Calendar) {
            val myFormat = "MM/dd/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val textViewDate : TextView = binding.TextViewDate
            textViewDate.setText(sdf.format(cal.time));
        }

    }
