package MPD.sys.Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import MPD.sys.Common.Common;
import MPD.sys.Core._System;
import MPD.sys.Core._Thread;
import MPD.sys.Struct.ProcessObject;

/**
 * 接受者线程
 * @author max
 * @date 2012-8-7
 *
 */
public class Accepter_Thread extends _Thread{

	//服务端口
	private int serverPort ; 
	//客户端口
	private int clientPort ;
	//服务套接字
	private ServerSocket serverSocket = null;
	//临时连接套接字
	private Socket tmp = null;
	//重新建立套接字
	private Socket s = null;
	//信息容器
	private String message = null;
	//接收池
	private static LinkedList<MPD.sys.Struct.ProcessObject> unProcessed= null;
	
	/**
	 * 构造方法
	 */
	public Accepter_Thread () {
		//获取服务器端口
		this.serverPort =Integer.parseInt(Common.C("Net_Config", "serverport"));
	
	}//end of construct
	
	/**
	 * 复写run方法
	 */
	public void run () {
		
		
		//初始化服务套接字
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		
		while(true){
			
			try {
				this.tmp = this.serverSocket.accept();
				//获取客户端口
				this.clientPort =
					Integer.parseInt(
					new BufferedReader(
						new InputStreamReader(this.tmp.getInputStream())
					).readLine()
					);
				//回馈
				InetAddress ip = tmp.getInetAddress();
				this.tmp.close();
				//重链接
				this.s = new Socket(ip,this.clientPort);
			
				//获取请求信息
				this.message = _System.getNetWork().receiver(this.s);
				//组装新变量,并且存储
				_System.getUnProcessed().addLast(new ProcessObject(this.s, this.message));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
			
		
		}//end of while
		
	}//end of method run()
	
}//end of class Accepter_Thread
