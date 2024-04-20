package com.example.myapplication_tips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.PrivateKey;
import java.util.PrimitiveIterator;

public class MainActivity extends AppCompatActivity {

    MaterialButton loginBtn, createAccountBtn;
    private AppCompatEditText passEt;
    AppCompatAutoCompleteTextView emailEt;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        createAccountBtn.setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
        });
        firebaseAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(v -> {
            loginEmailPassUser(emailEt.getText().toString().trim(),passEt.getText().toString().trim());
        }
        );

    }

    private void loginEmailPassUser(String email, String pwd) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)){
            firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent i = new Intent(MainActivity.this,EnterActivity.class);
                    startActivity(i);
                }
            });
        }
    }

    private void findViews() {
        createAccountBtn = findViewById(R.id.create_account);
        loginBtn = findViewById(R.id.email_signin);
        emailEt = findViewById(R.id.email);
        passEt = findViewById(R.id.password);
    }
}