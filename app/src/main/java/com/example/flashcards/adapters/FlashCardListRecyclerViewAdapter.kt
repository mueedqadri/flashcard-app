package com.example.flashcards.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.models.FlashCardModel

class FlashCardListRecyclerViewAdapter : RecyclerView.Adapter<FlashCardListRecyclerViewAdapter.FlashCardListItem>() {
    private var flashcard = emptyList<FlashCardModel>()
    private lateinit var mListener : OnItemClickListener
    private lateinit var deleteListener : OnDeleteClickListener

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardListItem {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val flashcardListItemView: View = layoutInflater.inflate(R.layout.flash_card_list_item, parent, false)
        return FlashCardListItem(flashcardListItemView,mListener, deleteListener)
    }

    override fun onBindViewHolder(holder: FlashCardListItem, position: Int) {
        val flashCard: FlashCardModel = flashcard[position]
        if(flashCard.image.hasImage){
            holder.questionTextView?.text = "Image-Card"
            if(flashCard.image.bitmap != null){
                holder.thumbnail?.setImageBitmap(flashCard.image.bitmap)
            }else{
                holder.thumbnail?.setImageURI(flashCard.image.uri)
            }
        }else{
            holder.questionTextView?.text = flashCard.question
        }
        holder.answerTextView?.text = flashCard.answer
        holder.flashcardPosition = position
    }

    override fun getItemCount(): Int {
        return flashcard.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFlashCards(flashCards: List<FlashCardModel>) {
        this.flashcard = flashCards
        notifyDataSetChanged()
    }

    fun getFlashCards(): List<FlashCardModel> {
        return this.flashcard;
    }

    inner class FlashCardListItem(flashcardListItemView: View?, listener: OnItemClickListener, deleteListener: OnDeleteClickListener) : RecyclerView.ViewHolder(flashcardListItemView!!) {
        val questionTextView: TextView? = flashcardListItemView?.findViewById<TextView>(R.id.questionTextView)
        val answerTextView: TextView? = flashcardListItemView?.findViewById<TextView>(R.id.answerTextView)
        val thumbnail: ImageView? = flashcardListItemView?.findViewById<ImageView>(R.id.flashCardThumbnail)
        private val deleteButton: Button? = flashcardListItemView?.findViewById<Button>(R.id.deleteFlashCardButton)
        var flashcardPosition = 0
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
            deleteButton?.setOnClickListener{
                deleteListener.onDeleteClick(adapterPosition)
            }
        }
    }
}