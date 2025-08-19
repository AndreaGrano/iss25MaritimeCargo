package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

//import main.java.dbdriver.Product;
import main.java.dbdriver.Wrapper;

public class Test_dbdriver {
	private Wrapper dbWrapper;

	@Before
	public void setUp() throws Exception {		
		this.dbWrapper = new Wrapper();
	}
	
//	@Test
//	public void createTest() {
//		Product baccaliegia = dbWrapper.createProduct("Baccaliegia", 80.2);
//		
//		assertEquals(baccaliegia.getName(), "Baccaliegia");
//		assertTrue(baccaliegia.getWeight() == 80.2);
//	}

	@Test
	public void avilableSlotTest() {
		int slot = dbWrapper.getFirstAvailableSlot();
		assertTrue(slot == 1);
	}
}
