package MAN.sys.Core;

import java.util.HashMap;
import java.util.Iterator;


/**
 * config �������
 * @author max
 * @date 2012-8-1
 *
 */
 public abstract class _Config {
	 
	 
	 //define the protected var
	 protected  _Config config = null;
	 protected HashMap<String, String> configs = null;
	 
	 /**
	  * <p>˽�й��췽��</p>
	  * <p>����config</p>
	  * @param null
	  * @return null
	  * @access protected
	  */
	 public _Config() {
		 this.configs = new HashMap<String,String>();
	 }
	 
	 /**
	  * ��ȡָ�������ݶ���,ͨ��String Key
	  * @return String
	  * @param String 
	 * @throws Exception 
	  */
	 public String get(String Key) throws Exception {
			// TODO Auto-generated method stub
			String container =  this.configs.get(Key);
			if( container == null )
				throw new  ArrayIndexOutOfBoundsException("��ֵԽ�� KEYΪ��"+Key);
			return container;
	 }
	 
	 /**
	  * ��ȡָ�������ݶ��� ��ͨ��String[] Keys
	  * @param Keys
	  * @return null
	  * @access public
	  */
	 public  String get(String[] Keys) throws Exception {
		// TODO Auto-generated method stub

			String[] container = new String[Keys.length];
			for ( int i = 0 ; i < Keys.length ; i++)
				container[i] = this.configs.get(Keys[i]);
			return null;
	 }
	 
	 /**
	  * ��ȡ���е�������Ϣ
	  * @return
	  */
	 public String[] getAll() {
		 return null;
//		//��ȡhashmap������
//		 HashMap_Helper h = 
//				 (HashMap_Helper) _Hook.getHook().
//				 					loadHelper(HashMap_Helper.class);
//		 return h.getAll(this.configs);
		 
	 }//end of getAll();
	 
	 /**
	  * 
	  */
	 public HashMap getConfigs() {
		 return this.configs;
	 }
}//end of class
