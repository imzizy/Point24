package Server;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Server_UI_Event  {
	Server_UI frame;
	Socket[] socket= new Socket[100];
	
	Server_UI_Event(Server_UI frame){
		this.frame=frame;
		
	}


}

/*class ConnectionThread extends Thread{
	Socket socket;
	Server_UI frame;
	private BufferedReader in;  
	private PrintWriter out;
	
	ConnectionThread(Socket socket,Server_UI frame){
		this.socket=socket;
		this.frame=frame;
		start();
		
	}
	public void run(){
		try{
			//下面就是傳說中的訊號處理部分
			while (true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = in.readLine();
				
				switch (line.toCharArray()[0]){
					case '/' :
//						System.out.print("Received");
						frame.textArea.setText(frame.textArea.getText()+line+"\r\n");
						
						break;
					case '!':
						frame.textArea.setText(frame.textArea.getText()+line+"\r\n");
						frame.myServer.SendMsgToAll("!Well");
						break;
					case '~': //這個大概就是成功的意思
						frame.myServer.SendMsgToAll(line);
						//JOptionPane.showMessageDialog(null, line,"Z" , JOptionPane.INFORMATION_MESSAGE);
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
}*/