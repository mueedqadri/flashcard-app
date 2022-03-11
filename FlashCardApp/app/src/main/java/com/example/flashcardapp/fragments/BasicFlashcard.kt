package com.example.flashcardapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.flashcardapp.R
import com.example.flashcardapp.models.BasicFlashCardModel
import com.example.flashcardapp.persistance.BasicFlashcardPersistance

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasicFlashcard.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasicFlashcard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_flashcard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBar = view.findViewById<Toolbar>(R.id.top_bar_display_flashcards);
        val cancelButton = view.findViewById<Button>(R.id.cancel_button);
        val saveButton = view.findViewById<Button>(R.id.save_button);
        val flashcardTitleField = view.findViewById<TextView>(R.id.flashcardTitleInputField)
        val flashcardBodyField = view.findViewById<TextView>(R.id.flashcardBodyInputField);
        toolBar.setNavigationOnClickListener({
            findNavController().navigate(R.id.action_basicFlashcard_to_displayFlashCards)
        })
        cancelButton.setOnClickListener({
            findNavController().navigate(R.id.action_basicFlashcard_to_displayFlashCards)
        })
        saveButton.setOnClickListener({
            val flashCardTitle:String =flashcardTitleField.text.toString()
            val flashCardBody:String = flashcardBodyField.text.toString()
            val note = BasicFlashCardModel(flashCardTitle,flashCardBody)
            if(!flashCardTitle.trim().isEmpty()) {
                BasicFlashcardPersistance.notes.add(note)
            }
            findNavController().navigate(R.id.action_basicFlashcard_to_displayFlashCards)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.c
         * @param param2 Parameter 2.
         * @return A new instance of fragment BasicFlashcard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BasicFlashcard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}