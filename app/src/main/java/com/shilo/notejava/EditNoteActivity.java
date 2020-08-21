package com.shilo.notejava;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.shilo.notejava.MainActivity.COLOR_EXTRA;

public class EditNoteActivity extends AppCompatActivity {
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final String EXTRA_TITLE = "title_note";
    public static final String EXTRA_CONTENT = "content_note";
    public static final String EXTRA_ID = "id_note";
    private EditText title;
    private EditText content;
    private View layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.editTextTitle);
        content = findViewById(R.id.editTextContent);
        layout = findViewById(R.id.edit_note_layout);
        //set background
        int color = getIntent().getIntExtra(COLOR_EXTRA,-1);
        if (color != -1){
            layout.setBackgroundColor(color);
        }//

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("edit note");
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            content.setText(intent.getStringExtra(EXTRA_CONTENT));
        } else {
            setTitle("add note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }
    private void saveNote(){
        String titleS = title.getText().toString();
        String contentS = content.getText().toString();

        if (titleS.trim().isEmpty() || contentS.trim().isEmpty()){
            Toast.makeText(this,
                    "please insert title and content", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, titleS);
        intent.putExtra(EXTRA_CONTENT, contentS);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            intent.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
