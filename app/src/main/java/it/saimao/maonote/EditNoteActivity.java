package it.saimao.maonote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.saimao.maonote.dao.NoteDao;
import it.saimao.maonote.databinding.ActivityEditNoteBinding;
import it.saimao.maonote.entities.Note;

public class EditNoteActivity extends AppCompatActivity {

    private ActivityEditNoteBinding binding;
    private Note note;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        initUi();
        initListeners();
    }

    private void initDatabase() {
        noteDao = AppDatabase.getInstance(this).noteDao();
    }

    private void initUi() {
        if (getIntent() != null) {
            note = (Note) getIntent().getSerializableExtra("note");
            if (note != null) {
                binding.etTitle.setText(note.getTitle());
                binding.etContent.setText(note.getContent());
                binding.btSave.setText(getString(R.string.update));
                binding.btCancel.setText(getString(R.string.delete));
            }
        }
    }

    private void initListeners() {
        binding.btCancel.setOnClickListener(view -> {
            if (note == null) {
                // ADD NOTE - Cancel
                finish();
            } else {
                // UPDATE NOTE - Delete
                noteDao.deleteNote(note);
                finish();
            }
        });

        binding.btSave.setOnClickListener(view -> {
            String title = binding.etTitle.getText().toString();
            String content = binding.etContent.getText().toString();
            if (note == null) {
                // ADD NOTE - add
                if (!title.isBlank() && !content.isBlank()) {
                    Note note = new Note(title, content);
                    Intent intent = new Intent();
                    intent.putExtra("note", note);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } else {
                // UPDATE NOTE - update
                if (!title.isBlank() && !content.isBlank()) {
                    if (!title.equals(note.getTitle()) || !content.equals(note.getContent())) {
                        // Modified note : Valid to update
                        note.setTitle(title);
                        note.setContent(content);
                        noteDao.updateNote(note);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}