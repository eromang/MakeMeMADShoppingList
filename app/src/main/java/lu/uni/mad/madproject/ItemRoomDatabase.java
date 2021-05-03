package lu.uni.mad.madproject;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * ItemRoomDatabase. Includes code to create the database.
 * After the app creates the database, all further interactions
 * with it happen through the ItemViewModel.
 */

@Database(entities = {Item.class}, version = 2, exportSchema = false)
public abstract class ItemRoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = ItemRoomDatabase.class.getSimpleName();

    public abstract ItemDao ItemDao();

    private static ItemRoomDatabase INSTANCE;

    public static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    Log.d(LOG_TAG, "Database instance creation");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new ItemRoomDatabase.PopulateDbAsync(INSTANCE).execute();
                }
            };

    // Populate the database with the initial data set
    // only if the database has no entries.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ItemDao mDao;

        // Initial data set
        private static String [] items = {"apple", "beer", "wine", "washing-up liquid", "soap",
                "bread", "cheese", "garbage bag", "rice", "pasta"};

        PopulateDbAsync(ItemRoomDatabase db) {
            mDao = db.ItemDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no items, then create the initial list of items.
            if (mDao.getAnyItem().length < 1) {
                for (int i = 0; i <= items.length - 1; i++) {
                    Log.d(LOG_TAG, "No items into the DB, populate it");
                    Item item = new Item(items[i], "test " + items[i]);
                    mDao.insert(item);
                }
            }
            Log.d(LOG_TAG, "Items existing in the DB, no need to populate");
            return null;
        }
    }

}
