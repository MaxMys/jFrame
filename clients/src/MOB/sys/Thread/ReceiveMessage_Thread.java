package MOB.sys.Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import MOB.app.Controller.Message_Controller;
import MOB.sys.Common.Common;
import MOB.sys.Core._System;
import MOB.sys.Core._Thread;

/**
 * 工作接受者线程
 * @author max
 * @date 2012-8-10
 *
 */
public class ReceiveMessage_Thread extends _Thread{

	private int messagePort = 0;
	private  ServerSocket ss  = null;
	private Socket tmp = null;
	private ArrayList<String> messages = null;
	/**
	 * 构造函数 
	 * @param null
	 * @return null
	 * @access public
	 */
	public ReceiveMessage_Thread() {
		super();
		this.messagePort = Integer.parseInt(Common.C("Net_Config", "receiveport")) ;
		this.messages = new ArrayList<String>();
	}
	
	/**
	 * 覆盖run()
	 * @param null
	 * @return null
	 * @access public
	 */
	public void run(){
		try {
			this.ss = new ServerSocket(this.messagePort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		
		while(true){
			try {
				this.messages.clear();
				this.tmp = ss.accept();
				String total = _System.getNetWork().receiver(this.tmp);
				final String[] message = total.split("!");
				//调用线程接受信息
				new Thread(){
					public void run(){
						new Message_Controller().receive(message);
					}//end run()
				}.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
		}//end of if
		
	}//end of run()
	
}//end of class
