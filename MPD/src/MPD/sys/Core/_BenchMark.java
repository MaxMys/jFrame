package MPD.sys.Core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author max
 * @package MPD.max.core
 * @date 2012-8-2
 * 
 */
//-------------------------------------------------
/**
 * 
 * MPD��ʱ���� 
 * �����ʹ�����ܹ���ĳ�����ϼ�¼ʱ�䣬���Ҽ����������֮���ʱ���
 * �ڴ�ʹ�����Ҳ�ܹ���ʾ,���Ƕ����ڴ����ʾ������ȷ������Ϊ�ο�
 *
 */
public class _BenchMark {

	//define the private vars
	//mark time
	HashMap<String,Long> timeMarker = null;
	//mark memory
	HashMap<String,Long> memMarker = null;
	
	/**
	 * ��ʼ��ʱ����
	 * @param null
	 * @return null
	 */
	public _BenchMark() {
		
		this.timeMarker = new HashMap<String,Long>();
		this.memMarker = new HashMap<String,Long>();
	}
	
	/**
	 * 
	 * ��¼��ǰʱ�䲢������
	 * @param name String �����ȡ������Ŷ
	 * @access public
	 * @return null
	 */
	public void markTime(String name) {
		if(name == null)
			return ;
		this.timeMarker.put(name, System.currentTimeMillis());
	}
	
	/**
	 * ��¼��ǰ���ڴ�ʹ��
	 * @param name
	 * @return null
	 * @access public
	 */
	public void markMem (String name) {
		
		if( name == null )
			return ;
		//get runtime first
		Runtime rt = Runtime.getRuntime();
		//get the usage of mem
		long tmp = rt.totalMemory() - rt.freeMemory();
		//mark the usage of mem
		this.memMarker.put(name,tmp);
	}//end of markMem(String name)
	
	/**
	 * ���������ʱ���ֵ
	 * @param point1 int ʱ���1
	 * @param point2 int ʱ���2
	 * @return long
	 * @access public
	 */
	public long elapsedTime(String point1 , String point2) {
		
		if( point1 == null && point2 == null)
			return 0;
		if( point2 == "" )
			return System.currentTimeMillis() - this.timeMarker.get(point1);
		
		if( this.timeMarker.get(point2) == null)
			return System.currentTimeMillis() - this.timeMarker.get(point1);
		
		if(point1 == "" || this.timeMarker.get(point1) == null) 
			return this.timeMarker.get(point2);
		
		return this.timeMarker.get(point2) - this.timeMarker.get(point1);
	
	}//end of elapsedTime(int point1 , int point2)
	
	/**
	 * 
	 * @param point1 ��һ���� String
	 * @param point2 �ڶ����� String
	 * @return  long
	 * @access public
	 * 
	 */
	public long elapsedMem (String point1 , String point2) {
		//get runtime first
		Runtime rt = Runtime.getRuntime();
		
		if(point1 == null && point2 == null)
			return 0;
		
		if( point2 =="")
			return rt.totalMemory() - rt.freeMemory()- this.memMarker.get(point1);
		
		if( point2 == null)
			return rt.totalMemory() - rt.freeMemory() - this.memMarker.get(point1);
		
		if( this.memMarker.get(point2) == null )
			return rt.totalMemory() - rt.freeMemory() - this.memMarker.get(point1);
		
		if( point1 == "" || this.memMarker.get(point1) == null )
			return this.memMarker.get(point2);
		
		return this.memMarker.get(point2) - this.memMarker.get(point1);
		
	}//end of elapsedMem
	
	
	
}//end of class BenchMark
