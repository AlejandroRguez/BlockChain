package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import blockchain.Blockchain;
import blockchain.OwnerAccount;
import blockchain.UserAccount;
import utils.AccountCreator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestV2 {

	@Test
	void t1_AccountCreationTest() {
		//1er Bloque
		Blockchain bk = Blockchain.getInstance();
		OwnerAccount oa = bk.getOwner();
		
		assertEquals(oa.getAmount(), 1000);
		
		//2º Bloque
		UserAccount a = AccountCreator.create(100, "Cuenta A");
		UserAccount b = AccountCreator.create(100, "Cuenta B");
		UserAccount c = AccountCreator.create(400, "Cuenta C");
		UserAccount d = AccountCreator.create(300, "Cuenta D");
		
		assertEquals(a.getAmount(), 99);
		assertEquals(b.getAmount(), 99);
		assertEquals(c.getAmount(), 399);
		assertEquals(d.getAmount(), 299);
		assertEquals(oa.getAmount(), 114);
		
		assertEquals(bk.getNumberOfAddedTransactions(), 4);
		assertEquals(bk.getNumberOfPendingTransactions(), 0);
		assertEquals(bk.getNumberOfTotalTransactions(), 4);
		assertEquals(bk.getNumberOfBlocks(), 2);
		
		//3er Bloque
		UserAccount e = AccountCreator.buy(c,100, "Cuenta E");
		UserAccount f = AccountCreator.buy(d,100, "Cuenta F");
		
		assertEquals(c.getAmount(), 299);
		assertEquals(d.getAmount(), 209);
		assertEquals(e.getAmount(), 98);
		assertEquals(f.getAmount(), 98);
		assertEquals(oa.getAmount(), 118);
		
		assertEquals(bk.getNumberOfAddedTransactions(), 8);
		assertEquals(bk.getNumberOfPendingTransactions(), 0);
		assertEquals(bk.getNumberOfTotalTransactions(), 8);
		assertEquals(bk.getNumberOfBlocks(), 3);
		
		assertEquals(oa.getAmount(), 118);
		assertEquals(bk.getAccounts().size(), 6);
		
		a.send(b, 10, 1);
		a.send(b, 10, 1);
		a.send(b, 10, 1);
		b.send(c, 10, 1);
		
		assertEquals(a.getAmount(),66);
		assertEquals(b.getAmount(), 132);
		assertEquals(c.getAmount(), 309);
		
		//5º Bloque
		b.send(c, 10, 1);
		b.send(c, 10, 1);
		b.send(c, 10, 1);
		c.send(d, 10, 1);
		
		assertEquals(b.getAmount(),99);
		assertEquals(c.getAmount(),342);
		assertEquals(d.getAmount(),219);
		
		//6º Bloque
		c.send(d, 10, 1);
		c.send(d, 10, 1);
		c.send(d, 10, 1);
		d.send(e, 10, 1);
		
		assertEquals(c.getAmount(),309);
		assertEquals(d.getAmount(),252);
		assertEquals(e.getAmount(),108);
		
		//7º Bloque
		d.send(e, 10, 1);
		d.send(e, 10, 1);
		d.send(e, 10, 1);
		e.send(f, 10, 1);
		
		assertEquals(d.getAmount(),219);
		assertEquals(e.getAmount(),141);
		assertEquals(f.getAmount(),108);
		
		//8º Bloque
		e.send(f, 10, 1);
		e.send(f, 10, 1);
		e.send(f, 10, 1);
		f.send(a, 10, 1);
		
		assertEquals(e.getAmount(), 108);
		assertEquals(f.getAmount(), 141);
		assertEquals(a.getAmount(), 76);
		
		//9º Bloque
		f.send(a, 10, 1);
		f.send(a, 10, 1);
		f.send(a, 10, 1);
		a.send(b, 10, 1);
		
		assertEquals(f.getAmount(), 108);
		assertEquals(a.getAmount(), 109);
		assertEquals(b.getAmount(), 109);
				
		a.send(b, 10, 1);
		a.send(b, 10, 1);
		a.send(b, 10, 1);
		
		assertEquals(a.getAmount(), 109);
		assertEquals(b.getAmount(), 109);
		
		assertEquals(bk.getNumberOfAddedTransactions(), 32);
		assertEquals(bk.getNumberOfPendingTransactions(), 3);
		assertEquals(bk.getNumberOfTotalTransactions(), 35);
		assertEquals(bk.getNumberOfBlocks(), 9);	
		
		assertEquals(oa.getAmount(), 118);
		assertEquals(bk.getProofOfWork(), "09");
		
		b.send(c, 10, 1);
		
		assertEquals(a.getAmount(), 76);
		assertEquals(b.getAmount(), 142);
		assertEquals(c.getAmount(), 319);
		
		assertEquals(oa.getAmount(), 1118);
		assertEquals(bk.getProofOfWork(), "000");
		
		UserAccount h = AccountCreator.create(-2, "");
		UserAccount i = AccountCreator.create(5, "");
		UserAccount j = AccountCreator.create(-2, "Espartaco");
		
		assertEquals(bk.getAccounts().size(), 6);
		
		a.send(h, -10, 1);
		a.send(i, -10, 1);
		a.send(j, -10, 1);
		
		assertEquals(bk.getNumberOfAddedTransactions(), 36);
		assertEquals(bk.getNumberOfPendingTransactions(), 0);
		assertEquals(bk.getNumberOfTotalTransactions(), 36);
		assertEquals(bk.getNumberOfBlocks(), 10);	
		assertEquals(bk.getTotalTokens(), 2090);
		double price = bk.getCurrentPrice();
		
		assertEquals(bk.getMarketCapitalization(), 2090*price);
		
		
		
		
		
	}

}
