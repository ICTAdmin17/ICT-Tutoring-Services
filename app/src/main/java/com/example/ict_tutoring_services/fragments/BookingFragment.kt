package com.example.ict_tutoring_services.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ict_tutoring_services.Adapters.bookingAdapter
import com.example.ict_tutoring_services.Adapters.studentAdapter
import com.example.ict_tutoring_services.R
import com.example.ict_tutoring_services.models.BookingModel
import com.example.ict_tutoring_services.models.tutorsAdapterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class BookingFragment : Fragment() {

    private lateinit var bookingsList:MutableList<BookingModel>
    private lateinit var bookingRecycler: RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        bookingRecycler = view.findViewById<RecyclerView>(R.id.bookingRecycler)
        bookingRecycler.layoutManager = LinearLayoutManager(this@BookingFragment.requireContext(),
            LinearLayoutManager.VERTICAL,false)
        bookingRecycler.setHasFixedSize(true)

        bookingsList = mutableListOf<BookingModel>()

        getUserData(this@BookingFragment.requireContext())

        return view
    }

    private fun getUserData(con: Context) {
        bookingRecycler.visibility = View.GONE
        //loadingTv.visibility = View.VISIBLE

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Booking Information")
        val query = FirebaseDatabase.getInstance().getReference("Booking Information").orderByChild("studentUid").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                bookingsList.clear()
                if(snapshot.exists()){
                    for(Tutor in snapshot.children){
                        val Booking = Tutor.getValue(BookingModel::class.java)
                        bookingsList.add(Booking!!)
                    }
//
//                    )
                    val myAdapter = bookingAdapter(con,bookingsList)
                    bookingRecycler.adapter = myAdapter


                    myAdapter.setOnItemClickListener(object : bookingAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            //Toast.makeText(this@UsersActivity,"you have clicked the $position in the list",Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this@UsersActivity,userDetailsActivity::class.java)
//
//                            intent.putExtra("userId", userList[position].idNumber)
//                            intent.putExtra("name", userList[position].name)
//                            intent.putExtra("surname", userList[position].surname)
//                            intent.putExtra("status", userList[position].status)
//
//                            startActivity(intent)
                        }

                    })

                    //loadingTv.visibility = View.GONE
                    bookingRecycler.visibility =  View.VISIBLE


                }
            }

            override fun onCancelled(error: DatabaseError) {
                //loadingTv.text = "unfortunatley there was an unforseen error"
                //loadingTv.visibility = View.VISIBLE
            }

        })
    }
}