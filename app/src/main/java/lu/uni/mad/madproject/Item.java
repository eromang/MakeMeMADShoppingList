package lu.uni.mad.madproject;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @description: Entity class that represents a item in the database
 * @author Eric ROMANG
 * @professor Dr. Jean Botev
 * @subject UNI S6 MAD - Project
 *
 */

@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "item")
    private String mItem;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "quantity")
    private String mQuantity;

    public Item(@NonNull String item, String description, String quantity) {
        this.mItem = item;
        this.mDescription = description;
        this.mQuantity = quantity;
    }

    /*
     * This constructor is annotated using @Ignore, because Room expects only
     * one constructor by default in an entity class.
     */

    @Ignore
    public Item(int id, @NonNull String item, String description, String quantity) {
        this.id = id;
        this.mItem = item;
        this.mDescription = description;
        this.mQuantity = quantity;
    }

    public String getItem() {
        return this.mItem;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getQuantity() { return mQuantity; }

    public void setQuantity(String quantity) { this.mQuantity = quantity; }
}
