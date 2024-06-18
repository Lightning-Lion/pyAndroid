package com.example.examnote;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Main extends AppCompatActivity {
    private NotesViewModel notesViewModel;
    private Main_liebiao mainliebiao;

    private Button jumpToSearchPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jumpToSearchPage = findViewById(R.id.jumpToSearchPage);
        jumpToSearchPage.setOnClickListener(v -> openSearchPage());
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        Button addFolderButton = findViewById(R.id.addFolderButton);
        addFolderButton.setOnClickListener(v -> {
            notesViewModel.insertFolder("新的文件夹");
        });

        RecyclerView foldersRecyclerView = findViewById(R.id.foldersRecyclerView);
        foldersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainliebiao = new Main_liebiao(this, notesViewModel); // 传递Context和NotesViewModel
        foldersRecyclerView.setAdapter(mainliebiao);

        notesViewModel.getAllFolders().observe(this, new Observer<List<Folder>>() {
            @Override
            public void onChanged(List<Folder> folders) {
                mainliebiao.setFolders(folders);
            }
        });
    }

    private void openSearchPage() {
        Intent intent = new Intent(Main.this,sousuo.class);
        startActivity(intent);
    }
}