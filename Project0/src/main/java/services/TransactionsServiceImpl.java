package services;

import java.util.Map;

import models.Transactions;
import repositories.TransactionDAO;

public class TransactionsServiceImpl implements TransactionsService {
	private static TransactionsServiceImpl instance;

	private TransactionsServiceImpl() {
	}

	public static TransactionsServiceImpl getInstance() {
		if (instance == null)
			instance = new TransactionsServiceImpl();
		return instance;
	}

	@Override
	public void add(Transactions t) {
		TransactionDAO.getInstance().add(t);
	}

	@Override
	public Map<Integer, Transactions> getAll() {
		return TransactionDAO.getInstance().getAll();
	}
}
