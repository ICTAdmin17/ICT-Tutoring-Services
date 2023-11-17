package com.example.ict_tutoring_services

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Sanitizer
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ict_tutoring_services.models.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class Registration_page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var datatbaseReference: DatabaseReference
    lateinit var nameEdt: EditText
    lateinit var surnameEdt: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var conPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)

        val RegisterBtn = findViewById<Button>(R.id.btnRegister)

        password = findViewById(R.id.etPassword)
        conPassword = findViewById(R.id.etConPassword)

        RegisterBtn.setOnClickListener {
            if (password.text.toString() == conPassword.text.toString()) {

                registerStudents()
            }

        }
    }

    fun registerStudents(){
        val email = findViewById<EditText>(R.id.etEmail)
        val passWord = findViewById<EditText>(R.id.etPassword)
        val inputEmail = email.text.toString()
        val inputPassword = passWord.text.toString()
        nameEdt = findViewById(R.id.Name)
        surnameEdt = findViewById(R.id.Surname)

        if(email.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields correctly",Toast.LENGTH_SHORT).show()
            return
        }else if(passWord.text.isEmpty()){
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show()
            return
        }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            Toast.makeText(this,"Please enter a valid Email",Toast.LENGTH_SHORT).show()
            return
        }else if(nameEdt.text.isEmpty()  && nameEdt.text.length > 3){
            Toast.makeText(this,"Please enter Your name ",Toast.LENGTH_SHORT).show()
            return

        }else if(surnameEdt.text.isEmpty() && surnameEdt.text.length  > 3){
            Toast.makeText(this,"Please please Your last name ",Toast.LENGTH_SHORT).show()
            return
        }



        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle("Creating Account Tutor")
        mProgressDialog.setMessage("Please wait")
        mProgressDialog.show()

        auth.createUserWithEmailAndPassword(inputEmail,inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // registration is  success, update UI with the signed-in user's information
                    val uid = auth.uid

                    datatbaseReference = FirebaseDatabase.getInstance().getReference("User Information")
                    val key = datatbaseReference.push().key
                    val userData = UserData(
                        uid.toString(),
                        nameEdt.text.toString(),
                        surnameEdt.text.toString(),
                        email.text.toString(),
                        password.text.toString(),
                        "student"
                    )
                    
                    val sanitizedEmail = email.text.toString().replace('.',',')
                    datatbaseReference.child(uid.toString()).setValue(userData).addOnSuccessListener {
                        Toast.makeText(baseContext, "You have successfully Registered", Toast.LENGTH_SHORT,).show()
                        Toast.makeText(this, "this person has added", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        mProgressDialog.dismiss()
                        Toast.makeText(baseContext, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT,).show()

                    }

                } else {
                    // If sign in fails, display a message to the user.
                    mProgressDialog.dismiss()
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT,).show()

                }
            }.addOnFailureListener{ Toast.makeText(baseContext, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT,).show()
            }
    }
}