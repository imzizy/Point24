package Client.Controller;

import java.awt.Frame;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import Client.ClientForm;
import Client.Socket.ClientSocket;
import Public.Config;
import Public.Point24Math;
import Public.Tool;

/**
 * @author Zizy
 *
 */
public class ClientController {
	private static ClientController instance; // 单例

	// int intRound = 0;//当前第几次游戏
	private int[] question = new int[4];// 题目

	private int onlineNum = 0;// 当前在线的人数
	private double startTime = 0;// 计时器的时间
	private long beginTime = 0;

	private ClientSocket clientSocket;// socket
	private String nickName = "";

	private Timer gameTimer;

	private boolean isTimerSet = false;// 计时器是否打开
	private boolean isGameStart = false;// 游戏是否开始
	private boolean isHost = false;// 是否是房主
	private boolean isWin=false;
	
	private boolean isConnected = false;

	// double time = 0;

	
	
	/**
	 * 得到游戏Instance
	 * 
	 * @return
	 */
	public static ClientController getInstance() {
		if (instance == null) {
			instance = new ClientController();
			return instance;
		} else {
			return instance;

		}

	}
	
 
	public Timer getGameTimer() {
		return gameTimer;
	}

	public double getGameTime() {
		return startTime;
	}

	public void setGameTime(double gameTime) {
		this.startTime = gameTime;
	}

	public void setTime(double time) {
		this.startTime = time;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	/**
	 * 設置并显示在线人数
	 * 
	 * @param onlineNum
	 *            ：當前在線人數
	 */
	public void setOnlineNum(final int onlineNum) {
		this.onlineNum = onlineNum;

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				ClientForm.lblOnlineNum.setText("在線人數：" + onlineNum + "");
			}
		});

	}

	public void setQuestion(int[] question) {
		this.question = question;
	}

	public int[] getQuestion() {
		return question;
	}

	public int getOnlineNum() {
		return onlineNum;
	}

	public double getTime() {
		return startTime;
	}

	public boolean isTimerSet() {
		return isTimerSet;
	}

	public boolean isGameStart() {
		return isGameStart;
	}

	public boolean isHost() {
		return isHost;
	}

	private ClientController() {
		clientSocket = new ClientSocket(this);
	}

	/**
	 * 发送普通信息
	 * 
	 * @param msg
	 *            ：信息
	 */
	public void sendTextMessage(String msg) {
		this.sendCommand(Config.FLAG_TEXT_MESSAGE + msg);
	}

	/**
	 * 发送原始命令
	 * 
	 * @param command
	 *            ：命令
	 */
	public void sendCommand(String command) {
		this.clientSocket.sendCommand(command);
	}

	/**
	 * 发送原始命令
	 * 
	 * @param command
	 *            ：命令
	 */
	public void sendCommand(char command) {
		this.clientSocket.sendCommand(command + "");
	}

	/**
	 * 在窗体中显示一条文字消息，并自动换行
	 * 
	 * @param msg
	 */
	public void showTextMessageLine(final String msg) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {

				ClientForm.txtOutputMsg.setText(msg + "\r\n"
						+ ClientForm.txtOutputMsg.getText());

			}
		});

	}

	/**
	 * 設置準備的狀態同時設置準備按鈕的可見性
	 * 
	 * @param isready
	 */
	public void setReadyState(boolean isready) {
		ClientForm.btnReady.setVisible(!isready);
	}

	public boolean connectServer(String nickName, String ip) {
		if (nickName.equals("") || nickName.length() < 2
				|| nickName.length() > 5) {
			Tool.alert("名字不可以少于2个字符或大于5个字符", null);
			return false;
		}
		try {
			if (!ip.equals("")) {
				this.clientSocket.setServerIp(ip);
			}

			this.clientSocket.connect();
			this.isConnected = true;
			this.showTextMessageLine("连线完成！");
			this.nickName = nickName;

			// TODO

			/*
			 * button.setEnabled(false); nickName.setEnabled(false);
			 * txtIp.setEnabled(false);
			 */

			new Thread(new Runnable() {

				@Override
				public void run() {
					// 得到当前在线人数
					sendCommand(Config.FLAG_ONLINE_NUM);

					while (ClientController.this.getOnlineNum() == 0)
						;

					int onlineNum = ClientController.this.onlineNum;

					// TODO
					/*
					 * btnReady.setVisible(true); if (onlineNum==1) { //我是host
					 * btnReady.setText("开始！"); Game.getInstance().isHost=true;
					 * }else{ //我是guest btnReady.setText("准备！");
					 * Game.getInstance().isHost=false;
					 * 
					 * }
					 */

				}
			}).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tool.alert("連接錯誤。", null);
			return false;
		}

		return true;

	}

	public void startGame(String raw) {

		int[] arraynum = Tool.NumberFormat(raw);
		ClientController.this.setQuestion(arraynum);

		ClientForm.lblQuestion.setText("");

		for (int i = 0; i <= 3; i++) {
			ClientForm.lblQuestion.setText(ClientForm.lblQuestion.getText()
					+ arraynum[i] + " ");
		}
		ClientForm.lblQuestion.setVisible(true);

		// gameForm.myGame.intRound++;
		for (int i = 1; i <= 10000000; i++)
			;
		ClientController.this.setTime(0);

		if (gameTimer != null) {
			try {
				gameTimer.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		gameTimer = new Timer();
		ClientController.this.startTime = 0;
		beginTime = System.currentTimeMillis();
		gameTimer.schedule(new TimerTask() {

			@Override
			public void run() {

				long endTime = System.currentTimeMillis();
				long costTime = (endTime - beginTime);
				beginTime = System.currentTimeMillis();
				ClientController.this.startTime += costTime / 1000f;
				Display.getDefault().syncExec(new Runnable() {

					@Override
					public void run() {
						double tempTime=(int) (ClientController.this.startTime * 100) / 100d;
						if (tempTime>Config.GAME_MAX_TIME) {
							ClientController.this.sendCommand(Config.FLAG_GAME_STOP);
						}
						ClientForm.lblTime.setText(String
								.valueOf(tempTime));

					}

				});

			}
		}, 10, 10);

		// 运算代码
		setControlVisibility(false);
		isWin=false;
		// for (int i = 0; i <= gameForm.myGame.intRound - 1; i++)
		// gameForm.myGame.timer[i].cancel();
		// gameForm.label.setVisible(true);
		//
		

	}

	/**
	 * 提交答案
	 * 
	 * @param ans
	 */
	public void submitAns(String ans) {

 		String[] strArray = { String.valueOf(this.question[0]),
				String.valueOf(this.question[1]),
				String.valueOf(this.question[2]),
				String.valueOf(this.question[3]) };
		//new Point24Math().change(strArray, 0, 3);
 		LinkedHashSet<String> allAns = new Point24Math().findAllAns(strArray);
 		int result_n=allAns.size();
		// //////////////////////////////

		if ((result_n == 0 && ans.equals("0"))
				|| (Math.abs(new Point24Math().main(ans) - 24) <= 0.00001f && Tool
						.isBug(ans, this.question))) {
			this.gameTimer.cancel();
			this.sendCommand("/" + this.nickName + "/" + ans + "/"
					+ String.valueOf(((int) (this.startTime * 100) / 100d))
					+ " Seconds");
			Tool.alert("計算正確~~~~>w<", null);
			writeRecord(Arrays.toString(strArray)+"\t"+ans);
			isWin=true;
 
			this.sendTextMessage(this.nickName + ": "
					+ String.valueOf(((int) (this.startTime * 100) / 100d))
					+ "s");

			this.startTime = 0;

			setControlVisibility(true);
			 

		} else {
			this.sendCommand("/" + ans + "/" + "Wrong.");
			Tool.alert("計算錯誤.TwT", null);
		 
		}

	}
	
	
	public void setControlVisibility(boolean isGameStop){
		if (isGameStop) {
			// 隐藏
			ClientForm.lblQuestion.setVisible(false);
			ClientForm.txtAnswer.setVisible(false);
			ClientForm.btnSubmit.setVisible(false);
			ClientForm.lblTime.setVisible(false);
			// 显示
			ClientForm.btnReady.setVisible(true);
		}else{
			// // 隐藏ready
			 ClientForm.btnReady.setVisible(false);
			 
			 ClientForm.lblQuestion.setVisible(true);
			 ClientForm.txtAnswer.setVisible(true);
			 ClientForm.btnSubmit.setVisible(true);
			 ClientForm.lblTime.setVisible(true);
		}
	}

	public void stopGame() {
		this.gameTimer.cancel();
		setControlVisibility(true);
		if (!isWin) {
			Tool.alert("TwT时间到！再接再厉！",null);
			writeRecord(Arrays.toString(this.question)+"\tYou have not found an answer!");
		}
		
 		
		
	}
	
	public void writeRecord(String str){
		ClientForm.txtRecord.setText(ClientForm.txtRecord.getText()+str+"\r\n");

	}
}
