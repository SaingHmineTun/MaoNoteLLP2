package it.saimao.maonote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.List;

import it.saimao.maonote.adapters.NoteAdapter;
import it.saimao.maonote.dao.NoteDao;
import it.saimao.maonote.databinding.ActivityMainBinding;
import it.saimao.maonote.entities.Note;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NoteDao noteDao;
    private List<Note> notes;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        initUi();
        initListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Koyaw", "Main Activity On Start");
        refreshNotes();
    }

    private void initListeners() {
        binding.fabAddNote.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditNoteActivity.class);
            startActivityForResult(intent, 123);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Note note = (Note) data.getSerializableExtra("note");
            noteDao.addNote(note);
            refreshNotes();
        }
    }

    private void refreshNotes() {
        notes = noteDao.getAllNotes();
        noteAdapter.setNotes(notes);
    }

    private void initUi() {
        noteAdapter = new NoteAdapter(notes, note -> {
            Intent intent = new Intent(this, EditNoteActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
        });
        binding.rv.setAdapter(noteAdapter);
        binding.rv.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initDatabase() {
        var db = AppDatabase.getInstance(this);
        noteDao = db.noteDao();
        notes = noteDao.getAllNotes();
    }
}