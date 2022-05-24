package com.example.irlrpg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText userNameEdit, pwdEdt;
    private Button loginBtn, registerTV;
    private ProgressBar loadingPB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEdit = findViewById(R.id.username);
        pwdEdt = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        loadingPB = findViewById(R.id.idPBLoading);
        registerTV = findViewById(R.id.buttonReg);
        mAuth = FirebaseAuth.getInstance();

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Login Successful...", Toast.LENGTH_SHORT).show();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdit.getText().toString();
                String pwd = pwdEdt.getText().toString();
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "Please enter your credentials/", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Fail to login.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

        @Override
        protected void onStart(){
            super.onStart();
            FirebaseUser user = mAuth.getCurrentUser();
            if(user != null){
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                this.finish();
            }
        }

}