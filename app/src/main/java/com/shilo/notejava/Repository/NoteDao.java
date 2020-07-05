package com.shilo.notejava.Repository;

import com.shilo.notejava.model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM table_note")
    void deleteAllNote();

    @Query("SELECT * FROM table_note")
    LiveData<List<Note>> getAllNotes();
}
