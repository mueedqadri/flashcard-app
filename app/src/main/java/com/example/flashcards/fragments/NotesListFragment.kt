package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.adapters.NotesListRecyclerViewAdapter
import com.example.flashcards.persistence.FlashCardDatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flashcardRecyclerViewUI: RecyclerView = view.findViewById(R.id.notesListRecyclerView)
        flashcardRecyclerViewUI.layoutManager = LinearLayoutManager(activity)
        val flashcardListRecyclerViewAdapter: NotesListRecyclerViewAdapter = NotesListRecyclerViewAdapter()
        flashcardRecyclerViewUI.adapter = flashcardListRecyclerViewAdapter
        context?.let { FlashCardDatabaseHandler(context= requireContext()).viewFlashCards() }
            ?.let { flashcardListRecyclerViewAdapter.setFlashCards(it) }

        view.findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_createNoteFragment)
        }
    }
}