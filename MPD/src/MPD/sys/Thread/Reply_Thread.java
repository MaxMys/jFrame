package MPD.sys.Thread;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import MPD.sys.Common.Common;
import MPD.sys.Core._System;
import MPD.sys.Core._Thread;
import MPD.sys.Struct.ProcessObject;

/**
 * 
 * @author max
 * @date 2012-8-7
 */
public class Reply_Thread extends _Thread {

	//循环变量
	private ProcessObject tmp = null ; 
	
	//空值睡眠时间
	private int emptyWait = 0;
	
	private PrintWriter out = null;
	/**
	 * 构造函数
	 * @param null
	 * @return null
	 * @access public
	 */
	public Reply_Thread () {
		//初始化
		this.emptyWait = Integer.parseInt(Common.C("System_Config","emptywait"));
	}
	
	/**
	 * 覆写run方法
	 * @param null
	 * @return null
	 * @access public
	 */
	public void run() {
	
		//永真循环
		while(true){
		
			try{
				
			   this.tmp = _System.getProcessed().pop();
			}catch(Exception e){
				
				try {
					sleep(this.emptyWait);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//end of inside catch
				continue;
			} //end of catch
			
			try {
				this.send();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//end of while
	}//end of run()
	
	/**
	 * sender()控制器,发送本次持有对象
	 * @param null
	 * @access private
	 * @return null
	 * @throws IOException 
	 */
	private void send() throws IOException {
	    try {
			_System.getNetWork().sender(this.tmp.s, this.tmp.m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end send()
	
}//end of Reply_Thread
