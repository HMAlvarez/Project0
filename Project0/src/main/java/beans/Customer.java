package beans;

public class Customer extends User{

	public Customer(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
		super.setCustomer_type(true);
	}
	
	public Customer() {
		super.setCustomer_type(true);
	}
}
