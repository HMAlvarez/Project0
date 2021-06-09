package services;

import java.util.Map;

import models.Transactions;

public interface TransactionsService {
	void add(Transactions t);

	Map<Integer, Transactions> getAll();
}
