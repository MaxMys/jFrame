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
	 * ��ʼ����ȡ�����ļ��ķ���
	 * C����û�е���N����ֹ�����޵ĵݹ�
	 * @param one
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	public static String C(Class<?> one , String index)  {
		
		//��ȡ����
		String key = Common.getKey(one, index);
		String tmp  = null;
		//���ڼ�����
		if( _System.getConfigs().containsKey(key) )
			return _System.getConfigs().get(key);
		//��̬ʵ����
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
		
		//����������Ϣ
		_System.getConfigs().put(key,tmp);
		
		return tmp;	
	}//end of C(Class config)

	/**
	 * ��ʼ����ȡ�����ļ��ķ���
	 * C����û�е���N����ֹ�����޵ĵݹ�
	 * @param one String
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	public static String C(String one , String index)  {
			
		//ȫ��Сд
		String key = Common.getKey(one, index);
		
		//���ڼ�����
		if( _System.getConfigs().containsKey(key) )
			return _System.getConfigs().get(key);
		
		//���������,ʵ�������һ�ȡ����ֵ
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
		//�������
		_System.getConfigs().put(key,tmp);
		
		return tmp;
	}
	
	/**
	 * ���û�������������������ʱ��Ч�� ���������޷�����
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
	 * C����������������������������,�涨ȫСд
	 * @param one
	 * @param index
	 * @return
	 */
	public static String getKey(String one,String index) {
		return one.toLowerCase()+"_"+index.toLowerCase();
	}//end of getKey(String one,String index)
	/**
	 * C�������������������������������涨ȫСд
	 * @param one
	 * @param index
	 * @return
	 */
	private static String getKey(Class<?> one, String index) {
		return one.getSimpleName().toLowerCase() + index.toLowerCase();
	}//end of getKey(Class<?> one,String index)
	/**
	 * ����Class��̬ʵ���� ĳ������,�������ж���֧�ָ����ձ�
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
	 * @param name String ��Ҫʵ������ȫ��
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
		
		//����app�еĸ���
		String[] app = Common.C(System_Config.class, "app").split(",");
		
		//define the path
		String path  = null;
		
		//����Ǻ����ļ� ���� ��֧�ֺ����ļ�
		if( name.startsWith("_") )
			return null;
		
		//�������ͨ�ļ�
		String parent = null;
		//��������
		if( name.split(".").length != 2 )
			return null;
		name = name.split(".")[0];
		
		if( name.split("_").length !=2 )
			return null;
		parent = name.split("_")[1] ; 
		
		//����ǰ׺
		String prefix  = Tools.arrContain(app, parent)  ?  "MPD.app." : "MPD.sys." ;
		
		path = prefix + name;
		
		//ʵ����
		try {
			return Class.forName(path);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		return null;
	}//end of getClass()

	/**
	 * M()����ʵ����Model 
	 * 
	 * @param name
	 * @return
	 */
	public static _Model M(String name) {
		
		if( name == null)
			return null;
		
		if( ! name.endsWith("_Model"))
			return null;
		//��֯·��
		String forName = null;
		forName = "MPD.app.Model."+name;
		
		return (_Model)Common.N(forName);
	}
	/**
	 * A��������ʵ����controller
	 * @param null
	 * @return Object(Controller)
	 * @throws Exception 
	 * @access public
	 */
	public static _Controller A(String name,HashMap hm) throws Exception {
		
		if(name == null) throw new Exception("no such controller");
		
		//û�к�׺����Ӧ
		if( ! name.endsWith("_Controller"))
			name = name+"_Controller";
		//û��ǰ׺����Ӧ
		if( ! name.startsWith(C("System_Config","apppath")))
			name = C("System_Config","apppath")+"Controller." + name;
		return (_Controller)Common.N(name);
	}//end of _Controller
	
	/**
	 * ʵ����H����
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
			
		//��֯·��
		String forName = null;
		forName = "MPD.sys."+h.get("fullname");
		
		return (_Helper)Common.N(forName);
		
	}//end of H()
	
	
	/**
	 * �����ļ�����,��ȡ�ļ�·��
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
		}//����������ȫ��
			
		if(!fullname.endsWith("java"))
			fullname += ".java";
		
	    HashMap<String,String> returnOne = new HashMap<String,String>();
		//��������
		if( fullname.split(".").length != 2 ) 
			return null;
		//��������
		returnOne.put("fullname",fullname.split(".")[0]);
		
		if( returnOne.get("fullname").split(".").length !=2 )
			return null;
		//��������
		returnOne.put("parent", returnOne.get("fullname").split("_")[1]);
		//�Լ�����
		returnOne.put("self", returnOne.get("fullname").split("_")[0]);
		
		return returnOne;
		
	}

	public static String md5(String str){
		if(Common.m == null) 
			Common.m = new MD5();
		return Common.m.getMD5ofStr(str);
	}
	

}//end of class Common
