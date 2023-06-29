package com.example.clubmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.razorpay.Checkout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MemberRegActivity extends AppCompatActivity implements PaymentResultListener {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button register;
    private TextInputLayout nameTextInputLayout, emailTextInputLayout, passwordTextInputLayout, phoneNumberTextInputLayout, addressTextInputLayout,ageTextInputLayout;
    private EditText nameEditText, emailEditText, passwordEditText, phoneNumberEditText, addressEditText,ageEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_reg);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        nameTextInputLayout = findViewById(R.id.name);
        emailTextInputLayout = findViewById(R.id.email);
        passwordTextInputLayout = findViewById(R.id.password);
        phoneNumberTextInputLayout = findViewById(R.id.phonenumber);
        addressTextInputLayout = findViewById(R.id.address);
        ageTextInputLayout = findViewById(R.id.age);

        nameEditText = nameTextInputLayout.getEditText();
        emailEditText = emailTextInputLayout.getEditText();
        passwordEditText = passwordTextInputLayout.getEditText();
        phoneNumberEditText = phoneNumberTextInputLayout.getEditText();
        addressEditText = addressTextInputLayout.getEditText();
        ageEditText = ageTextInputLayout.getEditText();
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }
    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_8ZxcNyMoCFszKL");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Student Club");
            options.put("description", "Payment for  Registration Fee");
            options.put("currency", "INR");
            options.put("amount", 500*100); // Amount in paise

            checkout.open(this, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        createUser();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Please Try again", Toast.LENGTH_SHORT).show();
        return;
    }

    private void createUser() {
        String username = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(this, "Please enter an age", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User creation successful, proceed to save user details in Firestore
                            String userId = mAuth.getCurrentUser().getUid();

                            // Create a User object with the entered details
                            UserModel user = new UserModel(userId, username, email, phoneNumber, address,age,"member");

                            // Save the User object to Firestore
                            db.collection("users")
                                    .document(userId)
                                    .set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // User details saved successfully
                                                Toast.makeText(MemberRegActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent =new Intent(MemberRegActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            } else {
                                                // Failed to save user details
                                                Toast.makeText(MemberRegActivity.this,  task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                Log.e("SignUpActivity", "Error saving user details to Firestore", task.getException());
                                                register.setEnabled(true);
                                            }
                                        }
                                    });
                        } else {

                            Toast.makeText(MemberRegActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            Log.e("SignUpActivity", "Error creating user with Firebase Authentication", task.getException());
                        }
                    }
                });
    }
}