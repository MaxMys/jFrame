package MPD.sys.Core;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import MPD.app.Entry.MPD_Message_Entry;
import MPD.app.Model.Client_Model;
import MPD.sys.Common.Common;
import MPD.sys.Helper.JSON_Helper;
import MPD.sys.Struct.ProcessObject;

/**
 * �������������
 * @author max
 * @date 2012-8-7
 *
 */
public abstract class _Controller {

	/**
	 * ���췽��
	 * @access public
	 * @return null
	 * @param null
	 */
	public _Controller() {
		
		
	}//_Controller()
	
	/**
	 * @param null
	 * @access protected
	 * @return String className
	 */
	protected String getName() {
		return this.getClass().getSimpleName();
	}//end of getName()
	
	/**
	 * ���õײ㷽��,������Ϣ���ƶ�ip,
	 * ������
	 * @return null
	 * @param host String
	 * @param port in �����������Ϊ0��Ĭ�Ͽͻ����Ž��ܶ˿�
	 * @param message String
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	protected void Send(String host , int port , String message) throws UnknownHostException, IOException{
		if(port == 0)
			port = Integer.parseInt(Common.C("Net_Config", "clientmessageport"));
		
		_System.getProcessed().addLast(
				new ProcessObject(
						new Socket(host,port)
						, message)
				);
	}//end of Send

	/**
	 * ���Ͷ���,����һ������
	 * @param host String ������ַ
	 * @param from ������
	 * @param message ��Ϣ
	 */
	protected void SendMessage(String host,int id,String from,String message,String time) {

		//"Э������/������/��Ϣ/ʱ��"
		String cmd = Common.C("Protocal_Config","messagetoclient")+"/"+id+"/"+from+"/"+message+"/"+time;
		
		try {
			this.Send(host, Integer.parseInt(Common.C("Net_Config", "clientmessageport")), cmd);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end of SendMessage()
	
	protected void SendMessage(String host,ArrayList<MPD_Message_Entry> messages){
		if(messages==null || messages.isEmpty())
			return;
		 Iterator<MPD_Message_Entry> t = messages.iterator();
		 String tmpStr = "";
		 MPD_Message_Entry entry = null;
		 Client_Model cm = (Client_Model)Common.M("Client_Model");
		 while(t.hasNext()){
			 entry = t.next();
			 tmpStr +=Common.C("Protocal_Config","messagetoclient")+"/"+entry.getID()+"/"+entry.getFROM_NUMBER()+"/"+entry.getCONTENT()+"/"+entry.getTIME();
			 tmpStr +="!";
		 }//end of while
		 try {
			this.Send(host, Integer.parseInt(Common.C("Net_Config", "clientmessageport")), tmpStr);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end of SendMessage
	
	/**
	 * ֧�ֽ�map����ת�����ַ���
	 * @param al HashMap<String,Object>[]
	 * @return String
	 * @access protected
	 */
	protected String arrayMaptoString(HashMap<String,Object>[] hms){
		String returnStr = "";
		for(int i = 0 ; i < hms.length ; i ++){
			returnStr += JSON_Helper.toJson(hms[i]);
			//����ָ���
			returnStr += "!!";
		}//end of for
		return returnStr;
	}//end arrayMaptoString
	
	/**
	 * ֧�ֽ�mapArrayListת�����ַ���
	 * @param al HashMap<String,Object>[]
	 * @return String
	 * @access protected
	 */
	protected String arrayMaptoString(ArrayList<HashMap<String,String>> al){
		String returnStr = "";
		Iterator<HashMap<String,String>> t = al.iterator();
		while(t.hasNext()){
			returnStr += JSON_Helper.toJson(t.next());
			//����ָ���
			returnStr += "!!";
		}//end of for
		return returnStr;
	}//end arrayMaptoString
	
	/**
	 * ��stringת����map[]
	 * @param str
	 * @return HashMap<String,Object>[]
	 * @access protected
	 */
	@SuppressWarnings("unchecked")
	protected HashMap<String,Object>[] stringToMapArray(String str){
		String[] tmp = str.split("!!");
		HashMap<String,Object>[] hms = new HashMap[tmp.length];
		for(int i = 0 ; i < tmp.length ; i ++){
			hms[i] = (HashMap<String, Object>) JSON_Helper.JsontoMap(tmp[i]);
		}//end of for
		return hms;
	}//end of stringToMapArray
	
}//end of _Controller
