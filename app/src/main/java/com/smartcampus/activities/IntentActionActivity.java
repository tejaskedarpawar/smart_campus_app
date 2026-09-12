package com.smartcampus.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.smartcampus.R;

/**
 * PRACTICAL 5: Explicit & Implicit Intents
 * Receives data from AppointmentActivity (Explicit Intent)
 * Uses Implicit Intents to: Call Patient, Send Email, Share Details
 */
public class IntentActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_action);

        // Receive data via Explicit Intent
        String name    = getIntent().getStringExtra("patientName");
        String mobile  = getIntent().getStringExtra("patientMobile");
        String email   = getIntent().getStringExtra("patientEmail");
        String details = getIntent().getStringExtra("appointmentDetails");

        TextView tvDetails = findViewById(R.id.tvAppointmentDetails);
        tvDetails.setText(details != null ? details : "No details received");

        // Implicit Intent 1: Call Patient
        Button btnCall = findViewById(R.id.btnCallPatient);
        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + (mobile != null ? mobile : "0000000000")));
            startActivity(callIntent);
        });

        // Implicit Intent 2: Send Email
        Button btnEmail = findViewById(R.id.btnSendEmail);
        btnEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + (email != null ? email : "")));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Appointment Confirmation - " + name);
            emailIntent.putExtra(Intent.EXTRA_TEXT, details);
            startActivity(Intent.createChooser(emailIntent, "Send Email via..."));
        });

        // Implicit Intent 3: Share Details
        Button btnShare = findViewById(R.id.btnShareDetails);
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, details);
            startActivity(Intent.createChooser(shareIntent, "Share Appointment Details..."));
        });
    }
}
