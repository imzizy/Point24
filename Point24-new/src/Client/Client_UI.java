package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.border.LineBorder;

public class Client_UI extends JFrame implements ActionListener {

	Game myGame;

	public JButton button = new JButton("連接服務器");
	public JLabel lblQuestion = new JLabel("1 2 3 4");
	public JButton btnSubmit = new JButton("提交");
	public Label label = new Label("100");
	public Socket socket;
	public JPanel contentPane;
	public JTextField txtNumInput = new JTextField();
	public final JTextField txtUserName = new JTextField();
	public final JLabel lblName = new JLabel("Name:");
	public final JTextField txtIp = new JTextField();
	public final JLabel lblIp = new JLabel("IP :");
	private final JLabel lblMadeByZizy = new JLabel("Copyright © Zizy");
	public JButton btnReady = new JButton("Ready!");
	public final JTextArea txtMessage = new JTextArea();
	private final JTextField txtInputMsg = new JTextField();
	private final JButton btnSendMsg = new JButton(">");
	public JLabel lblOnlineNum = new JLabel("在线：0人");

	private JScrollPane scrollPane = new JScrollPane();

	final Client myClient = new Client(this);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// try {
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		// } catch ( Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_UI frame = new Client_UI();
					frame.setVisible(true);
 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client_UI() {
		txtInputMsg.setBounds(10, 233, 79, 28);
		txtInputMsg.setColumns(10);
		txtUserName.setBounds(90, 11, 87, 21);
		txtUserName.setColumns(10);
		
		myGame = new Game(this);

		setTitle("24-point online        Made By Zizy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtUserName.getText().equals("") || txtUserName.getText().length()<2 ||txtUserName.getText().length()>5){
					JOptionPane.showMessageDialog(null, "名字不可以少于2个字符或大于5个字符");
					return;
				}
				try {
					if (txtIp.getText() != "")
						myClient.ServerIP = txtIp.getText();
					myClient.Connecting();
					myGame.client=myClient;
					
					myGame.showMessageLn("连线完成！");
					
					button.setEnabled(false);
					txtUserName.setEnabled(false);
					txtIp.setEnabled(false);
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							//得到当前在线人数
							myClient.SendMsg("-");
							while(!myClient.thread.lastMsgFlag.equals("-"));
							int onlineNum=Game.getInstance().onlineNum;
							if (onlineNum==1) {
								 //我是host
								btnReady.setText("开始！");
								btnReady.setVisible(true);
								Game.getInstance().isHost=true;
							}else{
								//我是guest
								btnReady.setText("准备！");
								btnReady.setVisible(true);
								Game.getInstance().isHost=false;
								
							}
							
						}
					}).start();
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					JOptionPane.showMessageDialog(null, "連接錯誤。", "Z",
							JOptionPane.INFORMATION_MESSAGE);

				}

			}
		});

		button.setName("btn_connect");
		button.setBounds(309, 10, 110, 23);
		contentPane.add(button);

		//隐藏
		lblQuestion.setVisible(false);
		txtNumInput.setVisible(false);
		btnSubmit.setVisible(false);
		btnReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Game.getInstance().isHost) {
					//如果是host
					//TODO 如果所有人都ready了就可以开始
					
					myGame.startGame();
				}else{
					//如果是guest
				}
			}
		});
		btnReady.setVisible(false);
		
		lblQuestion.setFont(new Font("黑体", Font.BOLD, 74));
		lblQuestion.setBounds(132, 88, 369, 70);
		lblQuestion.setVisible(false);
		contentPane.add(lblQuestion);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myGame.isTimerSet = true;
				// myGame.timer.wait();

				math.Result_n = 0;
				String[] strArray = { String.valueOf(myGame.questions[0]),
						String.valueOf(myGame.questions[1]),
						String.valueOf(myGame.questions[2]),
						String.valueOf(myGame.questions[3]) };
				math.Change(strArray, 0, 3);
				// //////////////////////////////

				if ((math.Result_n == 0 && txtNumInput.getText()
						.equals("0"))
						|| (Math.abs(math.main(txtNumInput.getText()) - 24) <= 0.00001f && Format
								.isBug(txtNumInput.getText(),
										myGame.questions))) {
					myGame.timer[myGame.intRound].cancel();
					myClient.SendMsg("/"
							+ txtUserName.getText()
							+ "/"
							+ txtNumInput.getText()
							+ "/"
							+ String.valueOf(((int) (myGame.time * 100) / 100d))
							+ " Seconds");
					JOptionPane.showMessageDialog(null, "計算正確~~~~>w<", "Z",
							JOptionPane.INFORMATION_MESSAGE);
					myClient.SendMsg("~"
							+ txtUserName.getText()
							+ "/"
							+ String.valueOf(((int) (myGame.time * 100) / 100d))
							+ "s");
					myGame.time = 0;
					
					//隐藏
					lblQuestion.setVisible(false);
					txtNumInput.setVisible(false);
					btnSubmit.setVisible(false);
					//显示
					btnReady.setVisible(true);
					
					// }else if (mymath.Result_n==0 &&
					// textField.getText().equals("0")){
					// myGame.timer[myGame.intRound].cancel();
					// myClient.SendMsg("~"+textField_1.getText()+"/"+String.valueOf(((int)(myGame.time*100)/100d))+"s");
					// JOptionPane.showMessageDialog(null, "計算正確~~~~>w<","Z"
					// , JOptionPane.INFORMATION_MESSAGE);
					// myClient.SendMsg("/"+textField_1.getText()+"/"+textField.getText()+"/"+String.valueOf(((int)(myGame.time*100)/100d))+" Seconds");
					//
					// myGame.time=0;
					
					
				} else {
					myClient.SendMsg("/" + txtUserName.getText() + "/"
							+ "Wrong.");
					JOptionPane.showMessageDialog(null, "計算錯誤.TwT", "Z",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnSubmit.setBounds(237, 199, 93, 23);
		contentPane.add(btnSubmit);

		txtNumInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10 || arg0.getKeyCode() == 13) {
					btnSubmit.doClick();
				}
			}
		});

		txtNumInput.setBounds(132, 168, 300, 21);
		contentPane.add(txtNumInput);
		txtNumInput.setColumns(10);

		label.setVisible(false);
		label.setAlignment(Label.RIGHT);
		label.setFont(new Font("黑体", Font.PLAIN, 35));
		label.setBounds(446, 0, 110, 33);
		contentPane.add(label);

		contentPane.add(txtUserName);
		lblName.setBounds(53, 14, 54, 15);

		contentPane.add(lblName);
		txtIp.setColumns(10);
		txtIp.setBounds(211, 11, 87, 21);

		contentPane.add(txtIp);
		lblIp.setBounds(187, 13, 54, 15);

		contentPane.add(lblIp);
		lblMadeByZizy.setFont(new Font("微软雅黑", Font.BOLD, 12));
		lblMadeByZizy.setVerticalAlignment(SwingConstants.TOP);
		lblMadeByZizy.setHorizontalAlignment(SwingConstants.LEFT);
		lblMadeByZizy.setBounds(463, 240, 119, 23);

		contentPane.add(lblMadeByZizy);

		btnReady.setBounds(225, 234, 117, 29);
		contentPane.add(btnReady);
		
		contentPane.add(txtInputMsg);
		btnSendMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtInputMsg.getText().equals("")) {
					return;
					
				}
				
				myClient.SendMsg("~" + txtUserName.getText()+": "+txtInputMsg.getText() );
				txtInputMsg.setText("");
				txtInputMsg.requestFocus();;
			}
		});
		btnSendMsg.setBounds(90, 236, 32, 23);
		
		contentPane.add(btnSendMsg);
		
		scrollPane.setBounds(10, 55, 110, 167);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(txtMessage);
		txtMessage.setEditable(false);
		txtMessage.setLineWrap(true);
		txtMessage.setColumns(10);
		
		lblOnlineNum.setBounds(10, 38, 112, 16);
		contentPane.add(lblOnlineNum);
		
		 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
