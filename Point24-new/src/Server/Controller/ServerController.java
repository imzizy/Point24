package Server.Controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.swt.widgets.Display;

import Public.Point24Math;
import Public.Tool;
import Server.ServerForm;
import Server.Socket.GameServerSocket;
 
public class ServerController {
	private static  ServerController instance;
	
	private ServerForm serverForm;
	
	private GameServerSocket serverSocket;

	private int onlineNum=0;//在線人數
	private int  hostId=0;//現在的房主是誰
	private int readyNum=0;//準備好的人數,不包含房主
	private String  question;//當前的問題
	
	private boolean isGameStart=false;//現在是否開始遊戲
	
	
	
	/**
	 * 	得到Controller的單一實例
	 * @return
	 */
	public static  ServerController getInstance(){
		if(instance==null){
			instance=new ServerController();
		}
		
		return instance;
		
	}
	
	private ServerController() {
		try {
			serverSocket=new GameServerSocket(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public GameServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(GameServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public int getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}
	
	 
	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public int getReadyNum() {
		return readyNum;
	}

	public void setReadyNum(int readyNum) {
		this.readyNum = readyNum;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public boolean isGameStart() {
		return isGameStart;
	}

	public void setGameStart(boolean isGameStart) {
		this.isGameStart = isGameStart;
	}

	/**
	 * 	發送消息給指定的client
	 * 
	 */
	public void sendCommandTo(int clientId,String msg){
		this.serverSocket.sendCommandTo(msg, clientId);
	}
	
	/**
	 *	發送消息給所有人
	 * 
	 */
	public void sendCommandToAll(String msg){
		this.serverSocket.sendCommandToAll(msg);
	}
 
	
	/**
	 * 	顯示消息到视窗中
	 * 
	 */
	public void showMessage(final String msg){
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	ServerForm.txtOutput.setText(msg+"\r\n"+ServerForm.txtOutput.getText()+"\r\n");
		    }
		});
		
	}
	
	
	/**
	 * 	开始游戏
	 * 
	 */
	public void startGame(){
		String question=Tool.CreateNum();
		sendCommandToAll(question);
		this.setReadyNum(0);
		
 		
		int[] nums=Tool.NumberFormat(question);
		LinkedHashSet<String> str_exp_sum=new Point24Math().findAllAns(Arrays.toString(nums).substring(1,Arrays.toString(nums).length()-1).split(", "));
		for (Iterator<String> iterator = str_exp_sum.iterator(); iterator.hasNext();) {
			String ans =  iterator.next();
 			showMessage(ans);
		}
		
		
		//顯示出來到底有哪幾個數字
 		showMessage(question);
		//TODO Server.readyPlayerAmount=0;
	}
	
}
