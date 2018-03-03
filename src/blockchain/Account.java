package blockchain;

public interface Account {
	
	double getAmount();
	String getAddress();
	void setAmount(double amount);
	void send(Account receiver, double amount, double fee);
	

}
