package services;

import java.util.List;

import models.Transaction;

public interface TransactionService {

	void add(Transaction t);

	List<Transaction> getAll();

}
