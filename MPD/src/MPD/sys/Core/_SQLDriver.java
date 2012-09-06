package MPD.sys.Core;

import java.util.HashMap;

/**
 * 
 * SQL��乹����������࣬�������е�SQL������Ҫ���øû��������������
 * @author max
 * @date 2012-8-1
 *
 */
 public abstract  class _SQLDriver {
	
	    //define the private var field
	    protected String ExecuSql = null ;  //���δ�ִ�е�SQL��
	    protected String subSql = null ; //�����Ӿ�;
	    
	    //�����Ӷ���
	    protected _SQLDriver sub= null ; //�����Ӷ���
	    
	    protected int mode ; //����ִ����Ϊ select  1 update  2 insert 3  delete 4
	    protected String where = null; //����where�Ӿ�����
	    protected String field = null;  //�����ѯ�ֶ�;
	    protected String table = null ; //������Ҫ��ѯ�ı�
		/**
		 * ���ù��췽��
		 */
	    protected  _SQLDriver() {
	    	
	    }//end of __construct()
  
	    /**
	     * select ���췽��,����д
	     * @return null;
	     * @access public;
	     */
	    
	    public void select() {
	    	
	    	this.mode = 1;
	    	
	    } //end of select()
	    
	    /**
	     * update ���췽���ɴ�д
	     * @return null;
	     * @access public;
	     */
		public void update() {
			
			this.mode = 2;
			
		}//end of update()
		
		/**
		 * delete ���췽������д
		 * @return null ; 
		 * @access public ;
		 */
		public void delete() {
			
			this.mode = 3;
			
		}//end of delete();
		
		/**
		 * insert ���췽������д
		 * @return null;
		 * @access public;
		 */
		public void insert() {
			
			this.mode = 4;
			
		}//end of insert();
		
		
		/**
		 * ���ò�ѯ����,��ִ̬��,֧���ַ�����������������
		 * @access public
		 * @param String whereSql
		 * @return null
		 * 
		 */
		public abstract void where(String whereSql);
		
		/**
		 * ���ò�ѯ��������ִ̬�У�֧��map��������������
		 * @param whereItems String[]
		 * @access public
		 * @return null
		 */
		public abstract void where(HashMap<String, String> whereItems);
		
		/**
		 * ��������Ҫ��ѯ���ֶΣ���ִ̬�У�֧���ַ���
		 * @param field String ;
		 * @return null ;
		 * @accsss public ;
		 */
		public abstract void field(String field);
		
		/**
		 * ��������Ҫ��ѯ���ֶΣ���ִ̬�У�֧���ַ�������
		 * @param fields String[]
		 * @return null 
		 * @access public
		 */
		public abstract void field(String[] fields);
		
		/**
		 * ���ñ� ��ִ̬�У�֧���ַ�����
		 * @param table String
		 * @access public
		 * @return null
		 */
		public abstract void table(String table);
		
		/**
		 * ���ñ���ִ̬�У�֧���ַ�������;
		 * @param tables
		 * @access public
		 * @return null
		 */
		public abstract void table(String[] tables);
		
		/**
		 * ��������
		 * @param null
		 * @return String
		 * @access public
		 */
		public abstract String createSql();
		

		/**
		 * ���ص�ǰ����¶���;
		 * @return SQLDriver
		 * @access public
		 * @param null
		 */
	    public abstract _SQLDriver subSql();
}
