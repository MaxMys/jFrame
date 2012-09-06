package MPD.sys.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import net.sf.json.JSONObject;

/** 
 * ��װJSONת���Ĺ����� 
 * @author llp
 * @date 2012-8-4
 */  
public class JSON_Helper {

	public JSON_Helper(){
		
	}
	
	/**
     * @description ��String����ת��ΪJSON����
     *
     * @param s String Ҫ����ת��������
     * @return String ת���������
     * @access private
     */
	private static String StringtoJson(String s){
		StringBuilder sb = new StringBuilder(s.length()+20);
		sb.append('\"');
		for(int i = 0;i<s.length();i++){
			char c = s.charAt(i);
			switch(c){
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		sb.append('\"');
		return sb.toString();
	}
	
	/**
     * @description ��Number����ת��ΪJSON����
     *
     * @param num Number Ҫ����ת��������
     * @return String ת���������
     * @access private
     */
	private static String NumbertoJson(Number num){
		return num.toString();
	}
	
	/**
     * @description ��Boolean����ת��ΪJSON����
     *
     * @param b Boolean Ҫ����ת��������
     * @return String ת���������
     * @access private
     */
	private static String BooleantoJson(Boolean b) {
		return b.toString();
	}
	
	/**
     * @description ��Object[]����ת��ΪJSON����
     *
     * @param array Object[] Ҫ����ת��������
     * @return String ת���������
     * @access private
     */
	private static String ArraytoJson(Object[] array){
		if(array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for(Object o:array){
			sb.append(toJson(o));
			sb.append(',');
		}
		//������','ת��Ϊ']'
		sb.setCharAt(sb.length()-1, ']');
		return sb.toString();
	}
	
	/**
     * @description ��Map<String��Object>����ת��ΪJSON����
     *
     * @param map Map<String��Object> Ҫ����ת��������
     * @return String ת���������
     * @access private
     */
	private static String MaptoJson(Map<String,Object> map){
		if(map.isEmpty())
			return "{}";
		StringBuilder sb = new StringBuilder(map.size()<<4);
		sb.append('{');
		Set<String> keys = map.keySet();
		for(String key : keys){
		Object value = map.get(key);
		sb.append('\"');
		sb.append(key);
		sb.append('\"');
		sb.append(':');
		sb.append(toJson(value));
		sb.append(',');
		}
		//������','���'}'
		sb.setCharAt(sb.length()-1, '}');
		return sb.toString();
	}
	
	/**
     * @description ��ڷ�������Object�������ΪJSON��ʽ
     *
     * @param o Object Ҫ����ת���Ķ���
     * @return String �����Ķ���
     * @access public
     */
	public static String toJson(Object o){
		if(o == null)
			return null;
		if(o instanceof String)
			return StringtoJson((String)o);
		if(o instanceof Number)
			return NumbertoJson((Number)o);
		if(o instanceof Boolean)
			return BooleantoJson((Boolean)o);
		if(o instanceof Object[])
			return ArraytoJson((Object[])o);
		if(o instanceof Map)
			return MaptoJson((Map<String,Object>)o);
		throw new RuntimeException("Unsupported type:"+o.getClass().getName());
	}

	/** 
     *@description:  ���ݸ���Json������ΪMap<String,Object> 
     *@param jsonStr String Ҫת����json��
     *@return Map<String,Object> ת����Ķ���
     *@access public
     */  
	public static Map<String, Object> JsontoMap(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject( jsonStr );
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map map = new HashMap();
		while( keyIter.hasNext())
        {
			key = (String)keyIter.next();
			value = jsonObject.get(key);
			map.put(key, value);
		}
		return map;
	}
}
