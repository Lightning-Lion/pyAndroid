package com.example.examnote;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Entity(tableName = "notes",
        foreignKeys = @ForeignKey(entity = Folder.class,
                parentColumns = "id",
                childColumns = "folderId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "folderId")})
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;
    private int folderId;
    private String location; // New location attribute
    private Boolean haveImage;
    private long date;

    // Constructors, getters, and setters
    public Note(String title, String content, int folderId, String location) {
        this.title = title;
        this.content = content;
        this.folderId = folderId;
        this.location = location;
        this.haveImage = false;
        this.date = zhuanhuanDaozhengshu(new Date());
    }

    public static String toChineseString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(date);
    }

    public static long zhuanhuanDaozhengshu(Date date) {
        return date.getTime() / 1000;
    }

    public static Date zhuanHuanDaoDate(long miaoshu) {
        return new Date(miaoshu * 1000);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Boolean getHaveImage() {
        return haveImage;
    }

    public void setHaveImage(Boolean haveImage) {
        this.haveImage = haveImage;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}