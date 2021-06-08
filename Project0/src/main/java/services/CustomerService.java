package services;

import java.util.Scanner;

public interface CustomerService {
	boolean login(Scanner scanner);
	boolean signUp(Scanner scanner);
	boolean logout();
}
