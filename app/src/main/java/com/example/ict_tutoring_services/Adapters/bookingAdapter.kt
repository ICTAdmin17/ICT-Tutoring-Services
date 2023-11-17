package com.example.ict_tutoring_services.Adapters

import android.content.Context
import android.os.Bundle
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
import com.example.ict_tutoring_services.fragments.bookingForm
import com.example.ict_tutoring_services.models.BookingModel
import com.example.ict_tutoring_services.models.tutorsAdapterModel



class bookingAdapter(val context:Context, val BookingList: List<BookingModel>):RecyclerView.Adapter<bookingAdapter.MyViewHolder>(){

    private lateinit var mListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    inner class MyViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        //val name = itemView.findViewById<TextView>(R.id.userName)
        //val booktutor = itemView.findViewById<Button>(R.id.BookTutor)

        init{

            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

        fun setData(bookings:BookingModel){
            itemView.findViewById<TextView>(R.id.tutorName).text = bookings.tutorName
            itemView.findViewById<TextView>(R.id.tutorModule).text = bookings.tutorModule
            itemView.findViewById<TextView>(R.id.bookedTime).text = bookings.time + " Date:" + bookings.date
            itemView.findViewById<TextView>(R.id.bookingStatus).text = bookings.bookingStatus

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookingAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.booking_list_item,parent,false)
        return MyViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: bookingAdapter.MyViewHolder, position: Int) {
        val tutor = BookingList[position]

        holder.setData(tutor)

    }

    override fun getItemCount(): Int {
        return BookingList.size
    }
}