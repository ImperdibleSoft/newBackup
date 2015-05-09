import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Cursor;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class WAbout implements Runnable {
	JFrame window;
	
	@Override
	public void run() {

		/*	Set the look & feel similar to the OS	*/
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			/*	Open the window	*/
			WAbout about = new WAbout();
			about.window.setVisible(true);
			
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension window = about.window.getSize();
			about.window.setLocation((screen.width - window.width)/2, (screen.height - window.height)/2);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public WAbout(){
		initialize();
	}

	/**
	 * Initialize the About window
	 */
	private void initialize() {
		window = new JFrame();
		window.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		window.setType(Type.POPUP);

		URL logo = WAbout.class.getResource( BackupData.getFavicon() );
		window.setIconImage(Toolkit.getDefaultToolkit().getImage(logo));
		
		window.setTitle("About");
		window.setResizable(false);
		window.setBounds(100, 100, 450, 250);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		/**
		 * Imperdible Backup logo
		 */
		ImageIcon backupLogo = new ImageIcon(logo);
		JLabel lblLogo = new JLabel(backupLogo);
		lblLogo.setBounds(10, 11, 128, 128);
		window.getContentPane().add(lblLogo);
		
		/**
		 * Title
		 */
		JLabel lblTitle = new JLabel("Imperdible Backup");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(148, 5, 286, 24);
		window.getContentPane().add(lblTitle);
		
		/**
		 * Version
		 */
		JLabel lblVersion = new JLabel("v"+ BackupData.getLauncherVersion());
		lblVersion.setBounds(148, 36, 286, 14);
		window.getContentPane().add(lblVersion);
		
		/**
		 * Website
		 */
		JLabel lblBackupWeb = new JLabel("<html><a href='http://backup.imperdiblesoft.com'>http://backup.imperdiblesoft.com</a></html>");
		lblBackupWeb.setBounds(148, 61, 286, 14);
		lblBackupWeb.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblBackupWeb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("http://backup.imperdiblesoft.com"));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		window.getContentPane().add(lblBackupWeb);
		
		/**
		 * Description
		 */
		JLabel lblDescription = new JLabel("<html><p>Imperdible Backup is a small backup software, designed</p><p>for personal use on a daily and simple backup task.</p></html>");
		lblDescription.setVerticalAlignment(SwingConstants.TOP);
		lblDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescription.setBounds(148, 86, 286, 78);
		window.getContentPane().add(lblDescription);

		/**
		 * Developer
		 */
		JLabel lblDeveloped = new JLabel("Developed by ImperdibleSoft");
		lblDeveloped.setBounds(148, 175, 286, 14);
		window.getContentPane().add(lblDeveloped);

		/**
		 * Developer Website
		 */
		JLabel lblDeveloperWeb = new JLabel("<html><a href='http://www.imperdiblesoft.com'>http://www.imperdiblesoft.com</a></html>");
		lblDeveloperWeb.setBounds(148, 196, 286, 14);
		lblDeveloperWeb.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblDeveloperWeb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("http://www.imperdiblesoft.com"));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		window.getContentPane().add(lblDeveloperWeb);
	}
}
