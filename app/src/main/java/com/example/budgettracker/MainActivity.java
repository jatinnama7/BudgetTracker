package com.example.budgettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TransactionDatabase db;
    private TransactionAdapter adapter;
    private TextView totalBalanceText, incomeText, expenseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalBalanceText = findViewById(R.id.total_balance);
        incomeText = findViewById(R.id.income);
        expenseText = findViewById(R.id.expense);

        db = TransactionDatabase.getInstance(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        findViewById(R.id.add_transaction_btn).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddTransactionActivity.class));
        });

        loadTransactions();
    }

    private void loadTransactions() {
        new Thread(() -> {
            List<Transaction> transactions = db.transactionDao().getAll();
            int totalIncome = 0, totalExpense = 0;

            for (Transaction t : transactions) {
                if (t.getAmount() > 0) {
                    totalIncome += t.getAmount();
                } else {
                    totalExpense += Math.abs(t.getAmount());
                }
            }

            int balance = totalIncome - totalExpense;
            int finalTotalIncome = totalIncome;
            int finalTotalExpense = totalExpense;
            runOnUiThread(() -> {
                totalBalanceText.setText("₹" + balance);
                incomeText.setText("₹" + finalTotalIncome);
                expenseText.setText("₹" + finalTotalExpense);
                adapter.setTransactions(transactions);
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransactions(); // Refresh when returning to main screen
    }
}
