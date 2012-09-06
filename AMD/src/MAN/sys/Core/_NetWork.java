package MAN.sys.Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import MAN.sys.Common.Common;
import MAN.sys.Helper.Base64_Helper;


public class _NetWork {

	//对象本身
	private static _NetWork netWork = null;
	//发送端口标记
	private static int sendPort = 11111;
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
	
	//接受者
	public synchronized String receiver(Socket s) throws IOException{
		System.out.println(s);
		//获取输入流
		this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.tmpin = this.in.readLine();
		//	this.tmpin = JSON_Helper.JsontoMap(this.tmpin);
		this.tmpin = Base64_Helper.decode(this.tmpin);
		return this.tmpin;
	}//end of receiver
	
	
	public void sendMessage(Socket s, String m) throws IOException {
		PrintWriter out = new PrintWriter(s.getOutputStream());
		//加密
		String tmpString = Base64_Helper.encoded(m);
		out.println(tmpString);
		out.flush();
	}//end of sendMessage
	/**
	 * 连接服务器
	 * @return null
	 * @access private
	 * @param null
	 * @synchronized
	 */
	public synchronized Socket getSocket(int localPort) {
		if(localPort == 0)
			localPort = this.getNextPort();
		//初始化发送Socket
		Socket s = null;
		try {
			//通知服务器
			s = new Socket(
					Common.C("Net_Config", "sever"),
					Integer.parseInt(Common.C("Net_Config", "serveport"))
					);
			PrintWriter out = new PrintWriter(s.getOutputStream());
			int tmpPort = localPort;
			out.println(tmpPort);
			out.flush();
			ServerSocket ss= new ServerSocket(tmpPort);
			s = ss.accept();
			ss.close();
		}catch(Exception e){
			 e.printStackTrace();
		}//end of catch
		return s;
		
	}//end of getSocket()

	
	private int getNextPort(){
		return this.sendPort++;
	}
}//end of _NetWork
