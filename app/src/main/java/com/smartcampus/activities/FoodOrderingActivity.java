package com.smartcampus.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.smartcampus.R;
import java.util.ArrayList;

/**
 * PRACTICAL 3: Event Handlers
 * Submit button enabled only when name, address filled and at least one food item selected.
 */
public class FoodOrderingActivity extends AppCompatActivity {

    private EditText etName, etAddress;
    private CheckBox cbPizza, cbBurger, cbPasta, cbSalad;
    private RadioGroup rgPayment;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_ordering);

        etName    = findViewById(R.id.etFoodName);
        etAddress = findViewById(R.id.etDeliveryAddress);
        cbPizza   = findViewById(R.id.cbPizza);
        cbBurger  = findViewById(R.id.cbBurger);
        cbPasta   = findViewById(R.id.cbPasta);
        cbSalad   = findViewById(R.id.cbSalad);
        rgPayment = findViewById(R.id.rgPayment);
        btnSubmit = findViewById(R.id.btnSubmitOrder);
        btnSubmit.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence s, int i, int i1, int i2) { validate(); }
            @Override public void afterTextChanged(Editable editable) {}
        };

        CompoundButton.OnCheckedChangeListener checkListener = (b, c) -> validate();

        etName.addTextChangedListener(textWatcher);
        etAddress.addTextChangedListener(textWatcher);
        cbPizza.setOnCheckedChangeListener(checkListener);
        cbBurger.setOnCheckedChangeListener(checkListener);
        cbPasta.setOnCheckedChangeListener(checkListener);
        cbSalad.setOnCheckedChangeListener(checkListener);

        btnSubmit.setOnClickListener(v -> submitOrder());
    }

    private void validate() {
        boolean hasName    = !etName.getText().toString().trim().isEmpty();
        boolean hasAddress = !etAddress.getText().toString().trim().isEmpty();
        boolean hasFood    = cbPizza.isChecked() || cbBurger.isChecked()
                           || cbPasta.isChecked() || cbSalad.isChecked();
        btnSubmit.setEnabled(hasName && hasAddress && hasFood);
    }

    private void submitOrder() {
        ArrayList<String> items = new ArrayList<>();
        if (cbPizza.isChecked())  items.add("Pizza");
        if (cbBurger.isChecked()) items.add("Burger");
        if (cbPasta.isChecked())  items.add("Pasta");
        if (cbSalad.isChecked())  items.add("Salad");

        int payId = rgPayment.getCheckedRadioButtonId();
        String payment = payId != -1
                ? ((RadioButton) findViewById(payId)).getText().toString()
                : "Not selected";

        String msg = "Order Placed!\nItems: " + String.join(", ", items)
                   + "\nPayment: " + payment
                   + "\nDelivery to: " + etAddress.getText().toString().trim();

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
