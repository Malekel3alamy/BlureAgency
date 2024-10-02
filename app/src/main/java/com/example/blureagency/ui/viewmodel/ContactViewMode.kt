package com.example.blureagency.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.StrictMode
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Properties
import javax.inject.Inject
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@HiltViewModel
class ContactViewMode @Inject constructor():ViewModel() {


    val clientMessageStatus = MutableLiveData<Resources<Unit>>()
    fun send(context: Context,receptient :String,message:String,subject:String) =
        viewModelScope.launch(Dispatchers.IO) {

            clientMessageStatus.postValue(Resources.Loading())

            if (internetConnection(context)) {

                val user_name = "mahmoudalataly1234@gmail.com"
                val password = "niyauoyoyffjyldu"
                val messageToSend = message
                val props = Properties()
                props.put("mail.smtp.auth", "true")
                props.put("mail.smtp.starttls.enable", "true")
                props.put("mail.smtp.host", "smtp.gmail.com")
                props.put("mail.smtp.port", "587")

                val session = Session.getInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(user_name, password)

                    }
                })
                try {
                    val message = MimeMessage(session)
                    message.setFrom(InternetAddress(user_name))
                    message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(receptient.trim())
                    )
                    message.setSubject(subject + " Service")
                    message.setText(messageToSend)
                    Transport.send(message)

                    clientMessageStatus.postValue(Resources.Success(Unit))


                } catch (e: MessagingException) {
                    throw RuntimeException(e)
                    clientMessageStatus.postValue(Resources.Error(e.message.toString()))
                }

                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
            } else {
                clientMessageStatus.postValue(Resources.Error("Failed To Reach To Internet"))
            }
        }
    }


    fun internetConnection(context: Context) : Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply{

            val internetStatus = getNetworkCapabilities(activeNetwork)?.run{
                when{
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->     true
                    else -> false
                }
            }
            return internetStatus?:false

        }

    }

