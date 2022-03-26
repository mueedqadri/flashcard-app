package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.adapters.FolderListRecyclerViewAdapter
import com.example.flashcards.models.FolderModel
import com.example.flashcards.persistence.FolderPersistence
import org.w3c.dom.Text

class HomepageFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //folder list - RecyclerView
        val folderRecyclerViewUI: RecyclerView = view.findViewById(R.id.folderListRecyclerView)
        folderRecyclerViewUI.layoutManager = LinearLayoutManager(activity)
        val folderListRecyclerViewAdapter: FolderListRecyclerViewAdapter = FolderListRecyclerViewAdapter()
        folderRecyclerViewUI.adapter = folderListRecyclerViewAdapter

        folderListRecyclerViewAdapter.setFolderList(FolderPersistence.folderList)

        //new folder - TODO: fix new folder name not displayed properly after creating folder and going back to homepage
        val folderNameInputField = view.findViewById<TextView>(R.id.newFolderNameField)
        val newFolderButton = view.findViewById<Button>(R.id.newFolderButton)

        newFolderButton.setOnClickListener{
            val folderName: String = folderNameInputField.toString()
            if (folderName.isNotEmpty()){
                val folder = FolderModel(folderName)

                FolderPersistence.folderList.add(folder)
                findNavController().navigate(R.id.action_homepageFragment_to_noteListFragment)
            }
        }
    }
}