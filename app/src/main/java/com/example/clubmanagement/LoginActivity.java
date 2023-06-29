package com.example.clubmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private Button login;
    TextInputLayout emailInputLayout, passwordInputLayout;
    TextInputEditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInputLayout = (TextInputLayout) findViewById(R.id.email);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.password);

        emailEditText = (TextInputEditText) emailInputLayout.getEditText();
        passwordEditText = (TextInputEditText) passwordInputLayout.getEditText();


        login=findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(emailEditText.getText().toString());
                System.out.println(passwordEditText.getText().toString());
                if(emailEditText.getText().toString().equals("admin") && passwordEditText.getText().toString().equals("admin")){
                    Intent intent =new Intent(LoginActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
                else {
                    mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "Sign in ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // Handle error during sign in
                                        Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}