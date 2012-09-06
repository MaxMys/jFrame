package MPD.sys.Struct;

import java.net.Socket;

/**
 * 已经处理完成的对象 
 * @author max 
 * @date 2018-8-6
 *
 */
public class ProcessObject {

	//socket
	public Socket s = null;
	//message
	public Object m = null;
	//starttime
	public long startTime = 0;
	
	/**
	 * 构造函数
	 * @access public
	 * @return null
	 * @param null
	 */
	public ProcessObject(Socket s , Object m ) {
		this.s = s;
		this.m = m;
		this.startTime = System.currentTimeMillis();
	}//end of construct()
	
	

	
	
}//end of class Procceed
