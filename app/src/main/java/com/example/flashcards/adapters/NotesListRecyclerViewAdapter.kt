package com.example.flashcards.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.models.FlashCardModel

class NotesListRecyclerViewAdapter : RecyclerView.Adapter<NotesListRecyclerViewAdapter.FlashCardListItem>() {
    private var flashcard = emptyList<FlashCardModel>()


    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardListItem {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val flashcardListItemView: View = layoutInflater.inflate(R.layout.notes_list_item, parent, false)
        return FlashCardListItem(flashcardListItemView,mListener)
    }

    override fun onBindViewHolder(holder: FlashCardListItem, position: Int) {
        val flashCard: FlashCardModel = flashcard[position]
        holder.questionTextView?.text = flashCard.question
        holder.answerTextView?.text = flashCard.answer
        holder.flashcardPosition = position
    }

    override fun getItemCount(): Int {
        return flashcard.size
    }

    fun setFlashCards(flashCards: List<FlashCardModel>) {
        this.flashcard = flashCards
        notifyDataSetChanged()
    }
    inner class FlashCardListItem(notesListItemView: View?, listener: onItemClickListener) : RecyclerView.ViewHolder(notesListItemView!!) {
        val questionTextView: TextView? = notesListItemView?.findViewById<TextView>(R.id.questionTextView)
        val answerTextView: TextView? = notesListItemView?.findViewById<TextView>(R.id.answerTextView)
        var flashcardPosition = 0

        init {

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }



}