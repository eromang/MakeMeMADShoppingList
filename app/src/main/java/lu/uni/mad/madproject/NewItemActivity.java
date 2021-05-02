package lu.uni.mad.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.example.roomwordssample.R;

import static lu.uni.mad.madproject.MainActivity.EXTRA_DATA_ID;
import static lu.uni.mad.madproject.MainActivity.EXTRA_DATA_UPDATE_ITEM;

/**
 * This class displays a screen where the user enters a new item.
 * The NewItemActivity returns the entered item to the calling activity
 * (MainActivity), which then stores the new item and updates the list of
 * displayed items.
 */

public class NewItemActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "lu.uni.mad.madproject.REPLY";
    public static final String EXTRA_REPLY_ID = "lu.uni.mad.madproject.REPLY_ID";

    private EditText mEditItemView;

    /**
     * Creation of the NewItemActivity used in two cases
     * First case, to create an item
     * Second case, to update an item
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        mEditItemView = findViewById(R.id.edit_item);
        int id = -1 ;

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String item = extras.getString(EXTRA_DATA_UPDATE_ITEM, "");
            if (!item.isEmpty()) {
                mEditItemView.setText(item);
                mEditItemView.setSelection(item.length());
                mEditItemView.requestFocus();
            }
        } // Otherwise, start with empty fields.

        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity).
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditItemView.getText())) {
                    // No item was entered, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new item that the user entered.
                    String item = mEditItemView.getText().toString();
                    // Put the new item in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, item);
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
