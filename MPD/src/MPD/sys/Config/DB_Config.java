package MPD.sys.Config;

import MPD.sys.Core._Config;


public class DB_Config extends _Config{
	
	
	public DB_Config() {
		super();
		//���ݿ�����
		this.configs.put("datebase", "orcl");
		//���ݿ��ַ
		this.configs.put("url", "jdbc:oracle:thin:@10.10.20.208:1521:orcl");
		//�û��� 
		this.configs.put("username", "scott");
		//����
		this.configs.put("password", "tiger");	
		//class.forName()�Ĳ���
		this.configs.put("forname", "oracle.jdbc.driver.OracleDriver");
		//driver Info
		this.configs.put("sqldriver", "OracleSqlDriver");
		/*
		 * ---------------------------------------------------------------
		 *  ���ӳ���Ϣ
		 * ---------------------------------------------------------------
		 */
		//��ʼ��������
		this.configs.put("initconn", "5");
		//���������
		this.configs.put("maxconn", "20");
		//���ӷ�ֵ��������ֵ֮��ͻ��Զ���������,���ӷ�ֵӦ��ҪС�������������
		//���ߵ����ӳ������������֮�󣬻���������,
		//�°汾��Ϊ�˱���������������Զ���⣬������ڣ���ô���ӷ�ֵ���Զ��������������-2
		this.configs.put("maxinuse", "18");
		
		
		
		/*
		 * ---------------------------------------------------------------
		 *  ����Ϣ
		 * ---------------------------------------------------------------
		 */
		//��ǰ׺
		this.configs.put("tableprefix", "");
		
		
		
		
		
		
		
	   
		
		
		
		
		
		
		
		
		
		

		
	}//end of __construct
	
		
}
