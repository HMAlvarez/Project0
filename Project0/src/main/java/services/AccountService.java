package services;

import beans.Account;

public interface AccountService {
	boolean withdraw(Integer account_id, Float amount);
	boolean deposit(Integer account_id, Float amount);
	boolean transfer(Integer from_id, Integer to_id, Float amount);
	void apply(Float amount);
	void update(Account account);
}
