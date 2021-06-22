package models;

public class Transaction {
	private Integer id;
	private Account source;
	private String type;
	private Float amount;
	private Account receiver;
	
	public Transaction() {
		super();
	}
	
	public Transaction(Account source, String type, Float amount) {
		this.source = source;
		this.type = type;
		this.amount = amount;
	}
	
	public Transaction(Account source, String type, Float amount, Account receiver) {
		this.source = source;
		this.type = type;
		this.amount = amount;
		this.receiver = receiver;
	}

	public Transaction(Integer id, Account source, String type, Float amount, Account receiver) {
		super();
		this.id = id;
		this.source = source;
		this.type = type;
		this.amount = amount;
		this.receiver = receiver;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getSource() {
		return source;
	}

	public void setSource(Account source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Account getReceiver() {
		return receiver;
	}

	public void setReceiver(Account receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", source=" + source + ", type=" + type + ", amount=" + amount + ", receiver="
				+ receiver + "]";
	}

}
