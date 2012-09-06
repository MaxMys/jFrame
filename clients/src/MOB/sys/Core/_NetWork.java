package MOB.sys.Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import MOB.sys.Common.Common;
import MOB.sys.Helper.Base64_Helper;
import MOB.sys.Helper.JSON_Helper;

public class _NetWork {

    //������
	private static _NetWork netWork = null;
	//���Ͷ˿ڱ��
	private static int sendPort = 11111;
	//String container
	private String tmpout = null;
	private String tmpin = null;
	//printWriter����
	private PrintWriter out = null;
	//BufferedReader ����
	private BufferedReader in = null;
	/**
	 * ���췽��
	 * @param null
	 * @access private
	 * @return null
	 */
	private _NetWork() {} //end of construct
	
	/**
	 * ��̬���췽��
	 * @return _NetWork
	 * @access public
	 * @param null
	 */
	public static _NetWork getNetWork () {
		if(_NetWork.netWork == null)
			return new _NetWork();
		return _NetWork.netWork;
	}//end of getNetWork()
	
	//������
	public synchronized String receiver(Socket s) throws IOException{
		//��ȡ������
		this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.tmpin = this.in.readLine();
		//	this.tmpin = JSON_Helper.JsontoMap(this.tmpin);
		this.tmpin = Base64_Helper.decode(this.tmpin);
		return this.tmpin;
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
		if(localPort == 0)
			localPort = this.getNextPort();
		//��ʼ������Socket
		Socket s = null;
		try {
			//֪ͨ������
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
	
		
	/**
	 * ��ȡ��һ�ζ˿�	
	 * @return int 
	 * @access private
	 * @param null;
	 */
	private int getNextPort(){
		return this.sendPort++;
	}
	
	/**
	 * �������ӽӿ�
	 * @param null
	 * @return null
	 * @access public
	 * @synchronized
	 */
	public  synchronized void keepalive() {
		//��ȡ�������Ӷ˿�
		int localPort = Integer.parseInt(Common.C("Net_Config", "keepalive"));
		Socket s = this.getSocket(localPort);

			//����绰����,��֤��ʽ
		String m = Common.C("Protocal_Config","alive")+"/"+Common.C("Sim_Config","number")+"/client";
		try {
			this.sendMessage(s,m);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if( ! s.isClosed()){
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
		}//if
	}//end of keepalive()
	
}//end of _NetWork
