package MAN.sys.Core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import MAN.sys.Common.Common;
import MAN.sys.Helper.Base64_Helper;
import MAN.sys.Helper.JSON_Helper;


public class _Controller {
	
	//���Ͷ˿ڱ��
	private  int sendPort = 11111;
	//String container
	private String tmpout = null;
	private String tmpin = null;
	//printWriter����
	private PrintWriter out = null;
	//BufferedReader ����
	private BufferedReader in = null;
	//private
    private Socket s = null;
	
	/**
	 * ���캯��
	 * @param null
	 * @return null
	 * @access public
	 */
	public _Controller() {
	}//end of construct()

	/**
	 * �����ߺ���
	 * @param  s Socket
	 * @param  message Object
	 * @throws Exception 
	 */
	protected String  request( String message) throws Exception{
		//���û�з���ֵ
		if(message == null){
			message ="error";
			throw new Exception("���Ϸ���Ϣ");
		}//end of if
		//��ȡsocket
		this.s = _System.getNetWork().getSocket(0);
		//������Ϣ
		_System.getNetWork().sendMessage(this.s, message);
		//��ȡֵ
		String tmp = _System.getNetWork().receiver(s);
		//�ر�����
		if(!this.s.isClosed())
			s.close();
		return tmp;
	}//end of sender
	
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
	 * ��stringת����map[]
	 * @param str
	 * @return HashMap<String,Object>[]
	 * @access protected
	 */
	@SuppressWarnings("unchecked")
	protected HashMap<String,Object>[] stringToMapArray(String str){
		System.out.println(str);
		String[] tmp = str.split("!!");
		HashMap<String,Object>[] hms = new HashMap[tmp.length];
		for(int i = 0 ; i < tmp.length ; i ++){
			hms[i] = (HashMap<String, Object>) JSON_Helper.JsontoMap(tmp[i]);
		}//end of for
		return hms;
	}//end of stringToMapArray
	

}
