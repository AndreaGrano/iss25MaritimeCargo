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
import java.util.*;

public class HoldWSServer extends WebSocketServer {
//	private Interaction qak_connection = ConnectionFactory.createClientSupport(ProtocolType.tcp, "localhost", ""+8003);
	private Interaction qak_connection = ConnectionFactory.createClientSupport(ProtocolType.tcp, "cargoservice", ""+8003); // in docker
	private List<WebSocket> websockets = new ArrayList<>();
	
	public HoldWSServer() {
		super(new InetSocketAddress("0.0.0.0", 8011));
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
			int slot = 0, PID = 0;
			double weight = 0;
			JSONObject reqBody = new JSONObject(message);
			CommUtils.outmagenta(message);
			if(reqBody.getString("type").compareTo("loadProduct") == 0) {
				try {
					PID = Integer.parseInt(reqBody.get("PID").toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				IApplMessage req = CommUtils.buildRequest("holdwsserver", "loadProduct", "loadProduct(" + PID + ")", "cargoservice");
				CommUtils.outmagenta("loadProduct(" + PID + ")");
				IApplMessage resp = this.qak_connection.request(req);
				if(resp.msgId().compareTo("loadAccepted") == 0) {
					CommUtils.outmagenta(resp.msgContent());
					try {
						String[] items = resp.msgContent().replaceAll("loadAccepted\\(", "").replaceAll("\\)", "").split(",");
						slot = Integer.parseInt(items[0]);
						PID = Integer.parseInt(items[1]);
						weight = Double.parseDouble(items[2]);
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch(ArrayIndexOutOfBoundsException e2) {
						CommUtils.outmagenta(resp.msgContent());
						e2.printStackTrace();
					}
					JSONObject element = (new JSONObject()).put("PID", PID).put("weight", weight);
					JSONObject respBody = (new JSONObject()).put(""+slot, element).put("type", "loadAccepted");
					CommUtils.outmagenta(respBody.toString());
					conn.send(respBody.toString());
				} else {
					CommUtils.outmagenta(resp.msgContent());
					String reason = resp.msgContent().replaceAll("loadRejected\\(", "").replaceAll("\\)", "");
					JSONObject respBody = (new JSONObject()).put("type", "loadRejected");
					switch (reason) {
					case "TOO_HEAVY":
						respBody = respBody.put("reason", "the product is too heavy");
						break;
					case "WEIGHT_ERROR":
						respBody = respBody.put("reason", "there is a weight error");
						break;
					case "NO_AVAILABLE_SLOT":
						respBody = respBody.put("reason", "there are no available slots");
						break;
					case "DOES_NOT_EXIST":
						respBody = respBody.put("reason", "the product does not exist");
						break;
					default:
						respBody = respBody.put("reason", "unknown");
						break;
					}
					CommUtils.outmagenta(respBody.toString());
					conn.send(respBody.toString());
				}
			} else if(reqBody.getString("type").compareTo("getHoldStatus") == 0) {
				IApplMessage req = CommUtils.buildRequest("holdwsserver", "getHoldStatus", "getHoldStatus(1)", "holdmanager");
				IApplMessage resp = this.qak_connection.request(req);
				String[] items = resp.msgContent().replaceAll("holdStatus\\(", "").replaceAll("\\)", "").split(",");
				int p1 = 0, p2 = 0, p3 = 0, p4 = 0;
				double w1 = 0, w2 = 0, w3 = 0, w4 = 0;
				try {
					p1 = Integer.parseInt(items[0]);
					w1 = Double.parseDouble(items[1]);
					p2 = Integer.parseInt(items[2]);
					w2 = Double.parseDouble(items[3]);
					p3 = Integer.parseInt(items[4]);
					w3 = Double.parseDouble(items[5]);
					p4 = Integer.parseInt(items[6]);
					w4 = Double.parseDouble(items[7]);
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch(ArrayIndexOutOfBoundsException e2) {
					CommUtils.outmagenta(resp.msgContent());
					e2.printStackTrace();
				}
				JSONObject respBody = (new JSONObject()).put("type", "holdStatus");
				JSONObject element = (new JSONObject()).put("PID", p1).put("weight", w1);
				respBody = respBody.put("1", element);
				element = (new JSONObject()).put("PID", p2).put("weight", w2);
				respBody = respBody.put("2", element);
				element = (new JSONObject()).put("PID", p3).put("weight", w3);
				respBody = respBody.put("3", element);
				element = (new JSONObject()).put("PID", p4).put("weight", w4);
				respBody = respBody.put("4", element);
				conn.send(respBody.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateHold(int slot, int PID, double weight) {
		JSONObject element = (new JSONObject()).put("PID", PID).put("weight", weight);
		JSONObject body = (new JSONObject()).put("type", "updateHold").put(""+slot, element);
		this.websockets.stream().forEach(w -> w.send(body.toString()));
	}
	
	public void haltCargo() {
		JSONObject body = (new JSONObject()).put("type", "haltCargo");
		CommUtils.outmagenta("halt cmd start");
		this.websockets.stream().forEach(w -> w.send(body.toString()));
		CommUtils.outmagenta("halt cmd end");
	}
	
	public void resumeCargo() {
		JSONObject body = (new JSONObject()).put("type", "resumeCargo");
		CommUtils.outmagenta("resume cmd start");
		this.websockets.stream().forEach(w -> w.send(body.toString()));
		CommUtils.outmagenta("resume cmd end");
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