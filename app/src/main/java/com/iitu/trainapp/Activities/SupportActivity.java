package com.iitu.trainapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iitu.trainapp.R;

public class SupportActivity extends BaseActivity {
    private EditText mailSubjectEt;
    private EditText mailBodyEt;
    private Button   sendMailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        mailSubjectEt  = findViewById(R.id.support_mail_subject);
        mailBodyEt     = findViewById(R.id.support_mail_body);
        sendMailButton = findViewById(R.id.support_send_mail_button);
        sendMailButton.setOnClickListener(new MailSender());
    }

    private class MailSender implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (mailBodyEt.getText().toString().isEmpty()) {
                Toast.makeText(SupportActivity.this, R.string.empty_mail_error, Toast.LENGTH_SHORT).show();
                return;
            }

            String recipient = "symbat9720@gmail.com"; // todo: Change recipient if needed.
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipient, null));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {recipient}); // For Android 4.3 support.
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, mailSubjectEt.getText().toString());
            emailIntent.putExtra(Intent.EXTRA_TEXT, mailBodyEt.getText().toString());
            startActivity(Intent.createChooser(emailIntent, getString(R.string.support_send_mail_title)));
        }
    }
}
