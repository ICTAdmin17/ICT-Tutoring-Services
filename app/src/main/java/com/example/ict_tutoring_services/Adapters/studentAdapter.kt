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
import com.example.ict_tutoring_services.models.tutorsAdapterModel



class studentAdapter(val context:Context, val usersList: List<tutorsAdapterModel>):RecyclerView.Adapter<studentAdapter.MyViewHolder>(){

    private lateinit var mListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    inner class MyViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        //val name = itemView.findViewById<TextView>(R.id.userName)
        val booktutor = itemView.findViewById<Button>(R.id.BookTutor)

        init{

            booktutor.setOnClickListener {
                Toast.makeText(context,"I was clicked to book a tutor",Toast.LENGTH_SHORT).show()
            }

            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }


        fun setData(user:tutorsAdapterModel){
            itemView.findViewById<TextView>(R.id.tutorName).text = user.name
            itemView.findViewById<TextView>(R.id.TutorSurname).text = user.surname
            itemView.findViewById<TextView>(R.id.tutorModule).text = user.module
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): studentAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tutor_list_item,parent,false)
        return MyViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: studentAdapter.MyViewHolder, position: Int) {
        val tutor = usersList[position]

        holder.setData(tutor)
        holder.booktutor.setOnClickListener {
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()

            val  frag = bookingForm()

            val bundle = Bundle()
            bundle.putInt("input_pos", position)
            bundle.putString("tutor_name", tutor.name)
            bundle.putString("tutor_email", tutor.email)
            bundle.putString("tutor_module", tutor.module)
            bundle.putString("tutor_uid",tutor.uid)

            frag.arguments = bundle


            val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val Ft: FragmentTransaction = manager.beginTransaction()
            Ft.replace(R.id.fragment_container, frag)

            Ft.commit()
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}