package com.example.lab1_intents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ExplicitActivity extends AppCompatActivity {

    EditText inputTextField;
    Button btn_OK;
    Button btn_Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);
        initUI();
        bindEvent();
    }

    public void initUI() {
        inputTextField = findViewById(R.id.inputTextField);
        btn_OK = findViewById(R.id.btn_OK);
        btn_Cancel = findViewById(R.id.btn_Cancel);
    }

    public void bindEvent() {
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OK(view);
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancel(view);
            }
        });

    }

    public void OK(View view) {
        Intent i = new Intent();
        String message = inputTextField.getText().toString();
        i.putExtra("Data", message);
        setResult(RESULT_OK, i);
        finish();
    }

    public void Cancel(View view) {
        finish();
    }

}
