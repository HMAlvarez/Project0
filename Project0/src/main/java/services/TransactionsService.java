package services;

import java.util.Map;

import beans.Transactions;

public interface TransactionsService {
	void add(Transactions t);
	Map<Integer, Transactions> getAll();
}
