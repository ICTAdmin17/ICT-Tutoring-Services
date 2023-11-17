package com.example.ict_tutoring_services.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ict_tutoring_services.R


class RatingFragment : Fragment() {

    private lateinit var ratingBar: RatingBar
    private lateinit var commentEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var displayRatingCommentTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rating, container, false)


        // Initialize UI elements
        ratingBar = view.findViewById(R.id.ratingBar)
        commentEditText = view.findViewById(R.id.commentEditText)
        submitButton = view.findViewById(R.id.submitButton)
        displayRatingCommentTextView = view.findViewById(R.id.displayRatingCommentTextView)

        // Handle the Submit Rating button click
        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            val comment = commentEditText.text.toString()

            if (rating > 0.0) {
                // Valid rating, you can display it within the app
                displayRatingAndComment(rating, comment)

                // Clear the input fields
                ratingBar.rating = 0.0F
                commentEditText.text.clear()

                Toast.makeText(requireContext(), "Rating submitted.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please provide a rating.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun displayRatingAndComment(rating: Float, comment: String) {
        val displayText = "Rating: $rating\nComment: $comment"
        displayRatingCommentTextView.text = displayText
        displayRatingCommentTextView.visibility = View.VISIBLE  // Make the TextView visible
    }
}