package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientMessageHandler extends Thread {
	Socket socket;
	Client_UI frame;
	private BufferedReader in;
	public String lastMsg="";
	public String lastMsgFlag="";
	public Client client;

	ClientMessageHandler(Client client,Socket socket, Client_UI frame) {
		this.socket = socket;
		this.frame = frame;
		this.client=client;
		start();

	}

	public void run() {
		try {
			// 下面就是傳說中的訊號處理部分
			while (true) {
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String line = in.readLine();

				switch (line.toCharArray()[0]) {
				case '#': // 數字，也就是开始讯号
					int[] arraynum = Format.NumberFormat(line);
					frame.myGame.questions = arraynum;
					frame.lblQuestion.setText("");
					for (int i = 0; i <= 3; i++) {
						frame.lblQuestion.setText(frame.lblQuestion.getText()
								+ arraynum[i] + " ");
					}
					frame.lblQuestion.setVisible(true);

					frame.myGame.intRound++;
					for (int i = 1; i <= 10000000; i++)
						;
					frame.myGame.time = 0;
					frame.myGame.SetTimer();
					for (int i = 0; i <= frame.myGame.intRound - 1; i++)
						frame.myGame.timer[i].cancel();
					frame.label.setVisible(true);

					// 隐藏ready
					frame.btnReady.setVisible(false);
					frame.lblQuestion.setVisible(true);
					frame.txtNumInput.setVisible(true);
					frame.btnSubmit.setVisible(true);
					break;
				case '!':// 連接完成的訊號
					// JOptionPane.showMessageDialog(null, "連接完成。","Z" ,
					// JOptionPane.INFORMATION_MESSAGE);
					break;
				case '~':// 文字消息符号
					// if (frame.lblNewLabel_1.getText().length()<65){
					// frame.lblNewLabel_1.setText(frame.lblNewLabel_1.getText()+line+"   ");
					// }else{
					// frame.label_1_copy.setText(frame.label_1_copy.getText()+line+"   ");
					// }
					frame.txtMessage.setText(line.substring(1) + "\r\n"
							+ frame.txtMessage.getText());
					break;
				case '-':// 在线人数

					Game.getInstance().onlineNum = Integer.parseInt(line
							.substring(1));
					frame.lblOnlineNum.setText("在线："
							+ Game.getInstance().onlineNum + "人");

				}
				
				//最后一个讯息
				lastMsg = line;
				lastMsgFlag = line.toCharArray()[0] + "";

				if (line.equals("Quit")) {
					break;
				} else if (line.equals("Ok")) {

				}
			}
			in.close();
			socket.close();
		} catch (IOException e) {
		}
	}
}