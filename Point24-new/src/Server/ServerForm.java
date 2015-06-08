package Server;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import Server.Controller.ServerController;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ServerForm {

	protected Shell shell;
	
	public static Text txtOutput;
	public static  Button btnStartGame;

	private static  ServerController serverController;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerForm window = new ServerForm();
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
		
		
		serverController=ServerController.getInstance();
		
		
		shell.open();
		shell.layout();
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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		txtOutput = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		txtOutput.setBounds(10, 10, 430, 221);
		
		btnStartGame = new Button(shell, SWT.NONE);
		btnStartGame.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				serverController.startGame();
			}
		});
		btnStartGame.setBounds(180, 240, 95, 28);
		btnStartGame.setText("Start Game");

	}

}
