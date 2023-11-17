package com.example.ict_tutoring_services

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class tutorLoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
//    val mProgressDialog = ProgressDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {

        val currentUser = FirebaseAuth.getInstance().currentUser


        if(currentUser != null){
            auth = Firebase.auth
            checkUser()
//            val intent = Intent(this,tutorMainPage::class.java)
//            startActivity(intent)

        }
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_tutor_login_page)




        auth = Firebase.auth

        val signbtn = findViewById<Button>(R.id.btnTutorLogin)
        val RegisterTxt = findViewById<TextView>(R.id.tutorRegisterText)

        signbtn.setOnClickListener {
            loginConfirmatin()
        }
        RegisterTxt.setOnClickListener {
            val intent = Intent(this,tutorRegistrationPage::class.java)
            startActivity(intent)
        }


    }

    private fun loginConfirmatin() {
        val tutorEmail = findViewById<EditText>(R.id.etTutorEmail)
        val tutorPassword = findViewById<EditText>(R.id.etTutorPassword)

        if(tutorEmail.text.isEmpty()|| tutorPassword.text.isEmpty()){
            Toast.makeText(this,"Please Ensure that you have filled the text boxes", Toast.LENGTH_SHORT).show()
            return
        }
        val emailInput = tutorEmail.text.toString()
        val Password = tutorPassword.text.toString()

//        mProgressDialog.setTitle("Login in process ")
//        mProgressDialog.setMessage("Please wait")
//        mProgressDialog.show()


        auth.signInWithEmailAndPassword(emailInput, Password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // now we have to check which user signed in
                    checkUser()
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }.addOnFailureListener{
//                mProgressDialog.dismiss()
                Toast.makeText(
                    baseContext,
                    "Authentication failed. ${it.localizedMessage} ",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        val ref = FirebaseDatabase.getInstance().getReference("Tutors Information")
        ref.child(firebaseUser!!.uid!!).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
//                mProgressDialog.dismiss()
                val userType = snapshot.child("userType").value

                if(userType == "student"){
                    val intent = Intent(this@tutorLoginPage,MainPage::class.java)
                    startActivity(intent)
                    Toast.makeText(this@tutorLoginPage,"You have successfully logged in ", Toast.LENGTH_SHORT).show()
                }else if(userType == "tutor"){
                    val intent = Intent(this@tutorLoginPage,tutorMainPage::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@tutorLoginPage,"You can not login here ", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@tutorLoginPage,"I don't know what this means but this happened ", Toast.LENGTH_SHORT).show()

            }

        })
    }
}