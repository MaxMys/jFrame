package MAN.sys.Config;

import MAN.sys.Core._Config;



public class Error_Config extends _Config {

	public Error_Config() {
		super();
		/*
		 * ---------------------------------------------------------------
		 * 默认异常处理器
		 * 如果需要更改的话，请继承后重写构造函数，保证无参数
		 * ---------------------------------------------------------------
		 */
		this.configs.put("defaulthandle", "MAN.sys.Core._ExceHandle");
		
	}
}
