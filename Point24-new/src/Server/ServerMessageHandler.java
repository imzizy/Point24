package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerMessageHandler extends Thread{
	Socket socket;
	Server_UI frame;
	int socketId;
	
	private BufferedReader in;  
	private PrintWriter out;
	
	public ServerMessageHandler(Socket socket,Server_UI frame,int id){
		this.socket=socket;
		this.frame=frame;
		this.socketId=id;
		
		start();
		
	}
	public void run(){
		try{
			//下面就是傳說中的訊號處理部分
			while (true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = in.readLine();
				if (line==null) {
					Server.getInstance().intPlayerAmount--;
					return;
				}
				switch (line.toCharArray()[0]){
					case '/' :
//						System.out.print("Received");
						frame.textArea.setText(frame.textArea.getText()+line+"\r\n");
						
						break;
					case '!':
						frame.textArea.setText(frame.textArea.getText()+line+"\r\n");
						frame.myServer.SendMsgToAll("!Well");
						break;
					case '~': //這個大概就是发送消息= =.
						frame.myServer.SendMsgToAll(line);
						//JOptionPane.showMessageDialog(null, line,"Z" , JOptionPane.INFORMATION_MESSAGE);
						break;
						
					case '-'://在线人数
						
						frame.myServer.SendMsgSpc("-"+frame.myServer.intPlayerAmount, socketId);
						break;
					case '*':
						//游戏开始
						Server_UI.startGame();
						break;
				}
				 
				
				if (line.equals("AllQuit")){
					break;
				}else if (line.equals("Ok")){
				 
				}
			}
			in.close();
			socket.close();
		} 
		catch (IOException e)
		{
		}
	}
}