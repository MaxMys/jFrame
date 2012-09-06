package MOB.sys.Core;

import java.util.Vector;

import frame.MainFrame;
import MOB.sys.Common.Common;
import MOB.sys.Struct._LinkedList;
import MOB.sys.Thread.ReceiveMessage_Thread;
import MOB.sys.Thread.KeepAlive_Thread;
import MOB.sys.Thread.Log_Thread;
import MOB.sys.Thread.Serve_Thread;
import MOB.sys.Thread.Worker_Thread;

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
		//��ʼ���������
		_System.setUnProcessed(new _LinkedList<String>());
		//��ʼ�������߽��̳�
		_System.setWorkerThread(new _LinkedList<Worker_Thread>());
		
		
		//������������߳�
		_System.setAcceptThread(new ReceiveMessage_Thread());
		_System.getAcceptThread().start();
		
		//���������߳�
		_System.setKeepAliveThread(new KeepAlive_Thread());
		_System.getKeepAliveThread().start();
		
		//�����������߳�
		_System.setServe(new Serve_Thread());
		_System.getServe().start();
		
		//��ʼ��������Ϣ
		try {
			Vector v = new Vector();
			v.addElement("adm");
			MainFrame frame = new MainFrame(v);
			_System.setFrame(frame);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("������ʱ ��"+_System.getBenchMark().elapsedTime("start", null));
		//System.out.println("�����ڴ�ʹ�� ��"+_System.getBenchMark().elapsedMem("start", null));
		//System.out.println(_System.getSelf());
		System.out.println("ϵͳ�����ɹ�!");
		
	}//end of start()
	
}//end of class Runtie()
