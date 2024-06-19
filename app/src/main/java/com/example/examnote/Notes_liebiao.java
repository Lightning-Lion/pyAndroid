package com.example.examnote;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class Notes_liebiao extends RecyclerView.Adapter<Notes_liebiao.NoteViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private Context context;

    private NotesSQL4 notesSQL4;

    public Notes_liebiao(Context context, NotesSQL4 notesSQL4) {
        this.context = context;
        this.notesSQL4 = notesSQL4;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteTitleTextView.setText(note.getTitle());

        holder.itemViewButton.setOnClickListener(v -> {

            Intent intent = new Intent(context, Note_main.class);
            intent.putExtra("noteId", note.getId());
            context.startActivity(intent);

            //跳转笔记内容

        });

        holder.renameButton.setOnClickListener(v -> showRenameDialog(note));
        holder.deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog(note));
    }

    private void showRenameDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("重命名");

        final EditText input = new EditText(context);
        input.setText(note.getTitle());
        builder.setView(input);

        builder.setPositiveButton("确定", (dialog, which) -> {
            String newTitle = input.getText().toString();
            if (!newTitle.isEmpty()) {
                note.setTitle(newTitle);
                notesSQL4.updateNote(note);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showDeleteConfirmationDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除笔记");
        builder.setMessage("确定要删除这个笔记吗?");
        builder.setPositiveButton("确定", (dialog, which) -> {
            notesSQL4.deleteNote(note);
            notifyDataSetChanged();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitleTextView;
        Button itemViewButton;
        Button renameButton;
        Button deleteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView);
            itemViewButton = itemView.findViewById(R.id.itemViewButton);
            renameButton = itemView.findViewById(R.id.renameButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}