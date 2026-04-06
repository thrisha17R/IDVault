package com.example.idvault;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText pinInput;
    Button unlockBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinInput = findViewById(R.id.pinInput);
        unlockBtn = findViewById(R.id.unlockBtn);

        unlockBtn.setOnClickListener(v -> {
            if(pinInput.getText().toString().equals("1234")){
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                Toast.makeText(this, "Wrong PIN", Toast.LENGTH_SHORT).show();
            }
        });
    }
}