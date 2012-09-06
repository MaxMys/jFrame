package MOB.sys.Thread;

import MOB.sys.Core._System;
import MOB.sys.Core._Thread;

/**
 * 保持连接线程
 * @author max
 * @date 2012-8-10
 *
 */
public class KeepAlive_Thread extends _Thread{

	/**
	 * 构造方法
	 */
	public KeepAlive_Thread() {
		super();
	}//end of construct
	
	/**
	 * 覆盖run方法
	 */
	public void run() {
		while(true){
		//保持状态
		_System.getNetWork().keepalive();
		//20000毫秒执行一次
		try {
			sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		
	  }//end of while
	}//end run()
	
}//end of class
