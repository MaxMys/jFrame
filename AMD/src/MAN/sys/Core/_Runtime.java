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
		//初始化待处理池
		_System.setUnProcessed(new _LinkedList<String>());
		//初始化工作者进程池
		_System.setWorkerThread(new _LinkedList<Worker_Thread>());

		
		//启动化服务线程
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
		
		//System.out.println("启动耗时 ："+_System.getBenchMark().elapsedTime("start", null));
		//System.out.println("启动内存使用 ："+_System.getBenchMark().elapsedMem("start", null));
		//System.out.println(_System.getSelf());
		System.out.println("系统启动成功!");
		
	}//end of start()
	
}//end of class Runtie()
