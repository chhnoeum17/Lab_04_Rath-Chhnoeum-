package com.example.labmad;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EXPENSE = "extra_expense";
    private List<Expense> allExpenses;

    public List<Expense> getAllExpenses() {
        if (allExpenses == null) {
            allExpenses = ExpenseStorage.loadList(this);
        }
        return allExpenses;
    }

    public Expense getLatestExpense() {
        if (allExpenses == null || allExpenses.isEmpty()) {
            return null;
        }
        return allExpenses.get(0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize with sample data if storage is empty
        ExpenseStorage.initializeWithSampleData(this);
        allExpenses = ExpenseStorage.loadList(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        FragmentManager fm = getSupportFragmentManager();

        if (fm.findFragmentById(R.id.nav_host) == null) {
            fm.beginTransaction()
                    .replace(R.id.nav_host, createHomeFragment(getLatestExpense()))
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment f;

            if (id == R.id.nav_add) {
                f = new AddExpenseFragment();
            } else if (id == R.id.nav_list) {
                f = new ExpenseListFragment();
            } else {
                f = createHomeFragment(getLatestExpense());
            }

            fm.beginTransaction()
                    .replace(R.id.nav_host, f)
                    .commit();
            return true;
        });
    }

    public void addExpense(Expense expense) {
        ExpenseStorage.addExpense(this, expense);
        allExpenses = ExpenseStorage.loadList(this);
        showHomeFragmentWithExpense(expense);
        Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();

        // Notify ExpenseListFragment if visible
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.nav_host);
        if (current instanceof ExpenseListFragment) {
            ((ExpenseListFragment) current).refreshList(allExpenses);
        }
    }

    public void navigateToAddExpense() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_add);
    }

    private void showHomeFragmentWithExpense(Expense e) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.nav_host, createHomeFragment(e))
                .commit();
    }

    private Fragment createHomeFragment(@Nullable Expense e) {
        HomeFragment hf = new HomeFragment();
        if (e != null) {
            Bundle args = new Bundle();
            args.putParcelable("latest_expense", e);
            hf.setArguments(args);
        }
        return hf;
    }
}