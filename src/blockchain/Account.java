package blockchain;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;

import logging.LogManager;

public class Account {
	
	 String address;
	 PrivateKey privateKey;
	 PublicKey publicKey;
	 double amount;
	 
	
	public Account(double amount) {
		createAccount(amount);
		
	}
	 
	private KeyPair getKeyPair() {
		try {
			return KeyPairGenerator.getInstance("RSA").generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			LogManager.write(Level.SEVERE, "Couldn´t get Key Pair Instance");
			return null;
		}
	}

	private MessageDigest getMessageDigest() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			LogManager.write(Level.SEVERE, "Couldn´t get MessageDigest Instance");
			return null;
		}
	}
	
	public Account createAccount(double amount) {
		if (amount < 0) {
			LogManager.write(Level.SEVERE, "Invalid account creation attempt");
			return null;
		} else {
			MessageDigest m = getMessageDigest();
			KeyPair keyPair = getKeyPair();
			Account a = new Account(amount);
			a.setPrivateKey(keyPair.getPrivate());
			a.setPublicKey(keyPair.getPublic());
			a.setAmount(amount);
			a.setAddress(m.digest(this.publicKey.toString().getBytes(StandardCharsets.UTF_8)).toString());
			LogManager.write(Level.INFO,
					"Created account with address -> " + this.getAddress() + " and amount -> " + this.getAmount());
			return a;
		}
	}

	public String getAddress() {
		return address;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	private void setAddress(String address) {
		this.address = address;
	}

	private void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	private void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public void send(Account receiver, double amount) {
		Transaction t = new Transaction (this, receiver, amount);
		if (t.isValid()) { 
			LogManager.write(Level.INFO, "New transaction generated: " + this.getAddress() + " -> " + amount + " -> " + receiver.getAddress());
			Blockchain.getInstance().addNewTransaction(t); 

		}
		else LogManager.write(Level.SEVERE, "Trying to create an invalid transaction");
	}
}
		
	



