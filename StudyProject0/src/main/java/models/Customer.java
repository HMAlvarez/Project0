package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
	private Integer id;
	private String username, password;
	private boolean isEmployee;
	
	private List<Account> accounts;

	public Customer() {
		super();
	}
	
	//TESTING - Will delete later
	public Customer(Integer id, String username, String password, boolean isEmployee) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.isEmployee = isEmployee;
	}
	
	public Customer(String username, String password, Account... accounts) {
		this.username = username;
		this.password = password;
		this.isEmployee = false;
		this.accounts = new ArrayList<Account>();
		for (Account a : accounts)
			this.accounts.add(a);
	}

	public Customer(Integer id, String username, String password, boolean isEmployee, List<Account> accounts) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.isEmployee = isEmployee;
		this.accounts = accounts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		if (this.accounts == null)
			this.accounts = new ArrayList<Account>();
		if (account != null && account.getId() != null && account.getId() >= 1) {
			this.accounts.add(account);
		}
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", username=" + username + ", password=" + password + ", isEmployee=" + isEmployee
				+ ", accounts=" + accounts + "]";
	}
	
}
