package com.example.scanner_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
public class Second extends AppCompatActivity {
    TextView qrCodesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        qrCodesTextView = findViewById(R.id.qrCodesTextView);

        List<String> savedQrCodes = getIntent().getStringArrayListExtra("SAVED_QR_CODES");

        StringBuilder qrCodesBuilder = new StringBuilder();
        for (String qrCode : savedQrCodes) {
            qrCodesBuilder.append(qrCode).append("\n");
        }

        String allQrCodesText = qrCodesBuilder.toString();
        qrCodesTextView.setText(allQrCodesText);

        // Make the links clickable
        Linkify.addLinks(qrCodesTextView, Linkify.ALL);
        qrCodesTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}



