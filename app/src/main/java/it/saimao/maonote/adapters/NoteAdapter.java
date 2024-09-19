package it.saimao.maonote.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.saimao.maonote.databinding.AdapterNoteBinding;
import it.saimao.maonote.entities.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {


    private List<Note> notes;
    private NoteClickListener listener;

    public NoteAdapter(List<Note> notes, NoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    public interface NoteClickListener {
        void onNoteClicked(Note note);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        var binding = AdapterNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {


        var note = notes.get(position);
        holder.binding.tvTitle.setText(note.getTitle());
        holder.binding.tvContent.setText(note.getContent());
        holder.binding.cv.setOnClickListener(view -> listener.onNoteClicked(note));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        this.notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        public AdapterNoteBinding binding;

        public NoteViewHolder(AdapterNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
