import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;


public class BackgroundTask implements Runnable{
	
	@Override
	public void run() {

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		}catch (Exception e){
		   e.printStackTrace();
		}
		
		
		/**
		 * 	Place the window
		 */
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		final WMainWindow mainWindow = new WMainWindow();
		if(BackupData.isStartMinimized() == false){
			mainWindow.setVisible(true);
			
			Dimension window = mainWindow.getSize();
			mainWindow.setLocation((screen.width - window.width)/2, (screen.height - window.height)/2);
		}

		/**
		 * Get the systemTray
		 */
		SystemTray sysTray = SystemTray.getSystemTray();
		
		/**
		 * Create the systemTray Icon
		 */
		Image sysTrayIconImage = Toolkit.getDefaultToolkit().getImage( BackupData.getSysTrayIconString() );
		final TrayIcon sysTrayIcon  = new TrayIcon(sysTrayIconImage, "Imperdible Backup", null);
		sysTrayIcon.setImageAutoSize(true);

		/**
		 * Open the System Tray Menu
		 */
		final JPopupMenu sysTrayMenu = new JPopupMenu();
		sysTrayIcon.addMouseListener(new MouseAdapter(){
	        public void mouseClicked(MouseEvent e) {
	            if (e.getButton()==3) {
	            	sysTrayMenu.setInvoker(sysTrayMenu);
	            	sysTrayMenu.setVisible(true);
	            	
	            	if((e.getXOnScreen() + sysTrayMenu.getWidth()) > screen.width && (e.getYOnScreen() + sysTrayMenu.getHeight()) > screen.height){
	            		sysTrayMenu.setLocation(e.getXOnScreen() - sysTrayMenu.getWidth(), e.getYOnScreen() - sysTrayMenu.getHeight());
	            		
	            	}else if((e.getXOnScreen() + sysTrayMenu.getWidth()) > screen.width){
	            		sysTrayMenu.setLocation(e.getXOnScreen() - sysTrayMenu.getWidth(), e.getYOnScreen());
	            		
	            	}else if((e.getYOnScreen() + sysTrayMenu.getHeight()) > screen.height){
	            		sysTrayMenu.setLocation(e.getXOnScreen(), e.getYOnScreen() - sysTrayMenu.getHeight());
	            		
	            	}else{
	            		sysTrayMenu.setLocation(e.getXOnScreen(), e.getYOnScreen());
	            	}
	            }
	        }
		});
		
		/**
		 * Open Imperdible Drive folder, if exists
		 */
		JMenuItem sysImperdibleDrive = new JMenuItem("Open Imperdible Drive");
		sysImperdibleDrive.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
		if(ImperdibleBackup.DriveConnected()){
			try {
				if(BackupData.getOs() == "windowsXP" || BackupData.getOs() == "windowsVista" || BackupData.getOs() == "windows7" || BackupData.getOs() == "windows8" || BackupData.getOs() == "windows10"){
					Runtime.getRuntime().exec("C:/Windows/explorer.exe /n, "+ BackupData.getImperdibleDrive());
					
				}else if(BackupData.getOs() == "linux"){
					System.out.println("Abriendo disco imperdible en Linux");
					
				}else if(BackupData.getOs() == "mac"){
					System.out.println("Abriendo disco imperdible en OS X");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			
			ImperdibleBackup.noImperdibleDrive();
		}
		}});
		sysTrayMenu.add(sysImperdibleDrive);

		/**
		 * Open Imperdible Backup
		 */
		JMenuItem sysImperdibleBackup = new JMenuItem("Open Imperdible Backup");
		Font mainAction = new Font(sysImperdibleBackup.getFont().getName(), Font.BOLD, sysImperdibleBackup.getFont().getSize());
		sysImperdibleBackup.setFont(mainAction);
		sysImperdibleBackup.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			if(mainWindow.isVisible() == false){
				mainWindow.setVisible(true);
				
			}else{
				mainWindow.toFront();
			}
		}});
		sysTrayMenu.add(sysImperdibleBackup);

		/**
		 * Actions sub-menu
		 */
		final JMenu sysActions = new JMenu("Actions");
		
		/**
		 * Create a new ImperdibleDrive
		 */
		final JMenuItem sysNewDrive = new JMenuItem("New Imperdible Drive");
		sysNewDrive.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			ImperdibleBackup.newDrive();
		}});
		sysActions.add(sysNewDrive);
		
		sysActions.addSeparator();

		/**
		 * Select a new folder
		 */
		final JMenuItem sysAddFolder = new JMenuItem("Add new folder");
		sysAddFolder.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			if(ImperdibleBackup.DriveConnected()){
				ImperdibleBackup.addFolder();
				
			}else{
				ImperdibleBackup.noImperdibleDrive();
			}
		}});
		sysActions.add(sysAddFolder);
		
		sysActions.addSeparator();

		/**
		 * Make a new Backup
		 */
		final JMenuItem sysBackup = new JMenuItem("Backup");
		sysBackup.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			if(ImperdibleBackup.DriveConnected()){
				Thread backup = new Thread(new TBackup());
				backup.start();
				
			}else{
				ImperdibleBackup.noImperdibleDrive();
			}
		}});
		sysActions.add(sysBackup);

		/**
		 * Restore the last copy
		 */
		final JMenuItem sysRestore = new JMenuItem("Restore");
		sysRestore.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			if(ImperdibleBackup.DriveConnected()){
				Thread restore = new Thread(new TRestore());
				restore.start();
				
			}else{
				ImperdibleBackup.noImperdibleDrive();
			}
		}});
		sysActions.add(sysRestore);

		/**
		 * Compress the current backup
		 */
		final JMenuItem sysCompress = new JMenuItem("Compress");
		sysCompress.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			if(ImperdibleBackup.DriveConnected()){
				Thread compress = new Thread(new TCompress());
				compress.start();
				
			}else{
				ImperdibleBackup.noImperdibleDrive();
			}
		}});
		sysActions.add(sysCompress);

		/**
		 * Clean Imperdible Drive
		 */
		final JMenuItem sysClean = new JMenuItem("Clean");
		sysClean.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			if(ImperdibleBackup.DriveConnected()){
				Thread clean = new Thread(new TClean());
				clean.start();
				
			}else{
				ImperdibleBackup.noImperdibleDrive();
			}
		}});
		sysActions.add(sysClean);
		
		sysTrayMenu.add(sysActions);
		sysTrayMenu.addSeparator();

		/**
		 * Show on startup
		 */
		final JCheckBoxMenuItem sysShowOnStartup = new JCheckBoxMenuItem("Show on startup");
		if(BackupData.getOs() == "windowsVista" || BackupData.getOs() == "windows7" || BackupData.getOs() == "windows8" || BackupData.getOs() == "windows10"){
			if(new File("C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Startup/Imperdible Security Software.lnk").exists()){
				sysShowOnStartup.setState(true);
			}
		}else if(BackupData.getOs() == "windowsXP"){
			if(new File("C:/Documents and Settings/All Users/Men� Inicio/Programas/Inicio/Imperdible Security Software.lnk").exists()){
				sysShowOnStartup.setState(true);
			}
		}else if(BackupData.getOs() == "linux"){
			if(new File("/etc/xdg/autostart/imperdible.desktop").length()>1){
				sysShowOnStartup.setState(true);
			} 
		}else if(BackupData.getOs() == "mac"){
			System.out.println("Comprobando si el inicio automatico en OS X esta activado.");
		}
		sysShowOnStartup.addItemListener(new ItemListener() {public void itemStateChanged(ItemEvent arg0) {
			if(sysShowOnStartup.getState() == false){
				BackupData.setShowOnStartup(false);
				
			}else{
				BackupData.setShowOnStartup(true);
			}
		}});
		sysTrayMenu.add(sysShowOnStartup);

		/**
		 * Start minimized
		 */
		final JCheckBoxMenuItem sysStartMinimized = new JCheckBoxMenuItem("Start minimized");
		if(BackupData.isStartMinimized() == false){
			sysStartMinimized.setState(false);
			
		}else{
			sysStartMinimized.setState(true);
		}
		sysStartMinimized.addItemListener(new ItemListener() {public void itemStateChanged(ItemEvent arg0) {
			if(new File("recursos/imperdible.inf").exists()){
				if(sysStartMinimized.getState()==false){
					BackupData.setStartMinimized(false);
				}else{
					BackupData.setStartMinimized(true);
				}
			}
		}});
		sysTrayMenu.add(sysStartMinimized);

		/**
		 * AutoDetect Imperdible Drive
		 */
		final JCheckBoxMenuItem sysAutoDetect = new JCheckBoxMenuItem("Autodetect ImperdibleDrive");
		final JCheckBoxMenuItem sysShowAtConnection = new JCheckBoxMenuItem("Open on Imperdible Drive connection");
		if(BackupData.isShowAtConnection() == true){
			sysAutoDetect.setState(true);
		}else{
			sysAutoDetect.setState(false);
		}
		sysAutoDetect.addItemListener(new ItemListener() {public void itemStateChanged(ItemEvent arg0) {
			if(new File("recursos/imperdible.inf").exists()){
				if(sysAutoDetect.getState() == false){
					BackupData.setShowAtConnection(false);
					sysShowAtConnection.setState(false);
					BackupData.setAutoDetect(false);
				}else{
					BackupData.setShowAtConnection(true);
				}
			}
		}});
		sysTrayMenu.add(sysAutoDetect);

		/**
		 * Show Imperdible Backup at Connection
		 */
		if(BackupData.isShowAtConnection() == true){
			sysShowAtConnection.setState(true);
		}else{
			sysShowAtConnection.setState(false);
		}
		sysShowAtConnection.addItemListener(new ItemListener() {public void itemStateChanged(ItemEvent arg0) {
			if(new File("recursos/imperdible.inf").exists()){
				if(sysShowAtConnection.getState() == false){
					BackupData.setShowAtConnection(false);
					
				}else{
					sysAutoDetect.setState(true);
					BackupData.setShowAtConnection(false);
				}
			}
		}});
		sysTrayMenu.add(sysShowAtConnection);

		/**
		 * Activate real time backup
		 */
		final JCheckBoxMenuItem sysAutoBackup = new JCheckBoxMenuItem("AutoBackup");
//		if(Imperdible.iniciocopiando==true){
//			miniAutoCopias.setState(true);
//		}
//		miniAutoCopias.addItemListener(new ItemListener() {public void itemStateChanged(ItemEvent arg0) {
//			if(new File(Imperdible.discoImperdible+"/ImperdibleSoftware/imperdible.inf").exists()){
//				if(miniAutoCopias.getState()==false){
//					if(!Ventanita.isShowing()){
//						Ventanita.setVisible(true);
//					}
//					Imperdible.desactivarCopiasAutomaticas();
//					miniNuevo.setEnabled(true);
//					miniNuevaCarpeta.setEnabled(true);
//					miniCopiar.setEnabled(true);
//					miniRestaurar.setEnabled(true);
//					miniLimpiar.setEnabled(true);
//					miniOpciones.setEnabled(true);
//				}else{
//					Ventanita.dispose();
//					bandejaIcono.displayMessage("Copiando...", "Imperdible esta sincronizando tus carpetas en segundo plano mientras trabajas.", TrayIcon.MessageType.INFO);
//					Imperdible.activarCopiasAutomaticas();
//					miniNuevo.setEnabled(false);
//					miniNuevaCarpeta.setEnabled(false);
//					miniCopiar.setEnabled(false);
//					miniRestaurar.setEnabled(false);
//					miniLimpiar.setEnabled(false);
//					miniOpciones.setEnabled(false);
//				}
//			}else{
//				if(Imperdible.aviso==true){
//					Imperdible.noHayDiscoImperdible();
//					Imperdible.aviso=false;
//					miniAutoCopias.setState(false);
//				}else{
//					Imperdible.aviso=true;
//				}
//			}
//		}});
		sysTrayMenu.add(sysAutoBackup);
		
		sysTrayMenu.addSeparator();

		/**
		 * Open "About" window
		 */
		JMenuItem sysAbout = new JMenuItem("About");
		sysAbout.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			Thread about = new Thread(new WAbout());
			about.start();
		}});
		sysTrayMenu.add(sysAbout);

		/**
		 * Close Imperdible Backup
		 */
		JMenuItem sysClose = new JMenuItem("Exit");
		sysClose.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ev){
			System.exit(0);
		}});
		sysTrayMenu.add(sysClose);
		
		try{
			sysTray.add(sysTrayIcon);
			
		}catch(Exception e){}
	}
}
