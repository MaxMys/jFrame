package MPD.app.Model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


import MPD.app.Entry.MPD_Client_Entry;
import MPD.app.Entry.MPD_CredCard_Entry;
import MPD.app.Entry.MPD_Weather_Entry;

import MPD.sys.Common.Tools;
import MPD.sys.Core._Model;

public class FeeControll_Model extends _Model{
   
	public HashMap<String,Float> queryfee(String phoneNumber){
		
		ArrayList<MPD_Client_Entry> a = (ArrayList<MPD_Client_Entry>) this.querry("select USED from MPD_CLIENT where PHONE =?",phoneNumber,MPD_Client_Entry.class);
		if(a==null||a.isEmpty())
		{
		return null;
		}
		float shengyu= a.get(0).getFEE();
		float yiyong=a.get(0).getUSED();
		HashMap<String,Float> h=new HashMap();
		h.put("yiyong", yiyong);
		h.put("shengyu", shengyu);
		return h;
	}
	
	public int addfee(String phoneNumber,String cardpw)
	{   
		
		ArrayList<MPD_CredCard_Entry> a = (ArrayList<MPD_CredCard_Entry>) 
		this.querry("select price from MPD_CREDCARD where PASSWORD=?",
				cardpw,
				           MPD_CredCard_Entry.class);
		
		if(a==null||a.isEmpty())
		{   
			System.out.println(cardpw);
			return 0;
			
		}
		float price=a.get(0).getPRICE();
		Object[] object2={price,phoneNumber};
		int i= this.update("update MPD_Client set FEE=FEE+? where PHONE=?",object2);
		if(i==0)
		{
			return 0;
		}
		return 1;
	}
	
	public String queryweather(String time,String sadress) throws ParseException
	{   
		ArrayList<MPD_Client_Entry> b = (ArrayList<MPD_Client_Entry>) this.querry("select * from MPD_CLIENT where PHONE =?",sadress,MPD_Client_Entry.class);
		if(b==null||b.isEmpty()){   
			return "无该用户 或者该用户数据已被删除";
		}
		
		int i=b.get(0).getWEATHER();
		if(i==0){
			return "您尚未开通该服务";
		}
		
		ArrayList<MPD_Weather_Entry> a = (ArrayList<MPD_Weather_Entry>) this.querry("select weather_state,weather_date from MPD_WEATHER where WEATHER_DATE =?",time,MPD_Weather_Entry.class);
		if(a==null||a.isEmpty()){   
			return "抱歉，系统中无相关天气数据";
		}
		
		String weatherstate=a.get(0).getWEATHER_STATE();
		String weatherdate=a.get(0).getWEATHER_DATE();
		
		return weatherdate+"  "+weatherstate;
	}
	
}


