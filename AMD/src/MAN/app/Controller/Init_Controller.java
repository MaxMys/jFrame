package MAN.app.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import MAN.app.View.ManagerFrame;
import MAN.sys.Core._Controller;
import MAN.sys.Core._System;

public class Init_Controller extends _Controller{

	public Init_Controller(){
		super();
	}
	
	public void userlist_init(HashMap<String,String> hm){
		String str = null;
		try {
			str = this.request("AMD/Client/getList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,Object>[] arrayhm = this.stringToMapArray(str);
		String name = null;
		System.out.println(arrayhm[0].get("NAME"));
		for(int i = 0;i < arrayhm.length;i++){
			name = (String) arrayhm[0].get("NAME");
			System.out.println(name);
			// 当取消新建时名称为空
			if (name != null) {
				// 获得当前拥有用户的数量
				int childCount = _System.getManagerframe().UserInfoTreeRoot.getChildCount();
				// 在用户列表树的最后创建新的用户
				_System.getManagerframe().UserInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
						name), _System.getManagerframe().UserInfoTreeRoot, childCount);
				// 刷新用户树模型
				_System.getManagerframe().UserInfoTreeModel.reload();
				// 设置新建用户为选中状态
				_System.getManagerframe().UserInfoTree.setSelectionRow(childCount);
			}
		}
	}
	
	public void optlist_init(HashMap<String,String> hm){
		String str = null;
		try {
			str = this.request("AMD/Client/getList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String,Object>[] arrayhm = this.stringToMapArray(str);
		String name = null;
		ManagerFrame mf = _System.getManagerframe();
		for(int i = 0;i < arrayhm.length;i++){
			name = (String) arrayhm[0].get("NAME");
			// 当取消新建时名称为空
			if (name != null) {
				// 获得当前拥有用户的数量
				int childCount = mf.OptInfoTreeRoot.getChildCount();
				// 在用户列表树的最后创建新的用户
				mf.OptInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
						name),mf.UserInfoTreeRoot, childCount);
				// 刷新用户树模型
				mf.OptInfoTreeModel.reload();
				// 设置新建用户为选中状态
				mf.OptInfoTree.setSelectionRow(childCount);
			}
		}
	}
}
