package com.example.examnote;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Note_main extends AppCompatActivity {
    private NotesSQL4 notesSQL4;
    private EditText title;
    private EditText dingwei;
    private EditText neirong;
    private ImageView imageView;

    private TextView riqiXianshi;

    private int noteId;
    private Note note;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        noteId = getIntent().getIntExtra("noteId", -1);
        notesSQL4 = new ViewModelProvider(this).get(NotesSQL4.class);

        title = findViewById(R.id.titleEditText);
        riqiXianshi = findViewById(R.id.riqiXianshi);
        neirong = findViewById(R.id.contentEditText);
        imageView = findViewById(R.id.imageView);
        dingwei = findViewById(R.id.location);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        notesSQL4.getNoteById(noteId).observe(this, note -> {
            if (note != null) {
                this.note = note;

                title.setText(note.getTitle());
                neirong.setText(note.getContent());
                dingwei.setText(note.getLocation());
                riqiXianshi.setText(Note.toChineseString(note.zhuanHuanDaoDate(note.getDate())));
                if (note.getHaveImage()) {
                    imageView.setImageResource(R.drawable.tupian);
                }
            }
        });
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveNote());

        Button crtupianButton = findViewById(R.id.crtupian);
        crtupianButton.setOnClickListener(v -> crAction());
    }

    private void saveNote() {
        note.setTitle(title.getText().toString());
        note.setContent(neirong.getText().toString());
        note.setLocation(dingwei.getText().toString());
        notesSQL4.updateNote(note);
        finish();
    }

    private int imageChoiced = 723;

    private void crAction() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,imageChoiced);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("图片选择成功！");
        if (resultCode == RESULT_OK && requestCode == imageChoiced) {
            imageView.setImageResource(R.drawable.tupian);
            note.setHaveImage(true);
        }
    }
}