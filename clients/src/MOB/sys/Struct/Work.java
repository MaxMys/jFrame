package MOB.sys.Struct;

import java.util.HashMap;

public class Work {

	public Class<?> controller = null;
	public String method =null;
	public HashMap<String,Object> hm = null;
	public _CallBack c = null;
	
	public _CallBack getC() {
		return c;
	}
	public void setC(_CallBack c) {
		this.c = c;
	}
	public Work(Class<?> controller ,String method,HashMap<String,Object> hm,_CallBack c){
		this.controller = controller;
		this.method = method;
		this.hm = hm;
		this.c = c;
	}
	public Class<?> getController() {
		return controller;
	}
	public void setController(Class<?> controller) {
		this.controller = controller;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public HashMap<String, Object> getHm() {
		return hm;
	}
	public void setHm(HashMap<String, Object> hm) {
		this.hm = hm;
	}
	
}
