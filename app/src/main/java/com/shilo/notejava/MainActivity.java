package com.shilo.notejava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
        viewModel.init();//I'm deleting that func
        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
                //viewModel.setNotes();
            }
        });
        ////////////////////////

        //recyclerview
        initRecyclerView();

        ////////////////////////
        //add new note
        FloatingActionButton actionButton = findViewById(R.id.add_note_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (MainActivity.this, EditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        //delete note func
        swipeToDeleteNote();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
            String content = data.getStringExtra(EditNoteActivity.EXTRA_CONTENT);

            Note note = new Note(title,content);
            int id = note.getId();
            viewModel.insert(note);

            Toast.makeText(this, "note saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
          int id = data.getIntExtra(EditNoteActivity.EXTRA_ID,-1);

          if (id == -1){
              Toast.makeText(this,"can't be updated", Toast.LENGTH_SHORT).show();
              return;
          }

          String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
          String content = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
          Note note = new Note(title,content);
          note.setId(id);
          viewModel.update(note);
          Toast.makeText(this,"note update", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "note didn't save", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Recycler view initialize
     */
    private void initRecyclerView(){
        adapter = new RecyclerAdapter();
        adapter.setOnRVClickListener(new RecyclerAdapter.RecyclerViewClickListener() {
            //edit exist note
            @Override
            public void onClick(Note note) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);

                intent.putExtra(EditNoteActivity.EXTRA_ID,note.getId());
                intent.putExtra(EditNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(EditNoteActivity.EXTRA_CONTENT,note.getText());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.recyclerView.setHasFixedSize(true);
        mainBinding.recyclerView.setAdapter(adapter);
    }

    /**
     * delete note swipe fund
     */
    private void swipeToDeleteNote(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mainBinding.recyclerView);
    }

    ////////////////

    /**
     * menu func to main activity
     * @param menu object
     * @return true for showing the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_notes:
                viewModel.deleteAllNotes();
                Toast.makeText(getApplicationContext(),
                        "all notes deleted", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
