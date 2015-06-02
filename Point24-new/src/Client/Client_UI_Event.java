package Client;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client_UI_Event implements ActionListener , KeyListener  {

	 
	Client_UI_Event(Client_UI frame){
		
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

/*class ConnectionThread extends Thread{
	Socket socket;
	Client_UI frame;
	private BufferedReader in;  
	
	ConnectionThread(Socket socket,Client_UI frame){
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
					case '#' : //數字
						int[] arraynum=Format.NumberFormat(line);
						frame.myGame.questions=arraynum;
						frame.lblNewLabel.setText("");
						for(int i=0;i<=3;i++){
							frame.lblNewLabel.setText(frame.lblNewLabel.getText()+arraynum[i]+" ");
						}
						frame.lblNewLabel.setVisible(true);
						
						frame.myGame.intRound++;
						for(long i=1;i<=10000000;i++) ;
						frame.myGame.time=0;
	                    frame.myGame.SetTimer();
	                    for(int i=0;i<=frame.myGame.intRound-1;i++) frame.myGame.timer[i].cancel();
						frame.label.setVisible(true);
						break;
					case '!'://連接完成的訊號
						//JOptionPane.showMessageDialog(null, "連接完成。","Z" , JOptionPane.INFORMATION_MESSAGE);
						break;
					case '~':
						if (frame.lblNewLabel_1.getText().length()<65){
							frame.lblNewLabel_1.setText(frame.lblNewLabel_1.getText()+line+"   ");
						}else{
							frame.label_1_copy.setText(frame.label_1_copy.getText()+line+"   ");
						}
						break;
				}
		 
				if (line.equals("Quit")){
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