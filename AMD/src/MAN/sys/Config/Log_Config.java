package MAN.sys.Config;

import MAN.sys.Core._Config;


public class Log_Config extends _Config{

	public Log_Config () {
		
		super();
		/*
		 * ---------------------------------------------------------------
		 * log�Ƿ���
		 * ---------------------------------------------------------------
		 */
		this.configs.put("log","on");
		
		
		/*
		 * ---------------------------------------------------------------
		 * �����Ƿ�log
		 * ---------------------------------------------------------------
		 */
		 this.configs.put("errorLog" , "false"); 
	
		
	}//end of errorConfig()
}
