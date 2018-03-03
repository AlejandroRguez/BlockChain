package utils;

import java.util.logging.Level;

import blockchain.UserAccount;
import blockchain.Blockchain;
import logging.LogManager;

public class AccountCreator {
	
	public static UserAccount create(double amount, String nickname) {
		if(!check(amount, nickname)) {return null; }
		
		if(Blockchain.getInstance().getOwner().getAmount() < amount) {
			LogManager.write(Level.SEVERE, "Error during creating account: There are not enough tokens availables at the moment");
			return null;
		}
		UserAccount a = new UserAccount(0, nickname);
		Blockchain.getInstance().getOwner().send(a, amount - 1, 0);
		return a;
		
	}
	
	public static UserAccount buy(UserAccount seller, double amount, String nickname) {
		if(!check(amount, nickname)) { return null; }
		
		if(seller.getAmount() < amount) {
			LogManager.write(Level.SEVERE, "Error during creating account: The seller hasn´t enough tokens");
			return null;
		}
		UserAccount a = new UserAccount(0, nickname);
		seller.send(a, amount - 2, 0);
		seller.send(Blockchain.getInstance().getOwner(), 2, 0);
		return a;
	}
	
	private static boolean check(double amount, String nickname) {
		if (amount < 0) {
			LogManager.write(Level.SEVERE, "Error during creating account: The amount can´t be negative");
			return false;
		}
		if (nickname.isEmpty()) {
			LogManager.write(Level.SEVERE, "Error during creating account: The nickname is required");
			return false;
		}
		return true;
	}

}
