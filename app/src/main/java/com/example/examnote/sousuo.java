package com.example.examnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class sousuo extends AppCompatActivity {

    Button doSearchButton;
    Button xzDate;
    EditText queryField;
    TextView sousuoRiqiXianshi;
    RecyclerView notesRecyclerView;

    long miao = -365*24*3600*1000;

    private NotesSQL4 viewModel;

    private Notes_liebiao notesLiebiao;

    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        viewModel = new ViewModelProvider(this).get(NotesSQL4.class);
        doSearchButton = findViewById(R.id.doSearch);
        queryField = findViewById(R.id.query);
        sousuoRiqiXianshi = findViewById(R.id.sousuoRiqiXianshi);
        xzDate = findViewById(R.id.xzeRiqi);
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager((this)));
        notesLiebiao = new Notes_liebiao(this, viewModel);
        notesRecyclerView.setAdapter(notesLiebiao);
        doSearchButton.setOnClickListener(v -> searchNotes());
        xzDate.setOnClickListener(v -> showDatePickerDialog());
    }


    private void searchNotes() {
        System.out.println("点击搜索按钮");
        String queryWord = queryField.getText().toString();
        if (queryWord.isEmpty()) {
            Toast.makeText(this, "请输入搜索关键词", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("查数据库："+queryWord);
            viewModel.searchNotes(queryWord,miao).observe(this, new Observer<List<Note>>() {

                @Override
                public void onChanged(List<Note> note) {

                    System.out.println("返回了几条笔记"+ note.size());
                    notesLiebiao.setNotes(note);
                }
            });
        }
    }

    private void showDatePickerDialog() {
        // 获取当前日期
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建并显示 DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                sousuo.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sousuoRiqiXianshi.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");

                        // 设置日期到 Calendar 对象
                        calendar.set(year, month, dayOfMonth);

                        // 显示时间选择对话框
                        showTimePickerDialog();
                    }
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        // 获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 创建并显示 TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                sousuo.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        sousuoRiqiXianshi.setText(
                                sousuoRiqiXianshi.getText().toString() + " " + hourOfDay + "时" + minute + "分"
                        );
                        System.out.println("选择的日期是"+calendar.getTime());
                        miao = calendar.getTime().getTime() / 1000;
                    }
                },
                hour,
                minute,
                true // 是否为24小时制
        );
        timePickerDialog.show();
    }

}