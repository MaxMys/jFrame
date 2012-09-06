package MPD.app.Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import MPD.app.Entry.MPD_Client_Entry;
import MPD.app.Entry.MPD_Message_Entry;
import MPD.sys.Core._Model;

public class Message_Model extends _Model {

	
	public  Message_Model() {
		super();
	}
	
	
	
	/**
	 * 插入一条未读短信
	 * @param fromuser_id
	 * @param touser_id
	 * @param content
	 * @param time
	 * @return
	 */
	public int InsertMessage(int fromuser_id, int touser_id, String content,String time,String from_num,String to_num){
		Object[] obj = new Object[6];
		obj[0] = fromuser_id;
		obj[1] = touser_id;
		obj[2] = content;
		obj[3] = time;
		obj[4] = from_num;
		obj[5] = to_num;
		return this.update("insert into mpd_message values(id.nextval,?,?,?,?,?,?)",obj);
	}//end of InsertMessage()
	
	/**
	 * 获取当前用户所有的message
	 * @param user_id
	 * @return Object
	 */
	public Object GetMessages(int user_id){
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("to_user", user_id);
		return this.table("mpd_message")
				 .where(hm)
				     .select(MPD_Message_Entry.class);
	}//end of GetMessages
	
	/**
	 * 通过id获得一条信息
	 * @param Messageid
	 * @return Object
	 */
	public MPD_Message_Entry GetMessageById(int Messageid){
		HashMap<String,Object> tmp = new HashMap<String,Object>();
		tmp.put("id", Messageid);
		ArrayList<MPD_Message_Entry> al = (ArrayList<MPD_Message_Entry>) this.table("mpd_message")
				.where(tmp)
					.select(MPD_Message_Entry.class);
		return al.get(0);
	}
	
	/**
	 * 通过id删除一条信息
	 * @param Messageid
	 * @return void
	 */
	public void DeleteMessageById(int Messageid){
		this.update("delete from MPD_Message where id = "+Messageid);
	}
}//end of Message_Model
