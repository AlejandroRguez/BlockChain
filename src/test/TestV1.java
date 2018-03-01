package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import blockchain.Account;
import blockchain.Blockchain;
import utils.AccountCreator;

//Test unitarios para la primera versión de la blockchain
//Con la adición de nuevas funcionalidades dejan de funcionar

class TestV1 {

	@Test
	void positiveTest() throws Exception {
		Blockchain blockchain = Blockchain.getInstance();
		Account a = AccountCreator.create(100, "a");
		Account b = AccountCreator.create(100, "b");
		Account c = AccountCreator.create(100, "c");
		Account d = AccountCreator.create(100, "d");
		Account e = AccountCreator.create(100, "e");
		Account f = AccountCreator.create(100, "f");
		Account g = AccountCreator.create(100, "g");
		
		a.send(b, 50, 1);
		a.send(c, 20, 1);
		a.send(d, 10, 1);
		b.send(c, 50, 1);
		
//		assertEquals(blockchain.getNumberOfAddedTransactions(), 4);
//		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
//		assertEquals(blockchain.getNumberOfTotalTransactions(), 4);
		
		b.send(d, 20, 1);
		b.send(e, 10, 1);
		
//		assertEquals(blockchain.getNumberOfAddedTransactions(), 4);
//		assertEquals(blockchain.getNumberOfPendingTransactions(),2);
//		assertEquals(blockchain.getNumberOfTotalTransactions(), 6);
//		
		c.send(d, 50, 1);
		c.send(e, 20, 1);
		
		c.send(f, 10, 1);
		d.send(g, 50, 1);
		d.send(a, 20, 1);
		d.send(b, 10, 1);
		
//		assertEquals(blockchain.getNumberOfAddedTransactions(), 12);
//		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
//		assertEquals(blockchain.getNumberOfTotalTransactions(), 12);
//		
//		assertEquals(a.getAmount(), 40);
//		assertEquals(b.getAmount(), 80);
//		assertEquals(c.getAmount(), 90);
//		assertEquals(d.getAmount(), 100);
//		assertEquals(e.getAmount(), 130);
//		assertEquals(f.getAmount(), 110);
//		assertEquals(g.getAmount(), 150);
//		
//		assertEquals(blockchain.getNumberOfBlocks(), 4);
		
		Account h = AccountCreator.create(-100, "h");
		
		b.send(h, 50, 1);
		c.send(h, 20, 1);
		
//		assertEquals(blockchain.getNumberOfAddedTransactions(), 12);
//		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
//		assertEquals(blockchain.getNumberOfTotalTransactions(), 12);
		
//		assertEquals(a.getAmount(), 40);
//		assertEquals(b.getAmount(), 80);
//		assertEquals(c.getAmount(), 90);
//		assertEquals(d.getAmount(), 100);
//		assertEquals(e.getAmount(), 130);
//		assertEquals(f.getAmount(), 110);
//		assertEquals(g.getAmount(), 150);
//		
//		assertEquals(blockchain.getNumberOfBlocks(), 4);
		
		a.send(b, 90, 1);
		b.send(c, 90, 1);
		c.send(h, 40, 1);
		
//		assertEquals(blockchain.getNumberOfAddedTransactions(), 12);
//		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
//		assertEquals(blockchain.getNumberOfTotalTransactions(), 12);
//		
//		assertEquals(a.getAmount(), 40);
//		assertEquals(b.getAmount(), 80);
//		assertEquals(c.getAmount(), 90);
//		assertEquals(d.getAmount(), 100);
//		assertEquals(e.getAmount(), 130);
//		assertEquals(f.getAmount(), 110);
//		assertEquals(g.getAmount(), 150);
//		
		assertEquals(blockchain.getNumberOfBlocks(), 4);
		
		Account i = AccountCreator.create(100, "");
		
		b.send(i, 50, 1);
		c.send(i, 20, 1);
		
//		assertEquals(blockchain.getNumberOfAddedTransactions(), 12);
//		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
//		assertEquals(blockchain.getNumberOfTotalTransactions(), 12);
//		
//		assertEquals(a.getAmount(), 40);
//		assertEquals(b.getAmount(), 80);
//		assertEquals(c.getAmount(), 90);
//		assertEquals(d.getAmount(), 100);
//		assertEquals(e.getAmount(), 130);
//		assertEquals(f.getAmount(), 110);
//		assertEquals(g.getAmount(), 150);
//		
//		assertEquals(blockchain.getNumberOfBlocks(), 4);
		System.out.println(blockchain.getTotalTokens());
	}
}
