package com.example.dailynotes.dailynotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.dailynotes.dailynotes.adapter.DailyNoteAdapter
import com.example.dailynotes.dailynotes.db.NoteDataBase
import com.example.dailynotes.dailynotes.utils.Constants
import com.example.dailynotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val noteDB : NoteDataBase by lazy {
        Room.databaseBuilder(this, NoteDataBase::class.java, Constants.NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val dailyNoteAdapter by lazy { DailyNoteAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }
    override fun onResume() {
        super.onResume()
        chickItem()
    }
    private fun chickItem(){
        binding.apply {
            if(noteDB.dao().getAllNotes().isNotEmpty()){
                RvNoteList.visibility = View.VISIBLE
                TvEmptyText.visibility = View.GONE
                dailyNoteAdapter.differ.submitList(noteDB.dao().getAllNotes())
                setUpRecyclerView()
            } else{
                RvNoteList.visibility = View.GONE
                TvEmptyText.visibility = View.VISIBLE
            }
        }
    }
    private fun setUpRecyclerView(){
        binding.RvNoteList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = dailyNoteAdapter
        }
    }

}