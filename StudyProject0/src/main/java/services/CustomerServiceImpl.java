package services;

import java.util.List;
import java.util.Scanner;

import driver.Driver;
import models.Account;
import models.Customer;
import repositories.CustomerDAO;

public class CustomerServiceImpl implements CustomerService {
	
	private static CustomerServiceImpl instance;
	
	private Customer customer;
	
	private CustomerServiceImpl() {
	}

	public static CustomerServiceImpl getInstance() {
		if (instance == null)
			instance = new CustomerServiceImpl();
		return instance;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}

	public Customer getCustomer(Integer id) {
		return CustomerDAO.getInstance().getById(id);
	}

	public Customer updateCustomer() {
		this.customer = CustomerDAO.getInstance().getById(this.customer.getId());
		return this.customer;
	}

	public void updateAccount(Account account) {
		this.customer.addAccount(account);
	}
	
	private static String[] parseInfo(Scanner scanner, boolean signingUp) {
		String[] info = new String[2];
		Driver.printMessage("Please enter %s login information:%n", signingUp ? "new" : "your");
		Driver.printMessage("Username: ", false);
		info[0] = scanner.next();
		scanner.nextLine();
		Driver.printMessage("Password: ", false);
		info[1] = scanner.next();
		scanner.nextLine();

		if (signingUp) {
			Driver.printMessage("Confirm Password: ", false);
			String confirmation = scanner.nextLine();
			if (!confirmation.equals(info[1])) {
				Driver.printMessage("Passwords do not match, try again!\n\n");
				return parseInfo(scanner, signingUp);
			}
		}

		return info;
	}

	@Override
	public boolean login(Scanner scanner) {
		String[] info = parseInfo(scanner, false);
		customer = CustomerDAO.getInstance().getByLoginInfo(info[0], info[1]);
		if (customer == null) {
			Driver.printMessage("No Customer available with sign-in credentials used.\n");
			return false;
		} else
			return true;
	}

	@Override
	public boolean signUp(Scanner scanner) {
		String[] info = parseInfo(scanner, true);
		customer = CustomerDAO.getInstance().getByLoginInfo(info[0], info[1]);
		if (customer != null) {
			Driver.printMessage("A Customer is already using this sign-in credential, please try again.\n");
			return false;
		} else {
			customer = new Customer(info[0], info[1]);
			CustomerDAO.getInstance().add(customer);
			Driver.printMessage("Logged in with account: " + customer);
			return true;
		}
	}

	@Override
	public boolean logout() {
		this.customer = null;
		Driver.printMessage("\nSigned Out!.\n");
		return true;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return CustomerDAO.getInstance().getAll();
	}

}
