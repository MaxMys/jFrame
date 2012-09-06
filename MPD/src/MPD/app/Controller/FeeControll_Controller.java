package MPD.app.Controller;

import java.text.ParseException;
import java.util.HashMap;

import MPD.app.Model.Client_Model;
import MPD.app.Model.FeeControll_Model;
import MPD.app.Model.Message_Model;
import MPD.sys.Common.Common;
import MPD.sys.Common.Tools;
import MPD.sys.Core._Controller;
/**
 * 用于控制查询话费 查询天气 充值话费
 * @author ttc
 *
 */


public class FeeControll_Controller extends _Controller {
    
	
	String sadress;
	String cardno;
	String cardpw;
	HashMap<String,Float> resultofqf;
	String resultofqw;
	int resultofaf;
	FeeControll_Model um;
	/**
	 * 构造方法
	 */
	public FeeControll_Controller()
	{
	  um=(FeeControll_Model)Common.M("FeeControll_Model");
	}
	

	/**
	 * 扣除话费
	 * @param number
	 * @param fee
	 * @return int
	 * @access public
	 */
	public int meFee(HashMap<String,String> hm){
		return 0;
	}
	/**
	 * 用户查询话费
	 * @param String sadress源地址 即发送方电话号码
	 * @return void
	 * @access public
	 */
	public String queryfee(HashMap<String,String> hm)
	{
		Client_Model c = (Client_Model)Common.M("Client_Model");
		Message_Model client = (Message_Model)Common.M("Message_Model");
		sadress=(String)hm.get("sadress");
		resultofqf=um.queryfee(sadress);
		if(resultofqf!=null)
		{
		float yiyong= resultofqf.get("yiyong");
		float shengyu= resultofqf.get("shengyu");
		client.InsertMessage(001,
			   c.getIdByNumber(sadress), 
			   "已用话费: "+yiyong+" 剩余话费: "+shengyu,
			   Tools.getTime(), 
			   "002", 
			   sadress.toString());
		}//end of if
		return "success";
	}//querryfee
	
	/**
	 * 用户充话费
	 * @param sadress String    
	 * @param content String  短信内容  里面存有密码
	 * @return void 
	 * @access public
	 */
	public String addfee(HashMap hm)
	{   
		Client_Model c = (Client_Model)Common.M("Client_Model");
		Message_Model client = (Message_Model)Common.M("Message_Model");
		
		sadress=(String)hm.get("sadress");
		String nr=(String)hm.get("content");
		System.out.println(nr);
		resultofaf=um.addfee(sadress,
				Common.md5(nr)//这里保存的是密码
				   );
		if(resultofaf!=0)
		{
			client.InsertMessage(001,
				   c.getIdByNumber(sadress), 
				   "充值成功",
				   Tools.getTime(), 
				   "004", 
				   sadress.toString());
		}//end of if
		return "success";
	}//end addfee
	
	/**
	 * 用户查询天气
	 * @param neirong String  短信内容 里面存有日期用于查询相应天气    
	 * @param sadress String  发送方号码 用于判断是否开通业务
	 * @return String
	 * @throws ParseException 
	 */
	public String queryweather(HashMap<String,String> hm) throws ParseException
	{   
		Client_Model c = (Client_Model)Common.M("Client_Model");
		Message_Model client = (Message_Model)Common.M("Message_Model");
		String time="1999-09-09";
		String sadress=(String) hm.get("sadress");
	    resultofqw=  um.queryweather(time,sadress);
	    client.InsertMessage(001,
				   c.getIdByNumber(sadress), 
				   resultofqw,
				   Tools.getTime(), 
				   "003", 
				   sadress.toString());
	    return "success";
	}//end of queryweather();
}//end of class
