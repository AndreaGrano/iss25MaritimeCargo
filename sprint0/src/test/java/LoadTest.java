package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class LoadTest {
	private final static String hostname= "localhost";
	private final static String portCS	= "8001";
	private final static String portDBW	= "8004";

	private Interaction connCS;
    private Interaction connDBW;
    
	@Before
	public void setUp() throws Exception {		
		try {
			connCS = ConnectionFactory.createClientSupport(ProtocolType.tcp, hostname, portCS);
			connDBW = ConnectionFactory.createClientSupport(ProtocolType.tcp, hostname, portDBW);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String createProdReq = CommUtils.buildDispatch("LoadTest", "createProduct", "createProduct(Prod1)", "LoadTest").toString();
		System.out.println("Richiesta creazione prodotto: " + createProdReq);
		
		String createProdResp = connDBW.request(createProdReq);
		System.out.println("Risposta creazione prodotto: " + createProdResp);
	}

	// Test 1: product exists and does NOT weigh too much
	@Test
	public void testLoadAccepted() throws Exception {
		String loadReq = CommUtils.buildDispatch("LoadTest", "loadProduct", "loadProduct(0, 50)", "LoadTest").toString();
		System.out.println("Richiesta carico prodotto: " + loadReq);
		
		String loadResp = connCS.request(loadReq);
		System.out.println("Risposta carico prodotto: " + loadResp);
	
		assertTrue(loadResp.contains("loadAccepted"));
	}

	// Test 2: product does NOT exist
	@Test
	public void testLoadRefusedNotFound() throws Exception {
		String loadReq = CommUtils.buildDispatch("LoadTest", "loadProduct", "loadProduct(5, 50)", "LoadTest").toString();
		System.out.println("Richiesta carico prodotto: " + loadReq);
		
		String loadResp = connCS.request(loadReq);
		System.out.println("Risposta carico prodotto: " + loadResp);
	
		assertTrue(loadResp.contains("loadRefused") && loadResp.contains("Not found"));
	}
	
	// Test 3: product exists, but it weighs too much
	@Test
	public void testLoadRefusedWeigth() throws Exception {
		String loadReq = CommUtils.buildDispatch("LoadTest", "loadProduct", "loadProduct(0, 150)", "LoadTest").toString();
		System.out.println("Richiesta carico prodotto: " + loadReq);
		
		String loadResp = connCS.request(loadReq);
		System.out.println("Risposta carico prodotto: " + loadResp);
	
		assertTrue(loadResp.contains("loadRefused") && loadResp.contains("Too heavy"));
	}
}
