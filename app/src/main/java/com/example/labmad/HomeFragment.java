package com.example.labmad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.util.Currency;

public class HomeFragment extends Fragment {

    private TextView tvLastExpense;
    private Button btnViewDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvLastExpense = view.findViewById(R.id.tvLastExpense);
        Button btnAddNew = view.findViewById(R.id.btnAddNew);
        btnViewDetail = view.findViewById(R.id.btnViewDetail);

        // Wire Add button to ask the activity to navigate to the AddExpenseFragment
        btnAddNew.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToAddExpense();
            }
        });

        // View detail opens the ExpenseDetailActivity with the latest expense (if exists)
        btnViewDetail.setOnClickListener(v -> {
            Expense e = null;
            if (getActivity() instanceof MainActivity) {
                e = ((MainActivity) getActivity()).getLatestExpense();
            }
            if (e != null) {
                Intent intent = new Intent(requireContext(), ExpenseDetailActivity.class);
                intent.putExtra(MainActivity.EXTRA_EXPENSE, (Parcelable) e);
                startActivity(intent);
            }
        });

        // If the fragment was created with an expense argument, show it
        Bundle args = getArguments();
        if (args != null && args.containsKey("latest_expense")) {
            Expense e = args.getParcelable("latest_expense");
            showExpense(e);
        } else {
            // Or read from activity if available
            if (getActivity() instanceof MainActivity) {
                Expense a = ((MainActivity) getActivity()).getLatestExpense();
                showExpense(a);
            }
        }
    }

    private void showExpense(@Nullable Expense e) {
        if (tvLastExpense == null) return;
        if (e == null) {
            tvLastExpense.setText(getString(R.string.no_expense_recorded));
            btnViewDetail.setEnabled(false);
            return;
        }

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        try {
            nf.setCurrency(Currency.getInstance(e.currency));
        } catch (Exception ignore) { }

        try {
            double amountValue = Double.parseDouble(e.amount);
            String formatted = nf.format(amountValue);
            tvLastExpense.setText(getString(R.string.last_expense_message, formatted));
        } catch (NumberFormatException ex) {
            tvLastExpense.setText(getString(R.string.invalid_amount_error));
        }
        
        btnViewDetail.setEnabled(true);
    }
}