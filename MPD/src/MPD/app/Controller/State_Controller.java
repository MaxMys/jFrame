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
 * �����û���¼״̬ ͨ��������Ӧmodel��ˢ�����ݿ���ALIVE�ֶ���IP�ֶ�
 * @author ttc
 *
 */
public class State_Controller extends _Controller{
    
	/**
	 * ���췽��
	 */
	public State_Controller(){
		super();
	}
	
	/**
	 * ˢ������
	 * ip String IP��ַ
	 * mark String �û���ʶ
	 */
	@SuppressWarnings("unchecked")
	public void keepAlive(HashMap hm) {
		
		String IP=(String) hm.get("ip");
		String mark=(String)hm.get("mark");
		if(hm.get("level").equals("client")){
			Client_Model Client_Model =(Client_Model)Common.M("Client_Model");
			//IΪ��ID
			int i=Client_Model.keepAlive(IP, mark);
			HashMap<String,Object> t = new HashMap<String,Object>();
			t.put("ip", IP);
			t.put("user_id", i);
			new Message_Controller().receiveMessage(t);
			return ;
		}//end if
	    //��ȡ����Ա��Ϣ
	    Admin_Model Admin_Model = (Admin_Model)Common.M("Admin_Model");
	    int i=Admin_Model.keepAlive(IP, mark);
			    
	}//end of KeepAlive()
	
}//end of class
