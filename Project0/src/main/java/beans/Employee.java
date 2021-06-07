package beans;

public class Employee extends User {

	public Employee(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
		super.setCustomer_type(false);
	}
	
	public Employee() {
		super.setCustomer_type(false);
	}
}
