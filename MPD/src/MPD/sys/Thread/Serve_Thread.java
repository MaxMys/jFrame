package MPD.sys.Thread;

import java.util.HashMap;

import MPD.sys.Common.Common;
import MPD.sys.Core._Dispatcher;
import MPD.sys.Core._System;
import MPD.sys.Core._Thread;
import MPD.sys.Struct.ProcessObject;

/**
 * �������߳�
 * @author max
 * @date 2012-8-7
 *
 */
public class Serve_Thread extends _Thread{

	//����߳���
	private int maxThreadNum = 0;
	//��߳���
	private int runThreadNum = 0;
	//�ճصȴ�ʱ��
	private int emptywait = 1000;
	//���صȴ�ʱ��
	private int fullwait = 1000;
	//·����
	private _Dispatcher dispatcher = null;
	//ѭ������ ��������
	private ProcessObject tmp = null;
	//ѭ������ ��������
	private HashMap<String,String> request = null;
	
	
	/**
	 * ���캯��
	 * @param null
	 * @return null
	 * @access public
	 */
	public Serve_Thread() {
		//����Ϊ������ȼ�
		this.setPriority(MAX_PRIORITY);
		this.maxThreadNum = Integer.parseInt(Common.C("System_Config", "threadnum"));
		
		this.emptywait = Integer.parseInt(Common.C("System_Config","emptywait"));
		this.fullwait = Integer.parseInt(Common.C("System_Config","fullwait"));
		
		this.dispatcher = _Dispatcher.getDispatcher();
		
	}//end of construct
	
	/**
	 * ��дrun()
	 * @access public
	 * @return null
	 * @param null
	 */
	public void run() {
		
		//����ѭ��
		while(true) {
			
			try{
			//��δ�������ȡ��socket
			this.tmp = _System.getUnProcessed().pop() ;
			}catch(java.util.NoSuchElementException e){
				//������̳�Ϊ��
				try {
					sleep(this.emptywait);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//end of inside catch
				continue;
			}//end of outside catch
			
			//����socket��Ч��,��ʱδ������Ѿ�û���ˣ�����ֻ�趪�����󼴿�
			if(!this.tmp.s.isConnected()){
				this.tmp = null;
				continue;
			}//end of if
			
			//�����Ϣ����String
			if( ! (this.tmp.m instanceof String) ){
				_System.getProcessed().addLast(new ProcessObject(this.tmp.s, "command is unsupported"));
				continue;
			}
			//�����Ϣ,·�ɵ���
			this.request = this.dispatcher.parseRequest(this.tmp);
			
			//���������ѭ��
			if(this.request == null){
				_System.getProcessed().addLast(new ProcessObject(this.tmp.s, "command is unsupported"));
				continue;
			}
			Worker_Thread w = new Worker_Thread(this.request , this.tmp);
			//���������߳�
			w.start();
			//������̳�
			_System.getWorkerThread().add(w);
			
		}//end while
		
	}//end of run()
	
}//end of Serve_Thread class
