package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.models.FlashCardModel
import com.example.flashcards.persistence.FlashCardDatabaseHandler
import com.google.android.material.snackbar.Snackbar
import java.util.*

class CreateNoteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flashcard_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarCreateNoteFragment)
        val questionInputField = view.findViewById<TextView>(R.id.questionInputField)
        val answerInputField = view.findViewById<TextView>(R.id.answerInputField)

        toolBar.setNavigationOnClickListener {
            if(questionInputField.text.toString().trim().isEmpty() && answerInputField.text.toString().isNotEmpty())  {
                Snackbar.make(view, R.string.empty_prompt, Snackbar.LENGTH_LONG)
                    .setAction(R.string.yes){
                        saveNote(questionInputField, answerInputField)
                    }
                    .show()
            } else {
                saveNote(questionInputField, answerInputField)
                Snackbar.make(view, R.string.save_flashcard_confirmation, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun saveNote(questionField: TextView, answerField: TextView) {
        val question: String = questionField.text.toString()
        val answer: String = answerField.text.toString()
        val id:String = UUID.randomUUID().toString();
        val flashCard = FlashCardModel(id,question, answer)
        if(question.isNotEmpty() || answer.isNotEmpty()){
            context?.let { FlashCardDatabaseHandler(context= it).addFlashCard(flashCard) };
        }
        findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment)
    }
}