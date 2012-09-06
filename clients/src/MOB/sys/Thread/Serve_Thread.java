package MOB.sys.Thread;

import java.util.HashMap;

import MOB.sys.Core._Thread;
import MOB.sys.Struct.Work;
import MOB.sys.Struct._CallBack;
import MOB.sys.Struct._LinkedList;

public class Serve_Thread extends _Thread{
	
	private _LinkedList<Work> unProcessPoll = null;
	public Serve_Thread(){
		super();
		//初始化未处理池
		this.unProcessPoll = new _LinkedList<Work>();
	}//end 
	
	/**
	 * 覆盖run()
	 * @access public
	 * @return null
	 * @param null
	 */
	public void run(){
		while(true){
			if(this.unProcessPoll.isEmpty()){
				try {
					sleep(1000);
					continue;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Work tmp = this.unProcessPoll.pop();
			new Worker_Thread(
					tmp.controller,
					tmp.method,
					tmp.hm,
					tmp.c).start();
			
		}//end of while
	}//end of run
	/**
	 * 加入一个工作
	 * @param controller
	 * @param method
	 * @param hm
	 */
	public void addWork(Class<?> controller,String method,HashMap hm,_CallBack c){
		
		this.unProcessPoll.addLast(new Work(controller,method,hm,c));
		
	}//end of addWork

}
