package Sample;

public class user {

	float amount;
	String name;
	int pin;

	user(String user, int pin) // we are using the constructor to assign default values
	{
		amount = 1000;
		name = user;
		this.pin = pin;
	}

	float withdraw(float WidAmt) {
		if (WidAmt <= amount && WidAmt >= 0) {
			amount = amount - WidAmt;
			return amount;
		}
		return 0;
	}

	float deposite(float DepoAmt) {
		if (DepoAmt > 0) {
			amount = amount + DepoAmt;
			return amount;
		}
		return 0;
	}

}
