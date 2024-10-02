package com.example.blureagency.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentContactUsBinding
import com.example.blureagency.ui.viewmodel.ContactViewMode
import com.example.movies.utils.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@AndroidEntryPoint
class ContactUsFragment : Fragment(R.layout.fragment_contact_us) {

    private val contactViewModel by viewModels<ContactViewMode>()
private lateinit var binding: FragmentContactUsBinding
lateinit var  adapter : ArrayAdapter<String>

    val items = arrayOf("Services","Web App","Mobile App","Marketing","Security","Network","Video Edit","General")
    var customer_service:String = ""




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactUsBinding.bind(view)

         setupSpinner()


        binding.serviceSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                  customer_service = items[p2]
                Log.d("CUSTOMER_SERVICE",customer_service)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) { Log.d("CUSTOMER_SERVICE",customer_service.toString())}

        }

binding.contactFragmentContactUsBtn.setOnClickListener {
setupEmptyInputError()



    val email = binding.emailEt.text.toString()
    val phone = binding.phoneNumberEt.text.toString()
    val message  = binding.messageEt.text.toString()
    Log.d("CUSTOMER_SERVICE",email)
    Log.d("CUSTOMER_SERVICE",phone)
    Log.d("CUSTOMER_SERVICE",message)
    if (email.isEmpty()||phone.isEmpty()|| customer_service.isEmpty()||message.isEmpty()){
        Toast.makeText(requireContext(),resources.getString(R.string.make_sure_to_enter_all_fields),Toast.LENGTH_SHORT).show()
    }else{

val receptient = message.plus("\n\n Client Phone Number : $phone \n\nClient Email : $email")

        contactViewModel.send(requireContext(),"mhmoodadel1899@gmail.com",receptient,customer_service)


        lifecycleScope.launch {
            contactViewModel.clientMessageStatus.observe(viewLifecycleOwner,Observer{
                when(it){
                    is Resources.Error -> {
                        hidePR()
                        Toast.makeText(requireContext(),"Sorry Failed To Send Message",Toast.LENGTH_SHORT).show()
                    }
                    is Resources.Loading -> {
                      showPR()
                    }
                    is Resources.Success -> {
                        hidePR()

                        Toast.makeText(requireContext(),"Sending message Succeeded ",Toast.LENGTH_SHORT).show()
                          clearData()
                    }
                }
            })
        }


     /*

     // send using gmail and intent
     val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_EMAIL,email)
        intent.putExtra(Intent.EXTRA_SUBJECT,customer_service)
        intent.putExtra(Intent.EXTRA_TEXT,message)


        startActivity(Intent.createChooser(intent,"Choose an Email client :"))*/

    }
}






    }

    private fun showPR(){
        binding.contactBtnPr.visibility = View.VISIBLE
        binding.contactFragmentContactUsBtn.visibility=View.INVISIBLE
    }

    private fun hidePR(){
        binding.contactBtnPr.visibility = View.INVISIBLE
        binding.contactFragmentContactUsBtn.visibility=View.VISIBLE
    }

    private fun setupEmptyInputError(){
        binding.emailEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

                val email = p0.toString()

                if (email.isEmpty()){
                    binding.emailEt.error = "Required"
                }
            }

        })

        binding.phoneNumberEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

                val phoneNumber = p0.toString()

                if (phoneNumber.isEmpty()){
                    binding.emailEt.error = "Required"
                }
            }

        })


    }

    private fun setupSpinner() {

         adapter = ArrayAdapter(requireContext(),R.layout.spinner_item,items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.serviceSpinner.adapter = adapter
    }

    private fun clearData(){
        binding.emailEt.setText(" ")
        binding.messageEt.setText(" ")
        binding.phoneNumberEt.setText(" ")
    }



}