package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class Test_productservice {
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
	}

	// Test 1: product creation and deletion requests
	@Test
	public void testProductCreationAndDeletion() throws Exception {
		System.out.println("Test 1: product creation and deletion");
		
		String createProdReq = CommUtils.buildRequest("Test_productservice", "createProduct", "createProduct(Prod1, 2)", "productservice").toString();
		System.out.println("Product creation request: " + createProdReq);
		
		String createProdResp = connCS.request(createProdReq);
		System.out.println("Product creation reply: " + createProdResp);
	
		assertTrue(createProdResp.contains("1"));
		
		
		String deleteProdReq = CommUtils.buildRequest("Test_productservice", "createProduct", "deleteProduct(1)", "productservice").toString();
		System.out.println("Product creation request: " + deleteProdReq);
		
		String deleteProdResp = connCS.request(createProdReq);
		System.out.println("Product creation reply: " + deleteProdResp);
		
		assertTrue(createProdResp.contains("Prod1"));
	}
}
