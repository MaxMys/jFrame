package MPD.sys.Core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import MPD.app.Entry.MPD_Client_Entry;

import sun.reflect.Reflection;

/**
 * 模型抽象基类
 * @author max
 * @date 2012-8-9
 *
 */
public abstract class _Model extends _DB{
	
	   private String strSelect;
	   private String strFrom;
	   private String strWhere;
	   private String strOrder;
	   private String strLimit;
	   private String strGroup;
	   private final boolean ASC=true;
	   private final boolean DESC=false;
	   private boolean useWildCard;//使用通配符开关，true 为使用通配符，false 为普通sql语句
	   private String strSql;
	   private TreeMap<Integer,Object> tmp;
	   private Object[] args;  
	   private int count;
	   private Class<?> entry = null;
	   private String LastSql = null;

	public _Model () {
		
	   	useWildCard=true;
    	strSelect="";
    	strFrom="";
    	strWhere="";
    	strOrder="";
    	strLimit="";
    	strGroup="";
    	count=0;
    	if(useWildCard)
           tmp=new TreeMap<Integer,Object>();
	}
	/**
	 * 修改数据库的方法,返回影响行数
	 * @param sql
	 * @return
	 */
	protected int update( String sql ){
		return this.execuUpdate(sql , null);
	}//end of update
	/**
	 * 修改数据库的方法,返回影响行数
	 * @param sql
	 * @param args 对象数组
	 * @return
	 */
	protected int update (String sql , Object[] args){
		return this.execuUpdate(sql, args);
	}
	/**
	 * 修改数据的方法
	 * @param sql
	 * @param args 对象
	 * @return
	 */
	protected int update(String sql , Object args ){
		return this.execuUpdate(sql, new Object[]{ args });
	}
	/**
	 * 直接查询方法，直接输入完整的sql语句
	 * @param sql String 查询的SQL语句
	 * @param entry
	 * @return Object
	 * @access protected
	 */
	protected Object querry(  String sql , Class<?> entry ) {
		Object r = this.execuQuerry( entry , sql , null);
		 return r;
	}//end of querry()
	
	/**
	 * 使用 prestatement的方式进行查询,参数是多个数组
	 * @param sql String 查询的Sql语句
	 * @param args Object[] 参数
	 * @param entry 实体类
	 * @return Object
	 * @access protected
	 */
	protected Object querry(  String sql , Object[] args ,Class<?> entry ) {
		 
		return this.execuQuerry( entry , sql , args);
	}//end of querry()
	
	/**
	 * 使用prestatement 的方式查询，参数是单个对象
	 * @param sql String 查询的Sql语句
	 * @param args Object[] 参数
	 * @param entry 实体类
	 * @return Object
	 * @access protected
	 */
	protected Object querry( String sql, Object args , Class<?> entry){

		return this.execuQuerry( entry , sql , new Object[]{args});
	}//end of qerry
	
	/**
	 * group by 后接参数
	 * @param strG
	 * @return  _Model
	 * @access protected
	 */
	protected _Model grouBy(String strG){
	    		strGroup="GROUP BY "+strG+" ";
	    	return this;
	    }
	 
	/**
	 * 设置order属性
	 * @param strO
	 * @param order
	 * @return _Model
	 * @access public
	 */
	protected _Model order(String strO,boolean order){
	    		strOrder="ORDER BY "+strO+" ";
	    	 if(order==ASC)
	     		strOrder+="ASC ";
	     	   else 
	     		strOrder+="DESC ";
	    	return this;
	    }
	/**
	 *  设置limit
	 * @param begin
	 * @param end
	 * @return _Model
	 * @access protecteds
	 */
	protected _Model limit(int begin,int end){//Limit x,y
	    	strLimit="LIMIT "+begin+","+end+" ";
	    	return this;
	    }
	public _Model limit(String m){
		strLimit=m;
    	return this;
	}
	/**
	 * 设置表名   
	 * @param strF
	 * @return _Model
	 * @access protected
	 */
	public _Model table(String strF){//From 后接参数
	    	strFrom="FROM "+strF+" ";
	    	return this;
	    }
	/**
	 * 设置表
	 * @param strF
	 * @return _Model
	 * @access protected
	 */
	protected _Model table(String strF[]){//From 后接参数	  
	    		strFrom="FROM "+strF[0]+" ";
		    	for(int i=1;i<strF.length;i++)
		    	{
		    		strFrom+=(","+strF[i]+" ");
		    	 }
	    	return this;
	    }
	 
	/**
	 * 设置条件
	 * @param hashMap
	 * @return _Model
	 * @access protected
	 */
	public _Model where(HashMap hashMap){  //未完成
	    	if(useWildCard)
	    	{   int i=200;
	    		Iterator iterator = hashMap.keySet().iterator();
			    Object key = iterator.next();
				strWhere="WHERE "+key+"  = ? ";
				tmp.put(i++, hashMap.get(key));
				count++;
				while (iterator.hasNext()) { 
					key = iterator.next();
					strWhere+="AND "+key+"  = ? "; 
					tmp.put(i++, hashMap.get(key));
					count++;
				}
	       	}
	    	else{
	    		Iterator iterator = hashMap.keySet().iterator();
	    		Object key = iterator.next();
	    		strWhere="WHERE "+key+" = "+"'"+hashMap.get(key)+"' ";
	    		while (iterator.hasNext()) {    
	    			key = iterator.next();
	    			strWhere+="AND "+key+" = "+"'"+hashMap.get(key)+"' ";  
	    		}
	    	}
	    	
	    	return this;
	    }
	/**
	 * 设置条件
	 * @param strWh
	 * @return _Model
	 * @access protected
	 */
	protected _Model where(String strWh){//where后接参数
	    	if(useWildCard)
	    	{
	    		strWhere="WHERE "+strWh+" ";
	    		tmp.put(200, strWh);
	    		count++;
	    		}
	    	else
	    	{strWhere="WHERE "+strWh+" ";
	    	}
	    	return this;
	    }
	/**
	 * 设置条件
	 * @param strWh
	 * @return _Model
	 * @access protected
	 */
	protected _Model where(String strWh[]){ 
	    	if(useWildCard)
	    	{
	    		strWhere="WHERE ?";
	    		tmp.put(200, strWh[0]);
	    		count++;
		    	for(int i=1;i<strWh.length;i++)
		    	{
		    		strFrom+=(",? ");
		    		tmp.put(200+i, strWh[i]);
		    		count++;
		    	 }
	    	}
	    	else{
	    		strWhere="WHERE "+strWh[0];
	        	for(int i=1;i<strWh.length;i++)
	        	{
	        		strWhere+=(","+strWh[i]);
	        	}
	    	}
	    	return this;
	    }
	   
	/**
	 * select后接 参数
	 * @param strSel
	 * @return _Model
	 * @access protected
	 */
	protected _Model filed(String strSel[]){  
	    		strSelect="SELECT "+strSel[0];
	        	for(int i=1;i<strSel.length;i++)
	        		strWhere+=(","+strSel[i]);
	    	return this;
	    }
	/**
	 * select后接 参数
	 * @param strSel
	 * @return _Model
	 * @access protected
	 */
	protected _Model filed(String strSel){
    	strSelect="SELECT "+strSel+" ";
    	return this;
	  }
	/**
	 * 获取上次SQL语句
	 * @return
	 */
	protected String getLastSql(){
		return this.LastSql;
	}
	/**
	 * 查询方法
	 * @return Object Entry
	 */
	public Object select(Class<?> entry){     
		 this.entry = entry;
         if(strSelect=="")             //Select 为空时
        	  strSelect="SELECT * ";
         if(useWildCard)
         {   args=new Object[count];
             int i=0;
        	 Iterator iterator = tmp.keySet().iterator();
             while (iterator.hasNext()) {    
                  args[i++]=tmp.get(iterator.next());
             }      
         }//end of if
         //检测表名是否为空，为空映射当前模块名
         if(this.strFrom == null){
        	 String className = this.getClass().getSimpleName();
        	 this.strFrom = className.split("_")[0].toLowerCase();
         }//end of if
         //构建sql
         this.strSql=
        		 strSelect+
        		 strFrom+
        		 strWhere+
        		 strOrder+
        		 strLimit+
        		 strGroup;         
	         this.strSelect="";                 //清空变量值，以免影响下次结果。
	         this.strFrom="";
	         this.strWhere="";
	         this.strOrder="";
	         this.strLimit="";
	         this.strGroup="";
	         this.count=0;
	     //保存本次执行sql
	     this.LastSql = this.strSql ;
	    if(useWildCard)
	    	return this.querry(this.strSql, this.args, entry);
	    else
	    	return this.querry(this.strSql, this.entry);
    }
	/**
	 * 插入方法
	 * @param object
	 * @return 0 失败， 1 成功
	 */
	public int insert(Object object)
	{   
		if(args==null||args.length<=10)//为插入列分配定长数组，最多10个字段
			args=new Object[10];
		String varName=object.getClass().getSimpleName();
		varName=varName.substring(0,varName.lastIndexOf("_"));
		String strSql="INSERT INTO ";
		String strValues="VALUES(";
		strSql+=(varName+"( ID");
		strValues+="id.nextval";
        Object[] var=new Object[20];
        count=0;
       if( praseObject(object,var)>0) //初始count游标，记录数组下标
			for(int i=0;i<var.length;i++)  
			{   
				if(var[i]==null) break;
					strSql+=(","+var[i++]);
					strValues+=(",?");
					args[count++]=var[i];
			  }
			  if(count>0)
			  {
				  strSql+=") ";
				  strValues+=")";
				  strSql+=strValues;
//				  System.out.println(strSql);
//						  for(int j=0;j<count;j++)        //测试用,输出所有args元素
//							  System.out.print(args[j]+"   ");
							  
				  return this.update(strSql, this.args);
			  }
			  else
				System.out.println("空实体错误!");  
			   return 0;
	}
	/**
	 * 解析类方法
	 * @param object 要解析的类
	 * @param var    用来存储类变量的数组
	 * @return   类中已赋值的变量个数
	 */
	private int praseObject(Object object,Object var[])
	{   
		Field[] fields= object.getClass().getDeclaredFields();  //获取类中所有属性域
		String varName;
		int num=0;
		try{	
			for(int i=0;i<fields.length;i++)  
			{   
				boolean accessFlag=fields[i].isAccessible();
				fields[i].setAccessible(true);
			    varName=fields[i].getName();  //获取属性名
			    Object varObject=fields[i].get(object);
			   if( (fields[i].getType() ==String.class&&varObject!=null)
				 ||(fields[i].getType() ==float.class&&!varObject.equals(0.0f))
			     ||(fields[i].getType() ==int.class&&!varObject.equals(0)))
			    {
					var[num++]=varName;
				    var[num++]=varObject;
				}
			   fields[i].setAccessible(accessFlag);
			  }			
			}catch (IllegalArgumentException ex)
			{
			   ex.printStackTrace();	
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		return num;
	}
	/**
	 * 更新方法
	 * @param object 类中变量作为 SET后的参数，之前要调用where函数，设置条件
	 * @return 0 失败 1 成功
	 */
	public int update(Object object)
	{
		if(strWhere=="")
		{	System.out.println("之前未调用where语句");
			return 0;
		}
		String varName=object.getClass().getSimpleName();
		varName=varName.substring(0,varName.lastIndexOf("_"));
		String strSql="UPDATE "+varName+" SET ";
		Object[] var=new Object[20];
		this.args = new Object[10];
		int count = 0;
		 if( praseObject(object,var)>0) 
			{for(int i=0;i<var.length;i++)  
				{   
					if(var[i]==null) break;
					this.args[count++]=var[i];
				    strSql+=(var[i++]+"=?");
				    
				  }
			   strSql+=" "+strWhere;
			   System.out.println(strSql);
			   return this.update(strSql, this.args);
			}	 
		 return 0;
	}
	/**
	 * 删除方法
	 * @param object 类中变量作为where表达式的参数
	 * @return  0 失败， 1成功
	 */
	public int delete(Object object)
	{
		if(args==null||args.length<=10)//为插入列分配定长数组，最多10个字段
			args=new Object[10];
		 count=0;
		String varName=object.getClass().getSimpleName();
		varName=varName.substring(0,varName.lastIndexOf("_"));
		String strSql="DELETE FROM "+varName+" WHERE ";
		Object[] var=new Object[20];
		 if( praseObject(object,var)>0) 
			{for(int i=0;i<var.length;i++)  
				{   
					if(var[i]==null) break;
				    strSql+=(var[i++]+"= ? AND ");
				    args[count++]=var[i];
				  }
			   strSql=strSql.substring(0,strSql.lastIndexOf("AND"));
			   return this.update(strSql, this.args);
			}	 
		return 0;
	}
}

