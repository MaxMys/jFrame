package MAN.sys.Helper;


import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;

/** 
 * 封装Base64的工具类 
 * @author llp
 * @date 2012-8-4
 */  
public class Base64_Helper{  
   
	//字符编码
	public final static String ENCODING="UTF-8";   
   
	/**
     * 数据加密，算法（Base64）
     *
     * @param data String 要进行加密的数据
     * @return String 加密后的数据
     * @access public
     */
    public static String encoded(String data) throws UnsupportedEncodingException{  
        byte[] b=Base64.encode(data.getBytes(ENCODING));  
        return new String(b,ENCODING);  
    }  
    
    /**
     * 数据解密，算法（Base64）
     *
     * @param cryptData String 加密数据
     * @return String 解密后的数据
     * @access public
     */   
    public static String decode(String cryptData) throws UnsupportedEncodingException{  
        byte[] b=Base64.decode(cryptData.getBytes(ENCODING));  
        return new String(b,ENCODING);  
    }  
}  
