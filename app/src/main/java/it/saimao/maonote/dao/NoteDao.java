package it.saimao.maonote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.saimao.maonote.entities.Note;

@Dao
public interface NoteDao {

    @Insert // INSERT INTO notes (id, title, content) VALUES (note.getId(), note.getTitle(), note.getContent())
    void addNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM notes;")
    List<Note> getAllNotes();


}
