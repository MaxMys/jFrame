package MOB.app.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import frame.InfoPanel;

import MOB.sys.Common.Common;
import MOB.sys.Core._Controller;
import MOB.sys.Core._System;
import MOB.sys.Helper.JSON_Helper;

public class Message_Controller extends _Controller {

	public Message_Controller() {}
	
	/**
	 * 收到短信的方法
	 * @param m
	 * @return null
	 * @access public
	 */
	public void receive(String[] m) {
		for(int i = 0 ; i <m.length;i++){
			String[] tmp = m[i].split("/");
			String[] str = {tmp[2],tmp[3],tmp[4]};
			InsertMsg(str);
			try {
				this.request("AMD/Message_Controller/RecieveConfirm/Messageid="+tmp[1]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}//end of receive
	
	/**
	 * 将收到短信放入收件箱的方法
	 * @param str
	 * @return null
	 * @access public
	 */
	public void InsertMsg(String[] str){
		_System.getFrame().explorerPanel.insertMsgListTable(str);
	}
	
	/**
	 * 发送短信的方法
	 * @param m String 短信内容
	 * @return null
	 * @access public
	 */
	public void SendMsg(HashMap<String,Object> hm){
		String num = (String) hm.get("number");
		String message = null;
		String str = null;
		message = (String) hm.get("message");
		String fromnum = Common.C("Sim_Config", "number");
		try {
			//查话费
			if(num.equals("002"))
				str = this.request("CMD002/"+fromnum+"/"+num+"/3/"+message);
			//查天气
			else if(num.equals("003"))
				str = this.request("CMD003/"+fromnum+"/"+num+"/3/"+message);
			//冲话费
			else if(num.equals("004"))
				str = this.request("CMD004/"+fromnum+"/"+num+"/3/"+message);
			//普通短信
			else
				str = this.request("CMD001/"+fromnum+"/"+num+"/3/"+message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(str.equals("success")){
			JOptionPane.showMessageDialog(_System.getFrame().infoPanel, "发送成功");
		}
		else
			JOptionPane.showMessageDialog(_System.getFrame().infoPanel, "发送失败");
		
	}
}//end of class
