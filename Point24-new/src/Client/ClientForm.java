package Client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Client.Controller.ClientController;
import Public.Config;
import Public.Point24Math;
import Public.Tool;

public class ClientForm {

	static protected Shell shell;

	public static Text txtRecord;
	public static Text txtInputMsg;
	public static Text txtAnswer;
	public static Text txtOutputMsg;
	public static Label lblOnlineNum;
	public static Button btnReady;
	private Text txtIp;
	private Text txtNickName;

	private ClientController clientController;

	private CTabItem tbtmConnection;

	private CTabItem tbtmOfflineGame;

	private CTabItem tbtmGameCenter;

	public static Label lblQuestion;

	public static Label lblTime;

	public static Button btnSubmit;
	private Text txtOfflineAns;

	private Label lblOfflineQuestion;

	
	
	public ClientForm() {
		this.clientController = ClientController.getInstance();

	}

	public static Shell getShell(){
		return shell;
	}
	
	public Button getBtnReady() {
		return btnReady;
	}

	public Label getLblOnlineNum() {
		return lblOnlineNum;
	}

	public Text getTxtOutputMsg() {
		return txtOutputMsg;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ClientForm window = new ClientForm();
			window.open();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();

		clientController.setControlVisibility(true);
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
 				
				display.sleep();
 			}
		}
		
		System.exit(0);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(619, 388);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		final CTabFolder tabMain = new CTabFolder(shell, SWT.BORDER);
		tabMain.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		tabMain.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// System.out.println(((CTabItem)e.item).getText());

				// System.out.println(ClientForm.this.clientController.isConnected());
				boolean isConnected = ClientForm.this.clientController
						.isConnected();
				String tabText = ((CTabItem) e.item).getText();
				if (!isConnected && tabText.equals("Online Game Center")) {
					// System.out.println(((CTabItem)e.item).getText());
					tabMain.setSelection(tbtmConnection);
					Tool.alert("請先連接伺服器",shell);
				}
				// if ((!ClientForm.this.clientController.isConnected()) &&
				// (System.out.println(((CTabItem)e.item).getText());) {
				// //tabMain.setSelection(tbtmConnection);
				// Tool.alert("請先連接伺服器");
				// }
				//
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		tbtmConnection = new CTabItem(tabMain, SWT.NONE);
		tbtmConnection.setText("Connection");

		Composite composite_2 = new Composite(tabMain, SWT.NONE);
		tbtmConnection.setControl(composite_2);

		txtIp = new Text(composite_2, SWT.BORDER);
		txtIp.setBounds(252, 120, 103, 19);

		Label lblIp = new Label(composite_2, SWT.NONE);
		lblIp.setBounds(170, 123, 76, 14);
		lblIp.setText("Server IP");

		Button btnConnect = new Button(composite_2, SWT.NONE);
		btnConnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean res = clientController.connectServer(
						txtNickName.getText(), txtIp.getText());

				if (res) {
					tabMain.setSelection(tbtmGameCenter);
				}

			}
		});
		btnConnect.setBounds(252, 169, 95, 28);
		btnConnect.setText("連接");

		txtNickName = new Text(composite_2, SWT.BORDER);
		txtNickName.setBounds(252, 91, 103, 19);

		Label lblNickName = new Label(composite_2, SWT.NONE);
		lblNickName.setBounds(168, 94, 78, 14);
		lblNickName.setText("Nick Name");

		tbtmGameCenter = new CTabItem(tabMain, SWT.NONE);
		tbtmGameCenter.setText("Online Game Center");

		Composite composite_1 = new Composite(tabMain, SWT.NONE);
		tbtmGameCenter.setControl(composite_1);

		lblOnlineNum = new Label(composite_1, SWT.NONE);
		lblOnlineNum.setBounds(10, 10, 60, 14);
		lblOnlineNum.setText("在线人数：");

		txtOutputMsg = new Text(composite_1, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		txtOutputMsg.setBounds(10, 30, 135, 257);

		txtInputMsg = new Text(composite_1, SWT.BORDER);
		txtInputMsg.setBounds(10, 297, 89, 19);

		Button btnSendMsg = new Button(composite_1, SWT.NONE);
		btnSendMsg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String msg=txtInputMsg.getText();
				ClientForm.this.clientController.sendTextMessage(ClientForm.this.clientController.getNickName()+" : "+msg);
				txtInputMsg.setText("");
				
			}
		});
		btnSendMsg.setBounds(105, 293, 40, 28);
		btnSendMsg.setText(">");

		lblQuestion = new Label(composite_1, SWT.NONE);
		lblQuestion.setFont(SWTResourceManager.getFont(
				".Helvetica Neue DeskInterface", 72, SWT.BOLD));
		lblQuestion.setBounds(248, 93, 253, 93);
		lblQuestion.setText("1 2 3 4");

		txtAnswer = new Text(composite_1, SWT.BORDER);
		txtAnswer.setBounds(248, 211, 253, 19);

		btnSubmit = new Button(composite_1, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clientController.submitAns(txtAnswer.getText());
			}
		});
		btnSubmit.setBounds(313, 240, 95, 28);
		btnSubmit.setText("Submit");

		lblTime = new Label(composite_1, SWT.NONE);
		lblTime.setFont(SWTResourceManager.getFont(
				".Helvetica Neue DeskInterface", 30, SWT.NORMAL));
		lblTime.setBounds(318, 30, 166, 41);
		lblTime.setText("00:00");

		btnReady = new Button(composite_1, SWT.NONE);
		btnReady.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clientController.sendCommand(Config.FLAG_GAME_READY);
			}
		});
		btnReady.setBounds(313, 274, 95, 28);
		btnReady.setText("Ready!");

		CTabItem tbtmRecord = new CTabItem(tabMain, SWT.NONE);
		tbtmRecord.setText("Record");

		Composite composite = new Composite(tabMain, SWT.NONE);
		tbtmRecord.setControl(composite);

		txtRecord = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		txtRecord.setBounds(10, 43, 593, 182);

		Button btnSaveAsTxt = new Button(composite, SWT.NONE);
		btnSaveAsTxt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(shell, SWT.SAVE);// 设置为保存对话框
		        // 设置保存类型
		        dlg.setFileName("");
		       
		        dlg.setFilterNames(new String[] {
		                  "Text Files (*.txt)",           //这样输入文件名时不用加.xls
		                  "All Files (*.*)"
		                });
		                dlg.setFilterExtensions(new String[] {
		                   "*.txt",  "*.*"     
		                });
		        String path=dlg.open();
		        try {
					FileWriter fr=new FileWriter(new File(path));
					fr.append(txtRecord.getText());
					fr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		     //   System.out.println(path);
			}
		});
		btnSaveAsTxt.setBounds(262, 272, 95, 28);
		btnSaveAsTxt.setText("Save as txt");

		tbtmOfflineGame = new CTabItem(tabMain, SWT.NONE);
		tbtmOfflineGame.setText("Offline Game");
		
		Composite composite_3 = new Composite(tabMain, SWT.NONE);
		tbtmOfflineGame.setControl(composite_3);
		
		Button btnOfflineStart = new Button(composite_3, SWT.NONE);
		btnOfflineStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblOfflineQuestion.setText(Arrays.toString(Tool.NumberFormat(Tool.CreateNum())));
				
			}
		});
		btnOfflineStart.setText("Start");
		btnOfflineStart.setBounds(142, 207, 95, 28);
		
		txtOfflineAns = new Text(composite_3, SWT.BORDER);
		txtOfflineAns.setBounds(111, 165, 253, 19);
		
		lblOfflineQuestion = new Label(composite_3, SWT.NONE);
		lblOfflineQuestion.setText("1 2 3 4");
		lblOfflineQuestion.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 72, SWT.BOLD));
		lblOfflineQuestion.setBounds(111, 56, 344, 93);
		
		Button button_1 = new Button(composite_3, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] strArray = lblOfflineQuestion.getText().substring(1,  lblOfflineQuestion.getText().length()-1).split(", ");
				//new Point24Math().change(strArray, 0, 3);
		 		LinkedHashSet<String> allAns = new Point24Math().findAllAns(strArray);
		 		int result_n=allAns.size();
				// //////////////////////////////

				if ((result_n == 0 && txtOfflineAns.getText().equals("0"))
						|| (Math.abs(new Point24Math().main(txtOfflineAns.getText()) - 24) <= 0.00001f )) {
 					Tool.alert("計算正確~~~~>w<", null);
					clientController.writeRecord(Arrays.toString(strArray)+"\t"+txtOfflineAns.getText());
 		 
 					 

				} else {
 					Tool.alert("計算錯誤.TwT", null);
 
				}
			}
		});
		button_1.setText("Submit");
		button_1.setBounds(378, 161, 95, 28);
		
		Button btnShowAnswer = new Button(composite_3, SWT.NONE);
		btnShowAnswer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] strArray = lblOfflineQuestion.getText().substring(1,  lblOfflineQuestion.getText().length()-1).split(", ");
				//new Point24Math().change(strArray, 0, 3);
		 		LinkedHashSet<String> allAns = new Point24Math().findAllAns(strArray);
		 		Tool.alert(allAns.toString(), null);
			}
		});
		btnShowAnswer.setText("Show Answer");
		btnShowAnswer.setBounds(243, 207, 113, 28);

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmGame_1 = new MenuItem(menu, SWT.CASCADE);
		mntmGame_1.setText("Game");

		Menu menu_1 = new Menu(mntmGame_1);
		mntmGame_1.setMenu(menu_1);

		MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem.setText("Connect");

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmQuite = new MenuItem(menu_1, SWT.NONE);
		mntmQuite.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.exit(0);
			}
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mntmQuite.setText("Quite");

	}
}
