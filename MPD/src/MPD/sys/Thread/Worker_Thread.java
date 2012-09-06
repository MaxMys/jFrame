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
 * 工作者线程
 * @author max
 * @date 2012-8-7
 *
 */
public class Worker_Thread extends _Thread{

	//请求信息
	private HashMap<String,String> request = null;
	//请求对象
	private ProcessObject po = null;
	//controller对象
	private _Controller c = null;
	//调用controller的method
	private Method method = null;
	//参数列表
	private Class[] param = null;
	//结果对象
	private Object result = null;
	/**
	 * 构造方法
	 * @param request HashMap<String,String>
	 * @access publi
	 * @return null
	 */
	public Worker_Thread(HashMap<String,String> request,ProcessObject po) {
		this.request = request;
		this.po = po;
	}//end of construct
	
	/**
	 * 覆写run()
	 * @param null
	 * @return null
	 * @access public
	 */
	public void run(){
		//加入工作池
		_System.getWorkerThread().addLast(this);
		//尝试实例化
		try {
			this.c = Common.A(this.request.get("controller"),request);
			
		} catch (Exception e) {
			e.printStackTrace();
			_System.getProcessed().addLast(new ProcessObject(po.s, "No such Controller"));
			return;
		}//end of catch
		
		
		try{
			
			//获取参数类
			this.param = new Class[]{ HashMap.class };
			//获取方法对象
			this.method = this.c.getClass().getMethod(
					this.request.get("method"),
					this.param 
					);
		}catch(Exception e){
			e.printStackTrace();
			_System.getProcessed().addLast(new ProcessObject(po.s, "Method not found"));
			return;
		}
	
		
		//正常获取到方法之后，调用正常流程
		try{
			//设置参数
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
		//放入输出缓冲池
		this.addToProcessed(this.po.s, this.result);
		//出工作池
		_System.getWorkerThread().remove(this);
		
	}//end of run
	
	
	private void addToProcessed(Socket s,Object result){
		_System.getProcessed().addLast(new ProcessObject(s, result));
	}
	
}//end of class Worker_Thread
