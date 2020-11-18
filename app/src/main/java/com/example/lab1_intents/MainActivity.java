package com.example.lab1_intents;

import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button startExplicitActivity;
    private Button startImplicitActivity;
    private Button shareContent;
    private Button selectPhoto;

    static final int EXPLICIT_RESULT_REQUEST = 1;
    static final int SELECT_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        bindEvent();
    }


    public void initUI() {
        textView = findViewById(R.id.textView);
        startExplicitActivity = findViewById(R.id.btnExplicitActivity);
        startImplicitActivity = findViewById(R.id.btnImplicitActivity);
        shareContent = findViewById(R.id.btnShare);
        selectPhoto = findViewById(R.id.btnSelectPhoto);
    }

    public void bindEvent() {
        startExplicitActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callExplicitActivity();
            }
        });

        startImplicitActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callImplicitAction();
            }
        });

        shareContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callShare();
            }
        });

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callSelectPhoto();
            }
        });
    }

    public void callExplicitActivity() {
        Intent explicit_intent = new Intent(MainActivity.this, ExplicitActivity.class);
        startActivityForResult(explicit_intent, EXPLICIT_RESULT_REQUEST);
    }

    public void callSelectPhoto() {
        Intent photoSelect = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(photoSelect, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EXPLICIT_RESULT_REQUEST && resultCode == RESULT_OK) {
            String receivedMessage = data.getStringExtra("Data");
            assert receivedMessage != null;
            if (receivedMessage.equals("")) {
                Toast.makeText(getApplicationContext(), "Empty Input Field!", Toast.LENGTH_SHORT).show();
            } else
                textView.setText(receivedMessage);
        }

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            openOnGallery(imageUri);
        }
    }

    public void callShare() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT,"MPiP Send Title");
        intent.putExtra(Intent.EXTRA_TEXT,"Content send from MainActivity");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Share"));
    }

    public void openOnGallery(Uri imageUri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(imageUri, "image/*");
        startActivity(Intent.createChooser(intent,"Select a Photo App"));
    }

    public void callImplicitAction(){
        Intent intent = new Intent("mk.ukim.finki.mpip.IMPLICIT_ACTION");
        startActivity(intent);
    }

}
