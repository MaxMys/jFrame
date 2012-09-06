package MPD.app.Controller;

import java.net.Socket;
import java.util.HashMap;

import MPD.sys.Core._Controller;

public class test_Controller extends _Controller{

	//构造方法
	public test_Controller() {}
	
	public String test(HashMap<String,String> h) {
		return "hahhaa";
	}
	public void test() {
		System.out.println("ok");
	}
	
}
