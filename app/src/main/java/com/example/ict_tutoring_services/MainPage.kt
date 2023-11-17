package com.example.ict_tutoring_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ict_tutoring_services.fragments.BookingFragment
import com.example.ict_tutoring_services.fragments.HomeFragment
import com.example.ict_tutoring_services.fragments.RatingFragment
import com.example.ict_tutoring_services.fragments.settingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPage : AppCompatActivity() {
    private lateinit var bottomNav:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        replaceFragment(HomeFragment())
        bottomNav = findViewById(R.id.Bottom_nav)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home ->{
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.bottom_booking ->{
                    replaceFragment(BookingFragment())
                    true
                }

                R.id.bottom_rating ->{
                    replaceFragment(RatingFragment())
                    true
                }

                R.id.bottom_settings ->{
                    replaceFragment(settingsFragment())
                    true
                }
                else -> false
            }

        }
    }

    private fun replaceFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }
}