package com.example.ict_tutoring_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ict_tutoring_services.TutorFragment.tutorBookings
import com.example.ict_tutoring_services.TutorFragment.tutorDashboard
import com.example.ict_tutoring_services.TutorFragment.tutorRatings
import com.example.ict_tutoring_services.TutorFragment.tutorSettings
import com.example.ict_tutoring_services.fragments.BookingFragment
import com.example.ict_tutoring_services.fragments.HomeFragment
import com.example.ict_tutoring_services.fragments.RatingFragment
import com.example.ict_tutoring_services.fragments.settingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class tutorMainPage : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_main_page)
        replaceFragment(tutorDashboard())
        bottomNav = findViewById(R.id.tutor_Bottom_nav)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home ->{
                    replaceFragment(tutorDashboard())
                    true
                }

                R.id.bottom_booking ->{
                    replaceFragment(tutorBookings())
                    true
                }

                R.id.bottom_rating ->{
                    replaceFragment(tutorRatings())
                    true
                }

                R.id.bottom_settings ->{
                    replaceFragment(tutorSettings())
                    true
                }
                else -> false
            }

        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.tutor_fragment_container,fragment).commit()
    }
}