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
import com.example.flashcards.models.NoteModel
import com.example.flashcards.persistence.NotesPersistence
import com.google.android.material.snackbar.Snackbar

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
        val noteTitleInputField = view.findViewById<TextView>(R.id.questionInputField)
        val noteBodyInputField = view.findViewById<TextView>(R.id.answerInputField)

        toolBar.setNavigationOnClickListener {
            //If title is not present and note has a body show the Snackbar
            if(noteTitleInputField.text.toString().trim().isEmpty() && noteBodyInputField.text.toString().isNotEmpty())  {
                Snackbar.make(view, R.string.empty_prompt, Snackbar.LENGTH_LONG)
                    .setAction(R.string.yes){
                        //Save the note after user conformation
                        saveNote(noteTitleInputField, noteBodyInputField)
                    }
                    .show()
            } else {
                saveNote(noteTitleInputField, noteBodyInputField)
                //Show note saved successfully
                Snackbar.make(view, R.string.save_flashcard_confirmation, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun saveNote(noteTitleInputField: TextView, noteBodyInputField: TextView) {
        val noteTitle: String = noteTitleInputField.text.toString()
        val noteBody: String = noteBodyInputField.text.toString()
        val note = NoteModel(noteTitle, noteBody)

        //Check if the note has a body and title
        if(noteTitle.isNotEmpty() || noteBody.isNotEmpty()){
            NotesPersistence.notes.add(note)
        }
        findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment)
    }
}