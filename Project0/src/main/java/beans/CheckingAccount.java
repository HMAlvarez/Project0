package beans;

public class CheckingAccount extends BankAccount {

		private String checking_acc_number;
		private double checking_balance;
		
		public CheckingAccount(String checking_acc_number, double checking_balance) {
			this.checking_acc_number = checking_acc_number;
			this.checking_balance = checking_balance;
		}
		
		public CheckingAccount() {
			
		}

		public String getChecking_acc_number() {
			return checking_acc_number;
		}

		public void setChecking_acc_number(String checking_acc_number) {
			this.checking_acc_number = checking_acc_number;
		}

		public double getChecking_balance() {
			return checking_balance;
		}

		public void setChecking_balance(double checking_balance) {
			this.checking_balance = checking_balance;
		}
		
}
