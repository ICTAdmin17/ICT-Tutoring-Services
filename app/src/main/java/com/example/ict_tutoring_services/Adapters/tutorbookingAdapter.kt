package com.example.ict_tutoring_services.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.ict_tutoring_services.R
import com.example.ict_tutoring_services.TutorFragment.tutorBookings
import com.example.ict_tutoring_services.fragments.bookingForm
import com.example.ict_tutoring_services.models.BookingModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class tutorbookingAdapter(val context:Context, val TBookingList: List<BookingModel>):RecyclerView.Adapter<tutorbookingAdapter.MyViewHolder>(){

    private lateinit var mListener:onItemClickListener
    private lateinit var datatbaseReference: DatabaseReference

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    inner class MyViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        var currentItem: BookingModel? = null
        var  currentPos:Int = 0;
        //val name = itemView.findViewById<TextView>(R.id.userName)
        val acceptBooking = itemView.findViewById<Button>(R.id.acceptBooking)
        val DeclineBooking = itemView.findViewById<Button>(R.id.DeclineBooking)


        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }

            acceptBooking.setOnClickListener {
                Toast.makeText(context,"the current postion is:" + currentPos,Toast.LENGTH_LONG)

                datatbaseReference = FirebaseDatabase.getInstance().getReference("Booking Information")


                datatbaseReference.child(currentItem?.itemKey!!).child("bookingStatus").setValue("Accepted").addOnSuccessListener {
                    Toast.makeText(context, "Thank you for your response", Toast.LENGTH_SHORT).show()
                    val  frag = tutorBookings()

//                    val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                    val Ft: FragmentTransaction = manager.beginTransaction()
//                    Ft.replace(R.id.fragment_container, frag)
//
//                    Ft.commit()


                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Error occurred ${it.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            DeclineBooking.setOnClickListener {
                Toast.makeText(context,"the current postion is:" + currentPos,Toast.LENGTH_LONG)

                datatbaseReference = FirebaseDatabase.getInstance().getReference("Booking Information")


                datatbaseReference.child(currentItem?.itemKey!!).child("bookingStatus").setValue("Decline").addOnSuccessListener {
                    Toast.makeText(context, "Thank you for your response", Toast.LENGTH_SHORT).show()

                    val  frag = tutorBookings()

//                    val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
//                    val Ft: FragmentTransaction = manager.beginTransaction()
//                    Ft.replace(R.id.fragment_container,frag)
//
//                    Ft.commit()
                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Error occurred ${it.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        fun setData(bookings:BookingModel,position: Int){
            currentItem = bookings
            currentPos = position
            itemView.findViewById<TextView>(R.id.tutorName).text = bookings.studentEmail
            itemView.findViewById<TextView>(R.id.tutorModule).text = bookings.tutorModule
            itemView.findViewById<TextView>(R.id.bookedTime).text = bookings.time + " Date:" + bookings.date
            itemView.findViewById<TextView>(R.id.bookingStatus).text = bookings.bookingStatus

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tutorbookingAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tutor_booking_list_item,parent,false)
        return MyViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: tutorbookingAdapter.MyViewHolder, position: Int) {
        val tutor = TBookingList[position]

        holder.setData(tutor,position)

    }

    override fun getItemCount(): Int {
        return TBookingList.size
    }
}