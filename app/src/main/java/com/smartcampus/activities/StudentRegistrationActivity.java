package com.smartcampus.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.smartcampus.R;
import com.smartcampus.database.DatabaseHelper;
import java.util.List;

/**
 * PRACTICAL 8: SQLite Database
 * Register students, view all records from DB.
 */
public class StudentRegistrationActivity extends AppCompatActivity {

    private EditText etRoll, etName, etMarks;
    private TextView tvApplications;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        dbHelper      = new DatabaseHelper(this);
        etRoll        = findViewById(R.id.etRollNumber);
        etName        = findViewById(R.id.etStudentName);
        etMarks       = findViewById(R.id.etStudentMarks);
        tvApplications = findViewById(R.id.tvApplications);

        Button btnRegister   = findViewById(R.id.btnRegister);
        Button btnReset      = findViewById(R.id.btnResetStudent);
        Button btnViewAll    = findViewById(R.id.btnViewAll);

        btnRegister.setOnClickListener(v -> registerStudent());
        btnReset.setOnClickListener(v -> resetFields());
        btnViewAll.setOnClickListener(v -> viewAllStudents());
    }

    private void registerStudent() {
        String roll  = etRoll.getText().toString().trim();
        String name  = etName.getText().toString().trim();
        String marks = etMarks.getText().toString().trim();

        if (roll.isEmpty() || name.isEmpty() || marks.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double marksVal;
        try {
            marksVal = Double.parseDouble(marks);
            if (marksVal < 0 || marksVal > 100) {
                Toast.makeText(this, "Marks must be between 0 and 100", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid marks value", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.insertStudent(roll, name, marksVal);
        if (result != -1) {
            Toast.makeText(this, "Student registered successfully!", Toast.LENGTH_SHORT).show();
            resetFields();
        } else {
            Toast.makeText(this, "Error: Roll number may already exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        etRoll.setText(""); etName.setText(""); etMarks.setText("");
        tvApplications.setText("Applications will appear here");
    }

    private void viewAllStudents() {
        List<String[]> students = dbHelper.getAllStudents();
        if (students.isEmpty()) {
            tvApplications.setText("No students registered yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-20s %s\n", "Roll No.", "Name", "Marks"));
        sb.append("─────────────────────────────────────\n");
        for (String[] s : students) {
            sb.append(String.format("%-10s %-20s %s\n", s[0], s[1], s[2]));
        }
        tvApplications.setText(sb.toString());
    }
}
