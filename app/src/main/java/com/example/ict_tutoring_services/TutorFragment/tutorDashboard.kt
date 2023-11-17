package com.example.ict_tutoring_services.TutorFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ict_tutoring_services.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [tutorDashboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class tutorDashboard : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tutor_dashboard, container, false)

        val emailTv = view.findViewById<TextView>(R.id.tutorEmail)

        val currentUserEmail = Firebase.auth.currentUser?.email

        emailTv.text = currentUserEmail

        return view
    }


}