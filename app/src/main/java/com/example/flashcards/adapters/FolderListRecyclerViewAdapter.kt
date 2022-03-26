package com.example.flashcards.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.models.FolderModel

class FolderListRecyclerViewAdapter() : RecyclerView.Adapter<FolderListRecyclerViewAdapter.FolderListItem>(){
    private var folderList = emptyList<FolderModel>()
    inner class FolderListItem(folderListItemView: View?) : RecyclerView.ViewHolder(folderListItemView!!) {
        val folderName: TextView? = folderListItemView?.findViewById<TextView>(R.id.folderName)
        var folderPosition = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderListItem {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val folderListItemView: View = layoutInflater.inflate(R.layout.folder_list_item, parent, false)
        return FolderListItem(folderListItemView)
    }

    override fun onBindViewHolder(holder: FolderListItem, position: Int) {
        val folder: FolderModel = folderList[position]
        holder.folderName?.text = folder.folderName
        holder.folderPosition = position
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    fun setFolderList(folderList: List<FolderModel>){
        this.folderList = folderList
        notifyDataSetChanged()
    }
}