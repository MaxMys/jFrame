package MAN.sys.Thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import MAN.sys.Core._Controller;
import MAN.sys.Core._System;
import MAN.sys.Core._Thread;
import MAN.sys.Struct._CallBack;


public class Worker_Thread extends _Thread{

	//�ص�����
	private _CallBack callBack = null;
	private HashMap<String,Object> request = null;
	//controller����
	private MAN.sys.Core._Controller c = null;
	//����controller��method
	private Method method = null;
	//�����б�
	private Class[] param = null;
	//�������
	private Object result = null;
	private Class<?> controller = null;
	private String methodName = null;
	/**
	 * ���캯��
	 * @param request
	 * @param callBack
	 */
	public Worker_Thread(Class<?>  controller,String methodName,HashMap<String,Object> request , _CallBack callBack){
		this.setPriority(MAX_PRIORITY);
		this.controller = controller;
		this.methodName = methodName;
		this.request = request;
		this.callBack = callBack ;
	}//end of construct()
	
	
	/**
	 * ����run����
	 * @param null
	 * @return null
	 * @access public
	 */
	public void run() {
		_System.getWorkerThread().add(this);
		//����ʵ����
		try {
			this.c = (_Controller) controller.newInstance();
			//��ȡ������
			this.param = new Class[]{ HashMap.class };
			
			this.method = controller.getMethod(methodName, this.param );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		//������ȡ������֮�󣬵�����������
		try{
			//���ò���
			this.result = this.method.invoke( this.c ,  new Object[]{ this.request } );
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		//��������
		_System.getWorkerThread().remove(this);
		//���ִ�лص�
		this.callBack();
	}//end of run()
	/**
	 * �ص�
	 */
	private void  callBack(){
		if(this.callBack != null)
			this.callBack.exec();
	}//end of callBack()
	
}//end of class
