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
import com.shilo.notejava.Repository.NoteDatabase;
import com.shilo.notejava.adapter.RecyclerAdapter;
import com.shilo.notejava.databinding.ActivityMainBinding;
import com.shilo.notejava.dialogs.ColorDialog;
import com.shilo.notejava.model.Note;
import com.shilo.notejava.viewModel.MainViewModel;
import java.util.List;

import static com.shilo.notejava.EditNoteActivity.EDIT_NOTE_REQUEST;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int ADD_NOTE_REQUEST = 1;
    private ActivityMainBinding mainBinding;
    private RecyclerAdapter adapter;
    private MainViewModel viewModel;
    public static final String COLOR_EXTRA = "color";
    private int color;
    private ColorDialog dialog;

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


                //open dialog

                dialog = new ColorDialog();
                dialog.show(getSupportFragmentManager(),"pick color dialog");

                /////

                /*Intent intent = new Intent
                        (MainActivity.this, EditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);*/
            }
        });

        //delete note func
        swipeToDeleteNote();
    }

    /**
     * func for creating or editing note for main activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)//new note
        {
            String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
            String content = data.getStringExtra(EditNoteActivity.EXTRA_CONTENT);

            Note note = new Note(title,content, NoteDatabase.timeFormat());
            note.setColor(color);
            viewModel.insert(note);

            Toast.makeText(this, "note saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {//update note
          int id = data.getIntExtra(EditNoteActivity.EXTRA_ID,-1);

          if (id == -1){
              Toast.makeText(this,"can't be updated", Toast.LENGTH_SHORT).show();
              return;
          }

          String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);
          String content = data.getStringExtra(EditNoteActivity.EXTRA_TITLE);

          Note note = new Note(title,content, NoteDatabase.timeFormat());
          note.setColor(color);
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
        //update note
        adapter.setOnRVClickListener(new RecyclerAdapter.RecyclerViewClickListener() {
            //edit exist note
            @Override
            public void onClick(Note note) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);

                intent.putExtra(EditNoteActivity.EXTRA_ID,note.getId());
                intent.putExtra(EditNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(EditNoteActivity.EXTRA_CONTENT,note.getText());
                color = note.getColor();
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent
                (MainActivity.this, EditNoteActivity.class);
        switch (view.getId()){
            case R.id.purple_button:
                Toast.makeText(this,"purple checked",Toast.LENGTH_LONG).show();
                color = R.color.light_purple;

            case R.id.green_button:
                Toast.makeText(this,"green checked",Toast.LENGTH_LONG).show();
                color = R.color.light_green;
                break;
            case R.id.blue_button:
                Toast.makeText(this,"blue checked",Toast.LENGTH_LONG).show();
                color = R.color.light_blue;
                break;
            case R.id.orange_button:
                Toast.makeText(this,"orange checked",Toast.LENGTH_LONG).show();
                color = R.color.light_orange;
                break;
            case R.id.yellow_button:
                Toast.makeText(this,"yellow checked",Toast.LENGTH_LONG).show();
                color = R.color.yellow;
                break;
            case R.id.red_button:
                Toast.makeText(this,"red checked",Toast.LENGTH_LONG).show();
                color = R.color.light_red;
                break;
            case R.id.white_button:
                Toast.makeText(this,"white checked",Toast.LENGTH_LONG).show();
                color = R.color.white;
                break;
        }
        intent.putExtra(COLOR_EXTRA,color);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
        dialog.dismiss();
    }
}
