package com.example.flashcards.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.models.FolderModel
import com.example.flashcards.persistence.FolderPersistence
import org.w3c.dom.Text

class HomepageFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //folder list
        val folderListViewUI: ListView = view.findViewById(R.id.folderListView)
        folderListViewUI.adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, FolderPersistence.folderList)

        //new folder
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