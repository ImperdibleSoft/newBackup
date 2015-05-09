import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class WLogin implements Runnable {
	WMainWindow wMainWindow;
	JFrame window;
	
	JTextField textUser;
	JTextField textPassword;
	JButton btnLogin;
	
	public void run() {

		/*	Set the look & feel similar to the OS	*/
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			/*	Open the window	*/
			window.setVisible(true);
			
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension windowSize = window.getSize();
			window.setLocation((screen.width - windowSize.width)/2, (screen.height - windowSize.height)/2);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public WLogin(WMainWindow wMainWindow){
		this.wMainWindow = wMainWindow;
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
		
		window.setTitle("Login");
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
		 * Description
		 */
		JLabel lblDescription = new JLabel("<html>\r\n\t<p>You can use your ImperdibleSoft account to sync your configuration on different computers.</p>\r\n</html>");
		lblDescription.setVerticalAlignment(SwingConstants.TOP);
		lblDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescription.setBounds(148, 11, 286, 28);
		window.getContentPane().add(lblDescription);

		/**
		 * User field
		 */
		JLabel lblUser = new JLabel("User / Email:");
		lblUser.setBounds(148, 50, 66, 28);
		window.getContentPane().add(lblUser);
		
		textUser = new JTextField();
		textUser.setBounds(214, 50, 220, 28);
		window.getContentPane().add(textUser);
		textUser.setColumns(10);
		
		/**
		 * Password Field
		 */
		JLabel lblPassword = new JLabel("Password\r\n");
		lblPassword.setBounds(148, 84, 66, 28);
		window.getContentPane().add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setColumns(10);
		textPassword.setBounds(214, 84, 220, 28);
		window.getContentPane().add(textPassword);
		
		/**
		 * Login button
		 */
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				btnLogin.setEnabled(false);
				btnLogin.repaint();
				
				try {
					// Define the URL
					String prodEnv = "http://www.imperdiblesoft.com/APIs/public.php?getBackupLogin=true&";
					String devEnv = "http://dev.imperdiblesoft.com/APIs/public.php?getBackupLogin=true&";
					String localEnv = "http://localhost/imperdiblesoft/APIs/public.php?getBackupLogin=true&";
					String urlStart = devEnv;
					URL url;
					StringBuffer result;
					
					// Get the user ID
					url = new URL(urlStart + "return=id_usuario");
					result = makeRequest(url);
					if(result.toString() != "false"){
						BackupData.setUserID(result);
					}

					// Get the user Name
					url = new URL(urlStart + "return=nombre");
					result = makeRequest(url);
					if(result.toString() != "false"){
						BackupData.setUserName(result);
					}

					// Get the user Email
					url = new URL(urlStart + "return=email");
					result = makeRequest(url);
					if(result.toString() != "false"){
						BackupData.setUserEmail(result);
					}
					
					wMainWindow.showUserName();
					window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
				
				} catch (MalformedURLException e) {
					e.printStackTrace();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(334, 118, 100, 35);
		window.getContentPane().add(btnLogin);

		/**
		 * Developer
		 */
		JLabel lblDeveloped = new JLabel("Create your account at");
		lblDeveloped.setBounds(10, 196, 118, 14);
		window.getContentPane().add(lblDeveloped);

		/**
		 * Developer Website
		 */
		JLabel lblDeveloperWeb = new JLabel("<html><a href='http://www.imperdiblesoft.com'>http://www.imperdiblesoft.com</a></html>");
		lblDeveloperWeb.setBounds(126, 196, 158, 14);
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

	StringBuffer makeRequest(URL url) throws IOException{

		// Set login data
		String data = "user="+textUser.getText() +"&passwd="+ textPassword.getText() +"&account_type=1";
		
		// Create a URL Connection
		final HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		// Set method
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();
		
		// Parse the response
		BufferedReader in = new  BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}
		in.close();
		
		return response;
	}
}
