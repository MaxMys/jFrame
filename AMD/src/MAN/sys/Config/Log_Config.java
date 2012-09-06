package MAN.sys.Config;

import MAN.sys.Core._Config;


public class Log_Config extends _Config{

	public Log_Config () {
		
		super();
		/*
		 * ---------------------------------------------------------------
		 * logÊÇ·ñ¿ªÆô
		 * ---------------------------------------------------------------
		 */
		this.configs.put("log","on");
		
		
		/*
		 * ---------------------------------------------------------------
		 * ´íÎóÊÇ·ñlog
		 * ---------------------------------------------------------------
		 */
		 this.configs.put("errorLog" , "false"); 
	
		
	}//end of errorConfig()
}
