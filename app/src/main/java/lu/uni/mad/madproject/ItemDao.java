package lu.uni.mad.madproject;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import lu.uni.mad.madproject.Item;

/**
 * Data Access Object (DAO) for an item.
 * Each method performs a database operation, such as inserting or deleting an item,
 * running a DB query, or deleting all items.
 */

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Delete
    void deleteItem(Item item);

    @Query("SELECT * from item_table LIMIT 1")
    Item[] getAnyItem();

    @Query("SELECT * from item_table ORDER BY item ASC")
    LiveData<List<Item>> getAllItems();

    @Update
    void update(Item... item);

}
