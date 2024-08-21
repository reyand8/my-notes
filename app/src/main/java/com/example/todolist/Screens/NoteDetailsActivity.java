package com.example.todolist.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todolist.App;
import com.example.todolist.Model.MainViewModel;
import com.example.todolist.R;
import com.example.todolist.Model.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE";

    private EditText editText;
    private Note note;

    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, NoteDetailsActivity.class);
        if (note != null) {
            intent.putExtra(EXTRA_NOTE, note);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(getString(R.string.note_details_title));

        editText = findViewById(R.id.text);

        if (getIntent().hasExtra(EXTRA_NOTE)) {
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            editText.setText(note.text);
        } else {
            note = new Note();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        } else if (itemId == R.id.action_save) {
            if (editText.getText().length() > 0) {
                note.text = editText.getText().toString();
                note.done = false;
                note.timestamp = System.currentTimeMillis();
                if (getIntent().hasExtra(EXTRA_NOTE)) {
                    App.getInstance().getNoteDao().update(note);
                } else {
                    App.getInstance().getNoteDao().insert(note);
                }
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}