package MPD.sys.Config;

import MPD.sys.Core._Config;

public class Error_Config extends _Config {

	public Error_Config() {
		super();
		/*
		 * ---------------------------------------------------------------
		 * 默认异常处理器
		 * 如果需要更改的话，请继承后重写构造函数，保证无参数
		 * ---------------------------------------------------------------
		 */
		this.configs.put("defaulthandle", "MPD.sys.Core._ExceHandle");
		
	}
}
