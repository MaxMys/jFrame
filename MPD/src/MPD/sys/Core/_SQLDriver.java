package MPD.sys.Core;

import java.util.HashMap;

/**
 * 
 * SQL语句构造器抽象基类，对于所有的SQL语句必须要调用该基类的子类来创建
 * @author max
 * @date 2012-8-1
 *
 */
 public abstract  class _SQLDriver {
	
	    //define the private var field
	    protected String ExecuSql = null ;  //本次待执行的SQL；
	    protected String subSql = null ; //定义子句;
	    
	    //定义子对象
	    protected _SQLDriver sub= null ; //定义子对象
	    
	    protected int mode ; //本次执行行为 select  1 update  2 insert 3  delete 4
	    protected String where = null; //定义where子句内容
	    protected String field = null;  //定义查询字段;
	    protected String table = null ; //定义需要查询的表
		/**
		 * 公用构造方法
		 */
	    protected  _SQLDriver() {
	    	
	    }//end of __construct()
  
	    /**
	     * select 构造方法,可重写
	     * @return null;
	     * @access public;
	     */
	    
	    public void select() {
	    	
	    	this.mode = 1;
	    	
	    } //end of select()
	    
	    /**
	     * update 构造方法可从写
	     * @return null;
	     * @access public;
	     */
		public void update() {
			
			this.mode = 2;
			
		}//end of update()
		
		/**
		 * delete 构造方法可重写
		 * @return null ; 
		 * @access public ;
		 */
		public void delete() {
			
			this.mode = 3;
			
		}//end of delete();
		
		/**
		 * insert 构造方法可重写
		 * @return null;
		 * @access public;
		 */
		public void insert() {
			
			this.mode = 4;
			
		}//end of insert();
		
		
		/**
		 * 设置查询条件,多态执行,支持字符串等其他参数类型
		 * @access public
		 * @param String whereSql
		 * @return null
		 * 
		 */
		public abstract void where(String whereSql);
		
		/**
		 * 设置查询条件，多态执行，支持map等其他参数类型
		 * @param whereItems String[]
		 * @access public
		 * @return null
		 */
		public abstract void where(HashMap<String, String> whereItems);
		
		/**
		 * 设置所需要查询的字段，多态执行，支持字符串
		 * @param field String ;
		 * @return null ;
		 * @accsss public ;
		 */
		public abstract void field(String field);
		
		/**
		 * 设置所需要查询的字段，多态执行，支持字符串数组
		 * @param fields String[]
		 * @return null 
		 * @access public
		 */
		public abstract void field(String[] fields);
		
		/**
		 * 设置表 多态执行，支持字符串等
		 * @param table String
		 * @access public
		 * @return null
		 */
		public abstract void table(String table);
		
		/**
		 * 设置表，多态执行，支持字符串数组;
		 * @param tables
		 * @access public
		 * @return null
		 */
		public abstract void table(String[] tables);
		
		/**
		 * 构建表方法
		 * @param null
		 * @return String
		 * @access public
		 */
		public abstract String createSql();
		

		/**
		 * 返回当前类的新对象;
		 * @return SQLDriver
		 * @access public
		 * @param null
		 */
	    public abstract _SQLDriver subSql();
}
