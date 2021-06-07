package beans;

public class SavingAccount extends BankAccount{
	
	private String saving_acc_number;
	private double saving_balance;
	
	public SavingAccount(String saving_acc_number, double saving_balance) {
		this.saving_acc_number = saving_acc_number;
		this.saving_balance = saving_balance;
	}
	
	public SavingAccount() {
		
	}

	public String getSaving_acc_number() {
		return saving_acc_number;
	}

	public void setSaving_acc_number(String saving_acc_number) {
		this.saving_acc_number = saving_acc_number;
	}

	public double getSaving_balance() {
		return saving_balance;
	}

	public void setSaving_balance(double saving_balance) {
		this.saving_balance = saving_balance;
	}
	
}
