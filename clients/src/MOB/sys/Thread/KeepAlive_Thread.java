package MOB.sys.Thread;

import MOB.sys.Core._System;
import MOB.sys.Core._Thread;

/**
 * ���������߳�
 * @author max
 * @date 2012-8-10
 *
 */
public class KeepAlive_Thread extends _Thread{

	/**
	 * ���췽��
	 */
	public KeepAlive_Thread() {
		super();
	}//end of construct
	
	/**
	 * ����run����
	 */
	public void run() {
		while(true){
		//����״̬
		_System.getNetWork().keepalive();
		//20000����ִ��һ��
		try {
			sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		
	  }//end of while
	}//end run()
	
}//end of class
