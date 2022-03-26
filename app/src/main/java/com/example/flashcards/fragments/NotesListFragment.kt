package com.example.flashcards.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.adapters.NotesListRecyclerViewAdapter
import com.example.flashcards.models.FlashCardModel
import com.example.flashcards.persistence.FlashCardDatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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
        val flashcardListRecyclerViewAdapter: NotesListRecyclerViewAdapter =
            NotesListRecyclerViewAdapter()

        context?.let { FlashCardDatabaseHandler(context = requireContext()).viewFlashCards() }
            ?.let { flashcardListRecyclerViewAdapter.setFlashCards(it) }

        flashcardRecyclerViewUI.adapter = flashcardListRecyclerViewAdapter
        flashcardListRecyclerViewAdapter.onDeleteClickListener(object:
            NotesListRecyclerViewAdapter.OnDeleteClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDeleteClick(position: Int) {
                Snackbar.make(view, R.string.flashcard_delete_warning, Snackbar.LENGTH_LONG)
                    .setAction(R.string.yes) {
                        val item = flashcardListRecyclerViewAdapter.getFlashCards()[position]
                        FlashCardDatabaseHandler(context = requireContext()).deleteFlashCard(item.id)
                        flashcardListRecyclerViewAdapter.setFlashCards(flashcardListRecyclerViewAdapter.getFlashCards().filter {
                            it.id != item.id
                        } )
                        flashcardListRecyclerViewAdapter.notifyItemRemoved(position)
                        Snackbar.make(view, R.string.delete_successful, Snackbar.LENGTH_LONG)
                            .show()
                    }
                    .show()

            }
        })
        flashcardListRecyclerViewAdapter.setOnItemClickListener(object :
            NotesListRecyclerViewAdapter.OnItemClickListener {
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
    }

    private fun onListItemClick(position: Int) {

    }
}