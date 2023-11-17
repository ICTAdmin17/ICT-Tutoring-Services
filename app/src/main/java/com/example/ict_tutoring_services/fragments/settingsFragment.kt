package com.example.ict_tutoring_services.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ict_tutoring_services.LoginPage
import com.example.ict_tutoring_services.R
import com.google.firebase.auth.FirebaseAuth


class settingsFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val signOut = view.findViewById<Button>(R.id.signout1)
        auth = FirebaseAuth.getInstance()
        signOut.setOnClickListener {
            auth.signOut()

            val intent = Intent(this@settingsFragment.requireContext(),LoginPage::class.java)
            startActivity(intent)
            getActivity()?.getFragmentManager()?.popBackStack();


        }
        return view
    }


}