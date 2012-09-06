package MPD.sys.Config;

import MPD.sys.Core._Config;


public class DB_Config extends _Config{
	
	
	public DB_Config() {
		super();
		//数据库名字
		this.configs.put("datebase", "orcl");
		//数据库地址
		this.configs.put("url", "jdbc:oracle:thin:@10.10.20.208:1521:orcl");
		//用户名 
		this.configs.put("username", "scott");
		//密码
		this.configs.put("password", "tiger");	
		//class.forName()的参数
		this.configs.put("forname", "oracle.jdbc.driver.OracleDriver");
		//driver Info
		this.configs.put("sqldriver", "OracleSqlDriver");
		/*
		 * ---------------------------------------------------------------
		 *  连接池信息
		 * ---------------------------------------------------------------
		 */
		//初始化连接数
		this.configs.put("initconn", "5");
		//最大连接数
		this.configs.put("maxconn", "20");
		//连接阀值，超过阀值之后就会自动创建连接,连接阀值应该要小于最大连接数，
		//否者当连接超过最大连接数之后，会阻塞访问,
		//新版本中为了避免这种情况，会自动检测，如果大于，那么连接阀值会自动等于最大连接数-2
		this.configs.put("maxinuse", "18");
		
		
		
		/*
		 * ---------------------------------------------------------------
		 *  表信息
		 * ---------------------------------------------------------------
		 */
		//表前缀
		this.configs.put("tableprefix", "");
		
		
		
		
		
		
		
	   
		
		
		
		
		
		
		
		
		
		

		
	}//end of __construct
	
		
}
