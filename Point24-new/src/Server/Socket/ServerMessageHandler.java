package Server.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Public.Config;
import Server.ServerForm;
import Server.Controller.ServerController;

class ServerMessageHandler extends Thread {
	GameServerSocket serverSocket;
	//ServerForm serverForm;
	int socketId;
	Socket socket;
	ServerController serverController;
	
	private BufferedReader in;
	private PrintWriter out;

	public ServerMessageHandler(int socketId) {
		this.serverController = ServerController.getInstance();
		this.serverSocket=serverController.getServerSocket();
		
		this.socketId=socketId;
		
		this.socket=serverSocket.socket[socketId];
 

	}

	public void run() {
		try {
			// 下面就是傳說中的訊號處理部分
			while (true) {
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String raw = in.readLine();
				
				System.out.println(raw);
				if (raw == null) {
					serverController.setOnlineNum(serverController.getOnlineNum()-1);
					return;
				}
				
				String msgWithoutFlag = null;
				if (raw.length()==1) {
					msgWithoutFlag="";
				}else{
					msgWithoutFlag=raw.substring(1);
				}
				
				
				switch (raw.toCharArray()[0]) {
				case '/':
					 
					serverController.showMessage(msgWithoutFlag);
					break;
				case '!':
					serverController.showMessage(raw); 
					serverController.sendCommandToAll("!Well");
					 
					break;
				case Config.FLAG_TEXT_MESSAGE: // 這個大概就是发送消息= =.
					serverController.sendCommandToAll(raw); 
					break;

				case Config.FLAG_ONLINE_NUM:// 在线人数
					serverController.sendCommandTo(socketId, "-"+serverController.getOnlineNum());					
					break;
				case 'r':
					// ready
					synchronized (this) {
						serverController.setReadyNum(serverController.getReadyNum()+1);
					}
					
					System.out.println(serverController.getReadyNum()+"/"+serverController.getOnlineNum());
					serverController.sendCommandTo(socketId, ""+Config.FLAG_GAME_READY);
					
					//TODO
					if (serverController.getReadyNum()==serverController.getOnlineNum()) {
						serverController.startGame();
					}
					
					
					 	break;

				case '*':
					// 游戏开始
					// TODO 如果大家都准备好了
					if (serverController.getReadyNum()==serverController.getOnlineNum()-1) {
						serverController.startGame();
					}
					break;
				case Config.FLAG_GAME_STOP:
					serverController.sendCommandToAll(""+Config.FLAG_GAME_STOP);
					break;
				}

				if (raw.equals("AllQuit")) {
					break;
				} else if (raw.equals("Ok")) {

				}
			}
			in.close();
			socket.close();
		} catch (IOException e) {
		}
	}
}