package com.smartcampus.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.smartcampus.R;
import java.util.Calendar;

/**
 * PRACTICAL 4: ConstraintLayout + Input Validation
 * PRACTICAL 5: Explicit Intent → IntentActionActivity
 * Book Appointment button disabled until all fields filled.
 */
public class AppointmentActivity extends AppCompatActivity {

    private EditText etPatientName, etAge, etMobile;
    private Spinner spinnerDept;
    private RadioGroup rgTimeSlot, rgGender;
    private Button btnPickDate, btnBook, btnReset;
    private TextView tvSelectedDate;
    private String selectedDate = "";
    private boolean deptSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        etPatientName = findViewById(R.id.etPatientName);
        etAge         = findViewById(R.id.etPatientAge);
        etMobile      = findViewById(R.id.etPatientMobile);
        spinnerDept   = findViewById(R.id.spinnerDept);
        rgTimeSlot    = findViewById(R.id.rgTimeSlot);
        rgGender      = findViewById(R.id.rgGender);
        btnPickDate   = findViewById(R.id.btnPickDate);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnBook       = findViewById(R.id.btnBookAppointment);
        btnReset      = findViewById(R.id.btnResetAppointment);
        btnBook.setEnabled(false);

        // Spinner setup
        String[] departments = {"Select Department", "Cardiology", "Orthopedics",
                "Neurology", "Dermatology", "ENT", "General"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, departments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDept.setAdapter(adapter);

        spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, android.view.View v, int pos, long id) {
                deptSelected = pos != 0;
                validate();
            }
            @Override public void onNothingSelected(AdapterView<?> p) { deptSelected = false; }
        });

        // Date picker
        btnPickDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                selectedDate = day + "/" + (month + 1) + "/" + year;
                tvSelectedDate.setText("Date: " + selectedDate);
                validate();
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show();
        });

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence s, int i, int i1, int i2) { validate(); }
            @Override public void afterTextChanged(Editable e) {}
        };
        etPatientName.addTextChangedListener(watcher);
        etAge.addTextChangedListener(watcher);
        etMobile.addTextChangedListener(watcher);
        rgTimeSlot.setOnCheckedChangeListener((g, id) -> validate());

        btnBook.setOnClickListener(v -> bookAppointment());

        btnReset.setOnClickListener(v -> {
            etPatientName.setText(""); etAge.setText(""); etMobile.setText("");
            spinnerDept.setSelection(0);
            rgTimeSlot.clearCheck(); rgGender.clearCheck();
            selectedDate = ""; tvSelectedDate.setText("No date selected");
            deptSelected = false; validate();
        });
    }

    private void validate() {
        boolean nameOk   = !etPatientName.getText().toString().trim().isEmpty();
        boolean ageOk    = !etAge.getText().toString().trim().isEmpty();
        boolean mobileOk = etMobile.getText().toString().trim().length() == 10;
        boolean timeOk   = rgTimeSlot.getCheckedRadioButtonId() != -1;
        boolean dateOk   = !selectedDate.isEmpty();
        btnBook.setEnabled(nameOk && ageOk && mobileOk && deptSelected && timeOk && dateOk);
    }

    private void bookAppointment() {
        String name   = etPatientName.getText().toString().trim();
        String age    = etAge.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String dept   = spinnerDept.getSelectedItem().toString();
        int timeId    = rgTimeSlot.getCheckedRadioButtonId();
        String slot   = ((RadioButton) findViewById(timeId)).getText().toString();

        String msg = "✅ Appointment Booked!\nName: " + name
                   + "\nAge: " + age + "\nMobile: " + mobile
                   + "\nDept: " + dept + "\nDate: " + selectedDate + "\nSlot: " + slot;

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        // P5: Explicit Intent — pass data to IntentActionActivity
        Intent intent = new Intent(this, IntentActionActivity.class);
        intent.putExtra("patientName", name);
        intent.putExtra("patientMobile", mobile);
        intent.putExtra("patientEmail", "patient@example.com");
        intent.putExtra("appointmentDetails", msg);
        startActivity(intent);
    }
}
