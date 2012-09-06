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

	//������
	private static _NetWork netWork = null;
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
	
	/**
	 * �����ߺ���
	 * @param  s Socket
	 * @param  message Object
	 * @throws Exception 
	 */
	public synchronized void  sender( Socket s, Object message) throws Exception{
		//�������ʧЧ
		if(s.isClosed())
			return ;

		//���û�з���ֵ
		if(message == null)
			message = "ack";
		
		if( !(message instanceof String)){
			message = "error";
			throw new Exception("���Ϸ���Ϣ");
		}//end of if
		//��ȡ���ܴ�
		this.tmpout = Base64_Helper.encoded((String)message);
		//��ȡSocket��
		this.out = new PrintWriter(s.getOutputStream());
		//���
		this.out.println(this.tmpout);
		//flush
		this.out.flush();
	
	}//end of sender
	
	public synchronized String receiver(Socket s) throws IOException{
		//��ȡ������
		this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.tmpin = this.in.readLine();
		this.tmpin = Base64_Helper.decode(this.tmpin);
		return this.tmpin;
	}
}//end of _NetWork
