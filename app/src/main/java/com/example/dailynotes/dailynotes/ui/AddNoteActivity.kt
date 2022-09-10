package com.example.dailynotes.dailynotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.dailynotes.dailynotes.db.NoteDataBase
import com.example.dailynotes.dailynotes.db.NoteEntity
import com.example.dailynotes.dailynotes.utils.Constants.NOTE_DATABASE
import com.example.dailynotes.databinding.ActivityAddNoteBinding
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    private val noteDB : NoteDataBase by lazy {
        Room.databaseBuilder(this,NoteDataBase::class.java,NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var noteEntity: NoteEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDesc.text.toString()

                if (title.isNotEmpty() || desc.isNotEmpty()){
                    noteEntity= NoteEntity(0,title,desc)
                    noteDB.dao().insertNote(noteEntity)
                    finish()
                }
                else{
                    Snackbar.make(it,"Title and Description cannot be Empty️",Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }
}