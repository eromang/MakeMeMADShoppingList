package lu.uni.mad.madproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * @description: Provides the interface between the UI and the data layer of the app represented by ItemRepository
 * @author Eric ROMANG
 * @professor Dr. Jean Botev
 * @subject UNI S6 MAD - Project
 *
 */

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;

    private LiveData<List<Item>> mAllItems;

    public ItemViewModel(Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    public void insert(Item item) {
        mRepository.insert(item);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteItem(Item item) {
        mRepository.deleteItem(item);
    }

    public void update(Item item) {
        mRepository.update(item);
    }
}
