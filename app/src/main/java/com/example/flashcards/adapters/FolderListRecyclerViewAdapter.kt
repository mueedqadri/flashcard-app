package com.example.flashcards.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.models.FlashCardModel
import com.example.flashcards.models.FolderModel

class FolderListRecyclerViewAdapter() : RecyclerView.Adapter<FolderListRecyclerViewAdapter.FolderListItem>(){
    private var folderList = emptyList<FolderModel>()
    private lateinit var mListener : OnItemClickListener
    private lateinit var deleteListener : OnDeleteClickListener

    inner class FolderListItem(folderListItemView: View?, listener: OnItemClickListener, deleteListener: OnDeleteClickListener) : RecyclerView.ViewHolder(folderListItemView!!) {
        val folderName: TextView? = folderListItemView?.findViewById<TextView>(R.id.folderNameTextView)
        var folderPosition = 0
        private val deleteButton: Button? = folderListItemView?.findViewById<Button>(R.id.deleteFolderButton)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
            deleteButton?.setOnClickListener{
                deleteListener.onDeleteClick(adapterPosition)
            }
        }
    }

    interface  OnDeleteClickListener{
        fun onDeleteClick(position: Int)
    }

    fun onDeleteClickListener(listener: OnDeleteClickListener){
        deleteListener = listener

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderListItem {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val folderListItemView: View = layoutInflater.inflate(R.layout.folder_list_item, parent, false)
        return FolderListItem(folderListItemView, mListener,deleteListener)
    }

    override fun onBindViewHolder(holder: FolderListItem, position: Int) {
        val folder: FolderModel = folderList[position]
        holder.folderName?.text = folder.name
        holder.folderPosition = position
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    fun getFolders(): List<FolderModel> {
        return this.folderList;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFolderList(folderList: List<FolderModel>){
        this.folderList = folderList
        notifyDataSetChanged()
    }
}