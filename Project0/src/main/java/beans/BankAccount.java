package beans;

public class BankAccount {
	
	private Integer customer_id;
	private Integer bank_id;
	private String mail_address;
	private boolean pendingTransactions;
	
	public Integer getCustomer_id() {
		return customer_id;
	}
	
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	
	public Integer getBank_id() {
		return bank_id;
	}
	
	public void setBank_id(Integer bank_id) {
		this.bank_id = bank_id;
	}
	
	public String getMail_address() {
		return mail_address;
	}
	
	public void setMail_address(String mail_address) {
		this.mail_address = mail_address;
	}
	
	public boolean isPendingTransactions() {
		return pendingTransactions;
	}
	
	public void setPendingTransactions(boolean pendingTransactions) {
		this.pendingTransactions = pendingTransactions;
	}
	
}
