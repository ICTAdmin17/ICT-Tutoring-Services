package com.example.ict_tutoring_services.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.ict_tutoring_services.LoginPage
import com.example.ict_tutoring_services.R
import com.example.ict_tutoring_services.models.BookingModel
import com.example.ict_tutoring_services.models.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class bookingForm : Fragment() {

    lateinit var Tview: View
    lateinit var bookBtn:Button
    private lateinit var datatbaseReference: DatabaseReference
    lateinit var datePicker: Button
    lateinit var datePicked: TextView
    lateinit var timePickerBtn: Button
    lateinit var timePicked: TextView
    private val calendar = Calendar.getInstance()

    var inputPos: Int? = null
    var tutor_name: String = ""
    var tutor_email: String = ""
    var tutor_module: String = ""
    var tutor_uid: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
         Tview =  inflater.inflate(R.layout.fragment_booking_form, container, false)

        val user = FirebaseAuth.getInstance().currentUser

        bookBtn = Tview.findViewById(R.id.Book)
        datePicker = Tview.findViewById(R.id.DatePicker)
        datePicked = Tview.findViewById(R.id.DatePick)
        timePickerBtn = Tview.findViewById(R.id.timePicker)
        timePicked = Tview.findViewById(R.id.TimePick)

        datePicker.setOnClickListener {
            showDatePicker()
        }

        timePickerBtn.setOnClickListener {
            showTimePicker()
        }





        inputPos = arguments?.getInt("input_pos")
        tutor_name = arguments?.getString("tutor_name").toString()
        tutor_email = arguments?.getString("tutor_email").toString()
        tutor_module = arguments?.getString("tutor_module").toString()
        tutor_uid = arguments?.getString("tutor_uid").toString()

        Tview.findViewById<EditText>(R.id.tutorName).setText(tutor_name )
        Tview.findViewById<EditText>(R.id.tutorLastName).setText(tutor_name)
        Tview.findViewById<EditText>(R.id.tutorModule).setText(tutor_module)
        Tview.findViewById<EditText>(R.id.tutorEmail).setText(tutor_email)
        Tview.findViewById<EditText>(R.id.studentEmail).setText(user?.email,)

        val studentPhoneNo = Tview.findViewById<EditText>(R.id.phoneNumber).text
        val studentEmail = Tview.findViewById<EditText>(R.id.studentEmail).text.toString()


        bookBtn.setOnClickListener {

            if(timePicked.text.toString().equals("") || datePicked.text.toString().equals("") ||
                studentPhoneNo.toString().equals("")){

                Toast.makeText(this@bookingForm.requireContext(),"Please Ensure that you have filled everything",Toast.LENGTH_LONG).show()
            }else{
                datatbaseReference = FirebaseDatabase.getInstance().getReference("Booking Information")
                val key = datatbaseReference.push().key
                val booking = BookingModel(
                    tutor_uid,
                    tutor_name,
                    tutor_email,
                    tutor_module,
                    studentPhoneNo.toString(),
                    user?.uid,
                    user?.email,
                    timePicked.text.toString(),
                    datePicked.text.toString(),
                    "pending",
                    key
                )

                val frag = HomeFragment()
                datatbaseReference.child(key!!).setValue(booking).addOnSuccessListener {
                    Toast.makeText(this@bookingForm.requireContext(), "You have made your booking", Toast.LENGTH_LONG,).show()
                    val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                    val Ft: FragmentTransaction = manager.beginTransaction()
                    Ft.replace(R.id.fragment_container, frag)

                    Ft.commit()
                }.addOnFailureListener {
                    //mProgressDialog.dismiss()
                    Toast.makeText(this@bookingForm.requireContext(), "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT,).show()
                }
            }

        }
        return Tview
    }

    private fun showTimePicker() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{timepicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)
            timePicked.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(this@bookingForm.requireContext(),timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(this@bookingForm.requireContext(),{DatePicker,year:Int,monthOfYear:Int,dayofMonth:Int ->
            val selectedDate:Calendar = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayofMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            val formatedDate:String = dateFormat.format(selectedDate.time)
            datePicked.text = formatedDate
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }

}