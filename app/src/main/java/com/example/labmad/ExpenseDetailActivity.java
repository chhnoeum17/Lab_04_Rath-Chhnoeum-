package com.example.labmad;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Currency;

public class ExpenseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        TextView tvAmount = findViewById(R.id.tvAmount);
        TextView tvCategory = findViewById(R.id.tvCategory);
        TextView tvRemark = findViewById(R.id.tvRemark);
        TextView tvDate = findViewById(R.id.tvDate);

        Expense expense = getIntent().getParcelableExtra(MainActivity.EXTRA_EXPENSE);

        if (expense != null) {
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
            try {
                currencyFormatter.setCurrency(Currency.getInstance(expense.currency));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                double amountValue = Double.parseDouble(expense.amount);
                tvAmount.setText(currencyFormatter.format(amountValue));
            } catch (NumberFormatException e) {
                tvAmount.setText(R.string.invalid_amount_error);
                e.printStackTrace();
            }

            tvCategory.setText(getString(R.string.category_format, expense.category));
            tvRemark.setText(expense.remark);
            tvDate.setText(getString(R.string.date_format, expense.createdDate));
        } else {
            // Handle the case where the expense is not found
            tvAmount.setText(R.string.expense_not_found);
        }
    }
}