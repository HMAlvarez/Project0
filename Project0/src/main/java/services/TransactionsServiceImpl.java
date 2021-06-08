package services;

import java.util.Map;

import beans.Transactions;
import data.TransactionsRepository;

public class TransactionsServiceImpl implements TransactionsService {
	private static TransactionsServiceImpl instance;
	
	private TransactionsServiceImpl() {}
	
	public static TransactionsServiceImpl getInstance() {
		if (instance == null) instance = new TransactionsServiceImpl();
		return instance;
	}
	
	@Override
	public void add(Transactions t) {
		TransactionsRepository.getInstance().add(t);
	}
	
	@Override
	public Map<Integer, Transactions> getAll() {
		return TransactionsRepository.getInstance().getAll();
	}
}
