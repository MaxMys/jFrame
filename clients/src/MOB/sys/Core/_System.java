package MOB.sys.Core;

import java.util.HashMap;

import frame.MainFrame;

import MOB.sys.Struct._LinkedList;
import MOB.sys.Thread.ReceiveMessage_Thread;
import MOB.sys.Thread.KeepAlive_Thread;
import MOB.sys.Thread.Log_Thread;
import MOB.sys.Thread.Serve_Thread;
import MOB.sys.Thread.Worker_Thread;


/**
 * 
 * @author max
 * @date 2012-8-4
 *
 * 系统注册表,维持整个系统的运行状态
 * 
 */
public class _System {

	//系统本身
	private static _System self = null;
	//系统状态字
	private static HashMap<String,Object> state = null;
	//系统环境
	private static HashMap<String,String> configs = null;
	
	//benchMark单例对象
	private static _BenchMark benchMark = null;
	//Runtime()
	private static _Runtime runTime = null;
	//excHandler
	private static _ExceHandle exceHandle = null;
	//日志控制器
	private static _LogCon logCon = null;
	//NetWork
	private static _NetWork netWork = null;
	//保持连接
	private static KeepAlive_Thread keepAliveThread = null;
	//接收线程
	private static ReceiveMessage_Thread acceptThread = null;
	//日志写线程
	private static Log_Thread logThread = null;
	//服务县城
	private static Serve_Thread serve = null;
	
	//工作者线程池
	private static _LinkedList<Worker_Thread> workerThread = null;
	//网络接收后待处理池
	private static _LinkedList<String> unProcessed= null;
	//处理后待发送池
	private static _LinkedList<String> Processed  = null;
	//view
	private static MainFrame frame = null;
	
	public static MainFrame getFrame() {
		return frame;
	}

	public static void setFrame(MainFrame frame) {
		_System.frame = frame;
	}

	public static Serve_Thread getServe() {
		return serve;
	}

	public static void setServe(Serve_Thread serve) {
		_System.serve = serve;
	}

	public static _System getSelf() {
		return self;
	}

	public static void setSelf(_System self) {
		_System.self = self;
	}

	public static HashMap<String, Object> getState() {
		return state;
	}

	public static void setState(HashMap<String, Object> state) {
		_System.state = state;
	}

	public static HashMap<String, String> getConfigs() {
		return configs;
	}

	public static void setConfigs(HashMap<String, String> configs) {
		_System.configs = configs;
	}

	public static _BenchMark getBenchMark() {
		return benchMark;
	}

	public static void setBenchMark(_BenchMark benchMark) {
		_System.benchMark = benchMark;
	}

	public static _Runtime getRunTime() {
		return runTime;
	}

	public static void setRunTime(_Runtime runTime) {
		_System.runTime = runTime;
	}

	public static _LogCon getLogCon() {
		return logCon;
	}

	public static void setLogCon(_LogCon logCon) {
		_System.logCon = logCon;
	}

	public static _NetWork getNetWork() {
		return netWork;
	}

	public static void setNetWork(_NetWork netWork) {
		_System.netWork = netWork;
	}

	public static KeepAlive_Thread getKeepAliveThread() {
		return keepAliveThread;
	}

	public static void setKeepAliveThread(KeepAlive_Thread keepAliveThread) {
		_System.keepAliveThread = keepAliveThread;
	}

	public static ReceiveMessage_Thread getAcceptThread() {
		return acceptThread;
	}

	public static void setAcceptThread(ReceiveMessage_Thread acceptThread) {
		_System.acceptThread = acceptThread;
	}

	public static Log_Thread getLogThread() {
		return logThread;
	}

	public static void setLogThread(Log_Thread logThread) {
		_System.logThread = logThread;
	}

	
	public static _LinkedList<Worker_Thread> getWorkerThread() {
		return workerThread;
	}

	public static void setWorkerThread(_LinkedList<Worker_Thread> workerThread) {
		_System.workerThread = workerThread;
	}

	public static _LinkedList<String> getUnProcessed() {
		return unProcessed;
	}

	public static void setUnProcessed(_LinkedList<String> unProcessed) {
		_System.unProcessed = unProcessed;
	}

	public static _LinkedList<String> getProcessed() {
		return Processed;
	}

	public static void setProcessed(_LinkedList<String> processed) {
		Processed = processed;
	}

	public static _ExceHandle getExceHandle() {
		return exceHandle;
	}

	/**
	 * 系统构造函数
	 * @return self
	 * @access private
	 * @param null
	 */
	private _System () {
		
		_System.state = new HashMap<String,Object>();
		_System.configs = new HashMap<String,String>();
		_System.benchMark = new _BenchMark();
		
	}//end of construct()
	
	/**
	 * 系统初始化，单利对象
	 * @return _System
	 * @param null
	 */
	public static _System init(){
		
		if(self != null)
			return self;
		return self = new _System();
		
	}//end of init()
	
	
	/**
	 * init函数容易引起误会
	 * 实现init功能，改名为getSystem(0
	 * @return _System self
	 * @param null
	 * @access public
	 */
	public static _System getSystem(){
		return _System.init();
	}//end of getSystem()
	
	
	/**
	 * 封装的退出类,加入退出提示
	 * @param status int 状态
	 * @param message String 额外信息
	 * @param level 错误级别，可以不填
	 * @return null
	 * @access public
	 */
	public static void exit(int status,String message,String level) {
		if(level == "" || level == null)
			level = "NOTICE" ;
		//退出前记录信息,提供记录信息功能
		_LogCon.record("System", "退出:"+message, level, true);
		System.exit(status);
	}//end of exit()
	
	/**
	 * 设置异常处理类
	 * @param eh _ExceHandle
	 * @return null
	 * @access public
	 */
	public static void setExceHandle (_ExceHandle eh) {
		_System.exceHandle = eh;
	}
	
	/**
	 * 异常杀手,处理异常的终极武器
	 * @param e 异常
	 * @param Name  抛出异常的类
	 * @param extraMessage 额外信息
	 * @param level   级别  可以写 null 默认为NOTICE
	 * @return null
	 * @access public
	 */
	public static void ExceptionKiller (Exception e,Class<?> Name,String extraMessage,String level) {
		//没有自定义就设置默认的Handle
		if(_System.exceHandle == null)
			_System.exceHandle = new _ExceHandle(true);
		_System.exceHandle.exec(e, Name, extraMessage, level);
	}
	
	/**
	 * toString()方法
	 * @param null
	 * @return String
	 * @access public
	 */
	@Override
	public String toString() {
		String tmp = "_System [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]\n";
		tmp += "系统状态 :"+_System.state.toString().replaceAll(",", "\n")+"\n";
		tmp += "系统环境 :"+_System.configs.toString().replaceAll(",", "\n")+"\n";
		return tmp;
	}
}
