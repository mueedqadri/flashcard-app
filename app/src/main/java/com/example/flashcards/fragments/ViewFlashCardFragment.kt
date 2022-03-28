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
        val currentFolderId :Int = requireArguments().getInt("currentFolderId")
        requireArguments().getSerializable("currCard");

        var position = requireArguments().getInt("currCardPosition")
        val allcard = (requireArguments().getSerializable("allCards")) as List<FlashCardModel>
        val card = allcard.get(position);

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
        handleContent(card, viewCardImageQuestion, viewCardQuestion)

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
            findNavController().navigate(R.id.action_viewFlashCardFragment_to_flashcardListFragment,Bundle().apply {
                putInt("currentFolderId",currentFolderId)
            })
        }

        //Initial card visibility
        nextCardbutton.isVisible = allcard.size-1 != position
        previousCardbutton.isVisible = position != 0
        nextCardbutton.setOnClickListener{
            doSetup(viewCardAnswer, cardView, layoutExpand, viewAnswerButton)

            //Get next card position
            position++;

            //Set card visibility
            nextCardbutton.isVisible = allcard.size-1 != position
            previousCardbutton.isVisible = position != 0

            //Hiding views based on if it is an image or textcard
            handleContent(allcard[position], viewCardImageQuestion, viewCardQuestion)

        }
        previousCardbutton.setOnClickListener{
            doSetup(viewCardAnswer, cardView, layoutExpand, viewAnswerButton)
            //Get card from previous position
            position--;

            //Set card visibility
            previousCardbutton.isVisible = position != 0
            nextCardbutton.isVisible = allcard.size-1 != position

            //Hiding views based on if it is an image or text card
            handleContent(allcard[position], viewCardImageQuestion, viewCardQuestion)

        }
    }

    private fun doSetup(
        viewCardAnswer: TextView,
        cardView: CardView?,
        layoutExpand: LinearLayout,
        viewAnswerButton: Button
    ) {
        viewCardAnswer.text = "";

        //Collapse card answer
        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
        layoutExpand.visibility = View.GONE
        viewAnswerButton.text = "View Answer"
    }

    private fun handleContent(
        card: FlashCardModel,
        viewCardImageQuestion: ImageView,
        viewCardQuestion: TextView
    ) {
        if (!card.image.hasImage) {
            viewCardImageQuestion.visibility = View.GONE
            viewCardQuestion.visibility = View.VISIBLE
            viewCardQuestion.text = card.question
        } else {
            viewCardQuestion.visibility = View.GONE
            viewCardImageQuestion.visibility = View.VISIBLE
            if (card.image.bitmap != null) {
                viewCardImageQuestion.setImageBitmap(card.image.bitmap)
            } else {
                viewCardImageQuestion.setImageURI(card.image.uri)
            }
        }
    }

}