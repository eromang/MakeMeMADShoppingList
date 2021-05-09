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
 * @description: Data Access Object (DAO) for an item
 * @author Eric ROMANG
 * @professor Dr. Jean Botev
 * @subject UNI S6 MAD - Project
 *
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
