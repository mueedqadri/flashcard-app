package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.models.FlashCardModel
import com.example.flashcards.persistence.FlashCardDatabaseHandler
import com.google.android.material.snackbar.Snackbar
import java.util.*

class CreateFlashcardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_flash_card_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentFolderId :Int = requireArguments().getInt("currentFolderId")
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarCreateFlashcardFragment)
        val questionInputField = view.findViewById<TextView>(R.id.questionInputField)
        val answerInputField = view.findViewById<TextView>(R.id.answerInputField)

        toolBar.setNavigationOnClickListener {
            checkCard(questionInputField, answerInputField, view, backAction = true, currentFolderId)
        }
        view.findViewById<Button>(R.id.createFlashcardButton).setOnClickListener {
            checkCard(questionInputField, answerInputField, view, backAction = false, currentFolderId)
        }
    }

    private fun checkCard(
        questionInputField: TextView,
        answerInputField: TextView,
        view: View,
        backAction: Boolean,
        currentFolderId: Int
    ) {
        if(backAction && questionInputField.text.toString().trim().isNotEmpty() && answerInputField.text.toString().trim().isNotEmpty()){
            Snackbar.make(view, R.string.flashcard_save_warning, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes) {
                    findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment)
                }
                .show()
        }else if (backAction){
            findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment)
        }else{
            if (questionInputField.text.toString().trim().isEmpty() || answerInputField.text.toString().trim().isEmpty()) {
                Snackbar.make(view, R.string.empty_flashcard, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                saveNote(questionInputField, answerInputField, currentFolderId)
                Snackbar.make(view, R.string.save_flashcard_confirmation, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun saveNote(questionField: TextView, answerField: TextView, currentFolderId:Int) {
        val question: String = questionField.text.toString()
        val answer: String = answerField.text.toString()
        val id:String = UUID.randomUUID().toString()
        val flashCard = FlashCardModel(id,question, answer, currentFolderId)
        if(question.isNotEmpty() || answer.isNotEmpty()){
            context?.let { FlashCardDatabaseHandler(context= requireContext()).addFlashCard(flashCard) }
        }
        findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment,Bundle().apply {
            putInt("currentFolderId",currentFolderId)
        })
    }
}