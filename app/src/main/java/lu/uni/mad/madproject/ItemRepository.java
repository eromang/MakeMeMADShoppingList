package lu.uni.mad.madproject;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * @description: Holds the implementation code for the methods that interact with the database
 * @author Eric ROMANG
 * @professor Dr. Jean Botev
 * @subject UNI S6 MAD - Project
 *
 */

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;

    ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.ItemDao();
        mAllItems = mItemDao.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    public void insert(Item item) {
        new ItemRepository.insertAsyncTask(mItemDao).execute(item);
    }

    public void update(Item item)  {
        new ItemRepository.updateItemAsyncTask(mItemDao).execute(item);
    }

    public void deleteAll()  {
        new ItemRepository.deleteAllItemsAsyncTask(mItemDao).execute();
    }

    // Must run off main thread
    public void deleteItem(Item item) {
        new ItemRepository.deleteItemAsyncTask(mItemDao).execute(item);
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a item into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao mAsyncTaskDao;

        insertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all items from the database (does not delete the table).
     */
    private static class deleteAllItemsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ItemDao mAsyncTaskDao;

        deleteAllItemsAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Deletes a single item from the database.
     */
    private static class deleteItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao mAsyncTaskDao;

        deleteItemAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.deleteItem(params[0]);
            return null;
        }
    }

    /**
     *  Updates an item in the database.
     */
    private static class updateItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao mAsyncTaskDao;

        updateItemAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
