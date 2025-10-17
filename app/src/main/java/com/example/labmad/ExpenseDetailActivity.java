package com.example.labmad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ExpenseDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        TextView tvAmount = findViewById(R.id.tvAmount);
        TextView tvCurrency = findViewById(R.id.tvCurrency);
        TextView tvCategory = findViewById(R.id.tvCategory);
        TextView tvRemark = findViewById(R.id.tvRemark);
        TextView tvDate = findViewById(R.id.tvDate);
        Button btnAddNew = findViewById(R.id.btnAddNew);
        Button btnBackHome = findViewById(R.id.btnBackHome);

        Expense e = getIntent().getParcelableExtra(MainActivity.EXTRA_EXPENSE);
        if (e != null) {
            tvAmount.setText("Amount: " + e.amount);
            tvCurrency.setText("Currency: " + e.currency);
            tvCategory.setText("Category: " + e.category);
            tvRemark.setText("Remark: " + e.remark);
            tvDate.setText("Created Date: " + e.createdDate);
        }

        btnBackHome.setOnClickListener(v -> finish());

        btnAddNew.setOnClickListener(v -> {
            Intent i = new Intent(ExpenseDetailActivity.this, AddExpenseActivity.class);
            startActivity(i);
            finish();
        });
    }
}
