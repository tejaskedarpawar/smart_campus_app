package com.smartcampus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.smartcampus.R;

/**
 * PRACTICAL 7: SharedPreferences Demo
 * User signs up once; subsequent launches skip this screen.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone;
    private Button btnLogin;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("SmartCampusPrefs", MODE_PRIVATE);

        etName  = findViewById(R.id.etLoginName);
        etEmail = findViewById(R.id.etLoginEmail);
        etPhone = findViewById(R.id.etLoginPhone);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setEnabled(false);

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFields();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        etName.addTextChangedListener(watcher);
        etEmail.addTextChangedListener(watcher);
        etPhone.addTextChangedListener(watcher);

        btnLogin.setOnClickListener(v -> {
            String name  = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            // Save to SharedPreferences — stored permanently
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("userName", name);
            editor.putString("userEmail", email);
            editor.putString("userPhone", phone);
            editor.apply();

            Toast.makeText(this, "Welcome, " + name + "! Login saved.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        });
    }

    private void validateFields() {
        String name  = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        btnLogin.setEnabled(!name.isEmpty() && email.contains("@") && phone.length() == 10);
    }
}
