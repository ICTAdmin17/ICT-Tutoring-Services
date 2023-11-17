package com.example.ict_tutoring_services.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ict_tutoring_services.Adapters.studentAdapter
import com.example.ict_tutoring_services.R
import com.example.ict_tutoring_services.models.tutorsAdapterModel
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private lateinit var TutorList:MutableList<tutorsAdapterModel>
    private lateinit var RecyclerV: RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        RecyclerV = view.findViewById<RecyclerView>(R.id.myRecyclerView)
        RecyclerV.layoutManager = LinearLayoutManager(this@HomeFragment.requireContext(),LinearLayoutManager.VERTICAL,false)
        RecyclerV.setHasFixedSize(true)

        TutorList = mutableListOf<tutorsAdapterModel>()

        getUserData(this@HomeFragment.requireContext())

        return view
    }


    private fun getUserData(con: Context) {
        RecyclerV.visibility = View.GONE
        //loadingTv.visibility = View.VISIBLE

        databaseReference = FirebaseDatabase.getInstance().getReference("Tutors Information")
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                TutorList.clear()
                if(snapshot.exists()){
                    for(Tutor in snapshot.children){
                        val userData = Tutor.getValue(tutorsAdapterModel::class.java)
                        TutorList.add(userData!!)
                    }
//
//                    )
                    val myAdapter = studentAdapter(con,TutorList)
                    RecyclerV.adapter = myAdapter


                    myAdapter.setOnItemClickListener(object :studentAdapter.onItemClickListener{
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
                    RecyclerV.visibility =  View.VISIBLE


                }
            }

            override fun onCancelled(error: DatabaseError) {
                //loadingTv.text = "unfortunatley there was an unforseen error"
                //loadingTv.visibility = View.VISIBLE
            }

        })
    }

}