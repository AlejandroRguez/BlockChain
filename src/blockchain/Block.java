package blockchain;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
	
	 Long creationDate;
	 String previousBlockHash;
	 Transaction[] transactions = new Transaction[4];
	 String hash;
	 int index;
	
	 Block(String previousBlockHash, Transaction[] transactions, String hash, int index) throws NoSuchAlgorithmException {
		this.index = index;
		this.creationDate = System.currentTimeMillis();
		this.previousBlockHash = previousBlockHash;
		this.transactions = transactions;
		this.hash = hash;
	}
	 
	 String getHash() {return hash;}
	 int getIndex() {return index;}


}

