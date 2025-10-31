package com.example.labmad;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpenseData {

    private static final List<Expense> EXPENSES = new ArrayList<Expense>() {{
        add(new ExpenseBuilder().setId(1).setAmount(12.5).setCurrency("USD").setCategory("Food").setRemark("Noodles").setCreatedDate("12/10/2025").createExpense());
        add(new ExpenseBuilder().setId(2).setAmount(5.0).setCurrency("USD").setCategory("Transport").setRemark("Tuk-tuk").setCreatedDate("13/10/2025").createExpense());
        add(new ExpenseBuilder().setId(3).setAmount(30.0).setCurrency("USD").setCategory("Shopping").setRemark("T-shirt").setCreatedDate("14/10/2025").createExpense());
    }};

    /** Returns a mutable copy for the adapter */
    public static List<Expense> getAll() {
        return new ArrayList<>(EXPENSES);
    }

    /** Find an expense by its id, or null if not found. */
    @Nullable
    public static Expense findById(int id) {
        for (Expense e : EXPENSES) {
            if (e.id == id) return e;
        }
        return null;
    }
}
