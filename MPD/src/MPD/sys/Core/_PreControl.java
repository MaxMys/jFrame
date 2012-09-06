package MPD.sys.Core;

import java.util.HashMap;


public class _PreControl {
    
	private String commend_pc;
	private String tadress_pc;
	private String neirong_pc;
	private String length_pc;
    private String sadress_pc;
    private final String MATHODNAME="method";
    private final String CONTROLLERNAME="controller";
    
    public String getlength(HashMap hm)
	{
		length_pc=(String)hm.get("length");
		return length_pc;
	}
    
    public String getneirong(HashMap hm)
	{
		neirong_pc=(String)hm.get("neirong");
		return neirong_pc;
	}
    
    public String getcommend_pc(HashMap hm)
	{
		commend_pc=(String)hm.get("commend");
		return commend_pc;
	}
    
    public String gettadressorsppassword(HashMap hm)
	{
		tadress_pc=(String)hm.get("tadress");
		return tadress_pc;
	}
    
	public String getsadressorspname(HashMap hm)
	{
		sadress_pc=(String)hm.get("sadress");
		return sadress_pc;
	}
	
	public HashMap decide(HashMap hm)
	{
		commend_pc=(String)hm.get("commend");
		tadress_pc=(String)hm.get("tadress");
		neirong_pc=(String)hm.get("neirong");
		length_pc=(String)hm.get("length");
		
		//发送短信命令
		if(commend_pc=="CMD001")
		{  
		  
		  //查询话费
		  if(tadress_pc=="001")
		  {
			 hm.put(MATHODNAME, "queryfee") ;
			 hm.put(CONTROLLERNAME, "queryfeecontroller");
			 return hm;
			 //return "queryfee";
		  }
		  
		  //天气预报
		  if(tadress_pc=="002")
		  {   
			  hm.put(MATHODNAME, "queryweather") ;
			  hm.put(CONTROLLERNAME, "queryweathercontroller");
			  return hm;
			//  return "queryweather";
		  } 
		  
		  //话费充值
		  if(tadress_pc=="003")
		  {
			  hm.put(MATHODNAME, "addfee") ;
			  hm.put(CONTROLLERNAME, "addfeecontroller");
			  return hm;
			  //return "addfee";
		  } 
		  
		  //普通短信
		  else
		  {   
			  hm.put(MATHODNAME, "sendtother") ;
			  hm.put(CONTROLLERNAME, "sendtoothercontroller");
			  return hm;
			  //return "sendtoother";
		  }
		  
			
		}
		
		//登陆命令
		else if(commend_pc=="CMD002")
		{
			hm.put(MATHODNAME, "logon") ;
			hm.put(CONTROLLERNAME, "logoncontroller");
			return hm;
		}
		
		//下线命令
		else if(commend_pc=="CMD003")
		{   
			hm.put(MATHODNAME, "logoff") ;
			hm.put(CONTROLLERNAME, "logoffcontroller");
			return hm;
		}
		
		//操作员
		else if(commend_pc=="AMD001")
		{   
		   hm.put(CONTROLLERNAME, "spcontroller");
           String[] ss = neirong_pc.split(",");
           if(ss[0]=="chf")
           {   
        	   hm.put(MATHODNAME, "addfeesp") ;
        	   return hm;
           }
           else if(ss[0]=="kh")
           {   
        	   hm.put(MATHODNAME, "register") ;
        	   return hm;
           }	
           else if(ss[0]=="zx")
           {   
        	   hm.put(MATHODNAME, "unregister") ;
        	   return hm;
           }
			
		}
		return null;
		
	}
	
	
	
	
}
