package com.example.grocery.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Comment",foreignKeys = {@ForeignKey(entity = Product.class,
        parentColumns = "id",
        childColumns = "pID",
        onDelete = ForeignKey.CASCADE),@ForeignKey(entity = Customer.class,
        parentColumns = "id",
        childColumns = "cID",
        onDelete = ForeignKey.CASCADE)
})
public class Comment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "pID",index = true)
    public int pID;
    @ColumnInfo(name = "cID",index = true)
    public int cID;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "content")
    public String content;

    public Comment(int id, int pID, int cID, String date, String content) {
        this.id = id;
        this.pID = pID;
        this.cID = cID;
        this.date = date;
        this.content = content;
    }

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
