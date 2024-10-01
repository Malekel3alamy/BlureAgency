package com.example.blureagency.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentContactUsBinding
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

import dev.nurujjamanpollob.javamailer.entity.Attachment;
import dev.nurujjamanpollob.javamailer.sender.MailSendWrapper;
import dev.nurujjamanpollob.javamailer.sender.Provider;
import dev.nurujjamanpollob.javamailer.sender.Providers;


class ContactUsFragment : Fragment(R.layout.fragment_contact_us) {
private lateinit var binding: FragmentContactUsBinding
lateinit var  adapter : ArrayAdapter<String>

    val items = arrayOf("Services","Web App","Mobile App","Marketing","Security","Network","Video Edit","General")
    var customer_service:String = ""



    private val MAIL_SENDER_SEND_FROM_ADDRESS = "fromaddress@domain.com";
    private val MAIL_HOST = "mail.domain.com";
    private val MAIL_PASSWORD = "mailbosspasswordhere";
    private val smtpPortAddress = "465";
    private val socketFactoryPortAddress = "465";
    private val receiverMailAdd = "receiver@domain.com";
    private val subject = "Mail subject goes here";
    private val message = "Mail message body goes here, supports HTML markup";

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


    send()


    val email = binding.emailEt.text.toString()
    val phone = binding.phoneNumberEt.text.toString()
    val message  = binding.messageEt.text.toString()
    Log.d("CUSTOMER_SERVICE",email)
    Log.d("CUSTOMER_SERVICE",phone)
    Log.d("CUSTOMER_SERVICE",message)
    if (email.isEmpty()||phone.isEmpty()|| customer_service.isEmpty()||message.isEmpty()){
        Toast.makeText(requireContext(),resources.getString(R.string.make_sure_to_enter_all_fields),Toast.LENGTH_SHORT).show()
    }else{




       val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_EMAIL,email)
        intent.putExtra(Intent.EXTRA_SUBJECT,customer_service)
        intent.putExtra(Intent.EXTRA_TEXT,message)


        startActivity(Intent.createChooser(intent,"Choose an Email client :"))

    }
}






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

    fun send() {


          val MAIL_SENDER_SEND_FROM_ADDRESS = "mahmoudalataly1234@gmail.com"
         val MAIL_HOST = "mail.domain.com"
         val MAIL_PASSWORD = "mailbosspasswordhere"
         val smtpPortAddress = "465"
         val socketFactoryPortAddress = "465"
        var receiverMailAdd = "mhmood_adel1899@gmail.com"
         var subject = "Mail subject goes here"
         var message = "Mail message body goes here, supports HTML markup"

// to create Attachment instance from android intent Uri, keep following
         var attachment: Attachment? = Attachment(byteArrayOf(), "fileNameIncludingExtension", "fileMimeType")

// Create service provider configuration
        val serviceProviderConfig = Provider(
            MAIL_HOST, // SMTP Host name
            smtpPortAddress, // SMTP Host port number
            socketFactoryPortAddress, // java mail socket factory address, should be same as smtp port
            Providers.getSecureSocketFactoryName(), // Java secure socket factory name
            true, // use auth or no flag
            true // Use TLS to securely transfer mail flag
        )

        /*
        Basic mail credentials is provided, if you need to provide additional mail properties,
        use: serviceProviderConfig.putConfiguration(String propertyKey, String propertyValue);
        This can also be used to Override current mail service configuration
        */

// send email to server using wrapper
        val mailSendWrapper = MailSendWrapper(
            MAIL_SENDER_SEND_FROM_ADDRESS, // Set from address
            receiverMailAdd, // Receiver mail address
            MAIL_PASSWORD, // Mail box password for authorization purposes
            subject, // Mail subject
            message, // Mail body, supports HTML markup
            serviceProviderConfig // Service provider configuration
        )

// Listen to event
        mailSendWrapper.setMailSendEventListener(object : MailSendWrapper.MessageSendListener {

            // Invokes when the background thread started running
            override fun whileSendingEmail() {
                Toast.makeText(requireContext(), "Sending Mail...", Toast.LENGTH_SHORT).show()
            }

            // Invokes when the mail sender plugin finishes sending email
            override fun onEmailSent(toRecipientAddress: String) {
                // reset attachment
                attachment = null
                Toast.makeText(requireContext(), "Mail sent to $toRecipientAddress", Toast.LENGTH_SHORT).show()
            }

            // Invokes when mail sending fails
            override fun onEmailSendFailed(errorMessage: String) {
                // reset attachment
                attachment = null
                Toast.makeText(requireContext(), "Mail send Exception $errorMessage", Toast.LENGTH_SHORT).show()
            }
        })

// Send email to client
// When attachment is not null
// Call mailSendWrapper.setSendFileWithAttachment(attachment); to send the attached attachment
        if (attachment != null && attachment!!.isAttachmentNotNull()) {
            mailSendWrapper.setSendFileWithAttachment(attachment)
        } else {
            // When attachment is null
            mailSendWrapper.doSendEmailToFollowingClient()
        }



    }

}