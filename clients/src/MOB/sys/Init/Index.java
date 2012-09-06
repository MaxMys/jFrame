package MOB.sys.Init;

import MOB.sys.Common.Common;
import MOB.sys.Core._ExceHandle;
import MOB.sys.Core._LogCon;
import MOB.sys.Core._Runtime;
import MOB.sys.Core._System;



/**
 * ��Ŀ����ļ�
 * @date2012-8-4
 * @author max
 *
 */
public class Index {

	
	
	/**
	 * �������
	 * @param arg
	 * @return null
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		//��ʼ��ϵͳ
		 _System.init();
		
		//��¼����ʱ���
		 _System.getBenchMark().markMem("start");
		//��¼��ʼ��ʹ���ڴ�
		 _System.getBenchMark().markTime("start");
		
		
		 
		//������ʱ������ڴ����ϵͳ
		 _System.getState().put(
				 "start_time", 
				 _System.getBenchMark().elapsedTime(null, "start")
				 );
		 _System.getState().put(
				 "start_mem", 
				 _System.getBenchMark().elapsedMem(null, "start")
				 );

		 /**
		  * -----------------------------------------------
		  * ��Ҫ���룬����ɾ����������ĿĿ¼֮�����и��� 
		  * -----------------------------------------------
		  */
		 //������Ŀ����
		 Common.C("System_Config","project","MOB.");
		 //������Ŀ����·��
		 //apppath
		 Common.C("System_Config",
				 	"apppath",
				 		_System.getConfigs().get(
				 				Common.getKey("System_Config","project")
				 							)
				 								+"app."
				 );
		 //syspath
		 Common.C("System_Config",
				 	"syspath",
						 _System.getConfigs().get(
					 				Common.getKey("System_Config","project")
					 						)
					 							+"sys."
				);
	
		
		//�û��Զ�����������,������÷���
		/**
		 *---------------------------------------------------
		 *	ʾ������
		 *---------------------------------------------------
		 * 
		 *  ��̬�������ģʽ 
		 *  Common.C("system_config","mode","debug")
		 */
		
		 //��������ģʽ
		 Common.C("System_Config","mode");
		 
		
		 //��ʼ����־��,��ʼ����־�����
		 _System.setLogCon(new _LogCon());

		//��ʼ�����ж���
		 _System.setRunTime(_Runtime.init());
		 //��������ʱ�ļ�,�����������
		 _System.getRunTime().start();
		
	}//end of main()
	
}//end of main class
