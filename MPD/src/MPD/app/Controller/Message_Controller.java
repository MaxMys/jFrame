package MPD.app.Controller;
import java.util.ArrayList;
import java.util.HashMap;

import MPD.app.Entry.MPD_Message_Entry;
import MPD.app.Model.Client_Model;
import MPD.app.Model.Dropped_Message_Model;
import MPD.app.Model.Message_Model;
import MPD.sys.Common.Common;
import MPD.sys.Common.Tools;
import MPD.sys.Core._Controller;

/**
 * 基础短信控制器
 * @author max
 * @date 2012-8-8
 */
public class Message_Controller extends _Controller{

	public Message_Controller() {
		super();
	}//end of Construct()
	/**
	 * 发送短信
	 * @param fromuser String
	 * @param touser
	 */
	public String sendMessage(HashMap<String,Object> h) {
		    Message_Model m = (Message_Model) Common.M("Message_Model");
		    Client_Model c = (Client_Model) Common.M("Client_Model");
		    String fromuser = (String)h.get("fromuser") ;
            String touser =(String)h.get("touser") ;
            String content = (String)h.get("content");
            String time = Tools.getTime();
            int fromuser_id = c.getIdByNumber(fromuser);
            int touser_id = c.getIdByNumber(touser);
            if(fromuser_id == 0 || touser_id == 0)
            	return "error";
            m.InsertMessage(fromuser_id, touser_id, content, time,fromuser,touser);
            return "success";
	}
	
	
	/**
	 * 接受短信
	 * @param int user_id
	 */
	public void receiveMessage(HashMap<String,Object> hm) {
		String IP = (String)hm.get("ip");
		int i = (Integer) hm.get("user_id");
		//检测有没有短信
		Message_Model messageModel = (Message_Model)Common.M("Message_Model");
		ArrayList<MPD_Message_Entry> messages = (ArrayList<MPD_Message_Entry>)messageModel.GetMessages(i);
		this.SendMessage(IP, messages);
	}
	
	/**
	 * 短信确认
	 * @param Messageid int
	 */
	public void RecieveConfirm(HashMap<String,Object> hm){
		int id = Integer.parseInt((String) hm.get("Messageid")) ;
		Message_Model mm = new Message_Model();
		MPD_Message_Entry mme = mm.GetMessageById(id);
		int fromuser_id = mme.getFROM_USER();
		int touser_id = mme.getTO_USER();
		String from_num = mme.getFROM_NUMBER();
		String to_num = mme.getTO_NUMBER();
		String content = mme.getCONTENT();
		String time = mme.getTIME();
		Dropped_Message_Model dmm = new Dropped_Message_Model();
		dmm.InsertMessage(fromuser_id, touser_id, from_num, to_num, content, time);
		mm.DeleteMessageById(id);
	}//end of RecieveConfirm()
	
}//end of class Client_Message_Controller
