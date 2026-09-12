package com.smartcampus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.smartcampus.R;

/**
 * Dashboard: Central hub navigating to all practicals.
 */
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences prefs = getSharedPreferences("SmartCampusPrefs", MODE_PRIVATE);
        String name = prefs.getString("userName", "Student");

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome, " + name + "!");

        // P2 - Health Checkup
        CardView cardHealth = findViewById(R.id.cardHealth);
        cardHealth.setOnClickListener(v ->
                startActivity(new Intent(this, HealthCheckupActivity.class)));

        // P3 - Food Ordering
        CardView cardFood = findViewById(R.id.cardFood);
        cardFood.setOnClickListener(v ->
                startActivity(new Intent(this, FoodOrderingActivity.class)));

        // P4 - Appointment
        CardView cardAppointment = findViewById(R.id.cardAppointment);
        cardAppointment.setOnClickListener(v ->
                startActivity(new Intent(this, AppointmentActivity.class)));

        // P6 - Quiz
        CardView cardQuiz = findViewById(R.id.cardQuiz);
        cardQuiz.setOnClickListener(v ->
                startActivity(new Intent(this, QuizActivity.class)));

        // P8 - Student Registration
        CardView cardRegistration = findViewById(R.id.cardRegistration);
        cardRegistration.setOnClickListener(v ->
                startActivity(new Intent(this, StudentRegistrationActivity.class)));

        // Logout
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
