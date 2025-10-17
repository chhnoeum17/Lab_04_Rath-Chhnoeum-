package com.example.labmad;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EXPENSE = "extra_expense";
    private static final String STATE_LATEST_EXPENSE = "state_latest_expense";
    private static final String TAG = "MainActivity";

    private TextView tvLastExpense;
    private Button btnViewDetail;

    private Expense latestExpense = null;

    private final ActivityResultLauncher<Intent> addExpenseLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            // use lambda; annotate the single parameter to satisfy the non-null contract
            (@NonNull ActivityResult result) -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Expense returnedExpense; // don't initialize to null (redundant)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            // API 33+ new signature
                            returnedExpense = data.getParcelableExtra(AddExpenseActivity.EXTRA_RESULT_EXPENSE, Expense.class);
                        } else {
                            // older API: old signature
                            returnedExpense = data.getParcelableExtra(AddExpenseActivity.EXTRA_RESULT_EXPENSE);
                        }
                        if (returnedExpense != null) {
                            latestExpense = returnedExpense;
                            updateUi();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLastExpense = findViewById(R.id.tvLastExpense);
        btnViewDetail = findViewById(R.id.btnViewDetail);
        Button btnAddNew = findViewById(R.id.btnAddNew);

        if (savedInstanceState != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                latestExpense = savedInstanceState.getParcelable(STATE_LATEST_EXPENSE, Expense.class);
            } else {
                latestExpense = savedInstanceState.getParcelable(STATE_LATEST_EXPENSE);
            }
        }

        updateUi();

        btnAddNew.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            addExpenseLauncher.launch(intent);
        });

        btnViewDetail.setOnClickListener(v -> {
            if (latestExpense != null) {
                Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                intent.putExtra(EXTRA_EXPENSE, latestExpense);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (latestExpense != null) {
            outState.putParcelable(STATE_LATEST_EXPENSE, latestExpense);
        }
    }

    private void updateUi() {
        if (latestExpense != null) {
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
            try {
                currencyFormatter.setCurrency(Currency.getInstance(latestExpense.currency));
            } catch (Exception e) {
                Log.e(TAG, "Failed to set currency: " + latestExpense.currency, e);
            }

            try {
                double amountValue = Double.parseDouble(String.valueOf(latestExpense.amount));
                String formattedAmount = currencyFormatter.format(amountValue);
                tvLastExpense.setText(getString(R.string.last_expense_message, formattedAmount));
            } catch (NumberFormatException e) {
                tvLastExpense.setText(R.string.invalid_amount_error);
                Log.e(TAG, "Invalid amount for latestExpense: " + latestExpense.amount, e);
            }

            btnViewDetail.setEnabled(true);
        } else {
            tvLastExpense.setText(getString(R.string.no_expense_recorded));
            btnViewDetail.setEnabled(false);
        }
    }
}
