package com.example.scanner_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.Normalizer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.scanner_app.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.ArrayList;
import java.util.List;
import android.view.animation.Animation;import android.view.animation.AnimationUtils;
import android.view.MotionEvent;
import android.view.View;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    Button btn;
    Button btn2;
    Button btn3;
    TextView txt;
    List<String> savedQrCodes;private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("qr_codes");
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        txt = findViewById(R.id.txt);
        savedQrCodes = new ArrayList<>();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        btn.startAnimation(animation);
        btn2.startAnimation(animation);
        btn3.startAnimation(animation);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle button press animation
                        Animation pressAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_animation);
                        v.startAnimation(pressAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Handle button release animation
                        Animation releaseAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_animation2);
                        v.startAnimation(releaseAnimation);
                        break;
                }
                return false;
            }
        });

        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle button press animation
                        Animation pressAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_animation);
                        v.startAnimation(pressAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Handle button release animation
                        Animation releaseAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_animation2);
                        v.startAnimation(releaseAnimation);
                        break;
                }
                return false;
            }
        });

        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle button press animation
                        Animation pressAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_animation);
                        v.startAnimation(pressAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Handle button release animation
                        Animation releaseAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_animation2);
                        v.startAnimation(releaseAnimation);
                        break;
                }
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("SCAN A QR CODE!");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Second.class);
                intent.putStringArrayListExtra("SAVED_QR_CODES", (ArrayList<String>) savedQrCodes);
                startActivity(intent);
            }
        });



        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Third.class);
                startActivity(intent);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null && result.getContents() != null) {
                String qrCodeData = result.getContents();
                saveQrCodeToFirebase(qrCodeData);
                savedQrCodes.add(qrCodeData);

            }
        }
    }

    private void saveQrCodeToFirebase(String qrCodeLink) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            databaseReference.child(key).setValue(qrCodeLink)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // QR code link saved successfully
                            Toast.makeText(MainActivity.this, "QR code link saved", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error occurred while saving QR code link
                            String errorMessage = "Failed to save QR code link: " + e.getMessage();
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}
