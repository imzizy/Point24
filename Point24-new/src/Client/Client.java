package Client;

import java.awt.Frame;
import java.io.*;
import java.net.*;

public class Client {
	Socket socket;
	Client_UI frame;
	PrintWriter out;
	String ServerIP = "192.168.169.4";
	public ClientMessageHandler thread;
	Client(Client_UI frame) {
		this.frame = frame;

	}

	public void Connecting() throws UnknownHostException, IOException,
			ConnectException {
		socket = new Socket(ServerIP, 43210);
		thread = new ClientMessageHandler(this, socket, frame);
		SendMsg("!" + frame.txtUserName.getText()); // 連接後由客戶端發送名字
	}

	public void SendMsg(String msg) {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
