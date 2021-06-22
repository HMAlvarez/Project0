package services;

import java.util.Scanner;

import models.Account;

public interface AccountService {
	
	boolean withdraw(Scanner scanner);

	boolean deposit(Scanner scanner);

	boolean transfer(Scanner scanner);

	void apply(Scanner scanner);

	void update(Account account, boolean updateCurrentCustomer);
}
