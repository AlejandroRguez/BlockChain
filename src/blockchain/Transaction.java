package blockchain;
import product.Update;

public class Transaction {
	
	protected String sender;
	protected String recipient;
	protected Update update;
	protected double amount;
	
	 Transaction (String sender, String recipient, Update update, double amount) {
		this.sender = sender;
		this.recipient = recipient;
		this.update = update;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction {\n"
				+ "\tsender=" + sender + ","
				+ "\trecipient=" + recipient + ", "
				+ "\tupdate=" + update + ", "
				+ "\tamount="	+ amount + "\n}";
	}

	 Integer getHash() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		//result = prime * result + ((update == null) ? 0 : update.hashCode());
		return result;
	}
	
	

	
	
	

	
}
