package com.example.labmad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    public interface OnItemClick {
        void onClick(Expense e);
    }

    private List<Expense> expenses;
    private final OnItemClick onItemClick;

    public ExpenseAdapter(List<Expense> expenses, OnItemClick onItemClick) {
        this.expenses = expenses;
        this.onItemClick = onItemClick;
    }

    public void updateData(List<Expense> newList) {
        this.expenses = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense e = expenses.get(position);
        if (e == null) return;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        try {
            if (e.currency != null) formatter.setCurrency(Currency.getInstance(e.currency));
        } catch (Exception ignored) {}

        holder.tvAmount.setText(formatter.format(e.amount));
        holder.tvCategory.setText(holder.itemView.getContext().getString(R.string.category_format, safeString(e.category)));
        holder.tvDate.setText(holder.itemView.getContext().getString(R.string.date_format, safeString(e.createdDate)));

        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) onItemClick.onClick(e);
        });
    }

    @Override
    public int getItemCount() {
        return expenses == null ? 0 : expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvAmount, tvCategory, tvDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    private static String safeString(String s) {
        return s == null ? "" : s;
    }
}