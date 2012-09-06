import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import MOB.sys.Common.Common;
import MOB.sys.Helper.Base64_Helper;


public class test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
//		// TODO Auto-generated method stub
//		System.out.println("this is client");
//		Socket tmp = new Socket("localhost", 9999);
//		PrintWriter outs = new PrintWriter(tmp.getOutputStream());
//		outs.println("9998");
//		Socket s = new ServerSocket(9998).accept();
//		//发送请求信息
//		PrintWriter out = new PrintWriter(s.getOutputStream());
//		out.println(Base64_Helper.encoded("AMD/Client_Controller/getList/"));
//		out.flush();
//		BufferedReader b = new BufferedReader(new InputStreamReader(s.getInputStream()));
//		System.out.println(Base64_Helper.decode(b.readLine()));
//		System.out.println(Base64_Helper.decode(b.readLine()));
		
		Socket s = new Socket(
				"localhost",
				9999
				);
		PrintWriter out = new PrintWriter(s.getOutputStream());
		int tmpPort = 9998;
		out.println(tmpPort);
		out.flush();
		ServerSocket ss= new ServerSocket(tmpPort);
		s = ss.accept();
		ss.close();
		PrintWriter outs = new PrintWriter(s.getOutputStream());
		outs.println(Base64_Helper.encoded("AMD/Client_Controller/getList/"));
		outs.flush();
		BufferedReader b = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(Base64_Helper.decode(b.readLine()));
		System.out.println(Base64_Helper.decode(b.readLine()));
	}//end of the 
}//end of test class
