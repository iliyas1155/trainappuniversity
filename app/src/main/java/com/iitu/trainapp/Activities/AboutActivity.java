package com.iitu.trainapp.Activities;

import android.os.Bundle;
import android.text.Html;

import com.iitu.trainapp.R;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        HtmlTextView aboutTv = findViewById(R.id.about_text);
        aboutTv.setHtml(getString(R.string.about_text), new HtmlResImageGetter(aboutTv));
    }
}
