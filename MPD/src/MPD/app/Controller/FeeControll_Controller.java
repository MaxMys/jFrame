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
 * ���ڿ��Ʋ�ѯ���� ��ѯ���� ��ֵ����
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
	 * ���췽��
	 */
	public FeeControll_Controller()
	{
	  um=(FeeControll_Model)Common.M("FeeControll_Model");
	}
	

	/**
	 * �۳�����
	 * @param number
	 * @param fee
	 * @return int
	 * @access public
	 */
	public int meFee(HashMap<String,String> hm){
		return 0;
	}
	/**
	 * �û���ѯ����
	 * @param String sadressԴ��ַ �����ͷ��绰����
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
			   "���û���: "+yiyong+" ʣ�໰��: "+shengyu,
			   Tools.getTime(), 
			   "002", 
			   sadress.toString());
		}//end of if
		return "success";
	}//querryfee
	
	/**
	 * �û��仰��
	 * @param sadress String    
	 * @param content String  ��������  �����������
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
				Common.md5(nr)//���ﱣ���������
				   );
		if(resultofaf!=0)
		{
			client.InsertMessage(001,
				   c.getIdByNumber(sadress), 
				   "��ֵ�ɹ�",
				   Tools.getTime(), 
				   "004", 
				   sadress.toString());
		}//end of if
		return "success";
	}//end addfee
	
	/**
	 * �û���ѯ����
	 * @param neirong String  �������� ��������������ڲ�ѯ��Ӧ����    
	 * @param sadress String  ���ͷ����� �����ж��Ƿ�ͨҵ��
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
