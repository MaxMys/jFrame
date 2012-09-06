package MPD.sys.Common;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;

public class Tools {
	/**
	 * 在helper里面timehelper也有这个方法，但是当log启动的时候timehelper肯定没有启动,
	 * 所以在这里复写了一个静态方法，虽然有违复写的原则，但是为了不破坏程序结构就这么写了
	 * @access public
	 * @param null
	 * @return String
	 * @throws ParseException 
	 */
	
	public static Date string_to_date(String st) throws ParseException
	{

       Date date = (Date) DateFormat.getDateInstance().parse(st);
       return date;
		
	}
	
	
	public static String getTime_day(String meta) {
		//实例化格式
		SimpleDateFormat format = new SimpleDateFormat(meta,Locale.CHINA);
		//实例化日历，顺便设置时区
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		//传入当前时间戳
		
		ca.setTimeInMillis(System.currentTimeMillis());
		//返回
		return format.format(ca.getTime());
	}// end of getTime()
	
	public static String getTime() {
		//实例化格式
		SimpleDateFormat format = new SimpleDateFormat("y-M-d H:m:s",Locale.CHINA);
		//实例化日历，顺便设置时区
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		//传入当前时间戳
		ca.setTimeInMillis(System.currentTimeMillis());
		//返回
		return format.format(ca.getTime());
	}// end of getTime()
	
	/**
	 * @access public
	 * @param meta String 日期格式 
	 * @return String
	 * 
	 */
	public static String getTime(String meta) {
		//实例化格式
		SimpleDateFormat format = new SimpleDateFormat(meta,Locale.CHINA);
		//实例化日历，顺便设置时区
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		//传入当前时间戳
		ca.setTimeInMillis(System.currentTimeMillis());
		//返回
		return format.format(ca.getTime());
	}//end of getTime(String meta)
	
	/**
	 * 查看对象数组中是含有某对象 
	 * @param strs String[]
	 * @param str  String
	 * @return boolean
	 */
	public static boolean arrContain(Object[] strs , Object str) {
		
		for(int i = 0 ; i < strs.length ; i++) 
		
			if( strs[i] == str ) return true;
		
		return false;
		
	}// end of StrContain()
	
	/**
	 * 去除对象数组中冗余的信息
	 * @param source Object[]
	 * @param shift Object[]
	 * @return Object[]
	 * @access public
	 * @static
	 */
	public static Object[] ObjectShift (Object[] source , Object[] shift) {
		
		//define a new container
		Object[] o = new Object[source.length + shift.length];
		//source赋值到新容器
		for ( int i = 0 ; i < source.length ; i ++ ) 
			o[i] = source[i];
		
		// 计数变量
		int j = source.length ;
		
		for ( int i = 0  ; i < shift.length ; i ++ ,j++ ) {
			if( Tools.arrContain( o , shift[i] ) ) 
				continue;
			o[j] = shift[i];
		}//end of for
		return o;
		
	}//end of 
}
