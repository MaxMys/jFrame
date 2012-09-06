package MAN.sys.Thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import MAN.sys.Core._Controller;
import MAN.sys.Core._System;
import MAN.sys.Core._Thread;
import MAN.sys.Struct._CallBack;


public class Worker_Thread extends _Thread{

	//回调函数
	private _CallBack callBack = null;
	private HashMap<String,Object> request = null;
	//controller对象
	private MAN.sys.Core._Controller c = null;
	//调用controller的method
	private Method method = null;
	//参数列表
	private Class[] param = null;
	//结果对象
	private Object result = null;
	private Class<?> controller = null;
	private String methodName = null;
	/**
	 * 构造函数
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
	 * 覆盖run方法
	 * @param null
	 * @return null
	 * @access public
	 */
	public void run() {
		_System.getWorkerThread().add(this);
		//尝试实例化
		try {
			this.c = (_Controller) controller.newInstance();
			//获取参数类
			this.param = new Class[]{ HashMap.class };
			
			this.method = controller.getMethod(methodName, this.param );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		//正常获取到方法之后，调用正常流程
		try{
			//设置参数
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
		//出工作池
		_System.getWorkerThread().remove(this);
		//最后执行回调
		this.callBack();
	}//end of run()
	/**
	 * 回调
	 */
	private void  callBack(){
		if(this.callBack != null)
			this.callBack.exec();
	}//end of callBack()
	
}//end of class
