package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import blockchain.Account;
import blockchain.Blockchain;

class FirstPrototypeTest {

	@Test
	void positiveTest() throws Exception {
		Blockchain blockchain = Blockchain.getInstance();
		Account a = new Account(100);
		Account b = new Account(100);
		Account c = new Account(100);
		Account d = new Account(100);
		Account e = new Account(100);
		Account f = new Account(100);
		Account g = new Account(100);
		
		a.send(b, 50);
		a.send(c, 20);
		a.send(d, 10);
		b.send(c, 50);
		assertEquals(blockchain.getNumberOfAddedTransactions(), 4);
		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
		assertEquals(blockchain.getNumberOfTotalTransactions(), 4);
		
		b.send(d, 20);
		b.send(e, 10);
		assertEquals(blockchain.getNumberOfAddedTransactions(), 4);
		assertEquals(blockchain.getNumberOfPendingTransactions(),2);
		assertEquals(blockchain.getNumberOfTotalTransactions(), 6);
		c.send(d, 50);
		c.send(e, 20);
		
		c.send(f, 10);
		d.send(g, 50);
		d.send(a, 20);
		d.send(b, 10);
		assertEquals(blockchain.getNumberOfAddedTransactions(), 12);
		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
		assertEquals(blockchain.getNumberOfTotalTransactions(), 12);
		
		assertEquals(a.getAmount(), 40);
		assertEquals(b.getAmount(), 80);
		assertEquals(c.getAmount(), 90);
		assertEquals(d.getAmount(), 100);
		assertEquals(e.getAmount(), 130);
		assertEquals(f.getAmount(), 110);
		assertEquals(g.getAmount(), 150);
		
		assertEquals(blockchain.getNumberOfBlocks(), 4);
		
		Account h = new Account(-100);
		
		h.send(b, 50);
		h.send(c, 20);
		
		assertEquals(blockchain.getNumberOfAddedTransactions(), 12);
		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
		assertEquals(blockchain.getNumberOfTotalTransactions(), 12);
		
		assertEquals(a.getAmount(), 40);
		assertEquals(b.getAmount(), 80);
		assertEquals(c.getAmount(), 90);
		assertEquals(d.getAmount(), 100);
		assertEquals(e.getAmount(), 130);
		assertEquals(f.getAmount(), 110);
		assertEquals(g.getAmount(), 150);
		
		assertEquals(blockchain.getNumberOfBlocks(), 4);
		
		a.send(b, 90);
		b.send(c, 90);
		c.send(h, 40);
		
		assertEquals(blockchain.getNumberOfAddedTransactions(), 12);
		assertEquals(blockchain.getNumberOfPendingTransactions(),0);
		assertEquals(blockchain.getNumberOfTotalTransactions(), 12);
		
		assertEquals(a.getAmount(), 40);
		assertEquals(b.getAmount(), 80);
		assertEquals(c.getAmount(), 90);
		assertEquals(d.getAmount(), 100);
		assertEquals(e.getAmount(), 130);
		assertEquals(f.getAmount(), 110);
		assertEquals(g.getAmount(), 150);
		
		assertEquals(blockchain.getNumberOfBlocks(), 4);
	}
}
