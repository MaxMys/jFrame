package MPD.sys.Core;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import MPD.sys.Common.Common;
import MPD.sys.Struct._LinkedList;
import MPD.sys.Thread.Accepter_Thread;
import MPD.sys.Thread.Log_Thread;
import MPD.sys.Thread.Reply_Thread;
import MPD.sys.Thread.Script_Thread;
import MPD.sys.Thread.Serve_Thread;
import MPD.sys.Thread.Worker_Thread;

/**
 * 
 * ����ʱ
 * @author max
 * @date 2012-8-4
 * 
 */
public class _Runtime {

	private static _Runtime self = null;
	
	
	/**
	 * ���캯��
	 */
	private _Runtime() {}
	
	/**
	 * ��ʼ������,����_System.
	 * @return MyRuntime
	 * @access public
	 * @return MyRuntime
	 */
	public static _Runtime init(){
		if(_Runtime.self == null)
			_Runtime.self = new _Runtime();
		return _Runtime.self;
	}
	/**
	 * ��ȡRuntime
	 * @return MyRuntime self
	 * @access public
	 * @param null
	 */
	public static _Runtime getMyRuntime() {
		return self;
	}
	
	/**
	 * ��Index.java����
	 * ���� runTime()
	 * @param null
	 * @return null
	 * @access public
	 */
	public void start() {
		
		//������־д�߳�
		if(Common.C("Log_Config","log").equals("on")) {
			
			_System.setLogThread(new Log_Thread());
			_System.getLogThread().start();
			
		}
		//��ʼ�������
		_System.setNetWork(_NetWork.getNetWork());
		//��ʼ���������ӹ�����
		_System.setConnPoolManager(_ConnPoolManager.getConnectionPool());
		//��ʼ�������
		_System.setProcessed(new _LinkedList<MPD.sys.Struct.ProcessObject>());
		//��ʼ���������
		_System.setUnProcessed(new _LinkedList<MPD.sys.Struct.ProcessObject>());
		//��ʼ�������߽��̳�
		_System.setWorkerThread(new _LinkedList<Worker_Thread>());
		
		//��������ظ��߳�
		_System.setReplyThread(new Reply_Thread());
		_System.getReplyThread().start();
		
		//������������߳�
		_System.setAcceptThread(new Accepter_Thread());
		_System.getAcceptThread().start();
		
		//�����������߳�
		_System.setServeThread(new Serve_Thread());
		_System.getServeThread().start();
		
		//�����ű�����,����̨�߳�
		_System.setScriptThread(new Script_Thread());
		_System.getScriptThread().start();
			
		System.out.println("������ʱ ��"+_System.getBenchMark().elapsedTime("start", null));
		System.out.println("�����ڴ�ʹ�� ��"+_System.getBenchMark().elapsedMem("start", null));
		System.out.println(_System.getSelf());
		System.out.println("ϵͳ�����ɹ�!");
		
	}//end of start()
	
}//end of class Runtie()
