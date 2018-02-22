package blockchain;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import logging.LogManager;

public class Blockchain {
	
	private static final Integer TRANSACTIONS_PER_BLOCK = 4;
	private Integer currentTransactionsCounter;
	private Block current;
	private Transaction[] currentTransactions;
	private MessageDigest m = getMessageDigest();
	private static final Blockchain bk = new Blockchain();
	
	private Blockchain() {
		this.current = new Block("", currentTransactions, "GenesisBlock", 0);
	}
	
	public static Blockchain getInstance() {
		return bk;
	}
	//private List<Account> accounts = new ArrayList<Account>();
	
	public void addNewTransaction(Transaction t) {
		if(currentTransactionsCounter >= TRANSACTIONS_PER_BLOCK) {
			LogManager.write(Level.SEVERE, "No more transactions allowen in current block");
			return;
		}
		this.currentTransactions[currentTransactionsCounter] = t;
		currentTransactionsCounter ++;
		
		if(currentTransactionsCounter.equals(TRANSACTIONS_PER_BLOCK)) {
			executeTransactions();
			addNewBlock();
		}
	}
	
	private void executeTransactions() {
		int i = 0;
		while (i < currentTransactionsCounter) {
			currentTransactions[i].execute();
			i ++;
		}
	}
	
	private void addNewBlock (){
		Block b = new Block(current.getHash(), getCurrentTransactions(), proofOfWork(), current.getIndex()+1);
		LogManager.write(Level.INFO, "Block number" + b.getIndex() + "created correctly");
		b.pointer = current;
		LogManager.write(Level.INFO, "Actual state:" + current.getIndex() + " -> " + current.pointer.getIndex());
		b = current;
		LogManager.write(Level.INFO, "Actual state:" + current.getIndex() + " -> ");
		setCurrentTransactions();
		LogManager.write(Level.INFO, "New pending block:" + current.getIndex()
				+ ". Number of transactions:" + currentTransactions.length);
		currentTransactionsCounter = 0;
	}
	
	
	/*Habría que hacer un método parametrizable que recibiese un numero 
	 * y calculase hash para ese número de pares de transacciones
	 */
	private String getMerkleTreeRoot() {
		String firstHash = m.digest(
				getCurrentTransactions()[0].getHash().toString()
				.concat(getCurrentTransactions()[1].getHash().toString())
				.getBytes(StandardCharsets.UTF_8)).toString(); 
		String secondHash = m.digest(
				getCurrentTransactions()[2].getHash().toString()
				.concat(getCurrentTransactions()[3].getHash().toString())
				.getBytes(StandardCharsets.UTF_8)).toString();
		return m.digest(firstHash.concat(secondHash)
				.getBytes(StandardCharsets.UTF_8)).toString();
	}
	
	//Prueba de trabajo más sencilla, porque sólo lo estamos buscando nosotros
	//Se considera valido un hash que contenga la cadena 09
	private boolean validHash(String hash){
		if(hash.contains("09")) {
			return true;
		}
		return false;
	}
		
	
	//TODO: private int attempt = 1;
	private String proofOfWork() { 
		//attempt ++;
		Long timestamp = System.currentTimeMillis();
		String hash = m.digest(
				getMerkleTreeRoot()
				.concat(current.getHash())
				.concat(timestamp.toString())
				.concat(getNonceAsString())
				.getBytes(StandardCharsets.UTF_8)).toString();
		 
		if(validHash(hash)) {
			LogManager.write(Level.INFO, "Correct hash generated. A new block will be added");
			return hash;}		
		else {
			LogManager.write(Level.INFO, "Trying obtain a valid hash."); //Attempt:" + attempt);
			return proofOfWork();
		}
	}
	
	private Transaction[] getCurrentTransactions() {
		return currentTransactions;
	}
	
	private void setCurrentTransactions() {
		this.currentTransactions = new Transaction[4];
	}
	
	private Double getNonce() {
		return Math.random();
	}
	
	private String getNonceAsString() {
		return getNonce().toString();
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
	public String toString() {
		//TODO
		return "";
	}
	
	


}
