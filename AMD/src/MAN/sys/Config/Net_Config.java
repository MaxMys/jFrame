package MAN.sys.Config;

import MAN.sys.Core._Config;





public class Net_Config extends _Config{
	
	public Net_Config () {
		
		super();
		/*
		 * ---------------------------------------------------------------
		 * 服务器IP
		 * ---------------------------------------------------------------
		 */
		this.configs.put("sever", "10.10.20.242");
		/*
		 * ---------------------------------------------------------------
		 * 服务器端口
		 * ---------------------------------------------------------------
		 */
		this.configs.put("serveport", "7777");
		
	}//end of errorConfig()
}
