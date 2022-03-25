package com.example.flashcards.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.models.NoteModel
import com.example.flashcards.persistence.NotesPersistence
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class ViewFlashCardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_flashcard, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarViewCardFragment)
        val viewCardTitle = view.findViewById<TextView>(R.id.view_card_title)
        val viewCardQuestion = view.findViewById<TextView>(R.id.view_card_question)
        val viewCardAnswer = view.findViewById<TextView>(R.id.view_card_answer)
        val viewAnswerButton = view.findViewById<Button>(R.id.view_answer_button)
        val layoutExpand = view.findViewById<LinearLayout>(R.id.layout_expand)
        val cardView = view.findViewById<CardView>(R.id.view_cardview)
        val nextCardbutton = view.findViewById<Button>(R.id.nextflashcard)
        val deleteCardButton = view.findViewById<Button>(R.id.deleteflashcard)

        viewAnswerButton.setOnClickListener{
            if(layoutExpand.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                layoutExpand.visibility = View.VISIBLE
                viewAnswerButton.text = "Hide Answer"
            }
            else{
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                layoutExpand.visibility = View.GONE
                viewAnswerButton.text = "View Answer"
            }
        }
        toolBar.setNavigationOnClickListener {

        }
    }
}