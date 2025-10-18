package com.sumitcoder.service;

import com.sumitcoder.model.Order;
import com.sumitcoder.model.Seller;
import com.sumitcoder.model.Transaction;
import com.sumitcoder.model.User;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySeller(Seller seller);
    List<Transaction>getAllTransactions();
}
