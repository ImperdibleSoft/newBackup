import java.io.File;

public class BackupData {
	
	/*	Data related to the enviroment	*/
	private static String enviroment = "dev";
	private static final String prod = "http://www.imperdiblesoft.com/APIs/backup.php?";
	private static final String dev = "http://dev.imperdiblesoft.com/APIs/backup.php?";
	private static final String local = "http://localhost/imperdiblesoft/APIs/backup.php?";
	
	/*	Data related to the user */
	static OUserData myUser;
	
	/*	Data related to updating Imperdible Backup Launcher	*/
	private static Double launcherVersion = new Double(2.011);
	private static String serverVersion = new String("http://backup.imperdiblesoft.com/launcherVersion.txt");
	
	private static String currentLocation = new String( System.getProperty("user.dir") +"/launcher.jar" );
	private static String serverLocation = new String( "http://backup.imperdiblesoft.com/updates/launcher.jar" );
	
	private static String favicon = new String("/img/material_logo_128.png");
	private static String sysTrayIcon = new String("");

	/*	Data related to Imperdible Backup Config	*/
	private static final String OS = detectOS();
	
	private static File imperdibleDrive = new File("");
	private static File[] syncFolders = new File[20];
	
	private static boolean showOnStartup = false;
	private static boolean startMinimized = false;
	private static boolean autoDetect = true;
	private static boolean showAtConnection = true;
	private static boolean autoBackup = false;
	
	private static Double usedSpace = new Double(0);
	private static Double backupSize = new Double(0);
	
	private static int selectedRow = 0;
    private static boolean warning = true;
    private static boolean statusControl = true;
    private static boolean stop = false;

	public static Double getLauncherVersion() {
		return launcherVersion;
	}
	public static String getServerVersion() {
		return serverVersion;
	}
	
	public static String getCurrentLocation() {
		return currentLocation;
	}
	public static String getServerLocation() {
		return serverLocation;
	}

	public static String getFavicon() {
		return favicon;
	}
	public static String getSysTrayIconString() {
		return sysTrayIcon;
	}
	public static void setSysTrayIconString(String imageRoute) {
		sysTrayIcon = imageRoute;
	}
	
	public static String getOs() {
		return OS;
	}
	
	public static File getImperdibleDrive() {
		return imperdibleDrive;
	}
	public static void setImperdibleDrive(File imperdibleDrive) {
		BackupData.imperdibleDrive = imperdibleDrive;
	}
	public static File[] getSyncFolders() {
		return syncFolders;
	}
	public static void setSyncFolders(File[] syncFolders) {
		BackupData.syncFolders = syncFolders;
	}

	public static boolean isShowOnStartup() {
		return showOnStartup;
	}
	public static void setShowOnStartup(boolean showAtStartup) {
		BackupData.showOnStartup = showAtStartup;
	}
	public static boolean isStartMinimized() {
		return startMinimized;
	}
	public static void setStartMinimized(boolean startMinimized) {
		BackupData.startMinimized = startMinimized;
	}
	public static boolean isAutoDetect() {
		return autoDetect;
	}
	public static void setAutoDetect(boolean autoDetect) {
		BackupData.autoDetect = autoDetect;
	}
	public static boolean isShowAtConnection() {
		return showAtConnection;
	}
	public static void setShowAtConnection(boolean showAtConnection) {
		BackupData.showAtConnection = showAtConnection;
	}
	public static boolean isAutoBackup() {
		return autoBackup;
	}
	public static void setAutoBackup(boolean autoBackup) {
		BackupData.autoBackup = autoBackup;
	}

	public static Double getUsedSpace() {
		return usedSpace;
	}
	public static void setUsedSpace(Double usedSpace) {
		BackupData.usedSpace = usedSpace;
	}
	public static Double getBackupSize() {
		return backupSize;
	}
	public static void setBackupSize(Double backupSize) {
		BackupData.backupSize = backupSize;
	}

	public static int getSelectedRow() {
		return selectedRow;
	}
	public static void setSelectedRow(int selectedRow) {
		BackupData.selectedRow = selectedRow;
	}
	public static boolean isWarning() {
		return warning;
	}
	public static void setWarning(boolean warning) {
		BackupData.warning = warning;
	}
	public static boolean isStatusControl() {
		return statusControl;
	}
	public static void setStatusControl(boolean statusControl) {
		BackupData.statusControl = statusControl;
	}
	public static boolean isStop() {
		return stop;
	}
	public static void setStop(boolean stop) {
		BackupData.stop = stop;
	}

	private static String detectOS(){

		String temp = System.getProperty("os.name");
		String myOS = null;
		
		if(temp.indexOf("indows")!=-1 && temp.indexOf("10")!=-1){
			myOS="windows10";
			setSysTrayIconString("/img/white-icon.png");
			
		}else if(temp.indexOf("indows")!=-1 && temp.indexOf("8")!=-1){
			myOS="windows8";
			setSysTrayIconString("/img/white-icon.png");
			
		}else if(temp.indexOf("indows")!=-1 && temp.indexOf("7")!=-1){
			myOS="windows7";
			setSysTrayIconString("/img/white-icon.png");
			
		}else  if(temp.indexOf("indows")!=-1 && temp.indexOf("ista")!=-1){
			myOS="windowsVista";
			setSysTrayIconString("/img/white-icon.png");
			
		}else if(temp.indexOf("indows")!=-1){
			myOS="windowsXP";
			setSysTrayIconString("/img/white-icon.png");
			
		}else if(temp.indexOf("inux")!=-1){
			myOS="linux";
			setSysTrayIconString("/img/white-icon.png");
			
		}else{
			myOS="mac";
			setSysTrayIconString("/img/black-icon.png");
		}
		
		setSysTrayIconString("src/img/material_logo_128.png");
		return myOS;
	}
	
	public static String getUrl(){
		if(enviroment == "prod"){
			return prod;
			
		}else if(enviroment == "dev"){
			return dev;
			
		}else if(enviroment == "local"){
			return local;
			
		}else{
			return null;
		}
	}
}
