package blockchain;

public class Main {

	public static void main(String[] args) {
		Account a = new Account (10);
		Account b = new Account (15);
		Account c = new Account (20);
		
		a.send(b, 3);
		b.send(c, 6);
		c.send(a, 12);
		a.send(c, 7);
		
		a.send(b, 7);
		b.send(c, 8);
		c.send(a, 9);
		a.send(c, 10);
		
		Blockchain.getInstance().print();

	}

}
