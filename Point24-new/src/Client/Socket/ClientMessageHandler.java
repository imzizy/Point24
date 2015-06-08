package Client.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.eclipse.swt.widgets.Display;

import Client.ClientForm;
import Client.Controller.ClientController;
import Public.Config;
import Public.Tool;

class ClientMessageHandler extends Thread {

	private Socket socket;
	private BufferedReader in;

	private String lastMsg = "";
	private String lastMsgFlag = "";

	private ClientController clientController;

	private ClientSocket clientSocket;
	private ClientForm gameForm;

	ClientMessageHandler(ClientSocket clientSocket) {
		this.clientController = ClientController.getInstance();

		this.clientSocket = clientSocket;

		this.socket = clientSocket.socket;

		start();

	}

	public void run() {
		try {
			// 下面就是傳說中的訊號處理部分
			while (true) {
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				final String raw = in.readLine();// 原始资料
				System.out.println(raw);
				String msgWithotFlag;// 去除标头的资料
				if (raw.length() == 1) {
					msgWithotFlag = "";
				} else {
					msgWithotFlag = raw.substring(1);
				}

				switch (raw.toCharArray()[0]) {
				case '#': // 數字，也就是开始讯号
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							clientController.startGame(raw);
						}
					});

					break;
				case '!':// 連接完成的訊號
					break;
				case Config.FLAG_TEXT_MESSAGE:// 文字消息符号
					clientController.showTextMessageLine(msgWithotFlag);
					break;
				case Config.FLAG_ONLINE_NUM:// 在线人数

					clientController.setOnlineNum(Integer
							.parseInt(msgWithotFlag));

					break;

				case Config.FLAG_GAME_READY:// 收到回覆的READY訊號
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							clientController.setReadyState(true);
						}
					});
					break;
				case Config.FLAG_GAME_STOP:
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							clientController.stopGame();
						}
					});
					break;
					
				}

				// System.out.println(line);
				// 最后一个讯息
				lastMsg = raw;
				lastMsgFlag = raw.toCharArray()[0] + "";

				if (raw.equals("Quit")) {
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