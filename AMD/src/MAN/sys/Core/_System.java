package MAN.sys.Core;

import java.util.HashMap;

import MAN.app.View.ManagerFrame;
import MAN.sys.Struct._LinkedList;
import MAN.sys.Thread.Log_Thread;
import MAN.sys.Thread.Serve_Thread;
import MAN.sys.Thread.Worker_Thread;



/**
 * 
 * @author max
 * @date 2012-8-4
 *
 * ϵͳע���,ά������ϵͳ������״̬
 * 
 */
public class _System {

	//ϵͳ����
	private static _System self = null;
	//ϵͳ״̬��
	private static HashMap<String,Object> state = null;
	//ϵͳ����
	private static HashMap<String,String> configs = null;
	
	//benchMark��������
	private static _BenchMark benchMark = null;
	//Runtime()
	private static _Runtime runTime = null;
	//excHandler
	private static _ExceHandle exceHandle = null;
	//��־������
	private static _LogCon logCon = null;
	//NetWork
	private static _NetWork netWork = null;
	//��־д�߳�
	private static Log_Thread logThread = null;
	//�����س�
	private static Serve_Thread serve = null;
	
	//�������̳߳�
	private static _LinkedList<Worker_Thread> workerThread = null;
	//������պ�������
	private static _LinkedList<String> unProcessed= null;
	//���������ͳ�
	private static _LinkedList<String> Processed  = null;
	
    //����Ա����Ա����
	private static ManagerFrame managerframe = null;
	
	public static ManagerFrame getManagerframe() {
		return managerframe;
	}

	public static void setManagerframe(ManagerFrame managerframe) {
		_System.managerframe = managerframe;
	}

	public static Serve_Thread getServe() {
		return serve;
	}

	public static void setServe(Serve_Thread serve) {
		_System.serve = serve;
	}

	public static _System getSelf() {
		return self;
	}

	public static void setSelf(_System self) {
		_System.self = self;
	}

	public static HashMap<String, Object> getState() {
		return state;
	}

	public static void setState(HashMap<String, Object> state) {
		_System.state = state;
	}

	public static HashMap<String, String> getConfigs() {
		return configs;
	}

	public static void setConfigs(HashMap<String, String> configs) {
		_System.configs = configs;
	}

	public static _BenchMark getBenchMark() {
		return benchMark;
	}

	public static void setBenchMark(_BenchMark benchMark) {
		_System.benchMark = benchMark;
	}

	public static _Runtime getRunTime() {
		return runTime;
	}

	public static void setRunTime(_Runtime runTime) {
		_System.runTime = runTime;
	}

	public static _LogCon getLogCon() {
		return logCon;
	}

	public static void setLogCon(_LogCon logCon) {
		_System.logCon = logCon;
	}

	public static _NetWork getNetWork() {
		return netWork;
	}

	public static void setNetWork(_NetWork netWork) {
		_System.netWork = netWork;
	}

	

	public static Log_Thread getLogThread() {
		return logThread;
	}

	public static void setLogThread(Log_Thread logThread) {
		_System.logThread = logThread;
	}

	
	public static _LinkedList<Worker_Thread> getWorkerThread() {
		return workerThread;
	}

	public static void setWorkerThread(_LinkedList<Worker_Thread> workerThread) {
		_System.workerThread = workerThread;
	}

	public static _LinkedList<String> getUnProcessed() {
		return unProcessed;
	}

	public static void setUnProcessed(_LinkedList<String> unProcessed) {
		_System.unProcessed = unProcessed;
	}

	public static _LinkedList<String> getProcessed() {
		return Processed;
	}

	public static void setProcessed(_LinkedList<String> processed) {
		Processed = processed;
	}

	public static _ExceHandle getExceHandle() {
		return exceHandle;
	}

	/**
	 * ϵͳ���캯��
	 * @return self
	 * @access private
	 * @param null
	 */
	private _System () {
		
		_System.state = new HashMap<String,Object>();
		_System.configs = new HashMap<String,String>();
		_System.benchMark = new _BenchMark();
		
	}//end of construct()
	
	/**
	 * ϵͳ��ʼ������������
	 * @return _System
	 * @param null
	 */
	public static _System init(){
		
		if(self != null)
			return self;
		return self = new _System();
		
	}//end of init()
	
	
	/**
	 * init���������������
	 * ʵ��init���ܣ�����ΪgetSystem(0
	 * @return _System self
	 * @param null
	 * @access public
	 */
	public static _System getSystem(){
		return _System.init();
	}//end of getSystem()
	
	
	/**
	 * ��װ���˳���,�����˳���ʾ
	 * @param status int ״̬
	 * @param message String ������Ϣ
	 * @param level ���󼶱𣬿��Բ���
	 * @return null
	 * @access public
	 */
	public static void exit(int status,String message,String level) {
		if(level == "" || level == null)
			level = "NOTICE" ;
		//�˳�ǰ��¼��Ϣ,�ṩ��¼��Ϣ����
		_LogCon.record("System", "�˳�:"+message, level, true);
		System.exit(status);
	}//end of exit()
	
	/**
	 * �����쳣������
	 * @param eh _ExceHandle
	 * @return null
	 * @access public
	 */
	public static void setExceHandle (_ExceHandle eh) {
		_System.exceHandle = eh;
	}
	
	/**
	 * �쳣ɱ��,�����쳣���ռ�����
	 * @param e �쳣
	 * @param Name  �׳��쳣����
	 * @param extraMessage ������Ϣ
	 * @param level   ����  ����д null Ĭ��ΪNOTICE
	 * @return null
	 * @access public
	 */
	public static void ExceptionKiller (Exception e,Class<?> Name,String extraMessage,String level) {
		//û���Զ��������Ĭ�ϵ�Handle
		if(_System.exceHandle == null)
			_System.exceHandle = new _ExceHandle(true);
		_System.exceHandle.exec(e, Name, extraMessage, level);
	}
	
	/**
	 * toString()����
	 * @param null
	 * @return String
	 * @access public
	 */
	@Override
	public String toString() {
		String tmp = "_System [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]\n";
		tmp += "ϵͳ״̬ :"+_System.state.toString().replaceAll(",", "\n")+"\n";
		tmp += "ϵͳ���� :"+_System.configs.toString().replaceAll(",", "\n")+"\n";
		return tmp;
	}
}
