package MOB.sys.Core;

import java.util.HashMap;

public class _Dispatcher {

	public _Dispatcher() {}
	
	public HashMap<String,String>parseRequest(String request){
		 HashMap<String,String> tmp = null;
		 tmp.put("controller", "message");
		 tmp.put("method", "receive");
		 tmp.put("from", "00000000001");
		 tmp.put("content","��ð��������һظ���Ĳ��Զ���");
		 return tmp;
	}
}
