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

import com.google.firebase.auth.FirebaseAuth;

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
        Button btnLogout = view.findViewById(R.id.btnLogout);

        btnAddNew.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToAddExpense();
            }
        });

        btnViewDetail.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                Expense e = ((MainActivity) getActivity()).getLatestExpense();
                if (e != null) {
                    Intent intent = new Intent(requireContext(), ExpenseDetailActivity.class);
                    intent.putExtra(MainActivity.EXTRA_EXPENSE, (Parcelable) e);
                    startActivity(intent);
                }
            }
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        Bundle args = getArguments();
        if (args != null && args.containsKey("latest_expense")) {
            Expense e = args.getParcelable("latest_expense");
            showExpense(e);
        } else {
            showExpense(null);
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
            if (e.currency != null) {
                nf.setCurrency(Currency.getInstance(e.currency));
            }
        } catch (Exception ignore) { }

        String formatted = nf.format(e.amount);
        tvLastExpense.setText(getString(R.string.last_expense_message, formatted));
        btnViewDetail.setEnabled(true);
    }
}