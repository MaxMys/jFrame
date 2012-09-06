package MPD.sys.Core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import MPD.app.Entry.MPD_Client_Entry;

import sun.reflect.Reflection;

/**
 * ģ�ͳ������
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
	   private boolean useWildCard;//ʹ��ͨ������أ�true Ϊʹ��ͨ�����false Ϊ��ͨsql���
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
	 * �޸����ݿ�ķ���,����Ӱ������
	 * @param sql
	 * @return
	 */
	protected int update( String sql ){
		return this.execuUpdate(sql , null);
	}//end of update
	/**
	 * �޸����ݿ�ķ���,����Ӱ������
	 * @param sql
	 * @param args ��������
	 * @return
	 */
	protected int update (String sql , Object[] args){
		return this.execuUpdate(sql, args);
	}
	/**
	 * �޸����ݵķ���
	 * @param sql
	 * @param args ����
	 * @return
	 */
	protected int update(String sql , Object args ){
		return this.execuUpdate(sql, new Object[]{ args });
	}
	/**
	 * ֱ�Ӳ�ѯ������ֱ������������sql���
	 * @param sql String ��ѯ��SQL���
	 * @param entry
	 * @return Object
	 * @access protected
	 */
	protected Object querry(  String sql , Class<?> entry ) {
		Object r = this.execuQuerry( entry , sql , null);
		 return r;
	}//end of querry()
	
	/**
	 * ʹ�� prestatement�ķ�ʽ���в�ѯ,�����Ƕ������
	 * @param sql String ��ѯ��Sql���
	 * @param args Object[] ����
	 * @param entry ʵ����
	 * @return Object
	 * @access protected
	 */
	protected Object querry(  String sql , Object[] args ,Class<?> entry ) {
		 
		return this.execuQuerry( entry , sql , args);
	}//end of querry()
	
	/**
	 * ʹ��prestatement �ķ�ʽ��ѯ�������ǵ�������
	 * @param sql String ��ѯ��Sql���
	 * @param args Object[] ����
	 * @param entry ʵ����
	 * @return Object
	 * @access protected
	 */
	protected Object querry( String sql, Object args , Class<?> entry){

		return this.execuQuerry( entry , sql , new Object[]{args});
	}//end of qerry
	
	/**
	 * group by ��Ӳ���
	 * @param strG
	 * @return  _Model
	 * @access protected
	 */
	protected _Model grouBy(String strG){
	    		strGroup="GROUP BY "+strG+" ";
	    	return this;
	    }
	 
	/**
	 * ����order����
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
	 *  ����limit
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
	 * ���ñ���   
	 * @param strF
	 * @return _Model
	 * @access protected
	 */
	public _Model table(String strF){//From ��Ӳ���
	    	strFrom="FROM "+strF+" ";
	    	return this;
	    }
	/**
	 * ���ñ�
	 * @param strF
	 * @return _Model
	 * @access protected
	 */
	protected _Model table(String strF[]){//From ��Ӳ���	  
	    		strFrom="FROM "+strF[0]+" ";
		    	for(int i=1;i<strF.length;i++)
		    	{
		    		strFrom+=(","+strF[i]+" ");
		    	 }
	    	return this;
	    }
	 
	/**
	 * ��������
	 * @param hashMap
	 * @return _Model
	 * @access protected
	 */
	public _Model where(HashMap hashMap){  //δ���
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
	 * ��������
	 * @param strWh
	 * @return _Model
	 * @access protected
	 */
	protected _Model where(String strWh){//where��Ӳ���
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
	 * ��������
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
	 * select��� ����
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
	 * select��� ����
	 * @param strSel
	 * @return _Model
	 * @access protected
	 */
	protected _Model filed(String strSel){
    	strSelect="SELECT "+strSel+" ";
    	return this;
	  }
	/**
	 * ��ȡ�ϴ�SQL���
	 * @return
	 */
	protected String getLastSql(){
		return this.LastSql;
	}
	/**
	 * ��ѯ����
	 * @return Object Entry
	 */
	public Object select(Class<?> entry){     
		 this.entry = entry;
         if(strSelect=="")             //Select Ϊ��ʱ
        	  strSelect="SELECT * ";
         if(useWildCard)
         {   args=new Object[count];
             int i=0;
        	 Iterator iterator = tmp.keySet().iterator();
             while (iterator.hasNext()) {    
                  args[i++]=tmp.get(iterator.next());
             }      
         }//end of if
         //�������Ƿ�Ϊ�գ�Ϊ��ӳ�䵱ǰģ����
         if(this.strFrom == null){
        	 String className = this.getClass().getSimpleName();
        	 this.strFrom = className.split("_")[0].toLowerCase();
         }//end of if
         //����sql
         this.strSql=
        		 strSelect+
        		 strFrom+
        		 strWhere+
        		 strOrder+
        		 strLimit+
        		 strGroup;         
	         this.strSelect="";                 //��ձ���ֵ������Ӱ���´ν����
	         this.strFrom="";
	         this.strWhere="";
	         this.strOrder="";
	         this.strLimit="";
	         this.strGroup="";
	         this.count=0;
	     //���汾��ִ��sql
	     this.LastSql = this.strSql ;
	    if(useWildCard)
	    	return this.querry(this.strSql, this.args, entry);
	    else
	    	return this.querry(this.strSql, this.entry);
    }
	/**
	 * ���뷽��
	 * @param object
	 * @return 0 ʧ�ܣ� 1 �ɹ�
	 */
	public int insert(Object object)
	{   
		if(args==null||args.length<=10)//Ϊ�����з��䶨�����飬���10���ֶ�
			args=new Object[10];
		String varName=object.getClass().getSimpleName();
		varName=varName.substring(0,varName.lastIndexOf("_"));
		String strSql="INSERT INTO ";
		String strValues="VALUES(";
		strSql+=(varName+"( ID");
		strValues+="id.nextval";
        Object[] var=new Object[20];
        count=0;
       if( praseObject(object,var)>0) //��ʼcount�α꣬��¼�����±�
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
//						  for(int j=0;j<count;j++)        //������,�������argsԪ��
//							  System.out.print(args[j]+"   ");
							  
				  return this.update(strSql, this.args);
			  }
			  else
				System.out.println("��ʵ�����!");  
			   return 0;
	}
	/**
	 * �����෽��
	 * @param object Ҫ��������
	 * @param var    �����洢�����������
	 * @return   �����Ѹ�ֵ�ı�������
	 */
	private int praseObject(Object object,Object var[])
	{   
		Field[] fields= object.getClass().getDeclaredFields();  //��ȡ��������������
		String varName;
		int num=0;
		try{	
			for(int i=0;i<fields.length;i++)  
			{   
				boolean accessFlag=fields[i].isAccessible();
				fields[i].setAccessible(true);
			    varName=fields[i].getName();  //��ȡ������
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
	 * ���·���
	 * @param object ���б�����Ϊ SET��Ĳ�����֮ǰҪ����where��������������
	 * @return 0 ʧ�� 1 �ɹ�
	 */
	public int update(Object object)
	{
		if(strWhere=="")
		{	System.out.println("֮ǰδ����where���");
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
	 * ɾ������
	 * @param object ���б�����Ϊwhere���ʽ�Ĳ���
	 * @return  0 ʧ�ܣ� 1�ɹ�
	 */
	public int delete(Object object)
	{
		if(args==null||args.length<=10)//Ϊ�����з��䶨�����飬���10���ֶ�
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

