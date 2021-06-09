package services;

import java.util.Map;
import java.util.Scanner;

import driver.Driver;
import models.Account;
import models.Customer;
import repositories.CustomerRepository;

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
		return CustomerRepository.getInstance().getById(id);
	}

	public Customer updateCustomer() {
		this.customer = CustomerRepository.getInstance().getById(this.customer.getId());
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
				Driver.printMessage("Password does not match confirmation, please try again!\n\n");
				return parseInfo(scanner, signingUp);
			}
		}

		return info;
	}

	@Override
	public boolean login(Scanner scanner) {
		String[] info = parseInfo(scanner, false);
		customer = CustomerRepository.getInstance().getByUsernameAndPassword(info[0], info[1]);
		if (customer == null) {
			Driver.printMessage("No customer account was found with the provided login information.\n");
			return false;
		} else
			return true;
	}

	@Override
	public boolean signUp(Scanner scanner) {
		String[] info = parseInfo(scanner, true);
		customer = CustomerRepository.getInstance().getByUsernameAndPassword(info[0], info[1]);
		if (customer != null) {
			Driver.printMessage("An account with the provided login information already exists, please try again.\n");
			return false;
		} else {
			customer = new Customer(info[0], info[1]);
			CustomerRepository.getInstance().add(customer);
			Driver.printMessage("Logged in with account: " + customer);
			return true;
		}
	}

	@Override
	public boolean logout() {
		this.customer = null;
		Driver.printMessage("\nYou have been logged out.\n");
		return true;
	}

	@Override
	public Map<Integer, Customer> getAllCustomers() {
		return CustomerRepository.getInstance().getAll();
	}
}
