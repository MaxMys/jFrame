package MAN.sys.Config;

import MAN.sys.Core._Config;





public class Net_Config extends _Config{
	
	public Net_Config () {
		
		super();
		/*
		 * ---------------------------------------------------------------
		 * ������IP
		 * ---------------------------------------------------------------
		 */
		this.configs.put("sever", "10.10.20.242");
		/*
		 * ---------------------------------------------------------------
		 * �������˿�
		 * ---------------------------------------------------------------
		 */
		this.configs.put("serveport", "7777");
		
	}//end of errorConfig()
}
