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

	//ѭ������
	private ProcessObject tmp = null ; 
	
	//��ֵ˯��ʱ��
	private int emptyWait = 0;
	
	private PrintWriter out = null;
	/**
	 * ���캯��
	 * @param null
	 * @return null
	 * @access public
	 */
	public Reply_Thread () {
		//��ʼ��
		this.emptyWait = Integer.parseInt(Common.C("System_Config","emptywait"));
	}
	
	/**
	 * ��дrun����
	 * @param null
	 * @return null
	 * @access public
	 */
	public void run() {
	
		//����ѭ��
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
	 * sender()������,���ͱ��γ��ж���
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
