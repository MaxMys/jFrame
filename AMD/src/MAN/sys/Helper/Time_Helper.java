package MAN.sys.Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 时间辅助类
 * 
 * @author max
 * @date 2012-8-2
 * 
 */
public class Time_Helper {

	//处理日期格式
	private SimpleDateFormat format = null;
	//日历类
	private Calendar ca = null;
	
	/**
	 * 构造方法
	 */
	public Time_Helper () {
		super();
	}
	/**
	 * 无参数类型；默认返回格式如2012-8-2 22:40:31
	 * @return String
	 * @param null
	 * @access public
	 */
	public String getTime() {
		//实例化格式
		this.format = new SimpleDateFormat("y-M-d H:m:s",Locale.CHINA);
		//实例化日历，顺便设置时区
		this.ca = Calendar.getInstance(Locale.CHINA);
		//传入当前时间戳
		this.ca.setTimeInMillis(System.currentTimeMillis());
		//返回
		return format.format(this.ca.getTime());
	}//end of getTime()
	
	/**
	 * 有参数类型，传入格式返回处相应时间
	 * @param mate
	 * @return String
	 * @access public
	 */
	public String getTime(String mate) {
		//实例化格式
		this.format = new SimpleDateFormat(mate,Locale.CHINA);
		//实例化日历，顺便设置时区
		this.ca = Calendar.getInstance(Locale.CHINA);
		//传入当前时间戳
		this.ca.setTimeInMillis(System.currentTimeMillis());
		//返回
		return format.format(this.ca.getTime());
	}//end of getTime(String mate);
}
