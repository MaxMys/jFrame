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
			// ��ȡ���½�ʱ����Ϊ��
			if (name != null) {
				// ��õ�ǰӵ���û�������
				int childCount = _System.getManagerframe().UserInfoTreeRoot.getChildCount();
				// ���û��б�������󴴽��µ��û�
				_System.getManagerframe().UserInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
						name), _System.getManagerframe().UserInfoTreeRoot, childCount);
				// ˢ���û���ģ��
				_System.getManagerframe().UserInfoTreeModel.reload();
				// �����½��û�Ϊѡ��״̬
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
			// ��ȡ���½�ʱ����Ϊ��
			if (name != null) {
				// ��õ�ǰӵ���û�������
				int childCount = mf.OptInfoTreeRoot.getChildCount();
				// ���û��б�������󴴽��µ��û�
				mf.OptInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
						name),mf.UserInfoTreeRoot, childCount);
				// ˢ���û���ģ��
				mf.OptInfoTreeModel.reload();
				// �����½��û�Ϊѡ��״̬
				mf.OptInfoTree.setSelectionRow(childCount);
			}
		}
	}
}
