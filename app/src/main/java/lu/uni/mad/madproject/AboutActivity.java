package lu.uni.mad.madproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.android.example.roomwordssample.BuildConfig;
import com.android.example.roomwordssample.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView textView = findViewById(R.id.about_text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(getString(R.string.about_title)));
        textView.append(Html.fromHtml(getString(R.string.about_version, BuildConfig.VERSION_NAME)));
        textView.append("\n");
        textView.append("\n");
        textView.append("\n");
        textView.append(Html.fromHtml(getString(R.string.about_github)));
        textView.append("\n");
        textView.append("\n");
        textView.append("\n");
        textView.append(Html.fromHtml(getString(R.string.about_license)));
        textView.append("\n");
        textView.append("\n");
        textView.append("\n");
        textView.append(Html.fromHtml(getString(R.string.about_author)));
        textView.append("\n");
        textView.append("\n");
        textView.append("\n");
        textView.append(Html.fromHtml(getString(R.string.about_contributors)));
    }
}