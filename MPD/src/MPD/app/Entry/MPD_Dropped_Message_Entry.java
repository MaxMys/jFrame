package MPD.app.Entry;

public class MPD_Dropped_Message_Entry {

	private int ID;
	private int FROM_USER;
	private int TO_USER;
	private String CONTENT;
	private String TIME;
	private String FROM_NUMBER;
	private String TO_NUMBER;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getFROM_USER() {
		return FROM_USER;
	}
	public void setFROM_USER(int fROM_USER) {
		FROM_USER = fROM_USER;
	}
	public int getTO_USER() {
		return TO_USER;
	}
	public void setTO_USER(int tO_USER) {
		TO_USER = tO_USER;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getTIME() {
		return TIME;
	}
	public void setTIME(String tIME) {
		TIME = tIME;
	}
	public String getFROM_NUMBER() {
		return FROM_NUMBER;
	}
	public void setFROM_NUMBER(String fROM_NUMBER) {
		FROM_NUMBER = fROM_NUMBER;
	}
	public String getTO_NUMBER() {
		return TO_NUMBER;
	}
	public void setTO_NUMBER(String tO_NUMBER) {
		TO_NUMBER = tO_NUMBER;
	}
	
}
