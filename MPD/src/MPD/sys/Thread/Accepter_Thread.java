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
 * �������߳�
 * @author max
 * @date 2012-8-7
 *
 */
public class Accepter_Thread extends _Thread{

	//����˿�
	private int serverPort ; 
	//�ͻ��˿�
	private int clientPort ;
	//�����׽���
	private ServerSocket serverSocket = null;
	//��ʱ�����׽���
	private Socket tmp = null;
	//���½����׽���
	private Socket s = null;
	//��Ϣ����
	private String message = null;
	//���ճ�
	private static LinkedList<MPD.sys.Struct.ProcessObject> unProcessed= null;
	
	/**
	 * ���췽��
	 */
	public Accepter_Thread () {
		//��ȡ�������˿�
		this.serverPort =Integer.parseInt(Common.C("Net_Config", "serverport"));
	
	}//end of construct
	
	/**
	 * ��дrun����
	 */
	public void run () {
		
		
		//��ʼ�������׽���
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		
		while(true){
			
			try {
				this.tmp = this.serverSocket.accept();
				//��ȡ�ͻ��˿�
				this.clientPort =
					Integer.parseInt(
					new BufferedReader(
						new InputStreamReader(this.tmp.getInputStream())
					).readLine()
					);
				//����
				InetAddress ip = tmp.getInetAddress();
				this.tmp.close();
				//������
				this.s = new Socket(ip,this.clientPort);
			
				//��ȡ������Ϣ
				this.message = _System.getNetWork().receiver(this.s);
				//��װ�±���,���Ҵ洢
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
