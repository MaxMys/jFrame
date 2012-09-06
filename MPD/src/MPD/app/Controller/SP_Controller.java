package MPD.app.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import MPD.app.Entry.MPD_CredCard_Entry;
import MPD.app.Entry.MPD_SpAdmin_Entry;
import MPD.app.Model.CredCard_Model;
import MPD.app.Model.SpControl_Model;
import MPD.sys.Common.Common;
import MPD.sys.Core._Controller;
/**
 * 用于控制sp操作员执行的操作 1 为用户充话费 2 为用户开户  3为用户注销 （4 为用户开通服务  5注销服务）
 * @author max
 *
 */
public class SP_Controller extends _Controller{
	SpControl_Model sm;
	
	/**
	 * 构造
	 */
	public SP_Controller()
	{
		sm=(SpControl_Model)Common.M("SpControl_Model");
	}
	
	/**
	 * 含参构造方法
	 * @param hm
	 */
	public SP_Controller(HashMap hm)
	{
		
	}
	
	/**
	 * sp操作员为用户充值话费
	 */
	public String sp_addfee(HashMap hm){
		String neirong = (String) hm.get("neirong");
		String[] ss=neirong.split(",");
		String phonenum=ss[1];
		String price=ss[2];
		return sm.sp_addfee(phonenum, price);
		
	}
	
	public String startservice(HashMap hm)
	{    
		 String neirong=(String) hm.get("neirong");
		 String[] ss=neirong.split(",");
		 String phonenum=ss[1];
		 
		 return sm.startservice(phonenum);	
	}
	
	public String stopSrvice(HashMap hm)
	{
		String neirong=(String)hm.get("neirong");
		String[] ss=neirong.split(",");
		String phonenum=ss[1];
		return sm.stopservice(phonenum);
	}
	
	/**
	 * 获取管理员列表
	 * @param hm null
	 * @return String
	 * @access public
	 */
	@SuppressWarnings("unchecked")
	public String getList(HashMap<String,String> hm){
		
		SpControl_Model c = (SpControl_Model)Common.M("SpControl_Model");
		//取出变量
		ArrayList<MPD_SpAdmin_Entry> al = c.getList(Integer.parseInt(hm.get("level")));
		
		Iterator<MPD_SpAdmin_Entry> t = al.iterator();
		
		ArrayList<HashMap<String,String>> tmpAl = new ArrayList<HashMap<String,String>>();
		HashMap<String,String>  tmpHm = new HashMap<String,String>();
		
		MPD_SpAdmin_Entry entry = null;
		
		while(t.hasNext()){
			tmpHm.clear();
			entry = t.next();
			tmpHm.put("ID", ""+entry.getID());
			tmpHm.put("NAME", entry.getADMIN_NAME());
			tmpHm.put("PASSWORD", entry.getADMIN_PASSWORD());
			tmpHm.put("ALIVE", ""+entry.getADMIN_ALIVE());
			tmpHm.put("IP", ""+entry.getID());
			tmpHm.put("LEVEL", ""+entry.getADMIN_LEVEL());
			//加入列表
			tmpAl.add(new HashMap<String,String>(tmpHm));
		}//end of while
		String result = this.arrayMaptoString(tmpAl);
		if(result.startsWith("!!"))
			return null;
		return result;
	}
	
	/**
	 * 删除某个管理员
	 * @param hms
	 * @return
	 */
	public String delete(HashMap<String,String> hm){
		int id = Integer.parseInt(hm.get("id"));
		int result = ((SpControl_Model)Common.M("SpControl_Model")).delete(id);
		if(result != 0)
			return "success";
		return "error";
	}
	
	/**
	 * 删除某个管理员通过名字
	 */
	public String deleteByName(HashMap<String,String> hm) {
		String name = hm.get("name");
		int result = ((SpControl_Model)Common.M("SpControl_Model")).deleteByName(name);
		if(result != 0)
			return "success";
		return "error";
	}
	
	/**
	 * 添加一个管理员
	 * @param name String
	 * @param password String
	 * @level level int
	 */
	public String insert(HashMap<String,String> hm) {
		String name = hm.get("name");
		String password = hm.get("password");
		password = Common.md5(password);
		int level = Integer.parseInt(hm.get("level"));
		int result = ((SpControl_Model)Common.M("SpControl_Model")).addAdmin(name, password, level);
		if(result != 0)
			return "success";
		else
			return "error";
	}
	
	/**
	 * 用户确认
	 * @param hm
	 * @return
	 */
	public String userConfirm(HashMap<String,String> hm) {
		String name = hm.get("name");
		String password = hm.get("password");
		password = Common.md5(password);
		int result  = ((SpControl_Model)Common.M("SpControl_Model")).userConfirm(name, password);
		if(result == 0)
			return "error";
		else 
			return "success";
	}
	
	/**
	 * 添加充值卡
	 * @return null
	 * @access public
	 * @param HashMap 
	 * @param password
	 */
	public String insFeeCard(HashMap<String,String> hm){
		String password = hm.get("password");
		float price = Float.parseFloat(hm.get("price")!=null?hm.get("price"):"0");
		password = Common.md5(password);
		CredCard_Model m = (CredCard_Model)Common.M("CredCard_Model");
		int result = m.insertCard(password, price);
		if(result == 0)
			return "error";
		return "success";
	}
	
	public String getFeeCardList(HashMap<String,String> hm){
		CredCard_Model c = (CredCard_Model)Common.M("CredCard_Model");
		//取出变量
		ArrayList<MPD_CredCard_Entry> al = c.getList();
		
		Iterator<MPD_CredCard_Entry> t = al.iterator();
		
		ArrayList<HashMap<String,String>> tmpAl = new ArrayList<HashMap<String,String>>();
		HashMap<String,String>  tmpHm = new HashMap<String,String>();
		
		MPD_CredCard_Entry entry = null;
		
		while(t.hasNext()){
			tmpHm.clear();
			entry = t.next();
			tmpHm.put("ID", ""+entry.getID());
			tmpHm.put("PASSWORD", entry.getPASSWORD());
			tmpHm.put("PRICE", ""+entry.getPRICE());
			//加入列表
			tmpAl.add((HashMap<String,String> )tmpHm.clone());
		}//end of while
		String result = this.arrayMaptoString(tmpAl);
		if(result.startsWith("!!"))
			return null;
		return result;
	}//end of insert
	
	/*
	 * 删除一张充值卡
	 * @param id int
	 * @return null
	 * @access public
	 */
	public String deleteFeeCard(HashMap<String,String> hm){
		int id = Integer.parseInt(hm.get("id"));
		CredCard_Model c = (CredCard_Model)Common.M("CredCard_Model");
		int result = c.delete(id);
		if(result == 0)
			return "error";
		else 
			return "success";
	}
	
}//end of class
