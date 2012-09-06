package MPD.sys.Init;

import MPD.sys.Common.Common;
import MPD.sys.Core._BenchMark;
import MPD.sys.Core._ExceHandle;
import MPD.sys.Core._LogCon;
import MPD.sys.Core._Runtime;
import MPD.sys.Core._System;

/**
 * 项目入口文件
 * @date2012-8-4
 * @author max
 *
 */
public class Index {

	
	
	/**
	 * 程序入口
	 * @param arg
	 * @return null
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		//初始化系统
		 _System.init();
		
		//记录启动时间戳
		 _System.getBenchMark().markMem("start");
		//记录初始化使用内存
		 _System.getBenchMark().markTime("start");
		
		
		 
		//将启动时间戳和内存存入系统
		 _System.getState().put(
				 "start_time", 
				 _System.getBenchMark().elapsedTime(null, "start")
				 );
		 _System.getState().put(
				 "start_mem", 
				 _System.getBenchMark().elapsedMem(null, "start")
				 );

		 /**
		  * -----------------------------------------------
		  * 重要代码，不可删除，更改项目目录之后自行更改 
		  * -----------------------------------------------
		  */
		 //设置项目名称
		 Common.C("System_Config","project","MPD.");
		 //设置项目基本路径
		 //apppath
		 Common.C("System_Config",
				 	"apppath",
				 		_System.getConfigs().get(
				 				Common.getKey("System_Config","project")
				 							)
				 								+"app."
				 );
		 //syspath
		 Common.C("System_Config",
				 	"syspath",
						 _System.getConfigs().get(
					 				Common.getKey("System_Config","project")
					 						)
					 							+"sys."
				);
	
		
		//用户自定义重新设置,快捷设置方法
		/**
		 *---------------------------------------------------
		 *	示例代码
		 *---------------------------------------------------
		 * 
		 *  动态部署调试模式 
		 *  Common.C("system_config","mode","debug")
		 */
		
		 //启动调试模式
		 Common.C("System_Config","mode");
		 
		//设置异常处理
			if( Common.C("Error_Config","defaulthandle").equals("MPD.sys.Core._ExceHandle"))
					_System.setExceHandle(new _ExceHandle(true));
			else
				try {
					//注意只支持午餐构造函数
					_System.setExceHandle((_ExceHandle)Class.forName(Common.C("Error_Config","defaulthandle")).newInstance());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//end of catch
			
		 //初始化日志类,初始化日志缓冲池
		 _System.setLogCon(new _LogCon());

		//初始化运行对象
		 _System.setRunTime(_Runtime.init());
		 //启动运行时文件,启动各项服务
		 _System.getRunTime().start();
		
	}//end of main()
	
}//end of main class
