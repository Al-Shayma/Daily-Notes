package com.example.dailynotes.dailynotes.db

import android.icu.text.CaseMap
import android.security.identity.AccessControlProfileId
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dailynotes.dailynotes.utils.Constants.NOTE_TABLE

@Entity(tableName = NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
 val noteId : Int,
    @ColumnInfo(name="name_title")
 val noteTitle : String,
    @ColumnInfo(name = "name_desc")
 val noteDesc : String

)
