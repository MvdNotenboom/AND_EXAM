package com.notenboom.and_exam_projecttracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.notenboom.and_exam_projecttracker.database.ItemRepository;

import java.util.List;

/*************************************************************************
 * General purpose viewmodel for the the logic between the data section and the UI
 ************************************************************************/

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository repository;
    private LiveData<List<Item>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        repository = new ItemRepository(application);
        allItems = repository.getAll();
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(Item item) {
        repository.update(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<Item>> getAll() {
        return allItems;
    }

}
