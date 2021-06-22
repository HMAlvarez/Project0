package services;

import java.util.List;
import java.util.Scanner;

import driver.Driver;
import models.Account;
import models.Transaction;
import repositories.AccountDAO;

public class AccountServiceImpl implements AccountService {
	
	private static AccountServiceImpl instance;

	private AccountServiceImpl() {
	}

	public static AccountServiceImpl getInstance() {
		if (instance == null)
			instance = new AccountServiceImpl();
		return instance;
	}

	public List<Account> getPendingAccounts() {
		return AccountDAO.getInstance().getPendingAccounts();
	}
	
	private static String[] parseWithdrawOrDepositInfo(Scanner scanner, boolean withdraw) {
		String[] info = new String[2];
		Driver.printAccounts(CustomerServiceImpl.getInstance().getCustomer());
		Driver.printMessage("Please enter the account you wish to %s: ", withdraw ? "withdraw from" : "deposit into");
		info[0] = scanner.next();
		scanner.nextLine();
		Driver.printMessage("Amount to " + (withdraw ? "withdraw: " : "deposit: "), false);
		info[1] = scanner.next();
		scanner.nextLine();
		return info;
	}
	
	private static String[] parseTransferInfo(Scanner scanner) {
		String[] info = new String[3];
		Driver.printAccounts(CustomerServiceImpl.getInstance().getCustomer());
		Driver.printMessage("\nPlease select the Account to Transfer FROM: ", false);
		info[0] = scanner.next();
		scanner.nextLine();
		Driver.printMessage("Please select the Account to Transfer TO: ", false);
		info[1] = scanner.next();
		scanner.nextLine();
		Driver.printMessage("Transaction amount: ", false);
		info[2] = scanner.next();
		scanner.nextLine();
		return info;
	}

	@Override
	public boolean withdraw(Scanner scanner) {
		String[] command = parseWithdrawOrDepositInfo(scanner, true);
		Integer account_id = Integer.parseInt(command[0]);
		Float amount = Float.parseFloat(command[1]);

//		if (!CustomerServiceImpl.getInstance().getCustomer().getAccounts()) {
//			Driver.printMessage("You do not have permission to withdraw from this Account %d.%n", account_id);
//			return false;
//		}

		Account account = AccountDAO.getInstance().getById(account_id);
		if (account != null) {
			if (amount < 0) {
				Driver.printMessage("Cannot withdraw a negative amount.");
				return false;
			} else if (amount > account.getBalance()) {
				Driver.printMessage("Cannot withdraw $%.2f from account %d because the original balance is only $%.2f.%n",
						amount, account_id, account.getBalance());
				return false;
			} else if (account.getPending()) {
				Driver.printMessage(
						"This account is waiting on employee Approval/Denial");
				return false;
			} else {
				boolean confirmation = Driver.getConfirmation(
						"Corfirm to withdraw $%.2f from account %d?", amount, account_id);
				if (confirmation) {
					account.setBalance(account.getBalance() - amount);
					update(account, true);
					TransactionServiceImpl.getInstance().add(new Transaction(account, "withdrawal", amount));
					Driver.printMessage("You have withdrawn $%.2f from account %d.%n", amount, account_id);
					Driver.logger.info(String.format("Customer %s withdrew $%.2f from account %d.",
							CustomerServiceImpl.getInstance().getCustomer().getUsername(), amount, account_id));
					return true;
				}
			}
		} else {
			Driver.printMessage("No account with the id of " + account_id);
		}

		return false;
	}

	@Override
	public boolean deposit(Scanner scanner) {
		String[] info = parseWithdrawOrDepositInfo(scanner, false);
		Integer account_id = Integer.parseInt(info[0]);
		Float amount = Float.parseFloat(info[1]);

//		if (!CustomerServiceImpl.getInstance().getCustomer().getAccounts().getId(account_id)) {
//			Driver.printMessage("You do not have permission to deposit into this Account %d.%n", account_id);
//			return false;
//		}

		Account account = AccountDAO.getInstance().getById(account_id);
		if (account != null) {
			if (amount < 0) {
				Driver.printMessage("Cannot deposit a negative amount.");
				return false;
			} else if (account.getPending()) {
				Driver.printMessage(
						"This account is waiting on employee Approval/Denial");
				return false;
			} else {
				account.setBalance(account.getBalance() + amount);
				update(account, true);
				TransactionServiceImpl.getInstance().add(new Transaction(account, "deposit", amount));
				Driver.printMessage("You have depositted $%.2f into account %d.%n", amount, account_id);
				Driver.logger.info(String.format("Customer %s depositted $%.2f into account %d.",
						CustomerServiceImpl.getInstance().getCustomer().getUsername(), amount, account_id));
				return true;
			}
		} else {
			Driver.printMessage("No account with the id of " + account_id);

		}
		return false;
	}

	@Override
	public boolean transfer(Scanner scanner) {
		String[] info = parseTransferInfo(scanner);
		Integer from_id = Integer.parseInt(info[0]);
		Integer to_id = Integer.parseInt(info[1]);
		Float amount = Float.parseFloat(info[2]);

//		if (!CustomerServiceImpl.getInstance().getCustomer().getAccounts().containsKey(from_id)) {
//			Driver.printMessage("You do not have permission to transfer money out of account %d.%n", from_id);
//			return false;
//		} else if (!CustomerServiceImpl.getInstance().getCustomer().getAccounts().containsKey(to_id)) {
//			Driver.printMessage("You do not have permisson to transfer money into account %d.%n", to_id);
//			return false;
//		}

		Account from = CustomerServiceImpl.getInstance().getCustomer().getAccounts().get(from_id);
		Account to = CustomerServiceImpl.getInstance().getCustomer().getAccounts().get(to_id);
		if (from == null) {
			Driver.printMessage("No account with the id of " + from_id);
			return false;
		} else if (to == null) {
			Driver.printMessage("No account with the id of " + to_id);
			return false;
		} else {
			if (amount < 0) {
				Driver.printMessage("Cannot transfer a negative amount.");
				return false;
			} else if (amount > from.getBalance()) {
				Driver.printMessage("Cannot transfer $%.2f from account %d because its balance is only $%.2f.",
						amount, from.getId(), from.getBalance());
				return false;
			} else if (from.getPending() || to.getPending()) {
				Driver.printMessage(
						"An account is still pending. Transaction is canceled. Account waiting for employee Approval/Denial");
				return false;
			} else {
				boolean confirmation = Driver.getConfirmation(
						"Confirm to transfer $%.2f from account %d to account %d?", amount, from.getId(),
						to.getId());

				if (confirmation) {
					from.setBalance(from.getBalance() - amount);
					to.setBalance(to.getBalance() + amount);
					update(from, true);
					update(to, true);
					TransactionServiceImpl.getInstance().add(new Transaction(from, "transfer", amount, to));
					Driver.printMessage("Transfer confirmed.");
					Driver.logger.info(String.format("Customer %s transferred $%.2f from account %d to account %d.",
							CustomerServiceImpl.getInstance().getCustomer().getUsername(), amount, from_id, to_id));
					return true;
				} else {
					Driver.printMessage("Transfer canceled.");
					return false;
				}
			}
		}
	}

	@Override
	public void apply(Scanner scanner) {
		Driver.printMessage("Enter inital deposit to make: ", false);
		Float amount = scanner.nextFloat();
		scanner.nextLine();
		if (amount < 0) {
			Driver.printMessage("Cannot create an account with a negative balance.");
			return;
		}

		Account a = new Account(amount);
		a.setCustomer_id(CustomerServiceImpl.getInstance().getCustomer().getId());
		if (!CustomerServiceImpl.getInstance().getCustomer().isEmployee())
			a.setPending(!CustomerServiceImpl.getInstance().getCustomer().isEmployee());
		AccountDAO.getInstance().add(a);
		CustomerServiceImpl.getInstance().getCustomer().addAccount(a);
		Driver.printMessage(
				"New account with balance $%.2f has made. Account waiting for employee Approval/Denial.%n",
				amount);
	}

	@Override
	public void update(Account account, boolean updateCurrentCustomer) {
		if (updateCurrentCustomer)
			CustomerServiceImpl.getInstance().getCustomer().addAccount(account);
		AccountDAO.getInstance().update(account);
	}

}
