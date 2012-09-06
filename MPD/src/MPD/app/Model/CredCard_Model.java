package MPD.app.Model;

import java.util.ArrayList;

import MPD.app.Entry.MPD_CredCard_Entry;
import MPD.sys.Core._Model;

public class CredCard_Model extends _Model{

	/**
	 * 构造函数
	 * @return ulll
	 * @access public
	 * @param null
	 */
	public CredCard_Model() {
		super();
	}//end of Construct();
	
	/**
	 * 插入一张充值卡
	 * @param password
	 * @param price
	 * @return int
	 * @access public
	 */
	public int insertCard(String password,float price){
		MPD_CredCard_Entry E = new MPD_CredCard_Entry(0,password,price);
		return this.insert(E);
//		return this.update("INSERT INTO mpd_credcard VALUES(id.nextval,?,?)",
//					new Object[]{password,price});
	}	
	/**
	 * 查看所有充值卡
	 * @return ArrayList<MPD_CredCard_Entry> 
	 * @access public
	 * @param null
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<MPD_CredCard_Entry> getList(){
		return (ArrayList<MPD_CredCard_Entry>)this.table("MPD_CredCard").select(MPD_CredCard_Entry.class);
	}
	
	/**
	 * 删除一张充值卡
	 * @param id
	 * @return int
	 * @access public
	 */
	public int delete(int id){
		//return this.where("id=188").update(new MPD_CredCard_Entry(0,null,55));
		return this.delete(new MPD_CredCard_Entry(id, null, 0));
		//return this.update("DELETE FROM mpd_credcard WHERE id=?", id);
	}
}//end of class
