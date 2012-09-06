package MOB.sys.Config;

import MOB.sys.Core._Config;

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
		 * 保持连接格式
		 * ---------------------------------------------------------------
		 */
		this.configs.put("alive", "ALI");
		
	}//end of construct
	
}//end of class
