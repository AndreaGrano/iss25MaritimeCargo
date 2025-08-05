package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class Test_cargoservice {
	private final static String hostname= "localhost";
	private final static String port	= "8001";

	private Interaction connCS;
    
	@Before
	public void setUp() throws Exception {		
		try {
			connCS = ConnectionFactory.createClientSupport(ProtocolType.tcp, hostname, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		String createProdReq = CommUtils.buildRequest("Test_cargoservice", "createProduct", "createProduct(Prod1, 2)", "productservice").toString();
//		System.out.println("Product creation request: " + createProdReq);
//		
//		String createProdResp = connCS.request(createProdReq);
//		System.out.println("Product creation reply: " + createProdResp);
	}

	// Test 1: product is registered in the DB, the added product's wieght does not result in the hold's weight exceeding MAX_WEIGHT and there is an available slot
	@Test
	public void testLoadAccepted() throws Exception {
		System.out.println("Test 1: load request accepted");
		
		String loadReq = CommUtils.buildRequest("Test_cargoservice", "loadProduct", "loadProduct(1752617792)", "cargoservice").toString();
		System.out.println("Load request: " + loadReq);
		
		String loadResp = connCS.request(loadReq);
		System.out.println("Reply: " + loadResp);
	
		assertTrue(loadResp.contains("loadAccepted"));
	}

	// Test 2: product is not registered in the DB yet
	@Test
	public void testLoadRefusedNotFound() throws Exception {
		System.out.println("Test 2: product is not registered in the DB yet");
		
		String loadReq = CommUtils.buildRequest("Test_cargoservice", "loadProduct", "loadProduct(5)", "cargoservice").toString();
		System.out.println("Load request: " + loadReq);
		
		String loadResp = connCS.request(loadReq);
		System.out.println("Reply: " + loadResp);
	
		assertTrue(loadResp.contains("loadRefused") && loadResp.contains("Not found"));
	}
	
	// Test 3: product weighs too much
	@Test
	public void testLoadRefusedWeigth() throws Exception {
		System.out.println("Test 3: product weighs too much");
		
		String loadReq = CommUtils.buildRequest("Test_cargoservice", "loadProduct", "loadProduct(1752616814)", "cargoservice").toString();
		System.out.println("Load request: " + loadReq);
		
		String loadResp = connCS.request(loadReq);
		System.out.println("Reply: " + loadResp);
	
		assertTrue(loadResp.contains("loadRefused") && loadResp.contains("Too heavy"));
	}
	
	// Test 4: no available slot
	@Test
	public void testLoadRefusedNoAvailableSlot() throws Exception {
		System.out.println("Test 4: no available slot");
		
		String loadReq = CommUtils.buildRequest("Test_cargoservice", "loadProduct", "loadProduct(1752617792)", "cargoservice").toString();
		System.out.println("Load request: " + loadReq);
			
		String loadResp = connCS.request(loadReq);
		System.out.println("First reply: " + loadResp);
		
		loadResp = connCS.request(loadReq);
		System.out.println("Second reply: " + loadResp);
		
		loadResp = connCS.request(loadReq);
		System.out.println("Third reply: " + loadResp);
		
		loadResp = connCS.request(loadReq);
		System.out.println("Fourth reply: " + loadResp);
		
		loadResp = connCS.request(loadReq);
		System.out.println("Fifth reply: " + loadResp);
		
		assertTrue(loadResp.contains("loadRefused") && loadResp.contains("No available slot"));
	}
}
