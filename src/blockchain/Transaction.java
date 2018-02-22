package blockchain;
import java.util.logging.Level;

import logging.LogManager;
import product.Update;

public class Transaction {
	
	protected Account sender;
	protected Account receiver;
	protected Update update;
	protected double amount;
	
	 Transaction (Account sender, Account receiver, Update update, double amount) {
		this.sender = sender;
		this.receiver = receiver;
		this.update = update;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction {\n"
				+ "\tsender=" + sender.getAddress() + ","
				+ "\trecipient=" + receiver.getAddress() + ", "
				+ "\tupdate=" + update + ", "
				+ "\tamount="	+ amount + "\n}";
	}

	 Integer getHash() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((receiver == null) ? 0 : receiver.getAddress().hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.getAddress().hashCode());
		//result = prime * result + ((update == null) ? 0 : update.hashCode());
		return result;
	}
	 
	 public void execute() {
		this.getSender().setAmount(this.getSender().getAmount() - this.getAmount()); 
		this.getReceiver().setAmount(this.getReceiver().getAmount() + this.getAmount()); 
	 }
	 
	 private Account getSender() {
		 return sender;
	 }
	 
	 private Account getReceiver() {
		 return receiver;
	 }
	 
	 private double getAmount() {
		 return amount;
	 }

	public boolean isValid() {
		boolean valid = true;
		if (this.getSender() == null) {
			LogManager.write(Level.SEVERE, "Error during create transaction: Invalid sender");
			valid = false;
		}
		if (this.getReceiver() == null) {
			LogManager.write(Level.SEVERE, "Error during create transaction: Invalid receiver");
			valid = false;
		}
		if (this.getSender().getAmount() < this.getAmount() || this.getAmount() <= 0) {
			LogManager.write(Level.SEVERE, "Error during create transaction: Invalid amount");
			valid = false;
		}
		return valid;
	}
	
	

	
	
	

	
}
