package com.example.examnote;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepository {
    private Folder_jiekou folderJiekou;
    private Note_jiekou noteJiekou;
    private ExecutorService executorService;

    public NotesRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        folderJiekou = database.folderDao();
        noteJiekou = database.noteDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insertFolder(Folder folder) {
        executorService.execute(() -> folderJiekou.insertFolder(folder));
    }

    public LiveData<List<Folder>> getAllFolders() {
        return folderJiekou.getAllFolders();
    }

    public void insertNote(Note note) {
        executorService.execute(() -> noteJiekou.insertNote(note));
    }

    public LiveData<List<Note>> getNotesByFolder(int folderId) {
        return noteJiekou.getNotesByFolder(folderId);
    }

    public LiveData<Note> getNoteById(int noteId) {
        return noteJiekou.getNoteById(noteId);
    }

    public void updateFolder(Folder folder) {
        executorService.execute(() -> folderJiekou.updateFolder(folder));
    }

    public void deleteFolder(Folder folder) {
        executorService.execute(() -> folderJiekou.deleteFolder(folder));
    }
    public LiveData<List<Note>> searchNotes(String query, long miao) {
        return noteJiekou.searchNotes(query,miao);
    }
    public void updateNote(Note note) {
        executorService.execute(() -> noteJiekou.updateNote(note));
    }

    public void deleteNote(Note note) {
        executorService.execute(() -> noteJiekou.deleteNote(note));
    }
}