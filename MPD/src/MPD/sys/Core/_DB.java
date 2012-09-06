package MPD.sys.Core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.TypeConstraintException;


/**
 * 
 * @author max
 * @date 2012-8-8
 * 重写DB类
 *
 */
public class _DB {

	private Connection conn = null;
	/**
	 * 构造方法
	 * @access private
	 * @param null
	 * @return null
	 */
	protected _DB() { } //end of __construct()
	
	/**
	 * 获取连接的方法
	 * @param null
	 * @return void
	 * @access private
	 */
	private void getConn(){
		this.conn = _System.getConnPoolManager().getConn();
	}//end getConn()
	
	/**
	 * 关闭数据库连接,一般非客户程序员调用
	 * @param null
	 * @access private
	 * @return void
	 */
	private  void release() {
		_System.getConnPoolManager().release(this.conn);
	}//end of close
	
	protected int execuUpdate(String sql , Object[] args){
		//影响行数
		int affectedRows = 0;
	    //获取连接
		this.getConn();
		try {
		PreparedStatement ps = this.conn.prepareStatement(sql);
		if(args != null)
			for(int i = 0 ; i < args.length && args[i]!=null ; i++){
				//动态映射
				if(args[i] instanceof String)
					ps.setString(i+1, (String)args[i]);
				else if(args[i] instanceof Boolean)
					ps.setBoolean(i+1, (Boolean)args[i]);
				else if(args[i] instanceof Double)
					ps.setDouble(i+1, (Double)args[i]);
				else if(args[i] instanceof Integer)
					ps.setInt(i+1, (Integer)args[i]);
				else if(args[i] instanceof Long)
					ps.setLong(i+1, (Long)args[i]);
				else if(args[i] instanceof Short)
					ps.setShort(i+1, (Short)args[i]);
				else if(args[i] instanceof Byte)
					ps.setByte(i+1, (Byte)args[i]);
				else if(args[i] instanceof Blob)
					ps.setBlob(i+1, (Blob)args[i]);
				else if(args[i] instanceof Date)
					ps.setDate(i+1, (Date)args[i]);
				else if(args[i] instanceof Ref)
					ps.setRef(i+1, (Ref)args[i]);
				else if(args[i] instanceof Float)
					ps.setFloat(i+1, (Float)args[i]);
				else throw new TypeConstraintException("数据类型不支持数据库"+i+":"+args[i]);
			}//end of for
			 //获取结果集
			affectedRows = ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}//end of catch
			//释放连接
			this.release();
			return affectedRows;
	}//end of execuUpdate()
	/**
	 *  执行语句多态执行，参数为语句,以及占位变量,通常调用
	 *  @return ResultSet
	 *  @access public
	 *  @param sql String
	 *  @param args String[]
	 *  
	 */
	protected Object execuQuerry(Class<?> classEntry,String sql,Object[] args){
			//数据集对象
			ResultSet r = null;
			//实体对象
			Object entry = null;
		    //获取连接
			this.getConn();
			try {

				PreparedStatement ps = this.conn.prepareStatement(sql);
	
			if(args != null)
				for(int i = 0 ; i < args.length ; i++){
					//动态映射
					if(args[i] instanceof String)
						ps.setString(i+1, (String)args[i]);
					else if(args[i] instanceof Boolean)
						ps.setBoolean(i+1, (Boolean)args[i]);
					else if(args[i] instanceof Double)
						ps.setDouble(i+1, (Double)args[i]);
					else if(args[i] instanceof Integer)
						ps.setInt(i+1, (Integer)args[i]);
					else if(args[i] instanceof Long)
						ps.setLong(i+1, (Long)args[i]);
					else if(args[i] instanceof Short)
						ps.setShort(i+1, (Short)args[i]);
					else if(args[i] instanceof Byte)
						ps.setByte(i+1, (Byte)args[i]);
					else if(args[i] instanceof Blob)
						ps.setBlob(i+1, (Blob)args[i]);
					else if(args[i] instanceof Date)
						ps.setDate(i+1, (Date)args[i]);
					else if(args[i] instanceof Ref)
						ps.setRef(i+1, (Ref)args[i]);
					else 
						throw new TypeConstraintException("数据类型不支持,或者传入null : 第"+(i+1)+"个参数"+args[i]);
					
				}//end of for
				 //获取结果集
				 r = ps.executeQuery();
				
				 if( r == null)
					 return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
			//映射实体
			try {
				entry = this.mergeObject(r, classEntry);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
			//提交
			try {
				this.conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
			//释放连接
			this.release();
			return entry;
	}//end of execu()
	
	/**
	 * resultSet填充entry对外实体
	 * Class需要遵循javaBean规范
	 * @param rs
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
    private Collection<Object> mergeObject(ResultSet rs, Class<?> clazz) throws Exception {
    	
		 ResultSetMetaData rmd = (ResultSetMetaData) rs.getMetaData();  
		 Collection<Object> coll = new ArrayList<Object>();  
		 while (rs.next()) {  
			 Object obj = clazz.newInstance();  
			 for (int i = 1; i <= rmd.getColumnCount(); i++)  
				 beanRegister(obj, rmd.getColumnName(i), rs.getString(i));//根据列名和对应值对对象属性进行赋值  
	       coll.add(obj);  
		 }//end of while
		 return coll;  
    }//end mergeObject
    /**
     * 根据get方法的返回值类型
     * 判断当前属性类型
     * 动态映射调用method
     * @param object
     * @param beanProperty
     * @param value
     */

    private void beanRegister(Object object, String beanProperty, String value) {  
        Object[] beanObject = beanMatch(object.getClass(), beanProperty);  
        Object[] cache = new Object[1];  
        Method getter = (Method) beanObject[0];  
        Method setter = (Method) beanObject[1];  
        try {  
            // 通过get获得方法类型  
            String methodType = getter.getReturnType().getName();  
            if ((methodType.equalsIgnoreCase("long") || methodType.equalsIgnoreCase("java.lang.Long"))  
                    && !"".equals(value) && value != null) {  
                cache[0] = new Long(value);  
                setter.invoke(object, cache);  
            } else if ((methodType.equalsIgnoreCase("int") || methodType.equalsIgnoreCase("java.lang.Integer"))  
                    && !"".equals(value) && value != null) {  
                cache[0] = new Integer(value);  
                setter.invoke(object, cache);  
            } else if ((methodType.equalsIgnoreCase("short") || methodType.equalsIgnoreCase("java.lang.Short"))  
                    && !"".equals(value) && value != null) {  
                cache[0] = new Short(value);  
                setter.invoke(object, cache);  
            } else if ((methodType.equalsIgnoreCase("float") || methodType.equalsIgnoreCase("java.lang.Float"))  
                    && !"".equals(value) && value != null) {  
                cache[0] = new Float(value);  
                setter.invoke(object, cache);  
            } else if ((methodType.equalsIgnoreCase("double") || methodType.equalsIgnoreCase("java.lang.Double"))  
                    && !"".equals(value) && value != null) {  
                cache[0] = new Double(value);  
                setter.invoke(object, cache);  
            } else if ((methodType.equalsIgnoreCase("boolean") || methodType.equalsIgnoreCase("java.lang.Boolean"))  
                    && !"".equals(value) && value != null) {  
                cache[0] = new Boolean(value);  
                setter.invoke(object, cache);  
            } else if ((methodType.equalsIgnoreCase("java.lang.String")) && !"".equals(value) && value != null) {  
                cache[0] = value;  
                setter.invoke(object, cache);  
            } else if (methodType.equalsIgnoreCase("java.io.InputStream")) {  
            } else if (methodType.equalsIgnoreCase("char")) {  
                cache[0] = (Character.valueOf(value.charAt(0)));  
                setter.invoke(object, cache);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } //end catch
    }//end of beanRegister
    
    /**
     * 
     * @param clazz
     * @param beanProperty
     * @return
     */
    private Object[] beanMatch(Class<?> clazz, String beanProperty) {  
        Object[] result = new Object[2];  
        char beanPropertyChars[] = beanProperty.toCharArray();  
        beanPropertyChars[0] = Character.toUpperCase(beanPropertyChars[0]);  
        String s = new String(beanPropertyChars);  
        String names[] = { ("set" + s).intern(), ("get" + s).intern(), ("is" + s).intern(), ("write" + s).intern(),  
                ("read" + s).intern() };  
        Method getter = null;  
        Method setter = null;  
        Method methods[] = clazz.getMethods();  
        for (int i = 0; i < methods.length; i++) {  
            Method method = methods[i];  
            if (!Modifier.isPublic(method.getModifiers()))  
                continue;  
            String methodName = method.getName().intern();  
            for (int j = 0; j < names.length; j++) {  
                String name = names[j];  
                if (!name.equals(methodName))  
                    continue;  
                if (methodName.startsWith("set") || methodName.startsWith("read"))  
                    setter = method;  
                else  
                    getter = method;  
            } //end of inside for
        }  //end of outside for
        result[0] = getter;  
        result[1] = setter;  
        return result;  
    }//end of beanMatch()
	
}//end class
