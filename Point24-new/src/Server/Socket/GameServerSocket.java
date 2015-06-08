package Server.Socket;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

import Server.Controller.ServerController;

/*
 * 這個類別中是Server的主函數類其中主線程用於接收請求，
 * 
 * 
 */
public class GameServerSocket extends ServerSocket implements Runnable {
	GameServerSocket serverSocket;
	ServerController serverController;

	Socket[] socket = new Socket[1000];
	private PrintWriter out;

	public static int readyPlayerAmount = 0;

	public GameServerSocket(final ServerController serverController) throws IOException {
		super(43210);// Listen 10000 Pork
		this.serverController = serverController;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Socket tempSocket; // Temp socket
					while (true) {
						// 服務器一直等待用戶連接，其中accept()函數只有等到連接了才會往下繼續執行
						tempSocket = GameServerSocket.this.accept();
						int tempSocketId=serverController.getOnlineNum();
						GameServerSocket.this.socket[tempSocketId] = tempSocket;
						
						new ServerMessageHandler( tempSocketId).start();
						
						serverController.showMessage(GameServerSocket.this.socket[tempSocketId].getInetAddress().getHostAddress()+"Connected");
						
						serverController.setOnlineNum(serverController.getOnlineNum()+1);
		 			}
				} catch (IOException e) {
					e.printStackTrace();
				}

				
			}
		}).start();
		 
	}

	public void sendCommandToAll(String command) {

		try {
			for (int i = 0; i <= serverController.getOnlineNum() - 1; i++) {
				out = new PrintWriter(socket[i].getOutputStream(), true);
				out.println(command);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void sendCommandTo(String command, int id)   {

		try {
			out = new PrintWriter(socket[id].getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.println(command);
	}

	@Override
	public void run() {
	
	}
}

/*class ListenThread extends Thread {
	GameServerSocket server;

	public ListenThread(GameServerSocket server) {
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
				new ServerMessageHandler(server.socket[server.intPlayerAmount],
						server.frame, server.intPlayerAmount);

				server.frame.textArea.setText(server.frame.textArea.getText()
						+ server.socket[server.intPlayerAmount]
								.getInetAddress().getHostAddress()
						+ "Connected\r\n");
				server.intPlayerAmount++;
			}
		} catch (IOException e) {
		}
	}
}*/