package MPD.sys.Config;

import MPD.sys.Core._Config;

public class Error_Config extends _Config {

	public Error_Config() {
		super();
		/*
		 * ---------------------------------------------------------------
		 * Ĭ���쳣������
		 * �����Ҫ���ĵĻ�����̳к���д���캯������֤�޲���
		 * ---------------------------------------------------------------
		 */
		this.configs.put("defaulthandle", "MPD.sys.Core._ExceHandle");
		
	}
}
