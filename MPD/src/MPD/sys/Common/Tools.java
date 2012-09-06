package MPD.sys.Common;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;

public class Tools {
	/**
	 * ��helper����timehelperҲ��������������ǵ�log������ʱ��timehelper�϶�û������,
	 * ���������︴д��һ����̬��������Ȼ��Υ��д��ԭ�򣬵���Ϊ�˲��ƻ�����ṹ����ôд��
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
		//ʵ������ʽ
		SimpleDateFormat format = new SimpleDateFormat(meta,Locale.CHINA);
		//ʵ����������˳������ʱ��
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		//���뵱ǰʱ���
		
		ca.setTimeInMillis(System.currentTimeMillis());
		//����
		return format.format(ca.getTime());
	}// end of getTime()
	
	public static String getTime() {
		//ʵ������ʽ
		SimpleDateFormat format = new SimpleDateFormat("y-M-d H:m:s",Locale.CHINA);
		//ʵ����������˳������ʱ��
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		//���뵱ǰʱ���
		ca.setTimeInMillis(System.currentTimeMillis());
		//����
		return format.format(ca.getTime());
	}// end of getTime()
	
	/**
	 * @access public
	 * @param meta String ���ڸ�ʽ 
	 * @return String
	 * 
	 */
	public static String getTime(String meta) {
		//ʵ������ʽ
		SimpleDateFormat format = new SimpleDateFormat(meta,Locale.CHINA);
		//ʵ����������˳������ʱ��
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		//���뵱ǰʱ���
		ca.setTimeInMillis(System.currentTimeMillis());
		//����
		return format.format(ca.getTime());
	}//end of getTime(String meta)
	
	/**
	 * �鿴�����������Ǻ���ĳ���� 
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
	 * ȥ�������������������Ϣ
	 * @param source Object[]
	 * @param shift Object[]
	 * @return Object[]
	 * @access public
	 * @static
	 */
	public static Object[] ObjectShift (Object[] source , Object[] shift) {
		
		//define a new container
		Object[] o = new Object[source.length + shift.length];
		//source��ֵ��������
		for ( int i = 0 ; i < source.length ; i ++ ) 
			o[i] = source[i];
		
		// ��������
		int j = source.length ;
		
		for ( int i = 0  ; i < shift.length ; i ++ ,j++ ) {
			if( Tools.arrContain( o , shift[i] ) ) 
				continue;
			o[j] = shift[i];
		}//end of for
		return o;
		
	}//end of 
}
