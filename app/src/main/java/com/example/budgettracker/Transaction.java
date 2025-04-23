package com.example.budgettracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String description;
    private int amount;

    public Transaction(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) { // Required by Room
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }
}
