package com.shilo.notejava.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title, text, date;
    private int color;

    /*public Note() {
    }*/

    public Note(String title, String text, String date){
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String toString(){
        return "title: "+ this.title
                + ". content:"+ this.text
                + ". Id:"+ this.id;
    }
}
