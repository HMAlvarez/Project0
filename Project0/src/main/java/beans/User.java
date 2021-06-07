package beans;

public abstract class User {
	
	//Variables
	private Integer user_id;
	private String username;
	private String password;
	private String first;
	private String last;
	private double initialDeposit;
	
	private boolean customer_type;

	public User(String username, String password, String first, String last) {
		super();
		this.username = username;
		this.password = password;
		this.first = first;
		this.last = last;
	}
	
	public User() {
		
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public double getInitialDeposit() {
		return initialDeposit;
	}

	public void setInitialDeposit(double initialDeposit) {
		this.initialDeposit = initialDeposit;
	}

	public boolean isCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(boolean customer_type) {
		this.customer_type = customer_type;
	}
	
}
	
