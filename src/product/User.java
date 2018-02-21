package product;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class User {
	
	private byte[] address;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private int amount;
	
	
	public User (int amount) throws NoSuchAlgorithmException {
		KeyPair keyPair = KeyPairGenerator.getInstance("SHA-256").generateKeyPair();
		this.privateKey = keyPair.getPrivate();
		this.publicKey = keyPair.getPublic();
		this.address =  MessageDigest.getInstance("SHA-256").digest(this.publicKey.toString().getBytes(StandardCharsets.UTF_8)); 
		this.amount = amount;
	}

}

