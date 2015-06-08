package Client.Socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.Controller.ClientController;

public class ClientSocket {
	Socket socket;
	ClientController game;
	PrintWriter out;
	private String serverIp = "localhost";
	public ClientMessageHandler clientMessageHandler;

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public ClientSocket(ClientController clientController) {
		this.game = clientController;

	}

	public void connect() throws UnknownHostException, IOException {
 			socket = new Socket(serverIp, 43210);
			clientMessageHandler = new ClientMessageHandler(this);
		 
 
		// SendMsg("!" + frame.txtUserName.getText()); // 連接後由客戶端發送名字
	}

	public void sendCommand(String msg) {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
