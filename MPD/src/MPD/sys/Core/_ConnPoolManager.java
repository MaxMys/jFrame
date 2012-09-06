package MPD.sys.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import sun.awt.windows.ThemeReader;

import MPD.sys.Common.Common;

/**
 * 数据库连接池基类
 * 维护数据库所有连接，并且实现连接的获取，切换，和管理，以及动态生成
 * @author max
 * @date 2012-8-8
 *
 */
public class _ConnPoolManager implements Runnable{

	//对象本身
	private static _ConnPoolManager self = null;
	//连接池对象
	private  LinkedList<Connection> connectionPool = null;
	//已经使用连接对象
	private  LinkedList<Connection> running = null;
	//最大连接数
	private int MAX_CONN = 0;
	//使用阀值
	private int MAX_IN_USE = 0 ;
	//初始化连接数
	private int INITNUM = 0;
	//现在正在使用的连接熟练
	private  int runningNum = 0;
	//闲置的连接数 
	private  int freeNum = 0;
	//连接信息
	private String forname = null ;
	private String url = null;
	private String username = null ;
	private String password = null;
	
	/**
	 * 私有化构造方法
	 * @param null
	 * @return null
	 * @access private
	 */
	private _ConnPoolManager() {
		//初始化连接池信息
		this.connectionPool = new LinkedList<Connection>();
		this.running = new LinkedList<Connection>();
		this.MAX_CONN = Integer.parseInt(Common.C("DB_Config","maxconn"));
		this.MAX_IN_USE = Integer.parseInt(Common.C("DB_Config","maxinuse"));
		this.INITNUM = Integer.parseInt(Common.C("DB_Config","initconn"));
		
		//校验数据,并且纠正
		if(this.MAX_CONN < this.MAX_IN_USE)
			this.MAX_IN_USE = this.MAX_CONN - 2 ;
		
		//初始化数据库信息
		this.forname = Common.C("DB_Config","forname");
		this.url = Common.C("DB_Config","url");
		this.username = Common.C("DB_Config","username");
		this.password = Common.C("DB_Config","password");
		
		//创建初始化连接
		new Thread(this).start();
		
	}//end of construct
	
	/**
	 * 静态方法，获取连接对象本身
	 * @param null
	 * @return _ConnectionPool
	 * @access public
	 * @static
	 */
	public static _ConnPoolManager getConnectionPool() {
		
		if( _ConnPoolManager.self != null)
			return _ConnPoolManager.self;
		_ConnPoolManager.self = new _ConnPoolManager();
		return _ConnPoolManager.self;
		
	}//end of getConnectionPool()
	
	/**
	 * 获取连接对象，同步方法
	 * @param null
	 * @return null
	 * @access public
	 * @synchronized 
	 */
	public synchronized Connection getConn() {
		Connection tmp = null;
		if( this.runningNum + this.freeNum > this.MAX_CONN)
			throw new ArrayIndexOutOfBoundsException("连接越界，当前连接数超出最大并发支持");

		//超过阀值创建连接
		if (this.runningNum + this.freeNum > this.MAX_IN_USE)
			this.createConnection();
		//如果当前无空闲连接创建连接
		if (this.freeNum == 0)
			this.createConnection();
		//取出对象并且加入运行池
		this.freeNum -- ;
		this.runningNum ++ ;
		tmp = this.connectionPool.pop();
		this.running.addLast(tmp);
		return tmp;
	}//end of getConn()
	
	/**
	 * 回收连接
	 * @param Connection()
	 * @return null
	 * @access public
	 * @synchronized
	 */
	public synchronized void release(Connection c){
		this.connectionPool.addLast(c);
		this.running.remove(c);
		this.freeNum ++ ;
		this.runningNum --;
	}
	/**
	 * 创建连接,同步方法
	 * @param null
	 * @access private
	 * @return null
	 * @synchronized
	 */
	private synchronized void createConnection() {
		Connection tmp = null;
		//直接连接
		try {
			Class.forName(this.forname);
			tmp  =  DriverManager.getConnection(
							this.url,
							this.username,
							this.password
					);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		
		//加入连接池
		this.connectionPool.addLast(tmp);
		//闲置连接数++
		this.freeNum ++;
	}//end of createConnection()

	/**
	 * 初始化调用，创建初始化连接数,重写RUN()
	 * @access public
	 * @return null
	 * @param null;
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//当现有连接总数小于初始化连接数时调用,初始化调用，其他时候调用无效
		while( this.freeNum + this.runningNum < this.INITNUM)
			this.createConnection();
		
	}//end of run()
	
}//end _ConnectionPool
