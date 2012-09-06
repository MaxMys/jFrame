package MPD.sys.Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import MPD.sys.Common.Common;
import MPD.sys.Core._System;
import MPD.sys.Core._Thread;
import MPD.sys.Helper.Base64_Helper;
import MPD.sys.Struct.ProcessObject;

/**
 * �ű��߳�
 * @author max
 * @date 2012-8-12
 *
 */
public class Script_Thread extends _Thread {

	private Socket s = null;
	private ServerSocket ss= null;
	/**
	 * ���췽��
	 * @param null
	 * @return null
	 * @access public
	 */
	public Script_Thread (){
		super();
	}//end of Script_Thread();
	
	/**
	 * ����run()
	 * @return null
	 * @param null
	 * @access public
	 */
	public void run(){
		String line = null;
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to Mobile Message PlatForm - Script Thread");
		while(true){
		  System.out.println("input a new command");
		  line = s.nextLine();
		  this.s = this.getSocket(3333);
		  try {
			  	this.sendMessage(this.s,line);
			  	String tmp = this.receiver(this.s) ;
			  	if( tmp == null)
			  		System.out.println("û������");
			  	else
				    System.out.println(tmp.replaceAll("!!", "\n"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		}//end of while
	}
//������
	public synchronized String receiver(Socket s) throws IOException{
		//��ȡ������
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String tmpin = in.readLine();
		//	this.tmpin = JSON_Helper.JsontoMap(this.tmpin);
		tmpin = Base64_Helper.decode(tmpin);
		return tmpin;
	}//end of receiver
	
	
	public void sendMessage(Socket s, String m) throws IOException {
		PrintWriter out = new PrintWriter(s.getOutputStream());
		//����
		String tmpString = Base64_Helper.encoded(m);
		out.println(tmpString);
		out.flush();
	}//end of sendMessage
	/**
	 * ���ӷ�����
	 * @return null
	 * @access private
	 * @param null
	 * @synchronized
	 */
	public synchronized Socket getSocket(int localPort) {
		//��ʼ������Socket
		Socket s = null;
		try {
			//֪ͨ������
			s = new Socket("localhost",Integer.parseInt(Common.C("Net_Config", "serverport")));
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
}//end of Script_Thread
