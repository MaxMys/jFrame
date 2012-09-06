package MPD.sys.Core;

import java.util.HashMap;

import MPD.sys.Struct.ProcessObject;

/**
 * 路由器
 * @author max
 *
 */
public class _Dispatcher {

	//本身单例对象
	private static _Dispatcher dispatcher = null;
	
	/**
	 * 构造方法
	 * @param null
	 * 
	 */
	private _Dispatcher(){}
	
	/**
	 * @param null
	 * @return _Dispatcher
	 * @access public
	 */
	public static _Dispatcher getDispatcher() {
		if(_Dispatcher.dispatcher == null)
			return new _Dispatcher();
		return _Dispatcher.dispatcher;
	}//end of getDispatcher
	
	public HashMap<String,String> parseRequest( ProcessObject po) {
		
		String message = (String) po.m;
		
		HashMap<String,String> tmp = new HashMap<String,String>();
		String[] tmpString = message.split("/");
		//客户信息 CMD001/from/to/length/content...
		if(tmpString[0].startsWith("CMD")){
			//普通短信
			if(tmpString[0].startsWith("CMD001")){
					tmp.put("controller", "Message");
					tmp.put("method", "sendMessage");
					tmp.put("fromuser",tmpString[1]);
					tmp.put("touser", tmpString[2]);
					tmp.put("content", tmpString[4]);
			}
			//查话费
			else if(tmpString[0].startsWith("CMD002")){
					tmp.put("controller", "FeeControll_Controller");
					tmp.put("method", "queryfee");
					tmp.put("sadress",tmpString[1]);
			}
			//查天气
			else if(tmpString[0].startsWith("CMD003")){
					tmp.put("controller", "FeeControll");
					tmp.put("method", "queryweather");
					tmp.put("sadress",tmpString[1]);
			}
			//冲话费
			else if(tmpString[0].startsWith("CMD004")){
					tmp.put("controller", "FeeControll");
					tmp.put("method", "addfee");
					tmp.put("sadress",tmpString[1]);
					tmp.put("content", tmpString[4]);
			}//end of elseif
		}//end of if
		//出现controller的信息
		else if(tmpString[0].startsWith("AMD")){
			tmp.put("controller", tmpString[1].endsWith("_Controller")?tmpString[1]:(tmpString[1]+"_Controller"));
			tmp.put("method", tmpString[2]);
			String[] param = null;
			for(int i=3 ; i <tmpString.length;i++){
				param = this.parseParm(tmpString[i]);
				if(param == null)
					continue;
				tmp.put(param[0], param[1]);
			}//end of for
		}//end of else if
		
		//特殊请求ALI/mark(username,phone) 保持连接,不校验用户真实性
		else if(tmpString[0].startsWith("ALI")){
			 tmp.put("controller", "State");
			 tmp.put("method", "keepAlive");
			 tmp.put("mark", tmpString[1]);
			 tmp.put("level", tmpString[2]);
			//加入socket信息
			tmp.put("ip",po.s.getInetAddress().toString().substring(1));
		}
		//安检
		if(!this.Security(tmp))
			return null;
		
		return tmp;
		
	}//end of parseRequest();
	
	/**
	 * 解析键值对
	 * @param s
	 * @return String[]
	 * @access private
	 */
	private String[] parseParm(String s) {
		if(s.split("=").length>2)
			return null;
		String[] tmp = new String[2];
		tmp = s.split("=");
		return tmp;
	}//end of parseParm();
	
	/**
	 * 安检
	 * @param message
	 * @return boolean
	 */
	private boolean  Security(HashMap<String,String> hm){
		return true;
	}//end of Security
	
}//end of class
