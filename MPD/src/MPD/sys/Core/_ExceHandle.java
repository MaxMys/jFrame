package MPD.sys.Core;


import MPD.sys.Common.Common;
import MPD.sys.Common.Tools;
import MPD.sys.Config.Log_Config;

//--------------------------------------------------------------------
// soft_10 team 
//--------------------------------------------------------------------

/**
 * 
 * �Զ����쳣��
 * @max
 * @date 2012-8-3
 */
public class _ExceHandle {

	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	//�쳣����
	@SuppressWarnings("unused")
	private String type = null;
	//ǿ��ִ��
	private boolean extra ;
	
	//�Զ���������
	@SuppressWarnings("unused")
	private String Class = null ; 
	@SuppressWarnings("unused")
	private String method = null;
	@SuppressWarnings("unused")
	private String file = null;
	@SuppressWarnings("unused")
	private int line = 0;
	
	//����trace��Ϣ
	private String traceInfo = null;
	
	/**
	 * ���캯�� �޲�������
	 * @param null
	 * @return this
	 * @access public
	 */
	public _ExceHandle (boolean extra) {
		this.extra = extra;
		
	}//end of _ExceHandle()
	
	
	/**
	 * ��ȡ�����e����Ϣ
	 * @param e Exception �����쳣
	 * @return null
	 * @access public
	 */
	public String getInfo(Exception e) {
		
		//��ȡ�����ջ
		StackTraceElement[] trace = e.getStackTrace() ; 
		//��ȡ�������
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
	 * ��������ģʽ�����������Ϣ,�������˳�;
	 * ���������¼����¼�����쳣
	 * @param e Exception 
	 * @param name �������
	 * @param extramessage ������Ϣ
	 * @param level ���󼶱� Ĭ��ΪNOTICE
	 * @return null
	 * @access public
	 */
	public void exec(Exception e,Class<?> Name,String extraMessage,String level) {
		
		if( level =="" || level == null )
			level = "NOTICE";
		//�������errorlog����¼��־
		if(  Common.C(Log_Config.class,"errorLog").equals("true") )
			_LogCon.record(Name.getSimpleName(), this.getInfo(e)+"\n extra:"+extraMessage, level, this.extra);
		
		//���������debugģʽ�������������ֹ����
		if( Common.C(Log_Config.class,"mode").equals("debug") ) {
			
			System.out.println(this.getInfo(e));
			//�˳�����
			_System.exit(0, extraMessage, level);
		}
		
		return ;
		
	}//end of print();
	
}//end of MyException()
