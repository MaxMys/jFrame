package MPD.sys.Thread;

import java.util.HashMap;

import MPD.sys.Common.Common;
import MPD.sys.Core._Dispatcher;
import MPD.sys.Core._System;
import MPD.sys.Core._Thread;
import MPD.sys.Struct.ProcessObject;

/**
 * 主服务线程
 * @author max
 * @date 2012-8-7
 *
 */
public class Serve_Thread extends _Thread{

	//最大线程数
	private int maxThreadNum = 0;
	//活动线程数
	private int runThreadNum = 0;
	//空池等待时间
	private int emptywait = 1000;
	//满池等待时间
	private int fullwait = 1000;
	//路由器
	private _Dispatcher dispatcher = null;
	//循环变量 处理容器
	private ProcessObject tmp = null;
	//循环变量 请求容器
	private HashMap<String,String> request = null;
	
	
	/**
	 * 构造函数
	 * @param null
	 * @return null
	 * @access public
	 */
	public Serve_Thread() {
		//设置为最高优先级
		this.setPriority(MAX_PRIORITY);
		this.maxThreadNum = Integer.parseInt(Common.C("System_Config", "threadnum"));
		
		this.emptywait = Integer.parseInt(Common.C("System_Config","emptywait"));
		this.fullwait = Integer.parseInt(Common.C("System_Config","fullwait"));
		
		this.dispatcher = _Dispatcher.getDispatcher();
		
	}//end of construct
	
	/**
	 * 重写run()
	 * @access public
	 * @return null
	 * @param null
	 */
	public void run() {
		
		//永真循环
		while(true) {
			
			try{
			//从未处理池中取出socket
			this.tmp = _System.getUnProcessed().pop() ;
			}catch(java.util.NoSuchElementException e){
				//如果进程池为空
				try {
					sleep(this.emptywait);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//end of inside catch
				continue;
			}//end of outside catch
			
			//检验socket有效性,此时未处理池已经没有了，这里只需丢弃对象即可
			if(!this.tmp.s.isConnected()){
				this.tmp = null;
				continue;
			}//end of if
			
			//如果信息不是String
			if( ! (this.tmp.m instanceof String) ){
				_System.getProcessed().addLast(new ProcessObject(this.tmp.s, "command is unsupported"));
				continue;
			}
			//检测信息,路由调度
			this.request = this.dispatcher.parseRequest(this.tmp);
			
			//检测出错继续循环
			if(this.request == null){
				_System.getProcessed().addLast(new ProcessObject(this.tmp.s, "command is unsupported"));
				continue;
			}
			Worker_Thread w = new Worker_Thread(this.request , this.tmp);
			//启动处理线程
			w.start();
			//加入进程池
			_System.getWorkerThread().add(w);
			
		}//end while
		
	}//end of run()
	
}//end of Serve_Thread class
