package com.example.ict_tutoring_services

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

@Suppress("DEPRECATION")
class LoginPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
//    val mProgressDialog = ProgressDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        val currentUser = FirebaseAuth.getInstance().currentUser
        auth = Firebase.auth


        if(currentUser != null){
            auth = Firebase.auth
            checkUser()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val signbtn = findViewById<Button>(R.id.btnLogin)
        val Register = findViewById<TextView>(R.id.registerText)
        val tutorLogin = findViewById<TextView>(R.id.tutorLoginTxt)



        Register.setOnClickListener {
            val intent = Intent(this,Registration_page::class.java)
            startActivity(intent)
        }

        tutorLogin.setOnClickListener{
            val i = Intent(this,tutorLoginPage::class.java)
            startActivity(i)
        }

        signbtn.setOnClickListener {
            loginConfirmatin()
        }
    }


    private fun loginConfirmatin() {
        val userEmail = findViewById<EditText>(R.id.etEmail)
        val userPassword = findViewById<EditText>(R.id.etPassword)

        if(userEmail.text.isEmpty()|| userPassword.text.isEmpty()){
            Toast.makeText(this,"Please Ensure that you have filled the text boxes", Toast.LENGTH_SHORT).show()
            return
        }

        val emailInput = userEmail.text.toString()
        val Password = userPassword.text.toString()

        auth.signInWithEmailAndPassword(emailInput, Password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // now we have to check which user signed in
                    Toast.makeText(
                        baseContext,
                        "checking.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    checkUser()
//                    val intent = Intent(this,MainPage::class.java)
//                    startActivity(intent)
//                    Toast.makeText(this,"You have successfully logged in ", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }.addOnFailureListener{
                //mProgressDialog.dismiss()
                Toast.makeText(
                    baseContext,
                    "Authentication failed. ${it.localizedMessage} ",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    private fun checkUser() {
        Toast.makeText(this@LoginPage,"I am checking the user ", Toast.LENGTH_SHORT).show()
        val firebaseUser = auth.currentUser
        val ref = FirebaseDatabase.getInstance().getReference("User Information")
        ref.child(firebaseUser!!.uid!!).addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
               // mProgressDialog.dismiss()
                val userType = snapshot.child("userType").value

                if(userType == "student"){
                    val intent = Intent(this@LoginPage,MainPage::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LoginPage,"You have successfully logged in ", Toast.LENGTH_SHORT).show()
                }else if(userType == "tutor"){
                    Toast.makeText(this@LoginPage,"This Page for the Tutor is still being Developed  ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginPage,tutorMainPage::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this@LoginPage,tutorLoginPage::class.java)
                    startActivity(intent)}
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}