package com.notenboom.and_exam_projecttracker.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.notenboom.and_exam_projecttracker.Item;

import java.util.List;

/*************************************************************************
 * Repository for the database that sends data upwards when a change is noticed
 * Also facilitates the data flow from and to the database
 * Uses LiveData to create instant updates
 ************************************************************************/

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;

    public ItemRepository(Application application) {
        ItemDatabase database = ItemDatabase.getInstance(application);
        itemDao = database.itemDao();
        allItems = itemDao.getAll();
    }

    public void insert(Item item) {
        new InsertAsyncTask(itemDao).execute(item);
    }

    public void update(Item item) {
        new UpdateAsyncTask(itemDao).execute(item);
    }

    public void delete(Item item) {
        new DeleteAsyncTask(itemDao).execute(item);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(itemDao).execute();
    }

    public LiveData<List<Item>> getAll() {
        return allItems;
    }

    private static class InsertAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        private InsertAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        private UpdateAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;

        private DeleteAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ItemDao itemDao;

        private DeleteAllAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.deleteAll();
            return null;
        }
    }
}
