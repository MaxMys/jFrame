package MPD.app.Model;

import java.util.ArrayList;
import java.util.HashMap;

import MPD.app.Entry.MPD_Client_Entry;
import MPD.app.Entry.MPD_SpAdmin_Entry;
import MPD.sys.Core._Model;



public class SpControl_Model extends _Model {
	
	
	public String sp_addfee(String phonenum,String price){
		float fprice=Float.parseFloat(price);
		HashMap tmp=new HashMap();
		tmp.put("phone",phonenum);
		 ArrayList<MPD_Client_Entry> a = 
		    	(ArrayList<MPD_Client_Entry>)
		    	this.filed("*")
							.table("MPD_CLIENT")
								.where(tmp)
									.select(MPD_Client_Entry.class);
		 if(a==null||a.isEmpty())
			 
		  {
			 return "无该用户";
		  }
		 
		 Object[] object2={fprice,phonenum};
		 int i=this.update("update Client set FEE=FEE+? where PHONE=?",object2);
		 return "充值成功，充值金额为: "+price+" 剩余话费为: "+a.get(0).getFEE();
		
	}
	
	public String register(String phonenum,String password,String name,String idcard){
		HashMap tmp=new HashMap();
		tmp.put("IDCARD",idcard);
		 ArrayList<MPD_Client_Entry> a = 
		    	(ArrayList<MPD_Client_Entry>)
		    	this.filed("*")
							.table("MPD_CLIENT")
								.where(tmp)
									.select(MPD_Client_Entry.class);
		 HashMap hm=new HashMap();
		 hm.put("PHONE", phonenum);
		 hm.put("NAME",name);
		 hm.put("PASSWORD", password);
		 hm.put("IDCARD", idcard);
		 long currenttime =System.currentTimeMillis();
		 hm.put("REGISTIME", currenttime);
		if(a==null||a.isEmpty())
		
		{int i=this.update("insert into MPD_CLIENT(ID,PHONE,NAME,PASSWORD,IDCARD,REGISTIME,ALIVE,FEE,IP,USED,WEATHER) VALUES (id.nextval,?,?,?,?,null,0,null,0,0)",hm);
		return "注册成功 电话号码为："+phonenum+" 密码为："+password+" 注册时间为:"+currenttime;
		}
		return "注册失败  该用户已存在";	
	}
	
	public String unregister(String phonenum)
	{  
		HashMap tmp=new HashMap();
		tmp.put("phone",phonenum);
		 ArrayList<MPD_Client_Entry> a = 
		    	(ArrayList<MPD_Client_Entry>)
		    	this.filed("*")
							.table("MPD_CLIENT")
								.where(tmp)
									.select(MPD_Client_Entry.class);
		 if(a==null||a.isEmpty()){
			 return "注销失败   无该用户";
		 }
	   int i=this.update("delete from MPD_CLIENT where PHONE=?",phonenum);
	   if(i==0){
		   return "注销失败";
	   }
	   return "注销成功";
	}
	public String startservice(String phonenum)
	{    
		HashMap tmp=new HashMap();
		tmp.put("phone",phonenum);
		 ArrayList<MPD_Client_Entry> a = 
		    	(ArrayList<MPD_Client_Entry>)
		    	this.filed("*")
							.table("MPD_CLIENT")
								.where(tmp)
									.select(MPD_Client_Entry.class);
		 if(a==null||a.isEmpty())			 
		 {
			 return "无该用户";
		 }
		 else if(a.get(0).getWEATHER()==1)
		 {
			 return "该用户已开通服务"; 
		 }
		 else if((a.get(0).getFEE()-5)<0)
		 {
			 return "余额不足";
		 }
		 int i=this.update("update MPD_CLIENT set WEATHER=1,FEE=FEE-5,USED=USED+5 where PHONE=?", phonenum);
		 return "已开通该业务 的余额为"+a.get(0).getFEE();
	}
	public String stopservice(String phonenum)
	{
		HashMap tmp=new HashMap();
		tmp.put("phone",phonenum);
		 ArrayList<MPD_Client_Entry> a = 
		    	(ArrayList<MPD_Client_Entry>)
		    	this.filed("*")
							.table("MPD_CLIENT")
								.where(tmp)
									.select(MPD_Client_Entry.class);
		 if(a==null||a.isEmpty())			 
		 {
			 return "无该用户";
		 }
		 else if(a.get(0).getWEATHER()==0)
		 {
			 return "该用户未开通服务"; 
		 }

		 int i=this.update("update MPD_CLIENT set WEATHER=1 where PHONE=?", phonenum);
		 return "已关闭该业务 余额为"+a.get(0).getFEE();
		
	}
	
	   /**
	    * 获取管理员列表
	    * @param pagesize 页面大小
	    * @return ArrayList<MPD_Client_Entry>
	    */
	   @SuppressWarnings("unchecked")
	   public ArrayList<MPD_SpAdmin_Entry> getList(int level){
	      HashMap<String,Object> hm = new HashMap<String,Object>();
	      hm.put("admin_level", level);
		  return ( ArrayList<MPD_SpAdmin_Entry> )
				  this.table("MPD_SpAdmin")
				  		.where(hm)
				  			.select(MPD_SpAdmin_Entry.class);
	   }//end of getList()
	   
	   /**
	    * 删除某个管理员
	    * @param id int
	    * @return int result
	    * @access public
	    */
	   public int delete(int id) {
		   return this.update("DELETE FROM mpd_spadmin WHERE id=?", id);
	   }//end of delete

	   public int deleteByName(String name){
		   return this.update("DELETE FROM mpd_spadmin WHERE name=?",name);
	   }
	   /**
	    *  添加管理员
	    * @param name
	    * @param password
	    * @param level
	    * @return
	    */
	   public int addAdmin(String name,String password,int level) {
		   return this.update("INSERT INTO mpd_spadmin VALUES(id.nextval,?,?,?,?,?)", 
				   new Object[]{"10.10.20.208",name,password,System.currentTimeMillis(),level});
	   }
	   
	   /**
	    * 用户确认 
	    * @param name
	    * @param password
	    * @return
	    */
	   public int userConfirm(String name,String password) {
		   HashMap<String,Object> hm = new HashMap<String,Object>();
		   hm.put("admin_name", name);
		   hm.put("admin_password", password);
		   ArrayList<MPD_SpAdmin_Entry> sp = (ArrayList<MPD_SpAdmin_Entry>)
				   this.table("mpd_spadmin")
				   			.where(hm)
				   				.select(MPD_SpAdmin_Entry.class);
		   if(sp == null || sp.isEmpty())
			   return 0;
		   return sp.get(0).getID();
	   }
}
