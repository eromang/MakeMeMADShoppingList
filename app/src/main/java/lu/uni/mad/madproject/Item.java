package lu.uni.mad.madproject;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Entity class that represents a word in the database
 * https://developer.android.com/codelabs/android-training-livedata-viewmodel?index=..%2F..%2Fandroid-training#0
 */

@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "item")
    private String mItem;

    public Item(@NonNull String item) {
        this.mItem = item;
    }

    /*
     * This constructor is annotated using @Ignore, because Room expects only
     * one constructor by default in an entity class.
     */

    @Ignore
    public Item(int id, @NonNull String item) {
        this.id = id;
        this.mItem = item;
    }

    public String getItem() {
        return this.mItem;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

}
