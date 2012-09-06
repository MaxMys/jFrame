package MPD.sys.Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import MPD.sys.Helper.Base64_Helper;
import MPD.sys.Helper.JSON_Helper;

public class _NetWork {

	//对象本身
	private static _NetWork netWork = null;
	//String container
	private String tmpout = null;
	private String tmpin = null;
	//printWriter对象
	private PrintWriter out = null;
	//BufferedReader 独享
	private BufferedReader in = null;
	/**
	 * 构造方法
	 * @param null
	 * @access private
	 * @return null
	 */
	private _NetWork() {} //end of construct
	
	/**
	 * 静态构造方法
	 * @return _NetWork
	 * @access public
	 * @param null
	 */
	public static _NetWork getNetWork () {
		if(_NetWork.netWork == null)
			return new _NetWork();
		return _NetWork.netWork;
	}//end of getNetWork()
	
	/**
	 * 发送者函数
	 * @param  s Socket
	 * @param  message Object
	 * @throws Exception 
	 */
	public synchronized void  sender( Socket s, Object message) throws Exception{
		//如果连接失效
		if(s.isClosed())
			return ;

		//如果没有返回值
		if(message == null)
			message = "ack";
		
		if( !(message instanceof String)){
			message = "error";
			throw new Exception("不合法信息");
		}//end of if
		//获取加密串
		this.tmpout = Base64_Helper.encoded((String)message);
		//获取Socket流
		this.out = new PrintWriter(s.getOutputStream());
		//输出
		this.out.println(this.tmpout);
		//flush
		this.out.flush();
	
	}//end of sender
	
	public synchronized String receiver(Socket s) throws IOException{
		//获取输入流
		this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.tmpin = this.in.readLine();
		this.tmpin = Base64_Helper.decode(this.tmpin);
		return this.tmpin;
	}
}//end of _NetWork
