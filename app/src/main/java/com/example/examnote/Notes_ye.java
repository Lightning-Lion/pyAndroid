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
    private NotesSQL4 notesSQL4;
    private Notes_liebiao notesLiebiao;
    private int folderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
//a
        folderId = getIntent().getIntExtra("folderSuoyin", -1);

        notesSQL4 = new ViewModelProvider(this).get(NotesSQL4.class);

        Button addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(v -> {
            notesSQL4.insertNote("新笔记", "笔记内容", folderId);
        });

        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesLiebiao = new Notes_liebiao(this, notesSQL4);
        notesRecyclerView.setAdapter(notesLiebiao);

        notesSQL4.getNotesByFolder(folderId).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesLiebiao.setNotes(notes);
            }
        });
    }
}