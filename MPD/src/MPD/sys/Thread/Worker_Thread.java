package MPD.sys.Thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;

import MPD.sys.Common.Common;
import MPD.sys.Core._Controller;
import MPD.sys.Core._System;
import MPD.sys.Core._Thread;
import MPD.sys.Struct.ProcessObject;

/**
 * �������߳�
 * @author max
 * @date 2012-8-7
 *
 */
public class Worker_Thread extends _Thread{

	//������Ϣ
	private HashMap<String,String> request = null;
	//�������
	private ProcessObject po = null;
	//controller����
	private _Controller c = null;
	//����controller��method
	private Method method = null;
	//�����б�
	private Class[] param = null;
	//�������
	private Object result = null;
	/**
	 * ���췽��
	 * @param request HashMap<String,String>
	 * @access publi
	 * @return null
	 */
	public Worker_Thread(HashMap<String,String> request,ProcessObject po) {
		this.request = request;
		this.po = po;
	}//end of construct
	
	/**
	 * ��дrun()
	 * @param null
	 * @return null
	 * @access public
	 */
	public void run(){
		//���빤����
		_System.getWorkerThread().addLast(this);
		//����ʵ����
		try {
			this.c = Common.A(this.request.get("controller"),request);
			
		} catch (Exception e) {
			e.printStackTrace();
			_System.getProcessed().addLast(new ProcessObject(po.s, "No such Controller"));
			return;
		}//end of catch
		
		
		try{
			
			//��ȡ������
			this.param = new Class[]{ HashMap.class };
			//��ȡ��������
			this.method = this.c.getClass().getMethod(
					this.request.get("method"),
					this.param 
					);
		}catch(Exception e){
			e.printStackTrace();
			_System.getProcessed().addLast(new ProcessObject(po.s, "Method not found"));
			return;
		}
	
		
		//������ȡ������֮�󣬵�����������
		try{
			//���ò���
			this.result = this.method.invoke( this.c ,  new Object[]{ this.request } );
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			_System.getProcessed().addLast(new ProcessObject(po.s, "param error"));
			return;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			_System.getProcessed().addLast(new ProcessObject(po.s, "param error"));
			return;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			_System.getProcessed().addLast(new ProcessObject(po.s, "param error"));
			return;
		}//end of catch
		//������������
		this.addToProcessed(this.po.s, this.result);
		//��������
		_System.getWorkerThread().remove(this);
		
	}//end of run
	
	
	private void addToProcessed(Socket s,Object result){
		_System.getProcessed().addLast(new ProcessObject(s, result));
	}
	
}//end of class Worker_Thread
