package MAN.sys.Helper;


import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;

/** 
 * ��װBase64�Ĺ����� 
 * @author llp
 * @date 2012-8-4
 */  
public class Base64_Helper{  
   
	//�ַ�����
	public final static String ENCODING="UTF-8";   
   
	/**
     * ���ݼ��ܣ��㷨��Base64��
     *
     * @param data String Ҫ���м��ܵ�����
     * @return String ���ܺ������
     * @access public
     */
    public static String encoded(String data) throws UnsupportedEncodingException{  
        byte[] b=Base64.encode(data.getBytes(ENCODING));  
        return new String(b,ENCODING);  
    }  
    
    /**
     * ���ݽ��ܣ��㷨��Base64��
     *
     * @param cryptData String ��������
     * @return String ���ܺ������
     * @access public
     */   
    public static String decode(String cryptData) throws UnsupportedEncodingException{  
        byte[] b=Base64.decode(cryptData.getBytes(ENCODING));  
        return new String(b,ENCODING);  
    }  
}  
