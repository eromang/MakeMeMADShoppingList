/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lu.uni.mad.madproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.example.roomwordssample.R;

import java.util.List;

/**
 * @description: Main class to start the application
 * @author Eric ROMANG
 * @professor Dr. Jean Botev
 * @subject UNI S6 MAD - Project
 *
 */

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_ITEM_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_ITEM= "extra_item_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_ITEM_DESC= "extra_item_desc_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_ITEM_QTY= "extra_item_qty_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "OnCreate");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ItemListAdapter adapter = new ItemListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the ItemViewModel.
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        // Get all the items from the database
        // and associate them to the adapter.
        mItemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable final List<Item> items) {
                // Update the cached copy of the items in the adapter.
                Log.d(LOG_TAG, "Getting all items");
                adapter.setItems(items);
            }
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Click on the fab");
                Intent intent = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the
        // RecyclerView to delete the swiped item.
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // onMove() not implemented
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // onSwiped() implementation
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Item myItem = adapter.getItemAtPosition(position);
                        Log.d(LOG_TAG, "Swipe delete of item " + myItem.getItem());
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_item_preamble) + " " +
                                myItem.getItem(), Toast.LENGTH_LONG).show();

                        // Delete the item.
                        mItemViewModel.deleteItem(myItem);
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ItemListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Item item = adapter.getItemAtPosition(position);
                Log.d(LOG_TAG, "Click on item " + item.getItem() + " for update");
                launchUpdateItemActivity(item);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // The options menu has a "Clear all data now"
    // that deletes all the entries in the database.
    // The options menu has also an about display
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // To clear data
        if (id == R.id.clear_data) {
            Log.d(LOG_TAG, "Click on clear data");
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data.
            mItemViewModel.deleteAll();
            return true;
        }

        // To display about
        if(id == R.id.action_view_about) {
            Log.d(LOG_TAG, "Click on about");
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the user enters a new item in the NewItemActivity,
     * that activity returns the result to this activity.
     * If the user entered a new item, save it in the database.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Item item = new Item(data.getStringExtra(NewItemActivity.EXTRA_REPLY),
                    data.getStringExtra(NewItemActivity.EXTRA_REPLY_DESC),
                    data.getStringExtra(NewItemActivity.EXTRA_REPLY_QTY));
            // Save the data.
            mItemViewModel.insert(item);
        } else if (requestCode == UPDATE_ITEM_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String item_data = data.getStringExtra(NewItemActivity.EXTRA_REPLY);
            String item_description = data.getStringExtra(NewItemActivity.EXTRA_REPLY_DESC);
            String item_qty = data.getStringExtra(NewItemActivity.EXTRA_REPLY_QTY);
            int id = data.getIntExtra(NewItemActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                Log.d(LOG_TAG, "Update item id " + id + "with name: " + item_data + ", " +
                        "and description: " + item_description + ", and quantity: " + item_qty);
                mItemViewModel.update(new Item(id, item_data, item_description, item_qty));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateItemActivity( Item item) {
        Intent intent = new Intent(this, NewItemActivity.class);
        Log.d(LOG_TAG, "Send data to NewItemActivity for item id " + item.getId() + "with name: " + item.getItem() + ", " +
                "and description: " + item.getDescription() + ", and quantity: " + item.getQuantity());
        intent.putExtra(EXTRA_DATA_UPDATE_ITEM, item.getItem());
        intent.putExtra(EXTRA_DATA_UPDATE_ITEM_DESC, item.getDescription());
        intent.putExtra(EXTRA_DATA_UPDATE_ITEM_QTY, item.getQuantity());
        intent.putExtra(EXTRA_DATA_ID, item.getId());
        startActivityForResult(intent, UPDATE_ITEM_ACTIVITY_REQUEST_CODE);
    }
}