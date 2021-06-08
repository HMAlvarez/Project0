package services;

import java.util.List;

import driver.Driver;
import beans.Account;
import beans.Transactions;
import data.AccountRepository;

public class AccountServiceImpl implements AccountService {
	private static AccountServiceImpl instance;
	
	private AccountServiceImpl() {}
	
	public static AccountServiceImpl getInstance() {
		if (instance == null) instance = new AccountServiceImpl();
		return instance;
	}
	
	public List<Account> getPendingAccounts() {
		return AccountRepository.getInstance().getPendingAccounts();
	}

	@Override
	public boolean withdraw(Integer account_id, Float amount) {
		Account account = AccountRepository.getInstance().getById(account_id);
		if (account != null) {
			if (amount < 0) {
				Driver.printMessage("You cannot withdraw a negative amount.");
				return false;
			} else if (amount > account.getBalance()) {
				Driver.printMessage("You cannot withdraw $%.2f from account %d because its balance is only $%.2f.%n", amount, account_id, account.getBalance());
				return false;
			} else if (account.isPending()) {
				Driver.printMessage("You cannot withdraw money from a pending account. Please wait until an employee approves the account.");
				return false;
			} else {
				boolean confirmation = Driver.getConfirmation("Are you sure you wish to withdraw $%.2f from account %d?", amount, account_id);
				if (confirmation) {
					account.setBalance(account.getBalance() - amount);
					update(account);
					TransactionsServiceImpl.getInstance().add(new Transactions(account, "withdrawal", amount));
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean deposit(Integer account_id, Float amount) {
		Account account = AccountRepository.getInstance().getById(account_id);
		if (account != null) {
			if (amount < 0) {
				Driver.printMessage("You cannot deposit a negative amount.");
				return false;
			} else if (account.isPending()) {
				Driver.printMessage("You cannot deposit money into a pending account. Please wait until an employee approves the account.");
				return false;
			} else {
				account.setBalance(account.getBalance() + amount);
				update(account);
				TransactionsServiceImpl.getInstance().add(new Transactions(account, "deposit", amount));
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean transfer(Integer from_id, Integer to_id, Float amount) {
		Account from = AccountRepository.getInstance().getById(from_id);
		Account to = AccountRepository.getInstance().getById(to_id);
		if (from != null && to != null) {
			if (amount < 0) {
				Driver.printMessage("You cannot transfer a negative amount.");
				return false;
			} else if (amount > from.getBalance()) {
				Driver.printMessage("You cannot transfer $%.2f from account %d because its balance is only $%.2f.", amount, from.getId(), from.getBalance());
				return false;
			} else if (from.isPending() || to.isPending()) {
				Driver.printMessage("One of the accounts you are attempting to transfer with has not been approved. The transfer could not be completed. Please wait until an employee approves the account(s).");
				return false;
			} else {
				boolean confirmation = Driver.getConfirmation("Are you sure you want to transfer $%.2f from account %d to account %d? Y/N: ", amount, from.getId(), to.getId());
				
				if (confirmation) {
					from.setBalance(from.getBalance() - amount);
					to.setBalance(to.getBalance() + amount);
					update(from);
					update(to);
					TransactionsServiceImpl.getInstance().add(new Transactions(from, "transfer", amount, to));
					return true;
				} else {
					Driver.printMessage("Transfer canceled.");
					return false;
				}
			}
		} else {
			Driver.printMessage("One of the provided accounts does not exist.");
			return false;
		}
	}
	
	@Override
	public void apply(Float amount) {
		Account a = new Account(amount);
		a.setCustomerId(CustomerServiceImpl.getInstance().getCustomer().getId());
		a.setPending(true);
		AccountRepository.getInstance().add(a);
		CustomerServiceImpl.getInstance().updateCustomer();
	}
	
	@Override
	public void update(Account account) {
		AccountRepository.getInstance().update(account);
	}	
}
