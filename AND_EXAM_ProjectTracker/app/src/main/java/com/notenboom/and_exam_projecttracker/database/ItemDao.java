package com.notenboom.and_exam_projecttracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.notenboom.and_exam_projecttracker.Item;

import java.util.List;

/*************************************************************************
 * DataAccessObject interface for the database handling and sending of SQL query's
 ************************************************************************/

@Dao
public interface ItemDao {
    @Insert
    void insert(Item item);
    @Update
    void update(Item item);
    @Delete
    void delete(Item item);
    @Query("DELETE FROM item_table")
    void deleteAll();
    @Query("SELECT*FROM item_table ORDER BY itemTitle, priority ASC")
    LiveData<List<Item>> getAll();
}
