package models;

public class Account {
	private Integer id;
	private Float balance;
	private Integer customer_id;
	private Boolean pending;
	
	public Account() {
		super();
	}
	
	public Account(Float balance) {
		this.balance = balance;
	}

	public Account(Integer id, Float balance, Integer customer_id, Boolean pending) {
		super();
		this.id = id;
		this.balance = balance;
		this.customer_id = customer_id;
		this.pending = pending;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public Boolean getPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", customer_id=" + customer_id + ", pending=" + pending
				+ "]";
	}
	
}
