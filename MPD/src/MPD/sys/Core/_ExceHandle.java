package MPD.sys.Core;


import MPD.sys.Common.Common;
import MPD.sys.Common.Tools;
import MPD.sys.Config.Log_Config;

//--------------------------------------------------------------------
// soft_10 team 
//--------------------------------------------------------------------

/**
 * 
 * 自定义异常类
 * @max
 * @date 2012-8-3
 */
public class _ExceHandle {

	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	//异常类型
	@SuppressWarnings("unused")
	private String type = null;
	//强制执行
	private boolean extra ;
	
	//自定义错误变量
	@SuppressWarnings("unused")
	private String Class = null ; 
	@SuppressWarnings("unused")
	private String method = null;
	@SuppressWarnings("unused")
	private String file = null;
	@SuppressWarnings("unused")
	private int line = 0;
	
	//整体trace信息
	private String traceInfo = null;
	
	/**
	 * 构造函数 无参数类型
	 * @param null
	 * @return this
	 * @access public
	 */
	public _ExceHandle (boolean extra) {
		this.extra = extra;
		
	}//end of _ExceHandle()
	
	
	/**
	 * 获取更多的e的信息
	 * @param e Exception 给出异常
	 * @return null
	 * @access public
	 */
	public String getInfo(Exception e) {
		
		//获取错误堆栈
		StackTraceElement[] trace = e.getStackTrace() ; 
		//获取错误变量
		this.Class = trace[0].getClassName() ; 
		this.method = trace[0].getMethodName();
		this.file = trace[0].getFileName();
		this.line = trace[0].getLineNumber();
		
		String time = Tools.getTime();
		
		for ( int i = 0 ; i < trace.length ; i++) {
			this.traceInfo += "[ "+time+" ] "+trace[i].getFileName()+" ("+trace[i].getLineNumber()+" )";
			this.traceInfo += trace[i].getClassName()+" : "+trace[i].getMethodName();
			this.traceInfo += "\n";		
		}//end of for
		
		return this.traceInfo;
		
	}//end of toString()
	
	/**
	 * 开启调试模式，输出调试信息,，并且退出;
	 * 开启错误记录，记录此条异常
	 * @param e Exception 
	 * @param name 出错的类
	 * @param extramessage 额外信息
	 * @param level 错误级别 默认为NOTICE
	 * @return null
	 * @access public
	 */
	public void exec(Exception e,Class<?> Name,String extraMessage,String level) {
		
		if( level =="" || level == null )
			level = "NOTICE";
		//如果开启errorlog，记录日志
		if(  Common.C(Log_Config.class,"errorLog").equals("true") )
			_LogCon.record(Name.getSimpleName(), this.getInfo(e)+"\n extra:"+extraMessage, level, this.extra);
		
		//如果开启了debug模式，输出错误并且终止程序
		if( Common.C(Log_Config.class,"mode").equals("debug") ) {
			
			System.out.println(this.getInfo(e));
			//退出程序
			_System.exit(0, extraMessage, level);
		}
		
		return ;
		
	}//end of print();
	
}//end of MyException()
