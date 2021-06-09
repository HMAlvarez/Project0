package services;

import java.util.Map;
import java.util.Scanner;

import models.Customer;

public interface CustomerService {
	boolean login(Scanner scanner);

	boolean signUp(Scanner scanner);

	boolean logout();

	Map<Integer, Customer> getAllCustomers();

}
