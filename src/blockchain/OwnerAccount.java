package blockchain;

import java.util.logging.Level;

import logging.LogManager;

public class OwnerAccount implements Account{
	
	private double amount;
	
	public OwnerAccount(double amount) {
		this.amount = amount;
	}

	@Override
	public double getAmount() {
		return amount;
	}

	@Override
	public String getAddress() {
		return "Owner";
	}

	@Override
	public void send(Account receiver, double amount, double fee) {
		Transaction t = new Transaction (this, receiver, amount, fee);
		if (t.isValid()) { 
			LogManager.write(Level.INFO, "New transaction generated: " + this.getAddress() + " -> " + amount + " -> " + receiver.getAddress());
			Blockchain.getInstance().addNewTransaction(t); 

		}
		else LogManager.write(Level.SEVERE, "Trying to create an invalid transaction");
		
	}

	@Override
	public void setAmount(double amount) {
		this.amount = amount;
		
	}

}
