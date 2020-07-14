package com.shilo.notejava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shilo.notejava.R;
import com.shilo.notejava.databinding.RawNoteBinding;
import com.shilo.notejava.model.Note;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private Context context;
    private List<Note> notes = new ArrayList<Note>();
    private RecyclerViewClickListener listener;

    /*public RecyclerAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }*/ // it's belong to mitch. 'codeinflow' do it in other way

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listNote= layoutInflater.inflate(R.layout.raw_note, parent, false);
        return new ViewHolder(listNote);*/ //replaced

        //////data binding
        RawNoteBinding rawNoteBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.raw_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(rawNoteBinding);
        ///////
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.rawNoteBinding.setNote(note);
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {

        RawNoteBinding rawNoteBinding;

        public ViewHolder(RawNoteBinding rawNote//, RecyclerViewClickListener listener
        ) {
            super(rawNote.getRoot());
            rawNoteBinding = rawNote;
            //mListener = listener;
            //rawNoteBinding.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(Note note);
    }

    public void setOnRVClickListener(RecyclerViewClickListener listener) {
        this.listener = listener;
    }
}
