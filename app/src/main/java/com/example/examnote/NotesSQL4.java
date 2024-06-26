package com.example.examnote;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NotesSQL4 extends AndroidViewModel {
    private NotesSQL3 repository;
    private LiveData<List<Folder>> allFolders;
    private LiveData<List<Note>> notesByFolder;

    public NotesSQL4(@NonNull Application application) {
        super(application);
        repository = new NotesSQL3(application);
        allFolders = repository.getAllFolders();
    }

    public LiveData<List<Folder>> getAllFolders() {
        return allFolders;
    }

    public LiveData<List<Note>> getNotesByFolder(int folderId) {
        notesByFolder = repository.getNotesByFolder(folderId);
        return notesByFolder;
    }

    public LiveData<Note> getNoteById(int noteId) {
        return repository.getNoteById(noteId);
    }

    public void insertFolder(String name) {
        Folder folder = new Folder(name);
        repository.insertFolder(folder);
    }

    public void insertNote(String title, String content, int folderId) {
        Note note = new Note(title, content, folderId, null);
        repository.insertNote(note);
    }

    public void updateFolder(Folder folder) {
        repository.updateFolder(folder);
    }

    public void deleteFolder(Folder folder) {
        repository.deleteFolder(folder);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }

    public LiveData<List<Note>> searchNotes(String query, long miao) {
        return repository.searchNotes(query,miao);
    }
}