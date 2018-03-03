package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import blockchain.UserAccount;
import logging.LogManager;

public class Blockchain{
	
	private static final Integer TRANSACTIONS_PER_BLOCK = 4;
	private static final Integer COINBASE_TRANSACTION_AMOUNT = 10;
	private static final Blockchain bk = new Blockchain();
	
	
	private List<Double> prices = new ArrayList<Double>();
	private double currentPrice = 30;
	private String securityPass = "09";
	private Integer currentTransactionsCounter = 0;
	private Block current;
	private Transaction[] currentTransactions = new Transaction[TRANSACTIONS_PER_BLOCK];
	private MessageDigest m = getMessageDigest();
	
	private List<UserAccount> accounts = new ArrayList<UserAccount>();
	private OwnerAccount owner;
			
	private Blockchain() {
		owner = new OwnerAccount(1000);
		this.current = new Block("", new Transaction[0] , "GenesisBlock", 0, "Owner");
	}
	
	public static Blockchain getInstance() {
		return bk;
	}
	
	public void addNewTransaction(Transaction t) {
		if(currentTransactionsCounter >= TRANSACTIONS_PER_BLOCK) {
			LogManager.write(Level.SEVERE, "No more transactions allowed in current block");
			return;
		}
		this.currentTransactions[currentTransactionsCounter] = t;
		currentTransactionsCounter ++;
		
		if(currentTransactionsCounter.equals(TRANSACTIONS_PER_BLOCK)) {
			//Generar número aleatorio y buscar en la lista de UserAccounts
				executeTransactions(t.getSender()); 
				addNewBlock(t.getSender());
			}
	}
	
	private void executeTransactions(Account miner) {
		int i = 0;
		while (i < currentTransactionsCounter) {
			currentTransactions[i].execute(miner);
			i ++;
		}
	}
	
	private void addNewBlock (Account miner){
		LogManager.write(Level.INFO, "Trying to obtain a valid hash..........");
		Block b = new Block(current.getHash(), getCurrentTransactions(), proofOfWork(), current.getIndex()+1, miner.getAddress());
		LogManager.write(Level.INFO, "Block number " + b.getIndex() + " created correctly");
		b.pointer = current;
		current = b;
		LogManager.write(Level.INFO, "Actual Block: " + current.getIndex());
		setCurrentTransactions();
		LogManager.write(Level.INFO, "Next block: " + (current.getIndex()+1)
				+ " Number of pending transactions: " + currentTransactionsCounter);
		executeCoinbase(miner);
		generateTokens();
		balanceProofOfWork();
	}
	
	private void executeCoinbase(Account miner) {
		miner.setAmount(miner.getAmount() + COINBASE_TRANSACTION_AMOUNT);;
	}
	
	private void generateTokens() {
		if(getNumberOfBlocks()%10 == 0) {
			owner.setAmount(owner.getAmount() + 1000);
			generatePrice();
		}
	}
	

	/* Habría que hacer un método parametrizable que recibiese un numero 
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
	
	
	private boolean validHash(String hash){
		if(hash.contains(securityPass)) {
			return true;
		}
		return false;
	}
		
	
	private int attempt = 1;
	
	private String proofOfWork() {
		Long timestamp = System.currentTimeMillis();
		String hash = m.digest(
				getMerkleTreeRoot()
				.concat(current.getHash())
				.concat(timestamp.toString())
				.concat(getNonceAsString())
				.getBytes(StandardCharsets.UTF_8)).toString();
		 
		if(validHash(hash)) {
			LogManager.write(Level.INFO, "Correct hash generated. Attempt -> " + attempt +  " A new block will be added");
			attempt = 1;
			return hash;}		
		else {
			attempt ++;
			return proofOfWork();
		}
	}
	
	private void balanceProofOfWork() {
		if(getNumberOfBlocks() % 10 == 0) {
			setSecurityPass("000");
		}
		else if(getNumberOfBlocks() % 5 == 0) {
			setSecurityPass("09");
		}		
	}
	
	private void setSecurityPass(String pass) {
		this.securityPass = pass;
	}
	
	private Transaction[] getCurrentTransactions() {
		return currentTransactions;
	}
	
	private void setCurrentTransactions() {
		currentTransactionsCounter = 0;
		this.currentTransactions = new Transaction[TRANSACTIONS_PER_BLOCK];
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


	public void print() {
		System.out.println("-------BLOCKCHAIN----------");
		Block b = current;
		while (b.pointer != null) {
			System.out.println(b.toString());
			System.out.println("///////////////////////////////////");
			b = b.pointer;
		}
		System.out.println(b.toString());
		System.out.println("///////////////////////////////////");
		
	}
	
	public List<UserAccount> getAccounts(){
		return accounts;
	}
	
	public int getNumberOfBlocks() {return current.getIndex() + 1;	}
	
	public double getCurrentPrice() { return currentPrice;	}
	
	private void setCurrentPrice(double price) { this.currentPrice = price; }
	
	private void generatePrice() { 
		setCurrentPrice(new Random().nextDouble()*(1.5-0.5) + 0.5);
		prices.add(getCurrentPrice()); 
	}
	
	public String getProofOfWork() {
		return securityPass;
	}
	
	private int generateMinerNumber() {
		return new Random().nextInt((accounts.size() - 0) + 1) + 0;
	}
	
	public int getNumberOfAddedTransactions() {return current.getIndex() * TRANSACTIONS_PER_BLOCK;}
	
	public int getNumberOfTotalTransactions() {return getNumberOfAddedTransactions() + getNumberOfPendingTransactions();}
	
	public int getNumberOfPendingTransactions() {return currentTransactionsCounter;}
	
	public double getTotalTokens() {return accounts.stream().mapToDouble(a -> a.getAmount()).sum() + owner.getAmount();}
	
	public double getMarketCapitalization() {return getTotalTokens() * getCurrentPrice();}
	
	public OwnerAccount getOwner() {return owner;}

	public void addAccount(UserAccount a) {this.accounts.add(a);}
	
	


}
