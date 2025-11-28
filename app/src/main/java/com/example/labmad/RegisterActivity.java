package com.example.labmad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword, etPasswordConfirm;
    private TextInputLayout tilEmail, tilPassword, tilPasswordConfirm;
    private Button btnRegister, btnToLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilPasswordConfirm = findViewById(R.id.tilPasswordConfirm);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        btnToLogin = findViewById(R.id.btnToLogin);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> doRegister());
        btnToLogin.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void doRegister() {
        String email = etEmail.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        String pwd2 = etPasswordConfirm.getText().toString().trim();

        // Clear previous errors
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilPasswordConfirm.setError(null);

        if (!isValidEmail(email)) {
            tilEmail.setError("Enter a valid email");
            return;
        }
        if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            return;
        }
        if (!pwd.equals(pwd2)) {
            tilPasswordConfirm.setError("Passwords do not match");
            return;
        }

        progressBar.setVisibility(android.view.View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(android.view.View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Registered: " + (user != null ? user.getEmail() : ""), Toast.LENGTH_SHORT).show();
                        // go to MainActivity
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Exception ex = task.getException();
                        if (ex instanceof FirebaseAuthUserCollisionException) {
                            tilEmail.setError("Email already in use");
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + (ex != null ? ex.getMessage() : "unknown"), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}