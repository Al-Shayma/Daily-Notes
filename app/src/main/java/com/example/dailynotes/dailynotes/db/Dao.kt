package com.example.dailynotes.dailynotes.db

import androidx.room.*
import androidx.room.Dao
import com.example.dailynotes.dailynotes.utils.Constants.NOTE_TABLE

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity)
    @Update()
    fun ubdateNote(noteEntity: NoteEntity)
    @Delete()
    fun deleteNote(noteEntity: NoteEntity)
    @Query("SELECT * FROM $NOTE_TABLE ORDER BY noteId DESC")
    fun getAllNotes(): MutableList<NoteEntity>
    @Query("SELECT * FROM $NOTE_TABLE WHERE noteId LIKE:id")
    fun getNotes(id : Int) : NoteEntity
}