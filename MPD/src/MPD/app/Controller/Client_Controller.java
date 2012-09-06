package MPD.app.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import MPD.app.Entry.MPD_Client_Entry;
import MPD.app.Model.Client_Model;
import MPD.sys.Common.Common;
import MPD.sys.Common.Tools;
import MPD.sys.Core._Controller;

public class Client_Controller extends _Controller {

	public Client_Controller(){
		
	}//end of Client_Controller
	
	/**
	 * 新建用户
	 * @param hm
	 * @return name String
	 */
	public String newClient(HashMap<String,Object> hm) {
		String name = (String)hm.get("name");
		String num =  (String)hm.get("phone");
		Client_Model c = (Client_Model)Common.M("Client_Model");
		int result = c.insert(name,num);
		if(result == 0)
			return "error";
		return "success";
	}
	
	/**
	 * 通过用户名字删除一个用户
	 * @param name String
	 * @return String
	 * @access public
	 */
	public String deleteByName(HashMap<String,String> hm){
		String name = hm.get("name");
		Client_Model c = (Client_Model)Common.M("Client_Model");
		int result = c.deleteByName(name);
		if(result == 0)
			return "error";
		return "success";
	}
	/**
	 * 获取所有用户列表
	 * @return null
	 * @access public
	 * @param null
	 */
	public  String getList(HashMap<String,Object> hm){
		Client_Model c = (Client_Model)Common.M("Client_Model");
		//取出变量
		ArrayList<MPD_Client_Entry> al = c.getList();
		
		Iterator<MPD_Client_Entry> t = al.iterator();
		
		ArrayList<HashMap<String,String>> tmpAl = new ArrayList<HashMap<String,String>>();
		HashMap<String,String>  tmpHm = new HashMap<String,String>();
		
		MPD_Client_Entry entry = null;
		
		while(t.hasNext()){
			tmpHm.clear();
			entry = t.next();
			tmpHm.put("ID", ""+entry.getID());
			tmpHm.put("PHONE",entry.getPHONE() );
			tmpHm.put("NAME", entry.getNAME());
			tmpHm.put("IDCARD", entry.getIDCARD());
			tmpHm.put("REGISTIME", entry.getREGISTIME());
			tmpHm.put("ALIVE", ""+entry.getALIVE());
			tmpHm.put("FEE", ""+entry.getFEE());
			tmpHm.put("USED", ""+entry.getUSED());
			tmpHm.put("IP", entry.getIP());
			tmpHm.put("WEATHER", ""+entry.getWEATHER());
			//加入列表
			tmpAl.add(new HashMap<String,String>(tmpHm));
		}//end of while
		return this.arrayMaptoString(tmpAl);
	}//end of getList
	
	
	@SuppressWarnings("unchecked")
	public  String onlineList(HashMap<String,Object> hm) {
		Client_Model c = (Client_Model)Common.M("Client_Model");
		//取出变量
		ArrayList<MPD_Client_Entry> al = c.getList();
		
		Iterator<MPD_Client_Entry> t = al.iterator();
		
		ArrayList<HashMap<String,String>> tmpAl = new ArrayList<HashMap<String,String>>();
		HashMap<String,String>  tmpHm = new HashMap<String,String>();
		
		MPD_Client_Entry entry = null;
		
		long now = System.currentTimeMillis();
		
		while(t.hasNext()){
			tmpHm.clear();
			entry = t.next();
			
			if(entry.getALIVE() < now - 20000)
				continue;
			tmpHm.put("ID", ""+entry.getID());
			tmpHm.put("PHONE",entry.getPHONE() );
			tmpHm.put("NAME", entry.getNAME());
			tmpHm.put("IDCARD", entry.getIDCARD());
			tmpHm.put("REGISTIME", entry.getREGISTIME());
			tmpHm.put("ALIVE", ""+entry.getALIVE());
			tmpHm.put("FEE", ""+entry.getFEE());
			tmpHm.put("USED", ""+entry.getUSED());
			tmpHm.put("IP", entry.getIP());
			tmpHm.put("WEATHER", ""+entry.getWEATHER());
			//加入列表
			tmpAl.add( new HashMap<String, String>(tmpHm));
		}//end of while;
		String result = this.arrayMaptoString(tmpAl);
		if(result!=null)
			return result;
		return null;
	}
}//end of class
