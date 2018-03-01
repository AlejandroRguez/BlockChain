package blockchain;

public class Block {
	
	 Long creationDate;
	 String previousBlockHash;
	 Transaction[] transactions = new Transaction[4];
	 String hash;
	 int index;
	 Block pointer;
	 String miner;
	
	 Block(String previousBlockHash, Transaction[] transactions, String hash, int index, String miner){
		this.index = index;
		this.creationDate = System.currentTimeMillis();
		this.previousBlockHash = previousBlockHash;
		this.transactions = transactions;
		this.hash = hash;
		this.miner = miner;
	}
	 
	 String getHash() {return hash;}
	 int getIndex() {return index;}
	 Transaction [] getTranscactions() {return transactions; }
	 
	@Override
	public String toString() {
		String s = "Block " + getIndex() + " -> Hash: " + getHash() + "\n";
		int i = 0;
		if(getTranscactions().length == 0) {
			s += "This block has no transactions"; 
			return s;
		}
		while (i < 4) {
			s += getTranscactions()[i].toString();
			i++;
		}
		return s;
	}
		


}

