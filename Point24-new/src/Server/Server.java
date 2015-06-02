package Server;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

/*
 * 這個類別中是Server的主函數類其中主線程用於接收請求，
 * 然後啟動UI_Event線程來控制介面
 * 在UI的構造函數中建立一個本類別的實體，這樣子就傳入了Frame.
 * 
 */
public class Server extends ServerSocket implements Runnable {
	ServerSocket server;

	public int intPlayerAmount = 0;
	Server_UI frame;
	Socket[] socket = new Socket[100];
	private PrintWriter out;

	public static Server instance;

	public static Server getInstance() {
		return instance;
	}

	public Server(Server_UI frame) throws IOException {
		super(43210);// Listen 10000 Pork
		this.frame = frame;

		Server.instance = this;

		new ListenThread(this);
	}

	public Server(ServerSocket server) throws IOException {
		this.server = server;

	}

	public void SendMsgToAll(String msg)   {
		
		try {
			for (int i = 0; i <= intPlayerAmount - 1; i++) {
				out = new PrintWriter(socket[i].getOutputStream(), true);

				out.println(msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SendMsgSpc(String msg, int id) throws IOException {

		out = new PrintWriter(socket[id].getOutputStream(), true);

		out.println(msg);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}

class ListenThread extends Thread {
	Server server;

	public ListenThread(Server server) {
		// TODO Auto-generated constructor stub
		this.server = server;

		start();
	}

	public void run() {
		try {
			Socket tempSocket; // Temp socket
			while (true) {
				// 服務器一直等待用戶連接，其中accept()函數只有等到連接了才會往下繼續執行
				tempSocket = this.server.accept();
				server.socket[server.intPlayerAmount] = tempSocket;
				new ServerMessageHandler(
						server.socket[server.intPlayerAmount], server.frame,server.intPlayerAmount);

				server.frame.textArea.setText(server.frame.textArea.getText()
						+ server.socket[server.intPlayerAmount]
								.getInetAddress().getHostAddress()
						+ "Connected\r\n");
				server.intPlayerAmount++;
			}
		} catch (IOException e) {
		}
	}
}