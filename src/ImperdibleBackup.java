import java.io.File;

import javax.swing.JOptionPane;


public class ImperdibleBackup {
	public static void main(String[] args){
		Thread sysTray = new Thread( new BackgroundTask() );
		sysTray.start();
	}

	public static void noImperdibleDrive() {
		JOptionPane.showMessageDialog(null, ""
			+ "<html>"
				+ "<p>No Imperdible Drive detected.<p>"
				+ "<p>Please, select an Imperdible Drive to perform this action.</p>"
			+ "</html>", "No Imperdible Drive", JOptionPane.ERROR_MESSAGE);
	}

	public static void newDrive() {
		// TODO Auto-generated method stub
		
	}

	public static void addFolder() {
		// TODO Auto-generated method stub
		
	}

	public static boolean DriveConnected() {
		return new File(BackupData.getImperdibleDrive() +"/ImperdibleSoftware/imperdible.inf").exists();
	}
}
