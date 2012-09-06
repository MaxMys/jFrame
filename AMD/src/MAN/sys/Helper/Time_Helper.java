package MAN.sys.Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * ʱ�丨����
 * 
 * @author max
 * @date 2012-8-2
 * 
 */
public class Time_Helper {

	//�������ڸ�ʽ
	private SimpleDateFormat format = null;
	//������
	private Calendar ca = null;
	
	/**
	 * ���췽��
	 */
	public Time_Helper () {
		super();
	}
	/**
	 * �޲������ͣ�Ĭ�Ϸ��ظ�ʽ��2012-8-2 22:40:31
	 * @return String
	 * @param null
	 * @access public
	 */
	public String getTime() {
		//ʵ������ʽ
		this.format = new SimpleDateFormat("y-M-d H:m:s",Locale.CHINA);
		//ʵ����������˳������ʱ��
		this.ca = Calendar.getInstance(Locale.CHINA);
		//���뵱ǰʱ���
		this.ca.setTimeInMillis(System.currentTimeMillis());
		//����
		return format.format(this.ca.getTime());
	}//end of getTime()
	
	/**
	 * �в������ͣ������ʽ���ش���Ӧʱ��
	 * @param mate
	 * @return String
	 * @access public
	 */
	public String getTime(String mate) {
		//ʵ������ʽ
		this.format = new SimpleDateFormat(mate,Locale.CHINA);
		//ʵ����������˳������ʱ��
		this.ca = Calendar.getInstance(Locale.CHINA);
		//���뵱ǰʱ���
		this.ca.setTimeInMillis(System.currentTimeMillis());
		//����
		return format.format(this.ca.getTime());
	}//end of getTime(String mate);
}
