package com.example.irlrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnQuest, btnTraining, logout;
    private int expUser, expNextLevel, intExp, strExp;
    private TextView expCurrent, expTotal, inteligence, strength;
    private ProgressBar expBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_autism);
        btnQuest = findViewById(R.id.goToQuests);
        btnTraining = findViewById(R.id.goToTraining);
        expCurrent = findViewById(R.id.exp);
        expTotal = findViewById(R.id.totalExp);
        expBar = findViewById(R.id.progressBar);
        inteligence = findViewById(R.id.intExp);
        strength = findViewById(R.id.strExp);
        logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();

        expUser = 300;
        expNextLevel = 1000;
        strExp = 100;
        intExp = 100;

        expCurrent.setText(String.valueOf(expUser));
        expTotal.setText(String.valueOf(expNextLevel));
        inteligence.setText(String.valueOf(intExp));
        strength.setText(String.valueOf(strExp));
        expBar.setMax(expNextLevel);
        expBar.setProgress(expUser);

        btnQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuestAutism.class));
                finish();
            }
        });

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TrainingAutism.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}