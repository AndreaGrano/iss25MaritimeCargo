package main.java;

import org.json.JSONObject;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ProductWSServer extends WebSocketServer {
//	private Interaction qak_connection = ConnectionFactory.createClientSupport(ProtocolType.tcp, "localhost", ""+8001);
	private Interaction qak_connection = ConnectionFactory.createClientSupport(ProtocolType.tcp, "productservice", ""+8001); // in docker
	private List<WebSocket> websockets = new ArrayList<>();
	
	public ProductWSServer() {
		super(new InetSocketAddress("0.0.0.0", 8013));
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		CommUtils.outblue("New connection!");
		this.websockets.add(conn);
		
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		CommUtils.outblue("Connection closed");
		this.websockets.remove(conn);
		
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		try {
			JSONObject reqBody = new JSONObject(message);
			String reqType = reqBody.getString("type");
			IApplMessage req = CommUtils.buildEvent("productwsserver", "mock", "mock(1)");
			if(reqType.compareTo("createProduct") == 0)
				req = this.buildCreateRequest(reqBody);
			else if(reqType.compareTo("deleteProduct") == 0)
				req = this.buildDeleteRequest(reqBody);
			IApplMessage resp = this.qak_connection.request(req);
			JSONObject respBody = new JSONObject();
			if(resp.msgId().compareTo("createdProduct") == 0) {
				respBody = this.buildCreateReply(resp);
			}
			else if(resp.msgId().compareTo("deletedProduct") == 0) {
				respBody = this.buildDeleteReply(resp);
			}
			conn.send(respBody.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public IApplMessage buildCreateRequest(JSONObject reqBody) {
		double weight = 0;
		String name = "";
		try {
			weight = Double.parseDouble(reqBody.get("weight").toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		name = reqBody.getString("name");
		IApplMessage req = CommUtils.buildRequest("productwsserver", "createProduct", "createProduct(" + name + "," + weight + ")", "productservice");
		return req;
	}
	
	public IApplMessage buildDeleteRequest(JSONObject reqBody) {
		int PID = 0;
		try {
			PID = Integer.parseInt(reqBody.get("PID").toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		IApplMessage req = CommUtils.buildRequest("productwsserver", "deleteProduct", "deleteProduct(" + PID + ")", "productservice");
		return req;
	}
	
	public JSONObject buildCreateReply(IApplMessage replBody) {
		int PID = 0;
		boolean repl = false;
		try {
			PID = Integer.parseInt(replBody.msgContent().replaceAll("createdProduct\\(", "").replaceAll("\\)", ""));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		if(PID < 0) repl = false;
		else repl = true;
		JSONObject respBody = (new JSONObject()).put("type", "createdProduct").put("response", repl);
		if(repl) {
			respBody.put("PID", PID);
		}
		return respBody;
	}
	
	public JSONObject buildDeleteReply(IApplMessage replBody) {
		String name = "";
		double weight = 0;
		boolean repl = false;
		try {
			String[] items = replBody.msgContent().replaceAll("deletedProduct\\(", "").replaceAll("\\)", "").split(",");
			name = items[0];
			weight = Double.parseDouble(items[1]);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch(ArrayIndexOutOfBoundsException e2) {
			CommUtils.outmagenta(replBody.msgContent());
			e2.printStackTrace();
		}
		if (name.compareTo("NOT_FOUND") == 0 || weight == 0) repl = false;
		else repl = true;
		JSONObject respBody = (new JSONObject()).put("type", "deletedProduct").put("response", repl);
		return respBody;
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		CommUtils.outblue(ex.getMessage());
		
	}

	@Override
	public void onStart() {
		CommUtils.outblue("Server started successfully!");
	}
}
