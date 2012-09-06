package MPD.sys.Config;

import MPD.sys.Core._Config;

public class Protocal_Config extends _Config {

	public Protocal_Config() {
		super();
		/*
		 * ---------------------------------------------------------------
		 * 发送给客户端短信
		 * ---------------------------------------------------------------
		 */
		this.configs.put("messagetoclient","STCM");
	}//end of construct
}//end of class
