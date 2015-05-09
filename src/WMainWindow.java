import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class WMainWindow {
	JFrame mainWindow;
	JLabel lblUser;
	public static boolean isLogged = false;
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					/*	Set the look & feel similar to the OS	*/
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					/*	Open the window	*/
					WMainWindow imperdibleBackup = new WMainWindow();
					if( BackupData.isStartMinimized() == false){
						imperdibleBackup.mainWindow.setVisible(true);
					}
					
					Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
					Dimension window = imperdibleBackup.mainWindow.getSize();
					imperdibleBackup.mainWindow.setLocation((screen.width - window.width)/2, (screen.height - window.height)/2);
					
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	public WMainWindow(){
		initialize();
	}
	
	/**
	 * Initialize the main window
	 */
	private void initialize() {
		mainWindow = new JFrame();
		mainWindow.setIconImage(Toolkit.getDefaultToolkit().getImage(WMainWindow.class.getResource("/img/material_logo_128.png")));

//		URL logo = WMainWindow.class.getResource( BackupData.getFavicon() );
//		mainWindow.setIconImage(Toolkit.getDefaultToolkit().getImage(logo));
		
		mainWindow.setTitle("Imperdible Backup");
		mainWindow.setBounds(100, 100, 800, 600);
		mainWindow.setMinimumSize(new Dimension(600, 470));
		mainWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mainWindow.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("100px"),
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("100px:grow"),
				ColumnSpec.decode("10px"),},
			new RowSpec[] {
				RowSpec.decode("fill:10px"),
				RowSpec.decode("fill:265px"),
				RowSpec.decode("fill:10px:grow"),
				RowSpec.decode("fill:70px"),
				RowSpec.decode("fill:10px"),
				RowSpec.decode("fill:55px"),
				RowSpec.decode("fill:10px"),}));
		
		/**
		 * Menu Panel
		 */
		JPanel pnlMenu = new JPanel();
		mainWindow.getContentPane().add(pnlMenu, "2, 2, fill, fill");
		pnlMenu.setLayout(null);
		
		/**
		 * New Drive Button
		 */
		final JButton btnNewDrive = new JButton("New Drive");
		btnNewDrive.setBounds(0, 0, 100, 35);
		pnlMenu.add(btnNewDrive);
		btnNewDrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewDrive.setEnabled(false);
				btnNewDrive.repaint();
			}
		});

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 45, 100, 1);
		pnlMenu.add(separator);
		
		/**
		 * Add folder Button
		 */
		JButton btnAddFolder = new JButton("Add Folder");
		btnAddFolder.setBounds(0, 55, 100, 35);
		if(ImperdibleBackup.DriveConnected()){
			btnAddFolder.setEnabled(true);
		}else{
			btnAddFolder.setEnabled(false);
		}
		pnlMenu.add(btnAddFolder);
		btnAddFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ImperdibleBackup.DriveConnected()){
					ImperdibleBackup.addFolder();
					
				}else{
					ImperdibleBackup.noImperdibleDrive();
				}
			}
		});

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 100, 100, 1);
		pnlMenu.add(separator_1);
		
		/**
		 * Backup button
		 */
		JButton btnBackup = new JButton("Backup");
		btnBackup.setBounds(0, 110, 100, 35);
		if(ImperdibleBackup.DriveConnected()){
			btnBackup.setEnabled(true);
		}else{
			btnBackup.setEnabled(false);
		}
		pnlMenu.add(btnBackup);
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ImperdibleBackup.DriveConnected()){
					
				}else{
					ImperdibleBackup.noImperdibleDrive();
				}
				
			}
		});
		
		/**
		 * Restore button
		 */
		JButton btnRestore = new JButton("Restore");
		btnRestore.setBounds(0, 150, 100, 35);
		if(ImperdibleBackup.DriveConnected()){
			btnRestore.setEnabled(true);
		}else{
			btnRestore.setEnabled(false);
		}
		pnlMenu.add(btnRestore);
		btnRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ImperdibleBackup.DriveConnected()){
					
				}else{
					ImperdibleBackup.noImperdibleDrive();
				}
				
			}
		});
		
		/**
		 * Compress button
		 */
		JButton btnCompress = new JButton("Compress");
		btnCompress.setBounds(0, 190, 100, 35);
		if(ImperdibleBackup.DriveConnected()){
			btnCompress.setEnabled(true);
		}else{
			btnCompress.setEnabled(false);
		}
		pnlMenu.add(btnCompress);
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ImperdibleBackup.DriveConnected()){
					
				}else{
					ImperdibleBackup.noImperdibleDrive();
				}
				
			}
		});
		
		/**
		 * Clean button
		 */
		JButton btnClean = new JButton("Clean");
		btnClean.setBounds(0, 230, 100, 35);
		if(ImperdibleBackup.DriveConnected()){
			btnClean.setEnabled(true);
		}else{
			btnClean.setEnabled(false);
		}
		pnlMenu.add(btnClean);
		btnClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ImperdibleBackup.DriveConnected()){
					
				}else{
					ImperdibleBackup.noImperdibleDrive();
				}
				
			}
		});
		
		/**
		 * Options panel
		 */
		JPanel pnlOptions = new JPanel();
		mainWindow.getContentPane().add(pnlOptions, "2, 4, 1, 3, fill, fill");
		pnlOptions.setLayout(null);
		
		/**
		 * Login button
		 */
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(0, 44, 100, 35);
		pnlOptions.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startLoginThread();
			}
		});
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 90, 100, 2);
		pnlOptions.add(separator_2);
		
		/**
		 * Exit button
		 */
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(0, 100, 100, 35);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		pnlOptions.add(btnExit);
		
		
		/**
		 * Folders list
		 */
		JList<String[]> listFolders = new JList<String[]>();
		mainWindow.getContentPane().add(listFolders, "4, 2, 1, 3, fill, fill");

		/**
		 * Status panel
		 */
		JPanel pnlStatus = new JPanel();
		mainWindow.getContentPane().add(pnlStatus, "4, 6, fill, fill");
		pnlStatus.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("100px:grow"),
				ColumnSpec.decode("10px"),},
			new RowSpec[] {
				RowSpec.decode("21px"),
				RowSpec.decode("34px"),}));
		/**
		 * Label (Task status)
		 */
		JLabel lblStatus = new JLabel("No Imperdible Drive detected.");
		pnlStatus.add(lblStatus, "2, 1, fill, fill");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);

		/**
		 * Progress bar (Task status)
		 */
		JProgressBar pbarStatus = new JProgressBar();
		pnlStatus.add(pbarStatus, "1, 2, 3, 1, fill, fill");
		pbarStatus.setEnabled(false);
		pbarStatus.setToolTipText("");
		
		/**
		 * Trucho
		 */
		lblUser = new JLabel("User:");
		lblUser.setVisible(false);
		lblUser.setBounds(0, 0, 100, 33);
		pnlOptions.add(lblUser);
		
	}
	
	public void showLblUser(){
		lblUser.setText(lblUser.getText() + BackupData.getUserName());
		lblUser.setVisible(true);
		lblUser.repaint();
	}

	public void startLoginThread(){
		Thread login = new Thread(new WLogin(this));
		login.start();
	}
	
	public boolean isVisible() {
		return mainWindow.isVisible();
	}

	public void setVisible(boolean b) {
		mainWindow.setVisible(b);
	}

	public void toFront() {
		mainWindow.toFront();
	}

	public void setLocation(int x, int y) {
		mainWindow.setLocation(x, y);
	}

	public Dimension getSize() {
		return mainWindow.getSize();
	}
}
