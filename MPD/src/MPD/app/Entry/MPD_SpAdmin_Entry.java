package MPD.app.Entry;

public class MPD_SpAdmin_Entry {
  
	private int ID;
	private String ADMIN_NAME;
	private String ADMIN_PASSWORD;
	private long ADMIN_ALIVE;
	private String ADMIN_IP;
	private int ADMIN_LEVEL;
	public MPD_SpAdmin_Entry() {
		super();
	}
	public MPD_SpAdmin_Entry(int iD, String aDMIN_NAME, String aDMIN_PASSWORD,
			long aDMIN_ALIVE, String aDMIN_IP, int aDMIN_LEVEL) {
		super();
		ID = iD;
		ADMIN_NAME = aDMIN_NAME;
		ADMIN_PASSWORD = aDMIN_PASSWORD;
		ADMIN_ALIVE = aDMIN_ALIVE;
		ADMIN_IP = aDMIN_IP;
		ADMIN_LEVEL = aDMIN_LEVEL;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getADMIN_NAME() {
		return ADMIN_NAME;
	}
	public void setADMIN_NAME(String aDMINNAME) {
		ADMIN_NAME = aDMINNAME;
	}
	public String getADMIN_PASSWORD() {
		return ADMIN_PASSWORD;
	}
	public void setADMIN_PASSWORD(String aDMINPASSWORD) {
		ADMIN_PASSWORD = aDMINPASSWORD;
	}
	public long getADMIN_ALIVE() {
		return ADMIN_ALIVE;
	}
	public void setADMIN_ALIVE(long aDMINALIVE) {
		ADMIN_ALIVE = aDMINALIVE;
	}
	public String getADMIN_IP() {
		return ADMIN_IP;
	}
	public void setADMIN_IP(String aDMINIP) {
		ADMIN_IP = aDMINIP;
	}
	public int getADMIN_LEVEL() {
		return ADMIN_LEVEL;
	}
	public void setADMIN_LEVEL(int aDMINLEVEL) {
		ADMIN_LEVEL = aDMINLEVEL;
	}
	
	
}
