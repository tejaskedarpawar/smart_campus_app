package com.smartcampus.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.smartcampus.R;

/**
 * PRACTICAL 2: Demonstrates three Layout types via Tabs:
 * Tab 1 → Nested LinearLayout
 * Tab 2 → RelativeLayout
 * Tab 3 → ConstraintLayout
 * All show the Health Checkup form.
 */
public class HealthCheckupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_checkup);

        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        // Tab 1: LinearLayout
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Linear");
        tab1.setIndicator("Linear");
        tab1.setContent(R.id.tabLinear);
        tabHost.addTab(tab1);

        // Tab 2: RelativeLayout
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Relative");
        tab2.setIndicator("Relative");
        tab2.setContent(R.id.tabRelative);
        tabHost.addTab(tab2);

        // Tab 3: ConstraintLayout
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Constraint");
        tab3.setIndicator("Constraint");
        tab3.setContent(R.id.tabConstraint);
        tabHost.addTab(tab3);

        setupLinearForm();
        setupRelativeForm();
        setupConstraintForm();
    }

    private void setupLinearForm() {
        Button btnSubmit = findViewById(R.id.btnSubmitLinear);
        Button btnReset  = findViewById(R.id.btnResetLinear);

        btnSubmit.setOnClickListener(v -> {
            EditText etName = findViewById(R.id.etNameLinear);
            EditText etAge  = findViewById(R.id.etAgeLinear);
            RadioGroup rgBlood = findViewById(R.id.rgBloodLinear);
            RadioGroup rgGender = findViewById(R.id.rgGenderLinear);
            EditText etMobile = findViewById(R.id.etMobileLinear);

            String name = etName.getText().toString().trim();
            String age  = etAge.getText().toString().trim();

            if (name.isEmpty() || age.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }
            int bloodId  = rgBlood.getCheckedRadioButtonId();
            int genderId = rgGender.getCheckedRadioButtonId();
            String blood  = bloodId  != -1 ? ((RadioButton)findViewById(bloodId)).getText().toString()  : "Not selected";
            String gender = genderId != -1 ? ((RadioButton)findViewById(genderId)).getText().toString() : "Not selected";

            Toast.makeText(this, "Health Checkup Submitted!\nName: " + name +
                    "\nAge: " + age + "\nBlood: " + blood + "\nGender: " + gender, Toast.LENGTH_LONG).show();
        });

        btnReset.setOnClickListener(v -> {
            ((EditText) findViewById(R.id.etNameLinear)).setText("");
            ((EditText) findViewById(R.id.etAgeLinear)).setText("");
            ((EditText) findViewById(R.id.etMobileLinear)).setText("");
            ((EditText) findViewById(R.id.etAddressLinear)).setText("");
            ((RadioGroup) findViewById(R.id.rgBloodLinear)).clearCheck();
            ((RadioGroup) findViewById(R.id.rgGenderLinear)).clearCheck();
        });
    }

    private void setupRelativeForm() {
        Button btnSubmit = findViewById(R.id.btnSubmitRelative);
        Button btnReset  = findViewById(R.id.btnResetRelative);

        btnSubmit.setOnClickListener(v -> {
            EditText etName = findViewById(R.id.etNameRelative);
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Relative Layout - Submitted: " + name, Toast.LENGTH_SHORT).show();
        });

        btnReset.setOnClickListener(v -> {
            ((EditText) findViewById(R.id.etNameRelative)).setText("");
            ((EditText) findViewById(R.id.etAgeRelative)).setText("");
            ((EditText) findViewById(R.id.etMobileRelative)).setText("");
            ((RadioGroup) findViewById(R.id.rgBloodRelative)).clearCheck();
            ((RadioGroup) findViewById(R.id.rgGenderRelative)).clearCheck();
        });
    }

    private void setupConstraintForm() {
        Button btnSubmit = findViewById(R.id.btnSubmitConstraint);
        Button btnReset  = findViewById(R.id.btnResetConstraint);

        btnSubmit.setOnClickListener(v -> {
            EditText etName = findViewById(R.id.etNameConstraint);
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Constraint Layout - Submitted: " + name, Toast.LENGTH_SHORT).show();
        });

        btnReset.setOnClickListener(v -> {
            ((EditText) findViewById(R.id.etNameConstraint)).setText("");
            ((EditText) findViewById(R.id.etAgeConstraint)).setText("");
            ((EditText) findViewById(R.id.etMobileConstraint)).setText("");
            ((RadioGroup) findViewById(R.id.rgBloodConstraint)).clearCheck();
            ((RadioGroup) findViewById(R.id.rgGenderConstraint)).clearCheck();
        });
    }
}
