package com.example.scanner_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Third extends AppCompatActivity {
    Button generate;
    ImageView img;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        generate = findViewById(R.id.generate);
        img = findViewById(R.id.img);
        text = findViewById(R.id.text);

        generate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle button press animation
                        Animation pressAnimation = AnimationUtils.loadAnimation(Third.this, R.anim.button_animation);
                        v.startAnimation(pressAnimation);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Handle button release animation
                        Animation releaseAnimation = AnimationUtils.loadAnimation(Third.this, R.anim.button_animation2);
                        v.startAnimation(releaseAnimation);
                        break;
                }
                return false;
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text.getText().toString(), BarcodeFormat.QR_CODE,300,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    img.setImageBitmap(bitmap);
                }catch (WriterException e){
                    throw new RuntimeException(e);
                }
            }
        });

    }
}