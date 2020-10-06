package com.shilo.notejava.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.shilo.notejava.model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> notes, sortDateNotes
            , sortColorNotes , searchedNotes;
    //private MutableLiveData<String> wordMu;
    private static NoteRepository instance;


    public NoteRepository (Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        notes = noteDao.getAllNotes();
        sortDateNotes = noteDao.getDateSortNotes();
        sortColorNotes = noteDao.getColorSortNotes();
        //searchedNotes = noteDao.getSearchedNotes(wordMu.getValue());
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return notes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteAllNote();
            return null;
        }
    }


    /*private void initializeNotes(){
        notes = new ArrayList<>();


        Note note1 = new Note();
        Note note2 = new Note();
        note1.setTitle("example");
        note2.setTitle("another one");
        notes.add(note2);
        notes.add(note1);
    }*/



    public LiveData<List<Note>> getNotes(){
        //initializeNotes();
        //getNotesFromCloud();
        //MutableLiveData<List<Note>> data = new MutableLiveData<>();
        //data.setValue(notes);
        return notes;
    }

    public LiveData<List<Note>> getDateSortNotes(){
        return sortDateNotes;
    }

    public LiveData<List<Note>> getColorSortNotes(){
        return sortColorNotes;
    }

    public LiveData<List<Note>> getNoteByWord(String word){
        Log.i("notes data", "Note repository:  " + word);
        return noteDao.getSearchedNotes(word);
    }

    /*private void getNotesFromCloud(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }*/




}
