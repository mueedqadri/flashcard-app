package com.example.flashcards.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.models.FlashCardModel
import com.example.flashcards.persistence.FlashCardDatabaseHandler
import com.google.android.material.snackbar.Snackbar
import java.util.*

class CreateFlashcardFragment : Fragment() {
    private val CAMERA_REQUEST_CODE = 200
    private val GALLERY_REQUEST_CODE = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_flash_card_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentFolderId :Int = requireArguments().getInt("currentFolderId")
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarCreateFlashcardFragment)
        val questionInputField = view.findViewById<TextView>(R.id.questionInputField)
        val answerInputField = view.findViewById<TextView>(R.id.answerInputField)
        val cameraQuestion = view.findViewById<Button>(R.id.questionCameraButton)
        toolBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment,Bundle().apply {
                putInt("currentFolderId",currentFolderId)
            })
        }
        view.findViewById<Button>(R.id.createFlashcardButton).setOnClickListener {
            checkCard(questionInputField, answerInputField, view, backAction = false, currentFolderId)
        }
        cameraQuestion.setOnClickListener{
            showImagePickDialog()
        }
    }

    private fun showImagePickDialog() {
        val options = arrayOf("Camera", "Gallery")
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Pick Image From")
        builder.setItems(
            options,
            DialogInterface.OnClickListener { dialog, which ->
                if (which == 0) {
                    capturePhoto()
                } else if (which == 1) {
                    openGalleryForImage()
                }
            })
        builder.create().show()
    }
    private fun capturePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        val imageView = view?.findViewById<ImageView>(R.id.image_view);
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE && data != null){
//            imageView?.setImageBitmap(data.extras?.get("data") as Bitmap)
//        } else if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE){
//            imageView?.setImageURI(data?.data) // handle chosen image
//        }
//
//    }


    private fun checkCard(
        questionInputField: TextView,
        answerInputField: TextView,
        view: View,
        backAction: Boolean,
        currentFolderId: Int
    ) {
        if(backAction && questionInputField.text.toString().trim().isNotEmpty() && answerInputField.text.toString().trim().isNotEmpty()){
            Snackbar.make(view, R.string.flashcard_save_warning, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes) {
                    findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment)
                }
                .show()
        }else if (backAction){
            findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment)
        }else{
            if (questionInputField.text.toString().trim().isEmpty() || answerInputField.text.toString().trim().isEmpty()) {
                Snackbar.make(view, R.string.empty_flashcard, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                saveNote(questionInputField, answerInputField, currentFolderId)
                Snackbar.make(view, R.string.save_flashcard_confirmation, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun saveNote(questionField: TextView, answerField: TextView, currentFolderId:Int) {
        val question: String = questionField.text.toString()
        val answer: String = answerField.text.toString()
        val id:String = UUID.randomUUID().toString()
        val flashCard = FlashCardModel(id,question, answer, currentFolderId)
        if(question.isNotEmpty() || answer.isNotEmpty()){
            context?.let { FlashCardDatabaseHandler(context= requireContext()).addFlashCard(flashCard) }
        }
        findNavController().navigate(R.id.action_createNoteFragment_to_notesListFragment,Bundle().apply {
            putInt("currentFolderId",currentFolderId)
        })
    }
}