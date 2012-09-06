package MOB.sys.Core;

import java.util.ArrayList;

import MOB.sys.Common.Common;
import MOB.sys.Common.Tools;

/**
 * 日志处理类
 * @author max
 * @package MPD.max.core
 * @date 2012-8-2
 *
 */
public class _LogCon {

	//由于hook此时没有初始化，所以内置错误级别直接写在了这里
	//日志级别，从上到下，由高到低
	final static String[] CONFIG = {
		//严重错误，导致系统崩溃无法使用	
		"EMERG",
		//警戒性错误，必须被立即修改的错误							
		"ALERT",
		//临界值错误，越界							
		"CRIT",
		//一般性							
		"ERR",
		//警告性错误：需要发出警告的错误							
		"WARN",
		//通知：程序可以运行但是还不够完美							
		"NOTICE",
		//信息：程序输出信息							
		"INFO",
		//SQL：SQL语句， 只有在调试模式开启才有							
		"SQL"
									};

	
	//日志记录方式
	final static int SYSTEM = 0;
	final static int MAIL = 1;
	final static int FILE = 3;
	final static int SAPI = 4;
	
	//缓冲池
	private static ArrayList<String> log = null;
	
	
	public _LogCon() {
		//初始化缓冲池
		_LogCon.log = new ArrayList<String>();
	}
	
	/**
	 * 记录日志，并且过滤没有设置的级别 ,存入缓冲池
	 * @static
	 * @access public
	 * @param PathInfo  返回调用的请求
	 * @param String message 日志信息
	 * @param String level
	 * @param boolean record 是否强制记录
	 * @return void
	 */
	public static void record(String PathInfo ,String message , String level , boolean record) {
		
		//级别包含，或者强制执行的时候，且要开启log的时候执行
		if(( record || Tools.arrContain(_LogCon.CONFIG,level) ) && Common.C("Log_Config", "log").equals("on")) {
			String now = Tools.getTime();
			_LogCon.add( now + " : " + PathInfo + " | " + level + " : " + message );
		}//end of if
	}//end of record
	
	private static void add(String str) {
		//如果没有初始化
		if( _LogCon.log == null )
			 _LogCon.log = new ArrayList<String>();
		//加入缓冲池
		_LogCon.log.add(str);
	}//end of add(String str)
	
	/**
	 * 日志保存，建议使用，增加缓冲池，效率高
	 * @param type 日志记录方式
	 * @param destination 写入目标
	 * @param extra	额外参数
	 */
	public static void save ( int type , String destination , String extra ) {
		
	}//end of save()
	
	/**
	 * 日志直接写入，产生就写入，不提倡使用，效率较低
	 * @param message 日志信息
	 * @param level	 日志级别
	 * @param type	日志记录方式
	 * @param destination  写入目标
	 * @param extra    额外参数
	 */
	public static void write ( String message , String level , int type , String destination , String extra) {
		
	}//end of write()
	
	
	
}//end of class logCon
