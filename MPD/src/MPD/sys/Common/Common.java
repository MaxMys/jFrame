package MPD.sys.Common;

import java.util.HashMap;

import MPD.sys.Config.System_Config;
import MPD.sys.Core._Config;
import MPD.sys.Core._Controller;
import MPD.sys.Core._Helper;
import MPD.sys.Core._Model;
import MPD.sys.Core._System;
import MPD.sys.Helper.MD5;

public class Common {
	
	private static MD5 m =null;

	/**
	 * 初始化获取配置文件的方法
	 * C方法没有调用N，阻止了无限的递归
	 * @param one
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	public static String C(Class<?> one , String index)  {
		
		//获取索引
		String key = Common.getKey(one, index);
		String tmp  = null;
		//存在即返回
		if( _System.getConfigs().containsKey(key) )
			return _System.getConfigs().get(key);
		//动态实例化
		_Config con;
		try {
			con = (_Config)one.newInstance();
			tmp = con.get(index);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//缓存配置信息
		_System.getConfigs().put(key,tmp);
		
		return tmp;	
	}//end of C(Class config)

	/**
	 * 初始化获取配置文件的方法
	 * C方法没有调用N，阻止了无限的递归
	 * @param one String
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	public static String C(String one , String index)  {
			
		//全部小写
		String key = Common.getKey(one, index);
		
		//存在即返回
		if( _System.getConfigs().containsKey(key) )
			return _System.getConfigs().get(key);
		
		//如果不存在,实例化并且获取配置值
		String tmp = null;
		try {
			tmp = (
					(_Config) 
						Common.N(
							_System.getConfigs().get(
								 Common.getKey("System_Config", "syspath")	
										)
								+"Config."
									+one
								)
					).get(index);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//缓存对象
		_System.getConfigs().put(key,tmp);
		
		return tmp;
	}
	
	/**
	 * 设置环境变量，仅仅在运行时有效， 后续代码无法覆盖
	 * @param one
	 * @param index
	 * @param value
	 */
	public static void C(Class<?> one , String index , String value) {
		
		String key = Common.getKey(one, index);
		
		_System.getConfigs().put(key, value);
		
	}//end of C();
	
	public static void C(String one , String index , String value) {
		
		String key = Common.getKey(one, index);
		
		_System.getConfigs().put(key, value);
		
	}
	
	
	/**
	 * C方法辅助函数，构建配置索引名,规定全小写
	 * @param one
	 * @param index
	 * @return
	 */
	public static String getKey(String one,String index) {
		return one.toLowerCase()+"_"+index.toLowerCase();
	}//end of getKey(String one,String index)
	/**
	 * C方法辅助函数，构建配置索引名，规定全小写
	 * @param one
	 * @param index
	 * @return
	 */
	private static String getKey(Class<?> one, String index) {
		return one.getSimpleName().toLowerCase() + index.toLowerCase();
	}//end of getKey(Class<?> one,String index)
	/**
	 * 根据Class动态实例化 某个对象,对于所有对象支持更加普遍
	 * @param one
	 * @return 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object N(Class<?> one){
		
		try {
			return one.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param name String 需要实例化的全名
	 * @return
	 * @access public
	 * @static
	 */
	public static Object N(String name)  {
		try {
			return Common.N(Class.forName(name));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}//end of N()
	
	public static Class<?> getClass(String name) {
		
		//定义app中的父类
		String[] app = Common.C(System_Config.class, "app").split(",");
		
		//define the path
		String path  = null;
		
		//如果是核心文件 返回 不支持核心文件
		if( name.startsWith("_") )
			return null;
		
		//如果是普通文件
		String parent = null;
		//解析名字
		if( name.split(".").length != 2 )
			return null;
		name = name.split(".")[0];
		
		if( name.split("_").length !=2 )
			return null;
		parent = name.split("_")[1] ; 
		
		//定义前缀
		String prefix  = Tools.arrContain(app, parent)  ?  "MPD.app." : "MPD.sys." ;
		
		path = prefix + name;
		
		//实例化
		try {
			return Class.forName(path);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		return null;
	}//end of getClass()

	/**
	 * M()用于实例化Model 
	 * 
	 * @param name
	 * @return
	 */
	public static _Model M(String name) {
		
		if( name == null)
			return null;
		
		if( ! name.endsWith("_Model"))
			return null;
		//组织路径
		String forName = null;
		forName = "MPD.app.Model."+name;
		
		return (_Model)Common.N(forName);
	}
	/**
	 * A方法用于实例化controller
	 * @param null
	 * @return Object(Controller)
	 * @throws Exception 
	 * @access public
	 */
	public static _Controller A(String name,HashMap hm) throws Exception {
		
		if(name == null) throw new Exception("no such controller");
		
		//没有后缀自适应
		if( ! name.endsWith("_Controller"))
			name = name+"_Controller";
		//没有前缀自适应
		if( ! name.startsWith(C("System_Config","apppath")))
			name = C("System_Config","apppath")+"Controller." + name;
		return (_Controller)Common.N(name);
	}//end of _Controller
	
	/**
	 * 实例化H方法
	 * @param name
	 * @return
	 */
	public static _Helper H(String name) {
		if( name == null )
			return null;
		HashMap<String,String> h = Common.parseName(name);
		
		if( 
				 h.get("self") == "" ||
					 h.get("parent") =="" ||
						 h.get("fullname") ==""
				)
				return null;
			
			if(!h.get("parent").equals("Helper"))
				return null;
			
		//组织路径
		String forName = null;
		forName = "MPD.sys."+h.get("fullname");
		
		return (_Helper)Common.N(forName);
		
	}//end of H()
	
	
	/**
	 * 解析文件名字,获取文件路径
	 * @param fullname
	 * @return Map parent self fullname
	 */
	public static HashMap<String,String> parseName(String fullname) {
		
		if(fullname.split(".").length >= 3){
			HashMap<String,String> h = new HashMap<String,String>();
			if(fullname.endsWith("java"))
				fullname = fullname.substring(0, fullname.length()-5);
			h.put("fullname",fullname);
			h.put("self", fullname.split("_")[0]);
			h.put("parent", fullname.split("_")[1]);
		}//如果输入的是全名
			
		if(!fullname.endsWith("java"))
			fullname += ".java";
		
	    HashMap<String,String> returnOne = new HashMap<String,String>();
		//解析名字
		if( fullname.split(".").length != 2 ) 
			return null;
		//整体名字
		returnOne.put("fullname",fullname.split(".")[0]);
		
		if( returnOne.get("fullname").split(".").length !=2 )
			return null;
		//父类名字
		returnOne.put("parent", returnOne.get("fullname").split("_")[1]);
		//自己名字
		returnOne.put("self", returnOne.get("fullname").split("_")[0]);
		
		return returnOne;
		
	}

	public static String md5(String str){
		if(Common.m == null) 
			Common.m = new MD5();
		return Common.m.getMD5ofStr(str);
	}
	

}//end of class Common
