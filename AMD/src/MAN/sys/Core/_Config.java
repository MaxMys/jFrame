package MAN.sys.Core;

import java.util.HashMap;
import java.util.Iterator;


/**
 * config 抽象基类
 * @author max
 * @date 2012-8-1
 *
 */
 public abstract class _Config {
	 
	 
	 //define the protected var
	 protected  _Config config = null;
	 protected HashMap<String, String> configs = null;
	 
	 /**
	  * <p>私有构造方法</p>
	  * <p>单例config</p>
	  * @param null
	  * @return null
	  * @access protected
	  */
	 public _Config() {
		 this.configs = new HashMap<String,String>();
	 }
	 
	 /**
	  * 获取指定的数据对象,通过String Key
	  * @return String
	  * @param String 
	 * @throws Exception 
	  */
	 public String get(String Key) throws Exception {
			// TODO Auto-generated method stub
			String container =  this.configs.get(Key);
			if( container == null )
				throw new  ArrayIndexOutOfBoundsException("键值越界 KEY为："+Key);
			return container;
	 }
	 
	 /**
	  * 获取指定的数据对象 ，通过String[] Keys
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
	  * 获取所有的配置信息
	  * @return
	  */
	 public String[] getAll() {
		 return null;
//		//获取hashmap辅助类
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
