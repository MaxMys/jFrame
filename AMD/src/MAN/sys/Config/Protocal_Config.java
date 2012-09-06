package MAN.sys.Config;

import MAN.sys.Core._Config;


/**
 * 通信格式
 * @author max
 * @date
 *
 */
public class Protocal_Config extends _Config{

	public Protocal_Config() {
		super();
		/*
		 * ---------------------------------------------------------------
		 * 请求头部
		 * ---------------------------------------------------------------
		 */
		this.configs.put("request", "AMD");
		
	}//end of construct
	
}//end of class
