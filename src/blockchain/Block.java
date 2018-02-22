package blockchain;

public class Block {
	
	 Long creationDate;
	 String previousBlockHash;
	 Transaction[] transactions = new Transaction[4];
	 String hash;
	 int index;
	 Block pointer;
	
	 Block(String previousBlockHash, Transaction[] transactions, String hash, int index){
		this.index = index;
		this.creationDate = System.currentTimeMillis();
		this.previousBlockHash = previousBlockHash;
		this.transactions = transactions;
		this.hash = hash;
	}
	 
	 String getHash() {return hash;}
	 int getIndex() {return index;}
	 
		@Override
		public String toString() {
			//TODO
			return "";
		}
		


}

