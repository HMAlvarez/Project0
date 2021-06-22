package driver;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import logging.AppLogger;
import models.Account;
import models.Customer;
import models.Transaction;
import repositories.CustomerDAO;
import services.AccountServiceImpl;
import services.CustomerServiceImpl;
import services.TransactionServiceImpl;

public class Driver {
	
	public static void main(String[] args) {
		
		scanner = new Scanner(System.in);
		boolean quit = false;
		
		AppLogger.logger.info("Program Started.");


		printMessage("Hello!\nThis is a console-based bank application meant to simulate simple banking operations.\n"
				+ "Please read all menu options and instructions carefully before typing. ");
		do {
			switch (currentMenu) {
			case FIRST_MENU:
				printMenu(main_menu, "Quit", true);
				quit = handleMainMenu();
				break;
			case SECOND_MENU:
				printAccounts(CustomerServiceImpl.getInstance().getCustomer());
				printMenu(customer_menu, "Logout", true, CustomerServiceImpl.getInstance().getCustomer().isEmployee());
				handleCustomerMenu();
				break;
			}
		} while (!quit);

		scanner.close();
	}
	
	private enum Menus {
		FIRST_MENU, SECOND_MENU
	}

	private static Menus currentMenu = Menus.FIRST_MENU;

	public static final Logger logger = LogManager.getLogger(Driver.class);

	public static Scanner scanner;
	private static String[] main_menu = { "1. Sign In", "2. Sign Up" };
	private static String[] customer_menu = { "1. Make a New Account", "2. Make a Withdrawal", "3. Make a Deposit",
			"4. Make a Transfer" };
	private static String[] employee_options = { "Approve or Deny Accounts", "View Customer Account",
			"View Transaction Logs" };

	public static void printAccounts(Customer customer) {
		if (customer == null) {
			logger.error("printAccounts: customer null");
			return;
		}

		boolean printingCurrentCustomer = customer.equals(CustomerServiceImpl.getInstance().getCustomer());

		if (printingCurrentCustomer)
			printMessage("\n\nYou are logged in as \"" + customer.getUsername() + "\"\n");
		else
			printMessage("\nCustomer Account(s) \"" + customer.getUsername() + "\":\n");

		if (customer.getAccounts() == null || customer.getAccounts().size() == 0) {
			if (printingCurrentCustomer)
				printMessage("No accounts currently open.\n");
			else
				printMessage("Customer \"%s\" does not have any accounts.%n", customer.getUsername());
		} else {
			if (printingCurrentCustomer)
				printMessage("Your accounts:");
			for(Account a: customer.getAccounts()) {
				System.out.println("Account #:" + a.getId() + ", Balance:" + a.getBalance() + ", Pending:" + a.getPending());
			}
		}
	}

	private static void printMenu(String[] menu, String extraOption, boolean printCarrot) {
		printMessage("\nPlease select a Menu Option:\n");
		for (String option : menu)
			printMessage(option);
		if (extraOption != null && !extraOption.isEmpty())
			printMessage("" + (menu.length + 1) + ". " + extraOption);
		if (printCarrot)
			printCarrot();
	}

	private static void printMenu(String[] menu, String extraOption, boolean printCarrot,
			boolean printEmployeeOptions) {
		printMenu(menu, null, false);
		if (printEmployeeOptions) {
			for (int i = 0; i < employee_options.length; i++) {
				printMessage("%d. %s%n", i + menu.length + 1, employee_options[i]);
			}
		}

		if (extraOption != null && !extraOption.isEmpty()) {
			int optionNum = menu.length + 1;
			if (printEmployeeOptions)
				optionNum += employee_options.length;
			printMessage("%d. %s%n", optionNum, extraOption);
		}
		if (printCarrot)
			printCarrot();
	}

	private static void printCarrot() {
		System.out.print(">> ");
	}

	public static void printMessage(String message) {
		System.out.println(message);
	}

	public static void printMessage(String message, boolean addNewline) {
		if (!addNewline)
			System.out.print(message);
		else
			System.out.println(message);
	}

	public static void printMessage(String message, Object... args) {
		System.out.printf(message, args);
	}

	public static boolean getConfirmation(String message, Object... args) {
		System.out.printf(message + " Y/N: ", args);
		boolean confirmation = scanner.nextLine().equalsIgnoreCase("y");
		return confirmation;
	}

	private static boolean handleMainMenu() {
		String username;
		switch (scanner.nextInt()) {
		case 1:
			if (CustomerServiceImpl.getInstance().login(scanner)) {
				currentMenu = Menus.SECOND_MENU;
				username = CustomerServiceImpl.getInstance().getCustomer().getUsername();
				logger.info("Customer " + username + " logged in.");
				return false;
			} else {
				printMenu(main_menu, "Quit", true);
				return false;
			}
		case 2: 
			if (CustomerServiceImpl.getInstance().signUp(scanner)) {
				currentMenu = Menus.SECOND_MENU;
				username = CustomerServiceImpl.getInstance().getCustomer().getUsername();
				logger.info("Customer " + username + " has signed up.");
				return false;
			} else {
				printMenu(main_menu, "Quit", true);
				return false;
			}
		case 3:
			printMessage("\nProgram Terminated!");
			return true;
		default:
			printMessage("This is not a valid Option. "
					+ " Please read the menu carefully and try again");
			return false;
		}
	}

	private static void handleCustomerMenu() {
		
		String[] command;
		Customer customer = CustomerServiceImpl.getInstance().getCustomer();
		if (customer == null) {
			logger.error("handleCustomerMenu: customer null");
			return;
		}

		command = scanner.nextLine().split(" ");
		switch (command[0]) {
		case "1":
			AccountServiceImpl.getInstance().apply(scanner);
			break;
		case "2":
			AccountServiceImpl.getInstance().withdraw(scanner);
			break;
		case "3":
			AccountServiceImpl.getInstance().deposit(scanner);
			break;
		case "4":
			AccountServiceImpl.getInstance().transfer(scanner);
			break;
		case "5":
		case "6":
		case "7":
		case "8":
			if (command[0].equals("8") || (command[0].equals("5") && !customer.isEmployee())) {
				String username = CustomerServiceImpl.getInstance().getCustomer().getUsername();
				logger.info("Customer " + username + " logged out.");
				CustomerServiceImpl.getInstance().logout();
				currentMenu = Menus.FIRST_MENU;
				return;
			} else
				handleEmployeeOptions(command);
			break;
		default:
			printMessage("Please enter a valid option.");
			return;
		}
	}

	private static void handleEmployeeOptions(String[] command) {
	
		switch (command[0]) {
		case "5":
			List<Account> pending = AccountServiceImpl.getInstance().getPendingAccounts();

			if (pending.isEmpty()) {
				printMessage("\nThere are accounts that need to be Approved/Denied.");
				break;
			}

			printMessage("\nPending accounts:");
			for (Account a : pending) {
				Customer c = CustomerServiceImpl.getInstance().getCustomer(a.getCustomer_id());
				printMessage("    Customer: %s, Requested balance: $%.2f%n", c.getUsername(), a.getBalance());
				boolean approved = getConfirmation("    Approved? ");
				if (approved) {
					a.setPending(false);
					AccountServiceImpl.getInstance().update(a, false);
					printMessage("Account %d with balance $%.2f for Customer %s has been approved.%n", a.getId(),
							a.getBalance(), c.getUsername());
				}
			}
			break;
		case "6":
			printMessage("Select a Customer to view open Accounts:");
			for(Customer c : CustomerServiceImpl.getInstance().getAllCustomers()) {
				System.out.println("Customer #:" + c.getId() + ", Username:" + c.getUsername());
			}
			printCarrot();
			int customer_id = scanner.nextInt();
			scanner.nextLine();
			Customer c = CustomerServiceImpl.getInstance().getCustomer(customer_id);
			printAccounts(c);
			break;
		case "7":
			List<Transaction> transactions = TransactionServiceImpl.getInstance().getAll();
			printMessage("\n\nTransaction Log:");
			for(Transaction t: TransactionServiceImpl.getInstance().getAll()) {
				System.out.println(t);
			}
			break;
		default:
			break;
		}
	}

}