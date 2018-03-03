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

public class UserAccount implements Account{
	
	 String address;
	 PrivateKey privateKey;
	 PublicKey publicKey;
	 double amount;
	 MessageDigest m = getMessageDigest();
	 String nickname;
	 
	
	public UserAccount(double amount, String nickname) {
		KeyPair keyPair = getKeyPair();
		this.privateKey = keyPair.getPrivate();
		this.publicKey = keyPair.getPublic();
		this.amount = amount;
		this.nickname = nickname;
		this.address = m.digest(this.publicKey.toString().getBytes(StandardCharsets.UTF_8)).toString();
		Blockchain.getInstance().addAccount(this);
		LogManager.write(Level.INFO,
				"Created account for " + nickname +  " with address -> " + this.getAddress() + " and amount -> " + this.getAmount());
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
	
	@Override
	public String getAddress() {
		return address;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	@Override
	public double getAmount() {
		return amount;
	}
	
	@Override
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getNickname() {
		return nickname;
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

}
		
	



