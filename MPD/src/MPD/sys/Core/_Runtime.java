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
 * 运行时
 * @author max
 * @date 2012-8-4
 * 
 */
public class _Runtime {

	private static _Runtime self = null;
	
	
	/**
	 * 构造函数
	 */
	private _Runtime() {}
	
	/**
	 * 初始化函数,仅由_System.
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
	 * 获取Runtime
	 * @return MyRuntime self
	 * @access public
	 * @param null
	 */
	public static _Runtime getMyRuntime() {
		return self;
	}
	
	/**
	 * 在Index.java调用
	 * 启动 runTime()
	 * @param null
	 * @return null
	 * @access public
	 */
	public void start() {
		
		//启动日志写线程
		if(Common.C("Log_Config","log").equals("on")) {
			
			_System.setLogThread(new Log_Thread());
			_System.getLogThread().start();
			
		}
		//初始化网络层
		_System.setNetWork(_NetWork.getNetWork());
		//初始化数据连接管理者
		_System.setConnPoolManager(_ConnPoolManager.getConnectionPool());
		//初始化输出池
		_System.setProcessed(new _LinkedList<MPD.sys.Struct.ProcessObject>());
		//初始化待处理池
		_System.setUnProcessed(new _LinkedList<MPD.sys.Struct.ProcessObject>());
		//初始化工作者进程池
		_System.setWorkerThread(new _LinkedList<Worker_Thread>());
		
		//启动网络回复线程
		_System.setReplyThread(new Reply_Thread());
		_System.getReplyThread().start();
		
		//启动网络接收线程
		_System.setAcceptThread(new Accepter_Thread());
		_System.getAcceptThread().start();
		
		//启动服务主线程
		_System.setServeThread(new Serve_Thread());
		_System.getServeThread().start();
		
		//启动脚本服务,控制台线程
		_System.setScriptThread(new Script_Thread());
		_System.getScriptThread().start();
			
		System.out.println("启动耗时 ："+_System.getBenchMark().elapsedTime("start", null));
		System.out.println("启动内存使用 ："+_System.getBenchMark().elapsedMem("start", null));
		System.out.println(_System.getSelf());
		System.out.println("系统启动成功!");
		
	}//end of start()
	
}//end of class Runtie()
