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
 * MPD计时器类 
 * 这个类使得你能够在某个点上记录时间，并且计算出在两点之间的时间差
 * 内存使用情况也能够显示,但是对于内存的显示并不精确仅仅作为参考
 *
 */
public class _BenchMark {

	//define the private vars
	//mark time
	HashMap<String,Long> timeMarker = null;
	//mark memory
	HashMap<String,Long> memMarker = null;
	
	/**
	 * 初始化时钟类
	 * @param null
	 * @return null
	 */
	public _BenchMark() {
		
		this.timeMarker = new HashMap<String,Long>();
		this.memMarker = new HashMap<String,Long>();
	}
	
	/**
	 * 
	 * 记录当前时间并且命名
	 * @param name String 给标记取个名字哦
	 * @access public
	 * @return null
	 */
	public void markTime(String name) {
		if(name == null)
			return ;
		this.timeMarker.put(name, System.currentTimeMillis());
	}
	
	/**
	 * 记录当前的内存使用
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
	 * 计算亮点的时间差值
	 * @param point1 int 时间点1
	 * @param point2 int 时间点2
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
	 * @param point1 第一个点 String
	 * @param point2 第二个点 String
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
