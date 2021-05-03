package lu.uni.mad.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.example.roomwordssample.R;

import static lu.uni.mad.madproject.MainActivity.EXTRA_DATA_ID;
import static lu.uni.mad.madproject.MainActivity.EXTRA_DATA_UPDATE_ITEM;
import static lu.uni.mad.madproject.MainActivity.EXTRA_DATA_UPDATE_ITEM_DESC;
import static lu.uni.mad.madproject.MainActivity.EXTRA_DATA_UPDATE_ITEM_QTY;

/**
 * This class displays a screen where the user enters a new item with
 * a description and a desired quantity
 * The NewItemActivity returns the entered item to the calling activity
 * (MainActivity), which then stores the new item, the description and the quantity
 * and updates the list of displayed items.
 */

public class NewItemActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "lu.uni.mad.madproject.REPLY";
    public static final String EXTRA_REPLY_DESC = "lu.uni.mad.madproject.REPLY_DESC";
    public static final String EXTRA_REPLY_QTY = "lu.uni.mad.madproject.REPLY_QTY";
    public static final String EXTRA_REPLY_ID = "lu.uni.mad.madproject.REPLY_ID";

    private EditText mEditItemView;
    private EditText mEditItemDescView;
    private Spinner spinner;

    /**
     * Creation of the NewItemActivity used in two cases
     * First case, to create an item
     * Second case, to update an item
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        // The item name and the item description TextViews
        mEditItemView = findViewById(R.id.edit_item);
        mEditItemDescView = findViewById(R.id.edit_description);

        // The quantity spinner
        spinner = findViewById(R.id.quantity_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int id = -1 ;

        // See if we received data from the MainActivity, to update an item
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String item = extras.getString(EXTRA_DATA_UPDATE_ITEM, "");
            String description = extras.getString(EXTRA_DATA_UPDATE_ITEM_DESC, "");
            String quantity = extras.getString(EXTRA_DATA_UPDATE_ITEM_QTY, "");

            if (!item.isEmpty()) {
                mEditItemView.setText(item);
                mEditItemView.setSelection(item.length());
                mEditItemView.requestFocus();

                mEditItemDescView.setText(description);
                mEditItemDescView.setSelection(description.length());
                mEditItemDescView.requestFocus();

                // Todo add the spinner return
                //spinner.setSelection(arrayAdapter.getPosition(quantity));
                int spinnerPosition = adapter.getPosition(quantity);
                spinner.setSelection(spinnerPosition);

            }
        }

        // Start with empty fields if no data provided

        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the MainActivity

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditItemView.getText())) {
                    // No item was entered, set the result as canceled
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new item, description and quantity that the user entered.
                    String item = mEditItemView.getText().toString();
                    String description = mEditItemDescView.getText().toString();
                    String qty = spinner.getSelectedItem().toString();
                    // Put the new item in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, item);
                    replyIntent.putExtra(EXTRA_REPLY_DESC, description);
                    replyIntent.putExtra(EXTRA_REPLY_QTY, qty);
                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}
