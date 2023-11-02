package com.example.grocery.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "Product",foreignKeys = {@ForeignKey(entity = Producer.class,
        parentColumns = "id",
        childColumns = "aID",
        onDelete = ForeignKey.CASCADE)
})
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "aID",index = true)
    public int aID;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "price")
    public double price;
    @ColumnInfo(name = "priceD")
    public double priceD;
    @ColumnInfo(name = "rate")
    public int rate;
    @ColumnInfo(name = "date")
    public String date;


    public Product(int id, String name, String image, int aID, String description, double price, double priceD, int rate, String date) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.aID = aID;
        this.description = description;
        this.price = price;
        this.priceD = priceD;
        this.rate = rate;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getaID() {
        return aID;
    }

    public void setaID(int aID) {
        this.aID = aID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceD() {
        return priceD;
    }

    public void setPriceD(double priceD) {
        this.priceD = priceD;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
