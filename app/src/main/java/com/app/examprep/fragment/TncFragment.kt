package com.app.examprep.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.examprep.R
import com.app.examprep.databinding.FragmentPrimaryBinding
import com.app.examprep.databinding.FragmentTncBinding

class TncFragment : Fragment(R.layout.fragment_tnc) {


    lateinit var binding : FragmentTncBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentTncBinding.bind(view)


        setUI(view)


    }

    private fun setUI(view: View) {

        binding.tvTitle.text = "Terms & Conditions"
        binding.tvNotes.text = "1.Acceptance of terms and conditions\n" +
                "When you complete the registration process or sign up to our\n" +
                "services, you agree to these Terms. These Terms may be\n" +
                "updated from time to time. Your continued use of our services\n" +
                "means that you accept any new or modified terms and\n" +
                "conditions that we come up with. These Terms will always be\n" +
                "available for users to read on this page.\n" +
                "2.Grant of Licence\n" +
                "Subject to your compliance with these Terms, we, hereby\n" +
                "grants you a revocable, non-exclusive, non-transferable,\n" +
                "limited license to download, install, and use our services for\n" +
                "limited use or access or view our networks and use our\n" +
                "services, solely for your personal and non-commercial\n" +
                "purposes and expressly conditioned upon your agreement\n" +
                "that all such access and use shall be governed by all of the\n" +
                "terms and conditions set forth in this Agreement. You have no\n" +
                "right to sublicense the license rights granted in this section.\n" +
                "3.Restrictions on usage\n" +
                "You will not use, copy, adapt, modify, prepare derivative works\n" +
                "based upon, distribute, license, sell, transfer, publicly display,\n" +
                "publicly perform, transmit, broadcast or otherwise exploit the\n" +
                "Site, Application, Services, or Collective Content, except as\n" +
                "expressly permitted in these Terms. No licenses or rights are\n" +
                "granted to you by implication or otherwise under any\n" +
                "intellectual property rights owned or controlled by Learning\n" +
                "Closet or its licensors, except for the licenses and rights\n" +
                "expressly granted in these Terms.\n" +
                "4.Privacy\n" +
                "In order to operate and provide the Service and the Mobile\n" +
                "App, we may collect certain information about You, including\n" +
                "technical and telemetry data related to your use of the Mobile\n" +
                "App. We may use third party service providers to help us\n" +
                "collect and analyse this data. The same shall not form breach\n" +
                "of privacy.\n" +
                "5.Authorization for communication\n" +
                "By downloading the Mobile App and/or accepting this\n" +
                "agreement, You authorize us to send You (including via email,\n" +
                "call and notifications) information regarding our services, such\n" +
                "as: (a) notices about Your use of the Subscription Service and\n" +
                "the Mobile App, including notices of violations of use; (b)\n" +
                "updates to the Subscription Service and Mobile App and new\n" +
                "features or products; and (c) promotional information and\n" +
                "materials regarding our products and services.\n" +
                "6.Termination of Agreement\n" +
                "We reserve the rights to suspend or terminate this Agreement\n" +
                "without notice if (i) you have materially breached these Terms\n" +
                "or our Policies, including the provisions in these Terms, (ii)\n" +
                "you have provided inaccurate, fraudulent, outdated or\n" +
                "incomplete information during the Account registration, or\n" +
                "Listing process or thereafter, (iii) you have violated applicable\n" +
                "laws, regulations or third party rights, or (iv) we believe in\n" +
                "good faith that such action is reasonably necessary to protect\n" +
                "the safety or property of VidhyaSoft Pvt. Ltd. or third parties,\n" +
                "for fraud prevention, risk assessment, security or investigation\n" +
                "purposes.\n" +
                "7.Refund policy\n" +
                "Subject to the mandatory condition that the request for refund\n" +
                "must be raised within 48 hours of making of the payment and\n" +
                "upon our satisfaction on the verification of the concerned\n" +
                "records regarding payment, the subscription fees paid by you\n" +
                "for any of our paid services may be refunded or adjusted, as\n" +
                "the case may be, by us in following cases only:\n" +
                "1. Where the fees for a single service is paid more than\n" +
                "once or double/extra amount paid/deducted for the\n" +
                "service.\n" +
                "2. Where any service/course was mistakenly purchased\n" +
                "instead of other, the fees paid shall be adjusted towards\n" +
                "the actual service/course you wanted to opt. The excess\n" +
                "fees paid if any shall be refunded to you and deficiency if\n" +
                "any in the opted service/course fees, shall be recovered\n" +
                "from you at the time of allocating you the opted\n" +
                "service/course.\n" +
                "3. Where we could not start your purchased service/course\n" +
                "or the batch (in case of classes) is closed by us within 30\n" +
                "days of payment for that service/course made by you.\n" +
                "4. Where age is a parameter for any service/course and it is\n" +
                "discovered after verification of the documents that you\n" +
                "are over aged for that service/course. You must provide\n" +
                "all the records with your refund request, in support of\n" +
                "your claim within the time prescribed above. If we find the\n" +
                "records in support of your claim to be correct and your\n" +
                "claim falls within the cases mentioned above, we will\n" +
                "refund/ adjust your payment within 30 to 60 days from\n" +
                "the date of receiving of your refund request with\n" +
                "supporting payment records.\n" +
                "8.NO WARRANTY\n" +
                "YOUR USE OF OUR SERVICES IS AT YOUR SOLE RISK.\n" +
                "OUR SERVICES ARE PROVIDED ON AN “AS IS” AND “AS\n" +
                "AVAILABLE” BASIS. VIDHYASOFT PVT. LTD. EXPRESSLY\n" +
                "DISCLAIMS ALL WARRANTIES OF ANY KIND, WHETHER\n" +
                "EXPRESS OR IMPLIED, INCLUDING THE IMPLIED\n" +
                "WARRANTIES OF MERCHANTABILITY, FITNESS FOR A\n" +
                "PARTICULAR PURPOSE AND NON-INFRINGEMENT.\n" +
                "9.Representation\n" +
                "Although due care has been taken and every effort has been\n" +
                "made by us to ensure that the information/content offered\n" +
                "through our services and courses are correct, but the\n" +
                "possibility of errors, omissions and/or discrepancies cannot be\n" +
                "ruled out. Therefore, we do not assume and hereby disclaim\n" +
                "any liability to any party for any loss, damage, or disruption\n" +
                "caused by errors or omissions, whether such errors or\n" +
                "omissions result from negligence, accident, or any other\n" +
                "cause. Should there be any discrepancy, error or omission\n" +
                "noted in the content offered through our services, we will be\n" +
                "obliged, if the same is brought to our notice."

    }

}