package com.example.flashcards.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
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
import com.example.flashcards.models.FlashCardModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class ViewFlashCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_flashcard, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("tagtag",requireArguments().getSerializable("currCard").toString())
        var currentFolderId :Int = requireArguments().getInt("currentFolderId")
        var currentCard = requireArguments().getSerializable("currCard");
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarViewCardFragment)
        val viewCardQuestion = view.findViewById<TextView>(R.id.view_card_question)
        val viewCardAnswer = view.findViewById<TextView>(R.id.view_card_answer)
        val viewAnswerButton = view.findViewById<Button>(R.id.view_answer_button)
        val layoutExpand = view.findViewById<LinearLayout>(R.id.layout_expand)
        val cardView = view.findViewById<CardView>(R.id.view_cardview)
        val nextCardbutton = view.findViewById<Button>(R.id.previous_card_button)
        val previousCardbutton = view.findViewById<Button>(R.id.previous_card_button)

         viewCardQuestion.text = (currentCard as FlashCardModel).question;
        viewAnswerButton.setOnClickListener{
            if(layoutExpand.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                layoutExpand.visibility = View.VISIBLE
                viewAnswerButton.text = "Hide Answer"
                viewCardAnswer.text = (currentCard as FlashCardModel).answer;
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