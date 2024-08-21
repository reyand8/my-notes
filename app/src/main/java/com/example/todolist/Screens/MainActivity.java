package com.example.todolist.Screens;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.Adapter;
import com.example.todolist.Model.MainViewModel;
import com.example.todolist.Model.Note;
import com.example.todolist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> NoteDetailsActivity.start(MainActivity.this, null));

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getNoteLiveData().observe(this, notes -> adapter.setItems(notes));
    }
}
