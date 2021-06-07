package Sample;

import java.util.Scanner;

public class ABC_Bank {
	String username;
	int pin, choice, count = 0;
	user objarr[] = new user[2000]; // this is very important to create an array of object.

	Scanner inp = new Scanner(System.in);

	void welcome() {
		System.out.println("Welcome to the bank\n1:create new user\n2:existing user\n3:exit");
		choice = inp.nextInt(); // we are taking the input to the choice using the inp object of the Scanner class

		if (choice == 1) {
			System.out.println("Enter your username");
			username = inp.next();
			System.out.println("Enter your pin");
			pin = inp.nextInt();
			objarr[count] = new user(username, pin);
			count++;
		} else if (choice == 2) {
			System.out.println("Enter your username");
			username = inp.nextLine();
			System.out.println("Enter your pin");
			pin = inp.nextInt();
			for (int i = 0; i < 10; i++) {
				if (objarr[i].name == username && objarr[i].pin == pin) {
					System.out.println("Welcome " + objarr[i].name + "\n1:Balance enquiry\n2:deposite\n3:withdraw");
					int choice2 = inp.nextInt();
					if (choice2 == 1) {
						System.out.println("Your balance is" + objarr[i].amount);
					} else if (choice2 == 2) {
						System.out.println("Enter the amount from deposite");
						float amt = inp.nextFloat();
						float latestamt = objarr[i].deposite(amt);
						System.out.println("Your updated balance is " + latestamt);
					} else if (choice2 == 3) {
						System.out.println("Enter the amount to withdraw");
						float amt = inp.nextFloat();
						float latestamt = objarr[i].withdraw(amt);
					} else {
						System.out.println("Unexpected number");
					}
				}
			}

		} else if (choice == 3) {
			System.exit(0);
		} else {
			System.out.println("Unexpected number");
		}
	}

	public static void main(String[] args) {
		ABC_Bank obj = new ABC_Bank();
		obj.welcome();
	}

}
