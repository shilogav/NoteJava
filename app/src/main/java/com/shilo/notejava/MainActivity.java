package com.shilo.notejava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shilo.notejava.adapter.RecyclerAdapter;
import com.shilo.notejava.databinding.ActivityMainBinding;
import com.shilo.notejava.model.Note;
import com.shilo.notejava.viewModel.MainViewModel;
import java.util.List;
import static com.shilo.notejava.EditNoteActivity.EDIT_NOTE_REQUEST;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    private ActivityMainBinding mainBinding;
    private RecyclerAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);********** data binding replace it
        //data binding
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        ////////////////////////
        // mvvm
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.init();
        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.notifyDataSetChanged();
                viewModel.setNotes();
            }
        });
        ////////////////////////

        //recyclerview
        initRecyclerView();

        ////////////////////////
        FloatingActionButton actionButton = findViewById(R.id.add_note_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (MainActivity.this, EditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
            String content = data.getStringExtra(EditNoteActivity.EXTRA_CONTENT);

            Note note = new Note(title,content);
            //TODO: add save note func to database
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //Recycler view initialize
    private void initRecyclerView(){
        adapter = new RecyclerAdapter(this, viewModel.getNotes().getValue());
        adapter.setOnRVClickListener(new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(Note note) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerView.setHasFixedSize(true);
        mainBinding.recyclerView.setAdapter(adapter);
    }
}
