package com.example.ict_tutoring_services

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import com.example.ict_tutoring_services.models.UserData
import com.example.ict_tutoring_services.models.tutorModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class tutorRegistrationPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var datatbaseReference: DatabaseReference
    lateinit var nameEdt: EditText
    lateinit var surnameEdt: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var conPassword: EditText
    lateinit var module:AutoCompleteTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_registration_page)

        val languages = resources.getStringArray(R.array.Modules)

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, languages)

        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        autocompleteTV.setAdapter(arrayAdapter)

        val RegisterBtn = findViewById<Button>(R.id.btnTutorRegister)
        auth = Firebase.auth

        password = findViewById(R.id.etPassword)
        conPassword = findViewById(R.id.etConPassword)

        RegisterBtn.setOnClickListener {
            if (password.text.toString() == conPassword.text.toString()) {

                registerStudents()
            }
        }
    }

    private fun registerStudents() {
        val email = findViewById<EditText>(R.id.etEmail)
        val passWord = findViewById<EditText>(R.id.etPassword)
        val inputEmail = email.text.toString()
        val inputPassword = passWord.text.toString()
        nameEdt = findViewById(R.id.Name)
        surnameEdt = findViewById(R.id.Surname)
        module = findViewById(R.id.autoCompleteTextView)


        if(email.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            return
        }else if(passWord.text.isEmpty()){
            Toast.makeText(this,"Please enter a password", Toast.LENGTH_SHORT).show()
            return
        }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            Toast.makeText(this,"Please enter a valid Email", Toast.LENGTH_SHORT).show()
            return
        }else if(nameEdt.text.isEmpty()){
            Toast.makeText(this,"Please Your name ", Toast.LENGTH_SHORT).show()
            return

        }else if(surnameEdt.text.isEmpty()){
            Toast.makeText(this,"Please Your last name ", Toast.LENGTH_SHORT).show()
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

                    datatbaseReference = FirebaseDatabase.getInstance().getReference("Tutors Information")
                    val key = datatbaseReference.push().key
                    val tutorInfo = tutorModel(
                        uid.toString(),
                        nameEdt.text.toString(),
                        surnameEdt.text.toString(),
                        email.text.toString(),
                        password.text.toString(),
                        "tutor",
                        module.text.toString()

                    )


                    val sanitizedEmail = email.text.toString().replace('.',',')
                    datatbaseReference.child(uid.toString()).setValue(tutorInfo).addOnSuccessListener {
                        Toast.makeText(baseContext, "You have successfully Registered", Toast.LENGTH_SHORT,).show()
                        Toast.makeText(this, "You are now a tutor at Ict tutoring services", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, tutorLoginPage::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        mProgressDialog.dismiss()
                        Toast.makeText(baseContext, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT,).show()
                    }

//                    val intent = Intent(this,MainPage::class.java)
//                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT,).show()

                }
            }.addOnFailureListener{ Toast.makeText(baseContext, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT,).show()
            }
    }
}