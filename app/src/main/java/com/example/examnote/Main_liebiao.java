package com.example.examnote;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Main_liebiao extends RecyclerView.Adapter<Main_liebiao.FolderViewHolder> {
    private List<Folder> folders = new ArrayList<>();
    private Context context;
    private NotesSQL4 notesSQL4;

    public Main_liebiao(Context context, NotesSQL4 notesSQL4) {
        this.context = context;
        this.notesSQL4 = notesSQL4;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_item, parent, false);
        return new FolderViewHolder(itemView);
    }//设定每一行的视图

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        Folder folder = folders.get(position);
        holder.folderNameTextView.setText(folder.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Notes_ye.class);
            intent.putExtra("folderSuoyin", folder.getId());
            context.startActivity(intent);

            //跳转到nate_ye

        });

        holder.renameButton.setOnClickListener(v -> showRenameDialog(folder));

        holder.deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog(folder));
    }

    private void showRenameDialog(Folder folder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("重命名");

        final EditText input = new EditText(context);
        input.setText(folder.getName());
        builder.setView(input);

        builder.setPositiveButton("确定", (dialog, which) -> {
            String newName = input.getText().toString();
            if (!newName.isEmpty()) {
                folder.setName(newName);
                notesSQL4.updateFolder(folder);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showDeleteConfirmationDialog(Folder folder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除文件夹");
        builder.setMessage("确认要删除文件夹吗?");
        builder.setPositiveButton("确定", (dialog, which) -> {
            notesSQL4.deleteFolder(folder);
            notifyDataSetChanged();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }

    static class FolderViewHolder extends RecyclerView.ViewHolder {
        TextView folderNameTextView;
        Button renameButton;
        Button deleteButton;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            folderNameTextView = itemView.findViewById(R.id.folderNameTextView);
            renameButton = itemView.findViewById(R.id.renameButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}