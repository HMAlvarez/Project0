package services;

import java.util.List;

import models.Transaction;
import repositories.TransactionDAO;

public class TransactionServiceImpl implements TransactionService {
	
	private static TransactionServiceImpl instance;

	private TransactionServiceImpl() {
	}

	public static TransactionServiceImpl getInstance() {
		if (instance == null)
			instance = new TransactionServiceImpl();
		return instance;
	}

	@Override
	public void add(Transaction t) {
		TransactionDAO.getInstance().add(t);
	}

	@Override
	public List<Transaction> getAll() {
		return TransactionDAO.getInstance().getAll();
	}

}
