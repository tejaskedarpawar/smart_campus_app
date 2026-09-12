package com.smartcampus.activities;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.smartcampus.R;

/**
 * PRACTICAL 6: AlertDialog + Notifications + PendingIntent
 * Quiz with 5 questions, submit confirmation dialog,
 * notification with pending intent to QuizResultActivity.
 */
public class QuizActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "quiz_channel";

    // Correct answers: Q1→Delhi(0), Q2→James Gosling(0), Q3→Linux(0),
    //                  Q4→Kotlin(1), Q5→SQLite(0)
    private final int[] answers = {0, 0, 0, 1, 0};
    private RadioGroup[] radioGroups;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        radioGroups = new RadioGroup[]{
            findViewById(R.id.rgQ1),
            findViewById(R.id.rgQ2),
            findViewById(R.id.rgQ3),
            findViewById(R.id.rgQ4),
            findViewById(R.id.rgQ5)
        };

        createNotificationChannel();

        Button btnSubmit = findViewById(R.id.btnSubmitQuiz);
        btnSubmit.setOnClickListener(v -> showConfirmationDialog());
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Submit Quiz")
            .setMessage("Are you sure you want to submit?")
            .setPositiveButton("Yes", (dialog, which) -> {
                calculateScore();
                sendResultNotification();
            })
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .show();
    }

    private void calculateScore() {
        score = 0;
        int[][] correctIndex = {{0},{0},{0},{1},{0}}; // index of correct radio in each group
        for (int i = 0; i < radioGroups.length; i++) {
            int checkedId = radioGroups[i].getCheckedRadioButtonId();
            if (checkedId != -1) {
                RadioButton rb = findViewById(checkedId);
                int index = radioGroups[i].indexOfChild(rb);
                if (index == answers[i]) score++;
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Quiz Results", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notifications for quiz results");
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(channel);
        }
    }

    private void sendResultNotification() {
        Intent resultIntent = new Intent(this, QuizResultActivity.class);
        resultIntent.putExtra("score", score);
        resultIntent.putExtra("total", radioGroups.length);

        PendingIntent pendingIntent = PendingIntent.getActivity(
            this, 0, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Smart Campus Quiz")
            .setContentText("Your test results are ready! Score: " + score + "/" + radioGroups.length)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm != null) nm.notify(1001, builder.build());

        // Also navigate directly
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(resultIntent);
    }
}
