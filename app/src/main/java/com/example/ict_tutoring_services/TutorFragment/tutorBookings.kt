package com.example.ict_tutoring_services.TutorFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ict_tutoring_services.Adapters.bookingAdapter
import com.example.ict_tutoring_services.Adapters.tutorbookingAdapter
import com.example.ict_tutoring_services.R
import com.example.ict_tutoring_services.models.BookingModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class tutorBookings : Fragment() {

    private lateinit var TbookingsList:MutableList<BookingModel>
    private lateinit var TbookingRecycler: RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tutor_bookings, container, false)
        TbookingRecycler = view.findViewById(R.id.TutorbookingRecycler)
        TbookingRecycler.layoutManager = LinearLayoutManager(this@tutorBookings.requireContext(),
            LinearLayoutManager.VERTICAL,false)
        TbookingRecycler.setHasFixedSize(true)


        TbookingsList = mutableListOf<BookingModel>()

        getUserData(this@tutorBookings.requireContext())

        return view
    }

    private fun getUserData(con: Context) {
        TbookingRecycler.visibility = View.GONE
        //loadingTv.visibility = View.VISIBLE

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Booking Information")
        val query = FirebaseDatabase.getInstance().getReference("Booking Information").orderByChild("tutorUid").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                TbookingsList.clear()
                if(snapshot.exists()){
                    for(Tutor in snapshot.children){
                        val Booking = Tutor.getValue(BookingModel::class.java)
                        TbookingsList.add(Booking!!)
                    }
//
//                    )
                    val myAdapter = tutorbookingAdapter(con,TbookingsList)
                    TbookingRecycler.adapter = myAdapter


                    myAdapter.setOnItemClickListener(object : tutorbookingAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            //This makes the list view clickable I'll see if I use it
                        }

                    })

                    //loadingTv.visibility = View.GONE
                    TbookingRecycler.visibility =  View.VISIBLE


                }
            }

            override fun onCancelled(error: DatabaseError) {
                //loadingTv.text = "unfortunatley there was an unforseen error"
                //loadingTv.visibility = View.VISIBLE
            }

        })
    }


}