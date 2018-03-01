package blockchain;
import java.util.logging.Level;

import logging.LogManager;

public class Transaction {
	
	protected Account sender;
	protected Account receiver;
	protected double amount;
	protected double fee;
	
	 Transaction (Account sender, Account receiver, double amount, double fee) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction {"
				+ "sender=" + sender.getAddress() + ","
				+ "recipient=" + receiver.getAddress() + ", "
				+ "amount="	+ amount + ", "
				+ "fee=" + fee + "}\n";
	}
	
	 Integer getHash() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((receiver == null) ? 0 : receiver.getAddress().hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.getAddress().hashCode());
		return result;
	}
	 
	 public void execute(Account miner) {
		this.getSender().setAmount(this.getSender().getAmount() - this.getAmount()); 
		this.getReceiver().setAmount(this.getReceiver().getAmount() + this.getAmount()); 
		miner.setAmount(miner.getAmount() + this.getFee());
	 }
	 
	 public Account getSender() {
		 return sender;
	 }
	 
	 private Account getReceiver() {
		 return receiver;
	 }
	 
	 private double getAmount() {
		 return amount;
	 }
	 
	 private double getFee() {
		 return fee;
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
		if(this.getSender().equals(this.getReceiver())) {
			LogManager.write(Level.WARNING, "You are trying send tokens to the same wallet");
		}
		return valid;
	}
	
	

	
	
	

	
}
