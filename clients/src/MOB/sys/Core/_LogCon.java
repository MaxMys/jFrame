package MOB.sys.Core;

import java.util.ArrayList;

import MOB.sys.Common.Common;
import MOB.sys.Common.Tools;

/**
 * ��־������
 * @author max
 * @package MPD.max.core
 * @date 2012-8-2
 *
 */
public class _LogCon {

	//����hook��ʱû�г�ʼ�����������ô��󼶱�ֱ��д��������
	//��־���𣬴��ϵ��£��ɸߵ���
	final static String[] CONFIG = {
		//���ش��󣬵���ϵͳ�����޷�ʹ��	
		"EMERG",
		//�����Դ��󣬱��뱻�����޸ĵĴ���							
		"ALERT",
		//�ٽ�ֵ����Խ��							
		"CRIT",
		//һ����							
		"ERR",
		//�����Դ�����Ҫ��������Ĵ���							
		"WARN",
		//֪ͨ������������е��ǻ���������							
		"NOTICE",
		//��Ϣ�����������Ϣ							
		"INFO",
		//SQL��SQL��䣬 ֻ���ڵ���ģʽ��������							
		"SQL"
									};

	
	//��־��¼��ʽ
	final static int SYSTEM = 0;
	final static int MAIL = 1;
	final static int FILE = 3;
	final static int SAPI = 4;
	
	//�����
	private static ArrayList<String> log = null;
	
	
	public _LogCon() {
		//��ʼ�������
		_LogCon.log = new ArrayList<String>();
	}
	
	/**
	 * ��¼��־�����ҹ���û�����õļ��� ,���뻺���
	 * @static
	 * @access public
	 * @param PathInfo  ���ص��õ�����
	 * @param String message ��־��Ϣ
	 * @param String level
	 * @param boolean record �Ƿ�ǿ�Ƽ�¼
	 * @return void
	 */
	public static void record(String PathInfo ,String message , String level , boolean record) {
		
		//�������������ǿ��ִ�е�ʱ����Ҫ����log��ʱ��ִ��
		if(( record || Tools.arrContain(_LogCon.CONFIG,level) ) && Common.C("Log_Config", "log").equals("on")) {
			String now = Tools.getTime();
			_LogCon.add( now + " : " + PathInfo + " | " + level + " : " + message );
		}//end of if
	}//end of record
	
	private static void add(String str) {
		//���û�г�ʼ��
		if( _LogCon.log == null )
			 _LogCon.log = new ArrayList<String>();
		//���뻺���
		_LogCon.log.add(str);
	}//end of add(String str)
	
	/**
	 * ��־���棬����ʹ�ã����ӻ���أ�Ч�ʸ�
	 * @param type ��־��¼��ʽ
	 * @param destination д��Ŀ��
	 * @param extra	�������
	 */
	public static void save ( int type , String destination , String extra ) {
		
	}//end of save()
	
	/**
	 * ��־ֱ��д�룬������д�룬���ᳫʹ�ã�Ч�ʽϵ�
	 * @param message ��־��Ϣ
	 * @param level	 ��־����
	 * @param type	��־��¼��ʽ
	 * @param destination  д��Ŀ��
	 * @param extra    �������
	 */
	public static void write ( String message , String level , int type , String destination , String extra) {
		
	}//end of write()
	
	
	
}//end of class logCon
