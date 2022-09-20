package com.example.dailynotes.dailynotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.room.Query
import androidx.room.Room
import com.example.dailynotes.dailynotes.db.NoteDataBase
import com.example.dailynotes.dailynotes.db.NoteEntity
import com.example.dailynotes.dailynotes.utils.Constants.BUNDLE_NOTE_ID
import com.example.dailynotes.dailynotes.utils.Constants.NOTE_DATABASE
import com.example.dailynotes.databinding.ActivityUpdateNoteBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    private val noteDB : NoteDataBase by lazy {
        Room.databaseBuilder(this,NoteDataBase::class.java,NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var noteEntity: NoteEntity
    private var noteId =0
    private var defaultTitle=""
    private var defaultDesc=""
    private var db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {
            noteId = it.getInt(BUNDLE_NOTE_ID)
        }
        binding.apply {
            defaultTitle = noteDB.dao().getNotes(noteId).noteTitle
            defaultDesc = noteDB.dao().getNotes(noteId).noteDesc

            edtTitle.setText(defaultTitle)
            edDes.setText(defaultDesc)


            btnDelete.setOnClickListener {
                noteEntity = NoteEntity(noteId, defaultTitle, defaultDesc)
                noteDB.dao().deleteNote(noteEntity)
                finish()
                db.collection("notes").document("DC")
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(this@UpdateNoteActivity,"deleted Successfully",Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@UpdateNoteActivity,"delete failed",Toast.LENGTH_SHORT).show()
                    }
            }


            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edDes.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()) {
                    noteEntity = NoteEntity(noteId, title, desc)
                    noteDB.dao().ubdateNote(noteEntity)
                    finish()
                } else {
                    Snackbar.make(it, "Title and Description cannot be Empty", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    }
