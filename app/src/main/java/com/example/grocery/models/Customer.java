package com.example.grocery.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Customer")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "std")
    public String sdt;
    @ColumnInfo(name = "aBalance")
    public float aBalace;

    public Customer(int id, String username, String password, String name, String sdt, float aBalace) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.sdt = sdt;
        this.aBalace = aBalace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public float getaBalace() {
        return aBalace;
    }

    public void setaBalace(float aBalace) {
        this.aBalace = aBalace;
    }
}
