package com.example.flashcards.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.adapters.FlashCardListRecyclerViewAdapter
import com.example.flashcards.adapters.FolderListRecyclerViewAdapter
import com.example.flashcards.models.FolderModel
import com.example.flashcards.persistence.FlashCardDatabaseHandler
import com.google.android.material.snackbar.Snackbar

class HomepageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.homepage_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val folderRecyclerViewUI: RecyclerView = view.findViewById(R.id.folderListRecyclerView)
        folderRecyclerViewUI.layoutManager = LinearLayoutManager(activity)
        val folderListRecyclerViewAdapter: FolderListRecyclerViewAdapter =
            FolderListRecyclerViewAdapter()

        context?.let { FlashCardDatabaseHandler(context= requireContext()).viewFolders() }
            ?.let { folderListRecyclerViewAdapter.setFolderList(it) }
        folderRecyclerViewUI.adapter = folderListRecyclerViewAdapter
        folderListRecyclerViewAdapter.setOnItemClickListener(object :
            FolderListRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("position",position.toString())
                var item = folderListRecyclerViewAdapter.getFolders()[position]
                findNavController().navigate(R.id.action_homepageFragment_to_noteListFragment,Bundle().apply {
                    putInt("currentFolderId",item.id);
                })
            }
        })
        view.findViewById<Button>(R.id.newFolderButton).setOnClickListener {
            val folderNameTextView = view.findViewById<TextView>(R.id.newFolderNameField)
            if (folderNameTextView.text.toString().trim().isEmpty()) {
                Snackbar.make(view, R.string.empty_folder, Snackbar.LENGTH_LONG)
                    .show()
            }
            else{
                val newFolderId = context?.let { it1 -> FlashCardDatabaseHandler(it1).addFolder(FolderModel(0, folderNameTextView.text.toString())) }
                if (newFolderId != null){
                    findNavController().navigate(R.id.action_homepageFragment_to_noteListFragment,Bundle().apply {
                        putInt("currentFolderId",newFolderId.toInt())
                    })
                }
            }
        }
    }
}