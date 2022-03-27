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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
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

        var position = requireArguments().getInt("currCardPosition")
        var allcard = (requireArguments().getSerializable("allCards")) as List<FlashCardModel>
        var card = allcard.get(position);

        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarViewCardFragment)
        val viewCardQuestion = view.findViewById<TextView>(R.id.view_card_question)
        val viewCardAnswer = view.findViewById<TextView>(R.id.view_card_answer)
        val viewAnswerButton = view.findViewById<Button>(R.id.view_answer_button)
        val layoutExpand = view.findViewById<LinearLayout>(R.id.layout_expand)
        val cardView = view.findViewById<CardView>(R.id.view_cardview)
        val nextCardbutton = view.findViewById<Button>(R.id.next_card_button)
        val previousCardbutton = view.findViewById<Button>(R.id.previous_card_button)
        val viewCardImageQuestion = view.findViewById<ImageView>(R.id.view_cardImage_question)
        //Setting initial card based on if it is an imagecard or a textcard
        if((card as FlashCardModel).image.hasImage == false){
            viewCardImageQuestion.visibility = View.GONE
            viewCardQuestion.visibility = View.VISIBLE
            viewCardQuestion.text = (card as FlashCardModel).question
        }
        else{
            viewCardQuestion.visibility = View.GONE
            viewCardImageQuestion.visibility = View.VISIBLE
            viewCardImageQuestion.setImageBitmap((card as FlashCardModel).image.bitmap)
        }

        viewAnswerButton.setOnClickListener{
            if(layoutExpand.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                layoutExpand.visibility = View.VISIBLE
                viewAnswerButton.text = "Hide Answer"
                viewCardAnswer.text = (allcard.get(position) as FlashCardModel).answer;
            }
            else{
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                layoutExpand.visibility = View.GONE
                viewAnswerButton.text = "View Answer"
            }
        }

        //On back click
        toolBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_viewFlashCardFragment_to_notesListFragment,Bundle().apply {
                putInt("currentFolderId",currentFolderId)
            })
        }

        //Initial card visibility
        nextCardbutton.isVisible = allcard.size-1 != position
        previousCardbutton.isVisible = position != 0
        nextCardbutton.setOnClickListener{
            viewCardAnswer.text = "";

            //Collapse card answer
            TransitionManager.beginDelayedTransition(cardView, AutoTransition())
            layoutExpand.visibility = View.GONE
            viewAnswerButton.text = "View Answer"

            //Get next card position
            position++;

            //Set card visibility
            Log.i("position in next",position.toString())
            nextCardbutton.isVisible = allcard.size-1 != position
            previousCardbutton.isVisible = position != 0

            //Set Card Data
            var card = allcard[position]
            //Hiding views based on if it is an image or textcard
            if((card as FlashCardModel).image.hasImage == false){
                viewCardImageQuestion.visibility = View.GONE
                viewCardQuestion.visibility = View.VISIBLE
            }
            else{
                viewCardQuestion.visibility = View.GONE
                viewCardImageQuestion.visibility = View.VISIBLE
            }
            viewCardQuestion.text = (card as FlashCardModel).question;
            viewCardImageQuestion.setImageBitmap((card as FlashCardModel).image.bitmap)
            Log.i("ANSWR",card.answer)

        }
        previousCardbutton.setOnClickListener{
            viewCardAnswer.text = "";

            //Collapsing answer on navigation
            TransitionManager.beginDelayedTransition(cardView, AutoTransition())
            layoutExpand.visibility = View.GONE
            viewAnswerButton.text = "View Answer"

            //Get card from previous position
            position--;
            Log.i("position in previous",position.toString())

            //Set card visibility
            previousCardbutton.isVisible = position != 0
            nextCardbutton.isVisible = allcard.size-1 != position

            //Set card data
            var card1 = allcard[position]
            //Hiding views based on if it is an image or text card
            if((card1 as FlashCardModel).image.hasImage == false){
                viewCardImageQuestion.visibility = View.GONE
                viewCardQuestion.visibility = View.VISIBLE
            }
            else{
                viewCardQuestion.visibility = View.GONE
                viewCardImageQuestion.visibility = View.VISIBLE
            }
            viewCardQuestion.text = (card1 as FlashCardModel).question;
            viewCardImageQuestion.setImageBitmap((card1 as FlashCardModel).image.bitmap)

        }

    }
}