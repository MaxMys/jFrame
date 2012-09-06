package MPD.sys.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import sun.awt.windows.ThemeReader;

import MPD.sys.Common.Common;

/**
 * ���ݿ����ӳػ���
 * ά�����ݿ��������ӣ�����ʵ�����ӵĻ�ȡ���л����͹����Լ���̬����
 * @author max
 * @date 2012-8-8
 *
 */
public class _ConnPoolManager implements Runnable{

	//������
	private static _ConnPoolManager self = null;
	//���ӳض���
	private  LinkedList<Connection> connectionPool = null;
	//�Ѿ�ʹ�����Ӷ���
	private  LinkedList<Connection> running = null;
	//���������
	private int MAX_CONN = 0;
	//ʹ�÷�ֵ
	private int MAX_IN_USE = 0 ;
	//��ʼ��������
	private int INITNUM = 0;
	//��������ʹ�õ���������
	private  int runningNum = 0;
	//���õ������� 
	private  int freeNum = 0;
	//������Ϣ
	private String forname = null ;
	private String url = null;
	private String username = null ;
	private String password = null;
	
	/**
	 * ˽�л����췽��
	 * @param null
	 * @return null
	 * @access private
	 */
	private _ConnPoolManager() {
		//��ʼ�����ӳ���Ϣ
		this.connectionPool = new LinkedList<Connection>();
		this.running = new LinkedList<Connection>();
		this.MAX_CONN = Integer.parseInt(Common.C("DB_Config","maxconn"));
		this.MAX_IN_USE = Integer.parseInt(Common.C("DB_Config","maxinuse"));
		this.INITNUM = Integer.parseInt(Common.C("DB_Config","initconn"));
		
		//У������,���Ҿ���
		if(this.MAX_CONN < this.MAX_IN_USE)
			this.MAX_IN_USE = this.MAX_CONN - 2 ;
		
		//��ʼ�����ݿ���Ϣ
		this.forname = Common.C("DB_Config","forname");
		this.url = Common.C("DB_Config","url");
		this.username = Common.C("DB_Config","username");
		this.password = Common.C("DB_Config","password");
		
		//������ʼ������
		new Thread(this).start();
		
	}//end of construct
	
	/**
	 * ��̬��������ȡ���Ӷ�����
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
	 * ��ȡ���Ӷ���ͬ������
	 * @param null
	 * @return null
	 * @access public
	 * @synchronized 
	 */
	public synchronized Connection getConn() {
		Connection tmp = null;
		if( this.runningNum + this.freeNum > this.MAX_CONN)
			throw new ArrayIndexOutOfBoundsException("����Խ�磬��ǰ������������󲢷�֧��");

		//������ֵ��������
		if (this.runningNum + this.freeNum > this.MAX_IN_USE)
			this.createConnection();
		//�����ǰ�޿������Ӵ�������
		if (this.freeNum == 0)
			this.createConnection();
		//ȡ�������Ҽ������г�
		this.freeNum -- ;
		this.runningNum ++ ;
		tmp = this.connectionPool.pop();
		this.running.addLast(tmp);
		return tmp;
	}//end of getConn()
	
	/**
	 * ��������
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
	 * ��������,ͬ������
	 * @param null
	 * @access private
	 * @return null
	 * @synchronized
	 */
	private synchronized void createConnection() {
		Connection tmp = null;
		//ֱ������
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
		
		//�������ӳ�
		this.connectionPool.addLast(tmp);
		//����������++
		this.freeNum ++;
	}//end of createConnection()

	/**
	 * ��ʼ�����ã�������ʼ��������,��дRUN()
	 * @access public
	 * @return null
	 * @param null;
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//��������������С�ڳ�ʼ��������ʱ����,��ʼ�����ã�����ʱ�������Ч
		while( this.freeNum + this.runningNum < this.INITNUM)
			this.createConnection();
		
	}//end of run()
	
}//end _ConnectionPool
