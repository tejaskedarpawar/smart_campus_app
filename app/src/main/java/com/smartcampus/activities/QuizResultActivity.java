package com.smartcampus.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.smartcampus.R;

/**
 * PRACTICAL 6: Opened via PendingIntent from Notification
 * Displays quiz score.
 */
public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 5);

        TextView tvScore   = findViewById(R.id.tvScore);
        TextView tvMessage = findViewById(R.id.tvResultMessage);

        tvScore.setText("Your Score: " + score + " / " + total);

        String message;
        if (score == total)      message = "🏆 Perfect Score! Outstanding!";
        else if (score >= total * 0.8) message = "🌟 Excellent work!";
        else if (score >= total * 0.6) message = "👍 Good job! Keep it up!";
        else if (score >= total * 0.4) message = "📚 Keep studying!";
        else                      message = "💪 Don't give up! Try again!";

        tvMessage.setText(message);

        Button btnBack = findViewById(R.id.btnBackToDashboard);
        btnBack.setOnClickListener(v -> finish());
    }
}
