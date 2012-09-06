package MPD.app.Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import MPD.app.Entry.MPD_Client_Entry;
import MPD.app.Entry.MPD_SpAdmin_Entry;
import MPD.sys.Common.Tools;
import MPD.sys.Core._Model;


public class Client_Model extends _Model {
	
	
	
		public Client_Model() {
			super();
		}//end of construct
		
		/**
		 * 添加一个用户
		 * @return
		 */
		public int insert(String name,String num) {
			return this.update(
					"insert into MPD_CLIENT(" +
					"ID," +
					"PHONE," +
					"NAME," +
					"PASSWORD," +
					"IDCARD," +
					"REGISTIME," +
					"ALIVE," +
					"FEE," +
					"IP," +
					"USED," +
					"WEATHER) " +
					"VALUES (" +
					"id.nextval," +
					"?," +
					"?," +
					"?," +
					"?," +
					"?," +
					"?," +
					"?," +
					"?," +
					"?)"
					, new Object[]{
						num==null?null:num,				
						name,
						null, 
						"510113199202101132",
						Tools.getTime(), 
						System.currentTimeMillis(), 
						1, 
						null,
						0,
						null
					});
		}
		/**
		 * 删除一个用户，用名字
		 * @param name String 用户的名字
		 * @return int 
		 * @access public
		 */
		public int deleteByName(String name) {
			return this.update("DELETE FROM mpd_client WHERE name=?",name);
		}
		/**
		 * 舒心用户状态
		 * @param ip
		 * @param mark
		 * @return id 用户id
		 */
		@SuppressWarnings("unchecked")
		public int keepAlive(String ip,String mark){
		   
		   long currenttime=System.currentTimeMillis();
		   Object[] object={ip,currenttime,mark};
		   ArrayList<MPD_Client_Entry> a = (ArrayList<MPD_Client_Entry>) this.querry("select * from MPD_CLIENT where PHONE=?",mark,MPD_Client_Entry.class);   
		   if(a==null||a.isEmpty())
			   return 0;
		   this.update("update MPD_CLIENT set IP=? , ALIVE=? where PHONE=?",object);
		   return a.get(0).getID();
	   }
	   
	   /**
	    * 通过用户电话，获取用户ID
	    * @param number
	    * @return
	    */
	   @SuppressWarnings("unchecked")
		public int getIdByNumber(String number){
			HashMap<String,Object> tmp =new HashMap<String,Object>();
			
		    tmp.put("phone",number);
		    ArrayList<MPD_Client_Entry> a = 
		    	(ArrayList<MPD_Client_Entry>)
		    	this.filed("ID")
							.table("MPD_CLIENT")
								.where(tmp)
									.select(MPD_Client_Entry.class);
		    
		    if( a == null || a.isEmpty() )
		    	return 0;
			return a.get(0).getID();
			//return e.getId();
		}//end of getIdByNumber();
	   
	   
	   /**
	    * 获取用户列表
	    * @param pagesize 页面大小
	    * @return ArrayList<MPD_Client_Entry>
	    */
	   @SuppressWarnings("unchecked")
	   public ArrayList<MPD_Client_Entry> getList(){
		  return ( ArrayList<MPD_Client_Entry> )
				  this.table("MPD_CLIENT")
				  		.select(MPD_Client_Entry.class);
	   }//end of getList()
	   
	 
	 
	   
	   
}//end of Client_Model
