package com.shilo.notejava.viewModel;

import android.app.Application;

import com.shilo.notejava.Repository.NoteRepository;
import com.shilo.notejava.model.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Note>> mNotes;
    private NoteRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        mNotes= repository.getNotes();
    }

    public void init(){
        if(mNotes != null){
            return;
        }
        //repository = NoteRepository.getInstance();
        mNotes = repository.getNotes();
    }

    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAllNotes(Note note){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getNotes(){
        return mNotes;
    }
}
