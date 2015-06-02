package Client;

import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
	int intRound = 0;
	myTimerTask[] mytimer = new myTimerTask[1000];
	boolean isTimerSet = false;
	int[] questions = new int[4];
	double time = 0;
	Timer[] timer = new Timer[1000];
	Client_UI frame;
	static Game instance;
	public int  onlineNum=0;//当前在线的人数
	public boolean isHost=false;
	
	public Client client;
	
	
	Game(Client_UI frame) {

		this.frame = frame;
		timer[0] = new Timer();
		
		this.instance=this;
	}

	public void sendMsg(String msg) {
		try {
			client.out = new PrintWriter(client.socket.getOutputStream(), true);
			client.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startGame(){
		sendMsg("*");
	}
	public static  Game getInstance(){
		return instance;
	}
	public void SetTimer() {
		mytimer[intRound] = new myTimerTask(this);
		timer[intRound] = new Timer();
		timer[intRound].schedule(mytimer[intRound], 10, 11);

	}

	public void CancelTimer() {
		timer[intRound].cancel();
	}
	
	public  void showMessageLn(String msg){
		frame.txtMessage.setText(msg+"\r\n"+frame.txtMessage.getText()); 
	}

}

class myTimerTask extends TimerTask {
	Game game;
	long begintime = System.currentTimeMillis();

	myTimerTask(Game game) {
		this.game = game;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// 运算代码
		long endtime = System.currentTimeMillis();
		long costTime = (endtime - begintime);
		begintime = System.currentTimeMillis();
		game.time += costTime / 1000f;
		game.frame.label.setText(String
				.valueOf(((int) (game.time * 100) / 100d)));

	}

}