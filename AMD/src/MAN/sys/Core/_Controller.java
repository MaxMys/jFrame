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
	
	//发送端口标记
	private  int sendPort = 11111;
	//String container
	private String tmpout = null;
	private String tmpin = null;
	//printWriter对象
	private PrintWriter out = null;
	//BufferedReader 独享
	private BufferedReader in = null;
	//private
    private Socket s = null;
	
	/**
	 * 构造函数
	 * @param null
	 * @return null
	 * @access public
	 */
	public _Controller() {
	}//end of construct()

	/**
	 * 发送者函数
	 * @param  s Socket
	 * @param  message Object
	 * @throws Exception 
	 */
	protected String  request( String message) throws Exception{
		//如果没有返回值
		if(message == null){
			message ="error";
			throw new Exception("不合法信息");
		}//end of if
		//获取socket
		this.s = _System.getNetWork().getSocket(0);
		//发送信息
		_System.getNetWork().sendMessage(this.s, message);
		//获取值
		String tmp = _System.getNetWork().receiver(s);
		//关闭连接
		if(!this.s.isClosed())
			s.close();
		return tmp;
	}//end of sender
	
	/**
	 * 支持将map数组转化成字符串
	 * @param al HashMap<String,Object>[]
	 * @return String
	 * @access protected
	 */
	protected String arrayMaptoString(HashMap<String,Object>[] hms){
		String returnStr = "";
		for(int i = 0 ; i < hms.length ; i ++){
			returnStr += JSON_Helper.toJson(hms[i]);
			//加入分隔符
			returnStr += "!!";
		}//end of for
		return returnStr;
	}//end arrayMaptoString
	
	/**
	 * 将string转化成map[]
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
