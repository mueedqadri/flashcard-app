package com.example.flashcards.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.adapters.FlashCardListRecyclerViewAdapter
import com.example.flashcards.persistence.FlashCardDatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class FlashCardListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.flashcard_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentFolderId :Int = requireArguments().getInt("currentFolderId")
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarFlashcardList)
        val flashcardRecyclerViewUI: RecyclerView = view.findViewById(R.id.flashcardListRecyclerView)
        flashcardRecyclerViewUI.layoutManager = LinearLayoutManager(activity)
        val flashcardListRecyclerViewAdapter: FlashCardListRecyclerViewAdapter =
            FlashCardListRecyclerViewAdapter()

        context?.let { FlashCardDatabaseHandler(context = requireContext()).viewFlashCards(currentFolderId) }
            ?.let { flashcardListRecyclerViewAdapter.setFlashCards(it) }

        flashcardRecyclerViewUI.adapter = flashcardListRecyclerViewAdapter
        flashcardListRecyclerViewAdapter.onDeleteClickListener(object:
            FlashCardListRecyclerViewAdapter.OnDeleteClickListener {
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
            FlashCardListRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                var item = flashcardListRecyclerViewAdapter.getFlashCards()[position]
                var allCards = flashcardListRecyclerViewAdapter.getFlashCards();
                Log.i("All the flashcards",allCards.toString())
                findNavController().navigate(R.id.action_flashcardListFragment_to_viewFlashCardFragment,Bundle().apply {
                    putString("fileName",item.toString())
                    putInt("currentFolderId", currentFolderId)
                    putSerializable("currCard", item)
                    putInt("currCardPosition", position)
                    putSerializable("allCards",allCards as Serializable)
                })
            }
        })
        toolBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_flashcardListFragment_to_homepageFragment)
        }
        view.findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            findNavController().navigate(R.id.action_flashcardListFragment_to_createFlashcardFragment,Bundle().apply {
                putInt("currentFolderId", currentFolderId)
            })
        }
    }
}