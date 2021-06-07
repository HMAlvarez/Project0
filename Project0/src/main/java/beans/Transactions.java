package beans;

public class Transactions {

	private int transaction_id;
	private int recipient_id;
	private int sender_id;
	private String senderFirstName;
	private String senderLastName;
	private String recipientUserName;
	private String sender_acc_number;
	private double transaction_amount;
	
	public int getTransaction_id() {
		return transaction_id;
	}
	
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	
	public int getRecipient_id() {
		return recipient_id;
	}
	
	public void setRecipient_id(int recipient_id) {
		this.recipient_id = recipient_id;
	}
	
	public int getSender_id() {
		return sender_id;
	}
	
	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}
	
	public String getSenderFirstName() {
		return senderFirstName;
	}
	
	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}
	
	public String getSenderLastName() {
		return senderLastName;
	}
	
	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}
	
	public String getRecipientUserName() {
		return recipientUserName;
	}
	
	public void setRecipientUserName(String recipientUserName) {
		this.recipientUserName = recipientUserName;
	}
	
	public String getSender_acc_number() {
		return sender_acc_number;
	}
	
	public void setSender_acc_number(String sender_acc_number) {
		this.sender_acc_number = sender_acc_number;
	}
	
	public double getTransaction_amount() {
		return transaction_amount;
	}
	
	public void setTransaction_amount(double transaction_amount) {
		this.transaction_amount = transaction_amount;
	}
	
}
