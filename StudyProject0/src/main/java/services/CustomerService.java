package services;

import java.util.List;
import java.util.Scanner;

import models.Customer;

public interface CustomerService {

	boolean login(Scanner scanner);

	boolean signUp(Scanner scanner);

	boolean logout();

	List<Customer> getAllCustomers();

}
