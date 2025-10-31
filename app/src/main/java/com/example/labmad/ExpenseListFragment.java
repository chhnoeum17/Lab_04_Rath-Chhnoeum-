package com.example.labmad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ExpenseListFragment extends Fragment implements ExpenseAdapter.OnItemClick {

    private RecyclerView recyclerExpenses;
    private ExpenseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);

        recyclerExpenses = view.findViewById(R.id.recyclerExpenses);
        recyclerExpenses.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize with an empty list, it will be updated in onResume
        adapter = new ExpenseAdapter(Collections.emptyList(), this);
        recyclerExpenses.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the list every time the fragment is shown
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            refreshList(mainActivity.getAllExpenses());
        }
    }

    public void refreshList(List<Expense> newExpenses) {
        if (adapter != null) {
            adapter.updateData(newExpenses);
        }
    }

    @Override
    public void onClick(Expense e) {
        Intent intent = new Intent(requireContext(), ExpenseDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_EXPENSE, (Parcelable) e);
        startActivity(intent);
    }
}