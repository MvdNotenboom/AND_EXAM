package com.notenboom.and_exam_projecttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*************************************************************************
 * Object class to hold the diffrent items for the recyclerviews
 * Here it also classified as an entity for the room database
 ************************************************************************/

@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String itemTitle;
    private String description;
    private String type;
    private int priority;

    public Item(String itemTitle, String description, String type, int priority) {
        this.itemTitle = itemTitle;
        this.description = description;
        this.type = type;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }
}
