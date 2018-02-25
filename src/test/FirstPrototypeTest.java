package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import blockchain.Account;
import blockchain.Blockchain;

class FirstPrototypeTest {

	@Test
	void positiveTest() {
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
		b.send(d, 20);
		b.send(e, 10);
		
		c.send(d, 50);
		c.send(e, 20);
		c.send(f, 10);
		
		d.send(g, 50);
		d.send(a, 20);
		d.send(b, 10);
		
		assertEquals(a.getAmount(), 40);
		assertEquals(b.getAmount(), 80);
		assertEquals(c.getAmount(), 90);
		assertEquals(d.getAmount(), 100);
		assertEquals(e.getAmount(), 130);
		assertEquals(f.getAmount(), 110);
		assertEquals(g.getAmount(), 150);
		
		assertEquals(Blockchain.getInstance().getNumberOfBlocks(), 4);		
	}
	
	//TODO: Test negativos, crear cuentas y transacciones inválidas

}
