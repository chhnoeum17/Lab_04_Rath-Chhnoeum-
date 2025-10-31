package com.example.labmad;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExpenseFragment extends Fragment {

    private TextInputEditText etAmount, etRemark, etDate;
    private TextInputLayout tilAmount, tilDate;
    private Spinner spCurrency, spCategory;
    private final Calendar myCalendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        tilAmount = view.findViewById(R.id.tilAmount);
        tilDate = view.findViewById(R.id.tilDate);
        etAmount = view.findViewById(R.id.etAmount);
        etRemark = view.findViewById(R.id.etRemark);
        etDate = view.findViewById(R.id.etDate);
        spCurrency = view.findViewById(R.id.spinnerCurrency);
        spCategory = view.findViewById(R.id.spinnerCategory);
        Button btnAddExpense = view.findViewById(R.id.btnAddExpense);

        // Set up spinners
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.currencies, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrency.setAdapter(currencyAdapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categoryAdapter);

        // Set up Date Picker
        DatePickerDialog.OnDateSetListener dateSetListener = (v, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateDateLabel();
        };

        etDate.setOnClickListener(v -> new DatePickerDialog(requireContext(), dateSetListener,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        // Set up button click listener
        btnAddExpense.setOnClickListener(v -> saveExpense());

        return view;
    }

    private void saveExpense() {
        String amount = etAmount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String remark = etRemark.getText().toString().trim();

        // Clear previous errors
        tilAmount.setError(null);
        tilDate.setError(null);

        if (amount.isEmpty()) {
            tilAmount.setError(getString(R.string.amount_error));
            return;
        }

        if (spCurrency.getSelectedItem() == null) {
            Toast.makeText(getContext(), R.string.currency_error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (spCategory.getSelectedItem() == null) {
            Toast.makeText(getContext(), R.string.category_error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (date.isEmpty()) {
            tilDate.setError(getString(R.string.date_error));
            return;
        }

        String currency = spCurrency.getSelectedItem().toString();
        String category = spCategory.getSelectedItem().toString();

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.addExpense(new Expense(amount, currency, category, remark, date));
        }
    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDate.setText(sdf.format(myCalendar.getTime()));
    }
}