package MPD.app.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import MPD.app.Entry.MPD_Client_Entry;
import MPD.app.Entry.MPD_Message_Entry;
import MPD.app.Model.Admin_Model;
import MPD.app.Model.Message_Model;
import MPD.app.Model.SpControl_Model;
import MPD.app.Model.Client_Model;
import MPD.sys.Common.Common;
import MPD.sys.Core._Controller;
/**
 * 控制用户登录状态 通过调用相应model来刷新数据库中ALIVE字段与IP字段
 * @author ttc
 *
 */
public class State_Controller extends _Controller{
    
	/**
	 * 构造方法
	 */
	public State_Controller(){
		super();
	}
	
	/**
	 * 刷新在线
	 * ip String IP地址
	 * mark String 用户标识
	 */
	@SuppressWarnings("unchecked")
	public void keepAlive(HashMap hm) {
		
		String IP=(String) hm.get("ip");
		String mark=(String)hm.get("mark");
		if(hm.get("level").equals("client")){
			Client_Model Client_Model =(Client_Model)Common.M("Client_Model");
			//I为其ID
			int i=Client_Model.keepAlive(IP, mark);
			HashMap<String,Object> t = new HashMap<String,Object>();
			t.put("ip", IP);
			t.put("user_id", i);
			new Message_Controller().receiveMessage(t);
			return ;
		}//end if
	    //获取管理员信息
	    Admin_Model Admin_Model = (Admin_Model)Common.M("Admin_Model");
	    int i=Admin_Model.keepAlive(IP, mark);
			    
	}//end of KeepAlive()
	
}//end of class
