package com.example.examnote;


import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Notes_ye extends AppCompatActivity {
    private NotesViewModel notesViewModel;
    private Notes_liebiao notesLiebiao;
    private int folderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
//a
        folderId = getIntent().getIntExtra("folderSuoyin", -1);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        Button addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(v -> {
            notesViewModel.insertNote("新笔记", "笔记内容", folderId);
        });

        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesLiebiao = new Notes_liebiao(this, notesViewModel);
        notesRecyclerView.setAdapter(notesLiebiao);

        notesViewModel.getNotesByFolder(folderId).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesLiebiao.setNotes(notes);
            }
        });
    }
}