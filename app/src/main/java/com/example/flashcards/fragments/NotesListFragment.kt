package com.example.flashcards.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.adapters.NotesListRecyclerViewAdapter
import com.example.flashcards.persistence.FlashCardDatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // var deleteButton = view.findViewById<Button>(R.id.notesListRecyclerView);

        val flashcardRecyclerViewUI: RecyclerView = view.findViewById(R.id.notesListRecyclerView)
        flashcardRecyclerViewUI.layoutManager = LinearLayoutManager(activity)
        val flashcardListRecyclerViewAdapter: NotesListRecyclerViewAdapter = NotesListRecyclerViewAdapter()

        var adapter = flashcardListRecyclerViewAdapter
        context?.let { FlashCardDatabaseHandler(context= requireContext()).viewFlashCards() }
            ?.let { flashcardListRecyclerViewAdapter.setFlashCards(it) }

        flashcardRecyclerViewUI.adapter = adapter
        adapter.setOnItemClickListener(object : NotesListRecyclerViewAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.i("position",position.toString())
                var item = flashcardListRecyclerViewAdapter.getFlashCards()[position]
                var allCards = flashcardListRecyclerViewAdapter.getFlashCards();
                findNavController().navigate(R.id.action_notesListFragment_to_viewFlashCardFragment,Bundle().apply {
                    putString("fileName",item.toString());

                    putSerializable("currCard", item)
                })
            }
        })

        view.findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_createNoteFragment)
        }

//        deleteButton.setOnClickListener{
//           Log.i("delete","janhavi")
//        }

//        val deletBtn = view.findViewById<Button>(R.id.deleteFlashCardButton)
//        deletBtn.setOnClickListener {
//
//        }
    }
}