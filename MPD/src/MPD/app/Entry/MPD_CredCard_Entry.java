package MPD.app.Entry;

public class MPD_CredCard_Entry {
     private int ID;
     private String PASSWORD;
     private float PRICE;
    
   
	public MPD_CredCard_Entry() {
		super();
	}
	public MPD_CredCard_Entry(int iD, String pASSWORD, float pRICE) {
		super();
		ID = iD;
		PASSWORD = pASSWORD;
		PRICE = pRICE;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String PRICE() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public float getPRICE() {
		return PRICE;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPRICE(float pRICE) {
		PRICE = pRICE;
	}
	
     
}
