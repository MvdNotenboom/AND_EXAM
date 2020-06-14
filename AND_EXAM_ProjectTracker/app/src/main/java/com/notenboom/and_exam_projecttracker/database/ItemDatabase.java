package com.notenboom.and_exam_projecttracker.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.notenboom.and_exam_projecttracker.Item;

/*************************************************************************
 * Database class this creates and does a first time only population with dummy data
 * Singleton in used to prevent multiple databases being created
 ************************************************************************/

@Database(entities = Item.class, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {
    private static ItemDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized ItemDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class, "item:database")
                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedbAsyncTask(instance).execute();
        }
    };

    private static class PopulatedbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ItemDao itemDao;

        private PopulatedbAsyncTask(ItemDatabase db) {
            itemDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Item("Project X", "Issue with xxx at xxxx its doing xxxxx", "bug", 1));
            itemDao.insert(new Item("Project Y", "Issue with yyy at yyyy its doing yyyyy", "bug", 2));
            itemDao.insert(new Item("Project Z", "Need to add zzz to zzzz to upgrade zzzzz", "feature", 4));
            return null;
        }
    }
}
