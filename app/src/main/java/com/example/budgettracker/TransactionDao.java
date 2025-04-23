package com.example.budgettracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(Transaction transaction);

    @Query("SELECT * FROM `Transaction` ORDER BY id DESC")
    List<Transaction> getAllTransactions();

    List<Transaction> getAll();
}
