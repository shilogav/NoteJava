package com.shilo.notejava.viewModel;

import com.shilo.notejava.Repository.NoteRepository;
import com.shilo.notejava.model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Note>> mNotes;
    private NoteRepository repository;

    public void init(){
        if(mNotes != null){
            return;
        }
        //repository = NoteRepository.getInstance();
        mNotes = repository.getNotes();
    }

    public void setNotes(){

    }

    public LiveData<List<Note>> getNotes(){
        return mNotes;
    }
}
