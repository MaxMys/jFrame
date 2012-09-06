package MPD.app.Model;

import MPD.sys.Core._Model;

public class Dropped_Message_Model extends _Model{

	public Dropped_Message_Model(){
		super();
	}
	
	/**
	 * 插入一条已读短信
	 * @param fromuser_id
	 * @param touser_id
	 * @param content
	 * @param time
	 * @return
	 */
	public int InsertMessage(int fromuser_id, int touser_id,String from_num,String to_num, String content,String time){
		Object[] obj = new Object[6];
		obj[0] = fromuser_id;
		obj[1] = touser_id;
		obj[2] = content;
		obj[3] = time;
		obj[4] = from_num;
		obj[5] = to_num;
		return this.update("insert into mpd_dropped_message values(id.nextval,?,?,?,?,?,?)",obj);
	}//end of InsertMessage()
}
