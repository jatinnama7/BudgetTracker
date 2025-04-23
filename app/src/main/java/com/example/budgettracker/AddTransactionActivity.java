package com.example.budgettracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText amountInput, descriptionInput;
    private Button addIncomeBtn, addExpenseBtn;

    private TransactionDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        amountInput = findViewById(R.id.amount_input);
        descriptionInput = findViewById(R.id.description_input);
        addIncomeBtn = findViewById(R.id.add_income_btn);
        addExpenseBtn = findViewById(R.id.add_expense_btn);

        db = TransactionDatabase.getInstance(this);

        addIncomeBtn.setOnClickListener(v -> {
            addTransaction(true); // income
        });

        addExpenseBtn.setOnClickListener(v -> {
            addTransaction(false); // expense
        });
    }

    private void addTransaction(boolean isIncome) {
        String desc = descriptionInput.getText().toString().trim();
        String amountStr = amountInput.getText().toString().trim();

        if (TextUtils.isEmpty(desc) || TextUtils.isEmpty(amountStr)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount = Integer.parseInt(amountStr);
        if (!isIncome) {
            amount = -amount; // Make it negative for expense
        }

        Transaction transaction = new Transaction(desc, amount);

        new Thread(() -> {
            db.transactionDao().insert(transaction);
            runOnUiThread(() -> {
                Toast.makeText(this, "Transaction added", Toast.LENGTH_SHORT).show();
                finish(); // Go back to MainActivity
            });
        }).start();
    }
}
