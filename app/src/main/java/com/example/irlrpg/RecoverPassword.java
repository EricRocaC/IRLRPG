package com.example.irlrpg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecoverPassword extends AppCompatActivity {

    private Button backToLog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        backToLog = findViewById(R.id.returnLogin);

        backToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecoverPassword.this, "Going to register...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RecoverPassword.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
