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
			 return "�޸��û�";
		  }
		 
		 Object[] object2={fprice,phonenum};
		 int i=this.update("update Client set FEE=FEE+? where PHONE=?",object2);
		 return "��ֵ�ɹ�����ֵ���Ϊ: "+price+" ʣ�໰��Ϊ: "+a.get(0).getFEE();
		
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
		return "ע��ɹ� �绰����Ϊ��"+phonenum+" ����Ϊ��"+password+" ע��ʱ��Ϊ:"+currenttime;
		}
		return "ע��ʧ��  ���û��Ѵ���";	
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
			 return "ע��ʧ��   �޸��û�";
		 }
	   int i=this.update("delete from MPD_CLIENT where PHONE=?",phonenum);
	   if(i==0){
		   return "ע��ʧ��";
	   }
	   return "ע���ɹ�";
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
			 return "�޸��û�";
		 }
		 else if(a.get(0).getWEATHER()==1)
		 {
			 return "���û��ѿ�ͨ����"; 
		 }
		 else if((a.get(0).getFEE()-5)<0)
		 {
			 return "����";
		 }
		 int i=this.update("update MPD_CLIENT set WEATHER=1,FEE=FEE-5,USED=USED+5 where PHONE=?", phonenum);
		 return "�ѿ�ͨ��ҵ�� �����Ϊ"+a.get(0).getFEE();
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
			 return "�޸��û�";
		 }
		 else if(a.get(0).getWEATHER()==0)
		 {
			 return "���û�δ��ͨ����"; 
		 }

		 int i=this.update("update MPD_CLIENT set WEATHER=1 where PHONE=?", phonenum);
		 return "�ѹرո�ҵ�� ���Ϊ"+a.get(0).getFEE();
		
	}
	
	   /**
	    * ��ȡ����Ա�б�
	    * @param pagesize ҳ���С
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
	    * ɾ��ĳ������Ա
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
	    *  ��ӹ���Ա
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
	    * �û�ȷ�� 
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
