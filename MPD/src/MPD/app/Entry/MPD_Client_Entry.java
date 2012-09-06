package MPD.app.Entry;

/**
 * MPD_CLIENT表的实体
 * @author 毛
 * 
 *
 */

public class MPD_Client_Entry {

	private int ID;
	private String PHONE;
	private String NAME; 
	private String IDCARD;
	private String REGISTIME;
	private long ALIVE;
	private float FEE;
	private float USED;
	private String IP;
	private int WEATHER;
	public MPD_Client_Entry(int iD, String pHONE, String nAME, String iDCARD,
			String rEGISTIME, long aLIVE, float fEE, float uSED, String iP,
			int wEATHER) {
		super();
		ID = iD;
		PHONE = pHONE;
		NAME = nAME;
		IDCARD = iDCARD;
		REGISTIME = rEGISTIME;
		ALIVE = aLIVE;
		FEE = fEE;
		USED = uSED;
		IP = iP;
		WEATHER = wEATHER;
	}
	
	public MPD_Client_Entry() {
		super();
	}

	public int getWEATHER() {
		return WEATHER;
	}
	public void setWEATHER(int wEATHER) {
		WEATHER = wEATHER;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getIDCARD() {
		return IDCARD;
	}
	public void setIDCARD(String iDCARD) {
		IDCARD = iDCARD;
	}
	public String getREGISTIME() {
		return REGISTIME;
	}
	public void setREGISTIME(String rEGISTIME) {
		REGISTIME = rEGISTIME;
	}
	public long getALIVE() {
		return ALIVE;
	}
	public void setALIVE(long aLIVE) {
		ALIVE = aLIVE;
	}
	public float getFEE() {
		return FEE;
	}
	public void setFEE(float fEE) {
		FEE = fEE;
	}
	public void setUSED(float uSED)
	{
		USED=uSED;
	}
	public float getUSED()
	{
		return USED;
	}
	
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	
	
}
