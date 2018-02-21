package blockchain;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import product.Update;

public class Blockchain {
	
	private static final Integer TRANSACTIONS_PER_BLOCK = 4;
	private Integer currentTransactionsCounter;
	private Block last;
	private Transaction[] currentTransactions;
	
	
	
	
	public void newTransaction (String sender, String recipient, int amount, Update update) throws Exception {
		if(currentTransactionsCounter > TRANSACTIONS_PER_BLOCK) {throw new Exception ("SEVERE: The number of transactions is not allowed in this protocol");}
		if(currentTransactionsCounter.equals(TRANSACTIONS_PER_BLOCK)) {	addNewBlock(); }
		else {
			if (validTransaction(sender, amount)) {
				currentTransactions[currentTransactionsCounter - 1] = 
						new Transaction (sender, recipient, update, amount);
				currentTransactionsCounter ++;
			} else throw new Exception("SEVERE: The transaction is not valid");
		}
	}
	
	public void addNewBlock () throws NoSuchAlgorithmException {
		Block b = new Block(last.getHash(), getCurrentTransactions(), proofOfWork(), last.getIndex()+1);
		setCurrentTransactions();
		currentTransactionsCounter = 0;
		last = b;
	}
	
	
	/*Habría que hacer un método parametrizable que recibiese un numero 
	 * y calculase hash para ese número de pares de transacciones
	 */
	private String getMerkleTreeRoot() throws NoSuchAlgorithmException {
		String firstHash = MessageDigest.getInstance("SHA-256").digest(
				getCurrentTransactions()[0].getHash().toString()
				.concat(getCurrentTransactions()[1].getHash().toString())
				.getBytes(StandardCharsets.UTF_8)).toString(); 
		String secondHash = MessageDigest.getInstance("SHA-256").digest(
				getCurrentTransactions()[2].getHash().toString()
				.concat(getCurrentTransactions()[3].getHash().toString())
				.getBytes(StandardCharsets.UTF_8)).toString();
		return MessageDigest.getInstance("SHA-256").digest(firstHash.concat(secondHash)
				.getBytes(StandardCharsets.UTF_8)).toString();
	}
	
	//Prueba de trabajo más sencilla, porque sólo lo estamos buscando nosotros
	//Se considera valido un hash que contenga la cadena 09
	private boolean validHash(String hash) throws NoSuchAlgorithmException {
		if(hash.contains("09")) {
			return true;
		}
		return false;
	}
	
	//TODO: Pensar cómo implementar este método.
	private boolean validTransaction(String sender, int amount) {
		return true;
	}
	
	private String proofOfWork() throws NoSuchAlgorithmException {
		Long timestamp = System.currentTimeMillis();
		String hash = MessageDigest.getInstance("SHA-256").digest(
				getMerkleTreeRoot()
				.concat(last.getHash())
				.concat(timestamp.toString())
				.concat(getNonceAsString())
				.getBytes(StandardCharsets.UTF_8)).toString();
		 
		if(validHash(hash)) {return hash;}		
		else return proofOfWork();
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

}
