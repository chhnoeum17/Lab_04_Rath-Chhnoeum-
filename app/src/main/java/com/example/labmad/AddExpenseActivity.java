package com.example.labmad;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT_EXPENSE = "extra_result_expense";

    private EditText etAmount, etRemark, etDate;
    private Spinner spCurrency, spCategory;
    private final Calendar myCalendar = Calendar.getInstance(); // made final to satisfy the IDE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etAmount = findViewById(R.id.etAmount);
        etRemark = findViewById(R.id.etRemark);
        etDate = findViewById(R.id.etDate);
        spCurrency = findViewById(R.id.spinnerCurrency);
        spCategory = findViewById(R.id.spinnerCategory);
        Button btnAddExpense = findViewById(R.id.btnAddExpense);

        // Populate spinners with adapters
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrency.setAdapter(currencyAdapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categoryAdapter);

        // Date Picker
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        etDate.setOnClickListener(v -> new DatePickerDialog(AddExpenseActivity.this, dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        btnAddExpense.setOnClickListener(v -> {
            String amountStr = etAmount.getText().toString().trim();
            if (amountStr.isEmpty()) {
                etAmount.setError("Enter amount");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException ex) {
                etAmount.setError("Invalid number");
                return;
            }

            String date = etDate.getText().toString().trim();
            if (date.isEmpty()) {
                etDate.setError("Select date");
                return;
            }

            if (spCurrency.getSelectedItem() == null) {
                Toast.makeText(this, "Please select a currency", Toast.LENGTH_SHORT).show();
                return;
            }

            if (spCategory.getSelectedItem() == null) {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
                return;
            }

            String currency = spCurrency.getSelectedItem().toString();
            String category = spCategory.getSelectedItem().toString();
            String remark = etRemark.getText().toString().trim();

            Expense e = new ExpenseBuilder().setAmount(amount).setCurrency(currency).setCategory(category).setRemark(remark).setCreatedDate(date).createExpense();
            Intent result = new Intent();
            result.putExtra(EXTRA_RESULT_EXPENSE, (Parcelable) e);
            setResult(Activity.RESULT_OK, result);
            finish();
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }
}