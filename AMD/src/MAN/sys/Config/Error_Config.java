package MAN.sys.Config;

import MAN.sys.Core._Config;



public class Error_Config extends _Config {

	public Error_Config() {
		super();
		/*
		 * ---------------------------------------------------------------
		 * Ĭ���쳣������
		 * �����Ҫ���ĵĻ�����̳к���д���캯������֤�޲���
		 * ---------------------------------------------------------------
		 */
		this.configs.put("defaulthandle", "MAN.sys.Core._ExceHandle");
		
	}
}
