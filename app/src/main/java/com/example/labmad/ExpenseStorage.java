package com.example.labmad;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExpenseStorage {
    private static final String FILENAME = "expenses.dat";

    public static void initializeWithSampleData(Context context) {
        List<Expense> currentExpenses = loadList(context);
        if (currentExpenses.isEmpty()) {
            ArrayList<Expense> sampleList = new ArrayList<>();
            sampleList.add(new Expense("49.99", "USD", "Web Development", "HTML, CSS, JavaScript beginner course", "01/10/2025"));
            sampleList.add(new Expense("59.00", "USD", "Python Programming", "Python full crash course for beginners", "02/10/2025"));
            sampleList.add(new Expense("75.00", "USD", "Data Science", "Learn data analysis and visualization", "03/10/2025"));
            sampleList.add(new Expense("89.00", "USD", "Machine Learning", "Introduction to AI and ML models", "04/10/2025"));
            sampleList.add(new Expense("99.00", "USD", "Mobile App Development", "Build Android apps using Kotlin", "05/10/2025"));
            sampleList.add(new Expense("65.00", "USD", "Database Design", "Learn MySQL and database normalization", "06/10/2025"));
            sampleList.add(new Expense("80.00", "USD", "Cybersecurity", "Basic principles of ethical hacking", "07/10/2025"));
            sampleList.add(new Expense("45.00", "USD", "UI/UX Design", "Design user-friendly and modern interfaces", "08/10/2025"));
            sampleList.add(new Expense("120.00", "USD", "AI for Everyone", "Practical applications of Artificial Intelligence", "09/10/2025"));
            sampleList.add(new Expense("70.00", "USD", "Cloud Computing", "AWS and Google Cloud fundamentals", "10/10/2025"));
            saveList(context, sampleList);
        }
    }

    public static void addExpense(Context context, Expense newExpense) {
        List<Expense> expenses = loadList(context);
        expenses.add(0, newExpense); // Add to the top of the list
        saveList(context, expenses);
    }

    public static ArrayList<Expense> loadList(Context context) {
        try (FileInputStream fis = context.openFileInput(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (ArrayList<Expense>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list if file not found or error
        }
    }

    private static void saveList(Context context, List<Expense> expenses) {
        try (FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(expenses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}