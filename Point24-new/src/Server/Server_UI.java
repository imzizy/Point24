package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

public class Server_UI extends JFrame {

	private JPanel contentPane;
	public static JTextArea textArea = new JTextArea();
	static Server myServer;
	
	
	JButton btnNewButton = new JButton("Send Num");
	private final JLabel label = new JLabel("Copyright © Zizy");
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_UI frame = new Server_UI();
					frame.setVisible(true);

					//new Server_UI_Event(frame);					//傳入這個frame
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
		
		
		
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	@SuppressWarnings("resource")
	public Server_UI() throws IOException {
		myServer=new Server(this);

		//服务器每两秒钟都发送在线几个人给所有人
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				Server.getInstance().SendMsgToAll("-"+Server.getInstance().intPlayerAmount);
			
				
			}
		}, 1000, 1000);
		
		
		setTitle("Server.  Made By Zizy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scroll = new JScrollPane(textArea); 
		scroll.setVisible(true);
		scroll.setHorizontalScrollBarPolicy( 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll.setBounds(20,20,400,200);    //设置 JScrollPane 宽100,高200
		textArea.setWrapStyleWord(true);
		 
		textArea.setLineWrap(true);
	 
		contentPane.add(scroll);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startGame();
			}
		});

		btnNewButton.setBounds(159, 230, 93, 23);
		contentPane.add(btnNewButton);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 12));
		label.setBounds(334, 251, 119, 23);
		
		contentPane.add(label);
		
		
	}
	public static void startGame(){
		String strNumber=Game.CreateNum();
		myServer.SendMsgToAll(strNumber);
		//顯示出來到底有哪幾個數字
		textArea.setText(textArea.getText()+strNumber+"\r\n");
	}
}
