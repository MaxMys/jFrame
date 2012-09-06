package MAN.sys.Core;

import java.util.Vector;

import MAN.app.View.ManagerFrame;
import MAN.sys.Common.Common;
import MAN.sys.Struct._LinkedList;
import MAN.sys.Thread.Log_Thread;
import MAN.sys.Thread.Serve_Thread;
import MAN.sys.Thread.Worker_Thread;


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

		
		//�����������߳�
		_System.setServe(new Serve_Thread());
		_System.getServe().start();
		
		try {
			Vector v = new Vector();
			v.addElement("manager");
			ManagerFrame frame = new ManagerFrame(v);
			frame.setVisible(true);
			_System.setManagerframe(frame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println("������ʱ ��"+_System.getBenchMark().elapsedTime("start", null));
		//System.out.println("�����ڴ�ʹ�� ��"+_System.getBenchMark().elapsedMem("start", null));
		//System.out.println(_System.getSelf());
		System.out.println("ϵͳ�����ɹ�!");
		
	}//end of start()
	
}//end of class Runtie()
