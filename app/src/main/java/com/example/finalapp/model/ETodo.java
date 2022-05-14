package com.example.finalapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.finalapp.utility.DateConverter;

import java.util.Date;

@Entity(tableName = "todo_table")
public class ETodo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "todo_date")
    @TypeConverters({DateConverter.class})
    private Date todo_date;

    @ColumnInfo(name = "isComplete")
    private Boolean isComplete;

    @ColumnInfo(name = "priority")
    private int priority;

    //Constructors
    @Ignore
    public ETodo()
    {

    }

    public ETodo(@NonNull String title, String description, Date todo_date, Boolean isComplete, int priority)
    {
        this.title = title;
        this.description = description;
        this.todo_date = todo_date;
        this.isComplete = isComplete;
        this.priority = priority;
    }

    //Getters and Setters

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTodo_date() {
        return todo_date;
    }

    public void setTodo_date(Date todo_date) {
        this.todo_date = todo_date;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}