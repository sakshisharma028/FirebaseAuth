package com.example.firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
val auth by lazy {
    FirebaseAuth.getInstance()
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                Log.d("TAG", "onVerificationCompleted:$credential")

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.w("TAG", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {

                } else if (e is FirebaseTooManyRequestsException) {

                }


            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG", "onCodeSent:$verificationId")




            }
        }
        auth.addAuthStateListener {
            //do from
        }
//        btn.setOnClickListener {
//           val email=ed11.text.toString()
//            val password=ed21.text.toString()
//            if(ed11.text.isNullOrEmpty()){
//                il1.isErrorEnabled=true
//                il1.setError("enter again")
//            }
//
//            if(password.length<=6){
//                il2.isErrorEnabled=true
//                il2.setError("minimum length should be 6")
//            }
//            else{
//            signIn(ed11.text.toString(),ed21.text.toString())
//            }
//        }
        btn.setOnClickListener {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                il1.editText?.text.toString(), // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                this, // Activity (for callback binding)
                callbacks) // OnVerificationStateChangedCallbacks
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
auth.signInWithCredential(credential)
    }

    private fun signIn(name: String, password: String) {
auth.createUserWithEmailAndPassword(name,password)
    .addOnCompleteListener {

    }
    .addOnSuccessListener {
        Toast.makeText(this,it.credential.toString(),Toast.LENGTH_LONG).show()
    }
    .addOnFailureListener {
        if(it.localizedMessage.contains("exist")){
            logIn(name,password)
        }
        Toast.makeText(this,it.localizedMessage.toString(),Toast.LENGTH_LONG).show()
    }
    }

    private fun logIn(name: String, password: String) {
auth.signInWithEmailAndPassword(name,password)
    .addOnFailureListener {
        Toast.makeText(this,it.localizedMessage.toString(),Toast.LENGTH_LONG).show()
    }
    }
}
