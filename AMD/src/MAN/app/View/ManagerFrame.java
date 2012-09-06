package MAN.app.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import MAN.app.Controller.Init_Controller;
import MAN.app.Explorer.Manager_PersonnelDialog;
import MAN.app.Explorer.Manager_SetDialog;
import MAN.app.mwing.MButton;
import MAN.app.mwing.MTable;
import MAN.sys.Core._System;

public class ManagerFrame extends JFrame{

	//�й��û���Ϣ�б�
	private MTable UserInfoListTable;
	public DefaultMutableTreeNode UserInfoTreeRoot;
	public DefaultTreeModel UserInfoTreeModel;
	public JTree UserInfoTree;
	private Vector<Vector> UserInfoListTableValueV;
	private Vector<String> UserInfoListTableColumnV;
	private DefaultTableModel UserInfoListTableModel;
	
	//�йز���Ա��Ϣ�б�
	private MTable OptInfoListTable;
	public DefaultMutableTreeNode OptInfoTreeRoot;
	public DefaultTreeModel OptInfoTreeModel;
	public JTree OptInfoTree;
	private Vector<Vector> OptInfoListTableValueV;
	private Vector<String> OptInfoListTableColumnV;
	private DefaultTableModel OptInfoListTableModel;
	
	//�йع���Ա��Ϣ�б�
	private MTable ManInfoListTable;
	public DefaultMutableTreeNode ManInfoTreeRoot;
	public DefaultTreeModel ManInfoTreeModel;
	public JTree ManInfoTree;
	private Vector<Vector> ManInfoListTableValueV;
	private Vector<String> ManInfoListTableColumnV;
	private DefaultTableModel ManInfoListTableModel;

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * @description Create the frame
	 * @param user �û���
	 */
	public ManagerFrame(final Vector v){
		super();
		setTitle("����Ա&����Ա��Ϣ�������");
		setBounds(100, 100, 900, 690);
//		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ��������������
		final JToolBar toolBar = new JToolBar();
		// ���ù������ı߿���ʽ
		toolBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		// ���ù����������ƶ�
		toolBar.setFloatable(false);
		// ����������ӵ������
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		//���ð�ť�����¼���Ӧ
		final MButton handsetButton = new MButton();
		handsetButton.addActionListener(new HandsetButtonActionListener());
		URL infoUrl = this.getClass().getResource("/img/info.png");
		handsetButton.setIcon(new ImageIcon(infoUrl));
		URL infoOverUrl = this.getClass().getResource("/img/info_over.png");
		handsetButton.setRolloverIcon(new ImageIcon(infoOverUrl));
		toolBar.add(handsetButton);
		
		//�˳���ť�����¼���Ӧ
		final MButton exitButton = new MButton();
		URL exitUrl = this.getClass().getResource("/img/exit.png");
		exitButton.setIcon(new ImageIcon(exitUrl));
		URL exitOverUrl = this.getClass().getResource("/img/exit_over.png");
		exitButton.setRolloverIcon(new ImageIcon(exitOverUrl));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null,"ȷ���˳���", "������ʾ",
							JOptionPane.YES_NO_OPTION);
					if (i == 0) System.exit(0);
			}
		});
		toolBar.add(exitButton);
		
//--------------------------------------------------------------------------------
		//�˲���Ϊ�б���壨�����û��б���Ϣ������Ա�б���Ϣ�͹���Ա�б���Ϣ��
		final JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
		
		//1���û���Ϣ�б���
		final JSplitPane UserInfoSplitPane = new JSplitPane();
		UserInfoSplitPane.setOneTouchExpandable(true);
		UserInfoSplitPane.setDividerSize(12);
		UserInfoSplitPane.setDividerLocation(170);
		tabbedPane.addTab("�û���Ϣ�б�", null, UserInfoSplitPane, null);
		
		//��tab����������ʾ�û���Ϣ�б�����
		final JPanel UserInfoTreePane = new JPanel();
		UserInfoSplitPane.setLeftComponent(UserInfoTreePane);
		UserInfoTreePane.setLayout(new BorderLayout());

		// ������ʾ�û���Ϣ���Ĺ������
		final JScrollPane UserInfoTreeScrollPane = new JScrollPane();
		// �����������ӵ��ϼ������
		UserInfoTreePane.add(UserInfoTreeScrollPane);
		
		// �����û���Ϣ���ĸ��ڵ�
		UserInfoTreeRoot = new DefaultMutableTreeNode("root");
		// ��ʼ���û���Ϣ��
		initTree(UserInfoTreeRoot, "UserInfo");


		// �����û���Ϣ��ģ��
		UserInfoTreeModel = new DefaultTreeModel(UserInfoTreeRoot);

		// �����û���Ϣ��
		UserInfoTree = new JTree(UserInfoTreeModel);
		// �����û���Ϣ���ĸ��ڵ㲻�ɼ�
		UserInfoTree.setRootVisible(false);
		//System.out.println(UserInfoTree.getSelectionModel().getSelectionMode());
		UserInfoTree.getSelectionModel().setSelectionMode(
				// �����û���Ϣ����ѡ��ģʽΪ��ѡ
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// ����û���Ϣ�������ӽڵ㣬������ѡ�е�һ���ӽڵ�
		if (UserInfoTreeRoot.getChildCount() > 0)
			UserInfoTree.setSelectionRow(0);
		// Ϊ�û���Ϣ����ӽӵ�ѡ���¼�������
		UserInfoTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						// ��ʼ���û���Ϣ�б�
						initUserInfoListTable();
					}
				});
		// ���û���Ϣ����ӵ����������
		UserInfoTreeScrollPane.setViewportView(UserInfoTree);
		
		final JPanel UserTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		UserTreeButtonPanel.setLayout(flowLayout_1);
		UserInfoTreePane.add(UserTreeButtonPanel, BorderLayout.SOUTH);
		//���û���Ϣ�б����������µ��û������˲���Ϊ��Ӱ�ť�����¼���Ӧ
		final MButton addUserTypeButton = new MButton();
		addUserTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ������û�����
				String name = addTreeNode(UserInfoTreeRoot, "�û�");
				// ��ȡ���½�ʱ����Ϊ��
				if (name != null) {
					// ��õ�ǰӵ���û�������
					int childCount = UserInfoTreeRoot.getChildCount();
					// ���û��б�������󴴽��µ��û�
					UserInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), UserInfoTreeRoot, childCount);
					// ˢ���û���ģ��
					UserInfoTreeModel.reload();
					// �����½��û�Ϊѡ��״̬
					UserInfoTree.setSelectionRow(childCount);
             		// ���½��û����浽���ݿ���
//					dao.iType(name, "card");
				}
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL UserInfoTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addUserTypeButton.setIcon(new ImageIcon(UserInfoTypeUrl));
		URL UserTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addUserTypeButton.setRolloverIcon(new ImageIcon(UserTypeOverUrl));
		UserTreeButtonPanel.add(addUserTypeButton);
		
		//ɾ���û���ť������Ӧ�¼�
		final MButton delUserTypeButton = new MButton();
		delUserTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ѡ�е��û�����
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) UserInfoTree
						.getLastSelectedPathComponent();
				// δѡ��Ҫɾ�����û�
				if (treeNode == null) {
					// ������ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ�����û���", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					// ��ֱ�ӷ���
					return;
				}
				// �����ɾ���û�������
				String name = treeNode.getUserObject().toString();
				// ����ɾ����ȷ����ʾ
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ���û���" + name
						+ "����", "������ʾ", JOptionPane.YES_NO_OPTION);
				// ȡ����ɾ������
				if (i != 0)
					// ֱ�ӷ���
					return;
			    
				// ���û��б��а����û�
//				if (dao.sPersonnelVByTypeName(name).size() > 0) {
//				// ������������Ƭ�Ĵ���ʽ	
//				String options[] = { "ȡ��", "����������Ƭ��", "ɾ��" };
//				// ������ʾ��Ϣ�����û�ѡ���ṩ�Ĵ���ʽ	
//				int optionIndex = JOptionPane.showOptionDialog(null,
//							"��ѡ��Ը���Ƭ������Ա�Ĵ���ʽ��", "������ʾ",
//							JOptionPane.YES_NO_CANCEL_OPTION,
//							JOptionPane.QUESTION_MESSAGE, null, options,
//							options[0]);
//				// �û�ȡ����ɾ������	
//				if (optionIndex == 0)
//				// ֱ�ӷ���		
//				return;
//				// �����Ƭ�е�����ID	
//				int typeId = dao.sTypeIdByUsedAndName("card", name);
//				// �û�ѡ������������Ƭ��	
//				if (optionIndex == 1) {
//				// ��ѯ���������Ƭ��		
//				Vector<Vector> cardV = dao.sTypeByUsedExcept("card",
//								typeId);
//				// ����һ��ѡ��������		
//				String[] cards = new String[cardV.size() + 1];
//				// ���һ����ʾѡ����		
//				cards[0] = "��ѡ��";
//						for (int j = 0; j < cardV.size(); j++) {// ��ʼ��ѡ��������
//							cards[j + 1] = cardV.get(j).get(2).toString();// ��ӿ��������Ƭ��
//						}
//						Object card = "��ѡ��";// Ĭ��Ϊѡ�С���ѡ��
//						while (card.equals("��ѡ��")) {// ��ѡ�е�Ϊ����ѡ��ʱִ��ѭ��
//							card = JOptionPane.showInputDialog(null,
//									"��ѡ��Ҫ�������Ƭ�У�", "������ʾ",
//									JOptionPane.INFORMATION_MESSAGE, null,
//									cards, cards[0]);// �����Ի������û�ѡ�����������Ƭ��
//							if (card == null)// �û�ȡ����ɾ������
//								return;// ֱ�ӷ���
//						}
//						int newTypeId = dao.sTypeIdByUsedAndName("card", card
//								.toString());// �����������Ƭ�е�����ID
//						dao.uPersonnelTypeIdByTypeId(typeId, newTypeId);// �޸���Ƭ�����
//					}
//					if (optionIndex == 2) {// �û�ѡ��ɾ�����������Ƭ
//						dao.dPersonnelByTypeId(typeId);// �����ݿ�ɾ�����������Ƭ
//					}
//				}
				
				// ���û�����ɾ���û�
				UserInfoTreeModel.removeNodeFromParent(treeNode);
//				// �����ݿ���ɾ����Ƭ��
//				dao.dTypeByName("card", name);
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL delUserTypeUrl = this.getClass().getResource("/img/del_tree.png");
		delUserTypeButton.setIcon(new ImageIcon(delUserTypeUrl));
		URL delUserTypeOverUrl = this.getClass().getResource(
				"/img/del_tree_over.png");
		delUserTypeButton.setRolloverIcon(new ImageIcon(delUserTypeOverUrl));
		UserTreeButtonPanel.add(delUserTypeButton);
		
//-----------------------------------------------------------------------------
		
		//��ʾѡ���û���Ϣ���ּ�������ť
		final JPanel UserInfoListPanel = new JPanel();
		UserInfoSplitPane.setRightComponent(UserInfoListPanel);
		UserInfoListPanel.setLayout(new BorderLayout());

		final JScrollPane UserInfoListScrollPane = new JScrollPane();
		UserInfoListPanel.add(UserInfoListScrollPane);

		UserInfoListTableColumnV = new Vector<String>();
		String UserInfoListTableColumns[] = {"id","����", "�ƶ��绰","ע��ʱ��" ,"�Ƿ�ͨ����","�ϴε�¼IP","���û���","���"};
		for (int i = 0; i < UserInfoListTableColumns.length; i++) {
			UserInfoListTableColumnV.add(UserInfoListTableColumns[i]);
		}

		UserInfoListTableValueV = new Vector<Vector>();

		UserInfoListTableModel = new DefaultTableModel(UserInfoListTableValueV,
				UserInfoListTableColumnV);

		UserInfoListTable = new MTable(UserInfoListTableModel);
		initUserInfoListTable();
		UserInfoListScrollPane.setViewportView(UserInfoListTable);

		final JPanel UserInfoButtonPanel = new JPanel();
		UserInfoButtonPanel.setLayout(new BoxLayout(UserInfoButtonPanel,
				BoxLayout.Y_AXIS));
		UserInfoListPanel.add(UserInfoButtonPanel, BorderLayout.EAST);
		
		//���ѡ���û���Ϣ��ť������Ӧ�¼�
		final MButton addUserButton = new MButton();
		URL addUserUrl = this.getClass().getResource("/img/add_info.png");
		addUserButton.setIcon(new ImageIcon(addUserUrl));
		URL addUserOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addUserButton.setRolloverIcon(new ImageIcon(addUserOverUrl));
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �ж��Ƿ�����û�
				if (UserInfoTreeRoot.getChildCount() == 0) {
					// ���������û�����ʾ
					JOptionPane.showMessageDialog(null, "�����½��û���", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// ��õ�ǰѡ�е��û�
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) UserInfoTree
							.getLastSelectedPathComponent();
					// ����û�����
					String UserName = treeNode.getUserObject().toString();
					// ��������û���Ϣ�ĶԻ������
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"����û���Ϣ", UserName, -1);
					// ��������û���Ϣ�ĶԻ���Ϊ�ɼ�
					personnelDialog.setVisible(true);
					// ˢ���û���Ϣ�б�
					initUserInfoListTable();
				}
			}
		});
		UserInfoButtonPanel.add(addUserButton);
		
		//�޸��û���Ϣ��ť������Ӧ�¼�
		final MButton updUserInfoButton = new MButton();
		URL updUserInfoUrl = this.getClass().getResource("/img/upd_info.png");
		updUserInfoButton.setIcon(new ImageIcon(updUserInfoUrl));
		URL updUserInfoOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updUserInfoButton.setRolloverIcon(new ImageIcon(updUserInfoOverUrl));
		updUserInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����û���ϸ��Ϣ�б��е�ѡ����
				int[] selectedRows = UserInfoListTable.getSelectedRows();
				// ��ѡ����һ���û�
				if (selectedRows.length == 1) {
					// ���ѡ���û���id
					int id = (Integer) UserInfoListTable.getValueAt(
							selectedRows[0], 1);
					// �����޸��û���Ϣ�ĶԻ������
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"�޸��û���Ϣ", "", id);
					// �����޸��û���Ϣ�ĶԻ���Ϊ�ɼ�
					personnelDialog.setVisible(true);
					initUserInfoListTable();
				} else {
					// δѡ��Ҫ�޸ĵ��û�
					if (selectedRows.length == 0) {
						// ������ʾ��Ϣ
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵ��û���",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					} 
					// ѡ���˶���û�
					else {
						// ������ʾ��Ϣ
						JOptionPane.showMessageDialog(null, "һ��ֻ���޸�һ���û���",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		UserInfoButtonPanel.add(updUserInfoButton);
//-----------------------------------------------------------------------------
		//2������Ա�б���
		final JSplitPane OptInfoSplitPane = new JSplitPane();
		OptInfoSplitPane.setOneTouchExpandable(true);
		OptInfoSplitPane.setDividerSize(12);
		OptInfoSplitPane.setDividerLocation(170);
		tabbedPane.addTab("����Ա��Ϣ�б�", null, OptInfoSplitPane, null);
		
		//��tab����������ʾ����Ա��Ϣ�б�����
		final JPanel OptInfoTreePane = new JPanel();
		OptInfoSplitPane.setLeftComponent(OptInfoTreePane);
		OptInfoTreePane.setLayout(new BorderLayout());
		// ������ʾ����Ա��Ϣ���Ĺ������
		final JScrollPane OptInfoTreeScrollPane = new JScrollPane();
		// �����������ӵ��ϼ������
		OptInfoTreePane.add(OptInfoTreeScrollPane);
		
		// ��������Ա��Ϣ���ĸ��ڵ�
		OptInfoTreeRoot = new DefaultMutableTreeNode("root");
		// ��ʼ������Ա��Ϣ��
		initTree(OptInfoTreeRoot, "OptInfo");

		// ��������Ա��Ϣ��ģ��
		OptInfoTreeModel = new DefaultTreeModel(OptInfoTreeRoot);

		// ��������Ա��Ϣ��
		OptInfoTree = new JTree(OptInfoTreeModel);
		// ���ò���Ա��Ϣ���ĸ��ڵ㲻�ɼ�
		OptInfoTree.setRootVisible(false);
		//System.out.println(UserInfoTree.getSelectionModel().getSelectionMode());
		OptInfoTree.getSelectionModel().setSelectionMode(
				// ���ò���Ա��Ϣ����ѡ��ģʽΪ��ѡ
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// �������Ա��Ϣ�������ӽڵ㣬������ѡ�е�һ���ӽڵ�
		if (OptInfoTreeRoot.getChildCount() > 0)
			OptInfoTree.setSelectionRow(0);
		// Ϊ����Ա��Ϣ����ӽӵ�ѡ���¼�������
		OptInfoTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						// ��ʼ������Ա��Ϣ�б�
						initOptInfoListTable();
					}
				});
		// ������Ա��Ϣ����ӵ����������
		OptInfoTreeScrollPane.setViewportView(OptInfoTree);
		
		final JPanel OptTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_2 = new FlowLayout();
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		OptTreeButtonPanel.setLayout(flowLayout_2);
		OptInfoTreePane.add(OptTreeButtonPanel, BorderLayout.SOUTH);
		//�ڲ���Ա��Ϣ�б����������µĲ���Ա�����˲���Ϊ��Ӱ�ť�����¼���Ӧ
		final MButton addOptTypeButton = new MButton();
		addOptTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ������û�����
				String name = addTreeNode(OptInfoTreeRoot, "����Ա");
				// ��ȡ���½�ʱ����Ϊ��
				if (name != null) {
					// ��õ�ǰӵ�в���Ա������
					int childCount = OptInfoTreeRoot.getChildCount();
					// �ڲ���Ա�б�������󴴽��µĲ���Ա
					OptInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), OptInfoTreeRoot, childCount);
					// ˢ�²���Ա��ģ��
					OptInfoTreeModel.reload();
					// �����½�����ԱΪѡ��״̬
					OptInfoTree.setSelectionRow(childCount);
             		// ���½��û����浽���ݿ���
//					dao.iType(name, "card");
				}
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL OptInfoTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addOptTypeButton.setIcon(new ImageIcon(OptInfoTypeUrl));
		URL OptTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addOptTypeButton.setRolloverIcon(new ImageIcon(OptTypeOverUrl));
		OptTreeButtonPanel.add(addOptTypeButton);
		
		//ɾ������Ա��ť������Ӧ�¼�
		final MButton delOptTypeButton = new MButton();
		delOptTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ѡ�еĲ���Ա����
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) OptInfoTree
						.getLastSelectedPathComponent();
				// δѡ��Ҫɾ���Ĳ���Ա
				if (treeNode == null) {
					// ������ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���Ĳ���Ա��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					// ��ֱ�ӷ���
					return;
				}
				// �����ɾ������Ա������
				String name = treeNode.getUserObject().toString();
				// ����ɾ����ȷ����ʾ
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ������Ա��" + name
						+ "����", "������ʾ", JOptionPane.YES_NO_OPTION);
				// ȡ����ɾ������
				if (i != 0)
					// ֱ�ӷ���
					return;
			    
				// ���û��б��а����û�
//				if (dao.sPersonnelVByTypeName(name).size() > 0) {
//				// ������������Ƭ�Ĵ���ʽ	
//				String options[] = { "ȡ��", "����������Ƭ��", "ɾ��" };
//				// ������ʾ��Ϣ�����û�ѡ���ṩ�Ĵ���ʽ	
//				int optionIndex = JOptionPane.showOptionDialog(null,
//							"��ѡ��Ը���Ƭ������Ա�Ĵ���ʽ��", "������ʾ",
//							JOptionPane.YES_NO_CANCEL_OPTION,
//							JOptionPane.QUESTION_MESSAGE, null, options,
//							options[0]);
//				// �û�ȡ����ɾ������	
//				if (optionIndex == 0)
//				// ֱ�ӷ���		
//				return;
//				// �����Ƭ�е�����ID	
//				int typeId = dao.sTypeIdByUsedAndName("card", name);
//				// �û�ѡ������������Ƭ��	
//				if (optionIndex == 1) {
//				// ��ѯ���������Ƭ��		
//				Vector<Vector> cardV = dao.sTypeByUsedExcept("card",
//								typeId);
//				// ����һ��ѡ��������		
//				String[] cards = new String[cardV.size() + 1];
//				// ���һ����ʾѡ����		
//				cards[0] = "��ѡ��";
//						for (int j = 0; j < cardV.size(); j++) {// ��ʼ��ѡ��������
//							cards[j + 1] = cardV.get(j).get(2).toString();// ��ӿ��������Ƭ��
//						}
//						Object card = "��ѡ��";// Ĭ��Ϊѡ�С���ѡ��
//						while (card.equals("��ѡ��")) {// ��ѡ�е�Ϊ����ѡ��ʱִ��ѭ��
//							card = JOptionPane.showInputDialog(null,
//									"��ѡ��Ҫ�������Ƭ�У�", "������ʾ",
//									JOptionPane.INFORMATION_MESSAGE, null,
//									cards, cards[0]);// �����Ի������û�ѡ�����������Ƭ��
//							if (card == null)// �û�ȡ����ɾ������
//								return;// ֱ�ӷ���
//						}
//						int newTypeId = dao.sTypeIdByUsedAndName("card", card
//								.toString());// �����������Ƭ�е�����ID
//						dao.uPersonnelTypeIdByTypeId(typeId, newTypeId);// �޸���Ƭ�����
//					}
//					if (optionIndex == 2) {// �û�ѡ��ɾ�����������Ƭ
//						dao.dPersonnelByTypeId(typeId);// �����ݿ�ɾ�����������Ƭ
//					}
//				}
				
				// �Ӳ���Ա����ɾ������Ա
				OptInfoTreeModel.removeNodeFromParent(treeNode);
//				// �����ݿ���ɾ������Ա
//				dao.dTypeByName("card", name);
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL delOptTypeUrl = this.getClass().getResource("/img/del_tree.png");
		delOptTypeButton.setIcon(new ImageIcon(delOptTypeUrl));
		URL delOptTypeOverUrl = this.getClass().getResource(
				"/img/del_tree_over.png");
		delOptTypeButton.setRolloverIcon(new ImageIcon(delOptTypeOverUrl));
		OptTreeButtonPanel.add(delOptTypeButton);
		
//------------------------------------------------------------------------------
		
		//��ʾѡ�в���Ա��Ϣ���ּ�������ť
		final JPanel OptInfoListPanel = new JPanel();
		OptInfoSplitPane.setRightComponent(OptInfoListPanel);
		OptInfoListPanel.setLayout(new BorderLayout());

		final JScrollPane OptInfoListScrollPane = new JScrollPane();
		OptInfoListPanel.add(OptInfoListScrollPane);

		OptInfoListTableColumnV = new Vector<String>();
		String OptInfoListTableColumns[] = {"id","����", "�ϴε�¼ip"};
		for (int i = 0; i < OptInfoListTableColumns.length; i++) {
			OptInfoListTableColumnV.add(OptInfoListTableColumns[i]);
		}

		OptInfoListTableValueV = new Vector<Vector>();

		OptInfoListTableModel = new DefaultTableModel(OptInfoListTableValueV,
				OptInfoListTableColumnV);

		OptInfoListTable = new MTable(OptInfoListTableModel);
		initOptInfoListTable();
		OptInfoListScrollPane.setViewportView(OptInfoListTable);

		final JPanel OptInfoButtonPanel = new JPanel();
		OptInfoButtonPanel.setLayout(new BoxLayout(OptInfoButtonPanel,
				BoxLayout.Y_AXIS));
		OptInfoListPanel.add(OptInfoButtonPanel, BorderLayout.EAST);
		
		//���ѡ�в���Ա��Ϣ��ť������Ӧ�¼�
		final MButton addOptButton = new MButton();
		URL addOptUrl = this.getClass().getResource("/img/add_info.png");
		addOptButton.setIcon(new ImageIcon(addOptUrl));
		URL addOptOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addOptButton.setRolloverIcon(new ImageIcon(addOptOverUrl));
		addOptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �ж��Ƿ���ڲ���Ա
				if (OptInfoTreeRoot.getChildCount() == 0) {
					// ������������Ա����ʾ
					JOptionPane.showMessageDialog(null, "�����½�����Ա��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// ��õ�ǰѡ�еĲ���Ա
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) OptInfoTree
							.getLastSelectedPathComponent();
					// ��ò���Ա����
					String OptName = treeNode.getUserObject().toString();
					// ������Ӳ���Ա��Ϣ�ĶԻ������
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"��Ӳ���Ա��Ϣ", OptName, -1);
					// ������Ӳ���Ա��Ϣ�ĶԻ���Ϊ�ɼ�
					personnelDialog.setVisible(true);
					// ˢ�²���Ա��Ϣ�б�
					initOptInfoListTable();
				}
			}
		});
		OptInfoButtonPanel.add(addOptButton);
		
		//�޸Ĳ���Ա��Ϣ��ť������Ӧ�¼�
		final MButton updOptInfoButton = new MButton();
		URL updOptInfoUrl = this.getClass().getResource("/img/upd_info.png");
		updOptInfoButton.setIcon(new ImageIcon(updOptInfoUrl));
		URL updOptInfoOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updOptInfoButton.setRolloverIcon(new ImageIcon(updOptInfoOverUrl));
		updOptInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ò���Ա��ϸ��Ϣ�б��е�ѡ����
				int[] selectedRows = OptInfoListTable.getSelectedRows();
				// ��ѡ����һ������Ա
				if (selectedRows.length == 1) {
					// ���ѡ�в���Ա��id
					int id = (Integer) OptInfoListTable.getValueAt(
							selectedRows[0], 1);
					// �����޸Ĳ���Ա��Ϣ�ĶԻ������
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"�޸Ĳ���Ա��Ϣ", "", id);
					// �����޸Ĳ���Ա��Ϣ�ĶԻ���Ϊ�ɼ�
					personnelDialog.setVisible(true);
					initOptInfoListTable();
				} else {
					// δѡ��Ҫ�޸ĵĲ���Ա
					if (selectedRows.length == 0) {
						// ������ʾ��Ϣ
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵĲ���Ա��",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					} 
					// ѡ���˶������Ա
					else {
						// ������ʾ��Ϣ
						JOptionPane.showMessageDialog(null, "һ��ֻ���޸�һ������Ա��",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		OptInfoButtonPanel.add(updOptInfoButton);
//-----------------------------------------------------------------------------
		//3������Ա�б���
		final JSplitPane ManInfoSplitPane = new JSplitPane();
		ManInfoSplitPane.setOneTouchExpandable(true);
		ManInfoSplitPane.setDividerSize(12);
		ManInfoSplitPane.setDividerLocation(170);
		tabbedPane.addTab("����Ա��Ϣ�б�", null, ManInfoSplitPane, null);
		
		//��tab����������ʾ����Ա��Ϣ�б�����
		final JPanel ManInfoTreePane = new JPanel();
		ManInfoSplitPane.setLeftComponent(ManInfoTreePane);
		ManInfoTreePane.setLayout(new BorderLayout());
		// ������ʾ����Ա��Ϣ���Ĺ������
		final JScrollPane ManInfoTreeScrollPane = new JScrollPane();
		// �����������ӵ��ϼ������
		ManInfoTreePane.add(ManInfoTreeScrollPane);
		
		// ��������Ա��Ϣ���ĸ��ڵ�
		ManInfoTreeRoot = new DefaultMutableTreeNode("root");
		// ��ʼ������Ա��Ϣ��
		initTree(ManInfoTreeRoot, "ManInfo");

		// ��������Ա��Ϣ��ģ��
		ManInfoTreeModel = new DefaultTreeModel(ManInfoTreeRoot);

		// ��������Ա��Ϣ��
		ManInfoTree = new JTree(ManInfoTreeModel);
		// ���ù���Ա��Ϣ���ĸ��ڵ㲻�ɼ�
		ManInfoTree.setRootVisible(false);
		ManInfoTree.getSelectionModel().setSelectionMode(
				// ���ù���Ա��Ϣ����ѡ��ģʽΪ��ѡ
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// �������Ա��Ϣ�������ӽڵ㣬������ѡ�е�һ���ӽڵ�
		if (ManInfoTreeRoot.getChildCount() > 0)
			ManInfoTree.setSelectionRow(0);
		// Ϊ����Ա��Ϣ����ӽӵ�ѡ���¼�������
		ManInfoTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						// ��ʼ������Ա��Ϣ�б�
						initManInfoListTable();
					}
				});
		// ������Ա��Ϣ����ӵ����������
		ManInfoTreeScrollPane.setViewportView(ManInfoTree);
		
		final JPanel ManTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_3 = new FlowLayout();
		flowLayout_3.setVgap(0);
		flowLayout_3.setHgap(0);
		ManTreeButtonPanel.setLayout(flowLayout_3);
		ManInfoTreePane.add(ManTreeButtonPanel, BorderLayout.SOUTH);
		//�ڹ���Ա��Ϣ�б����������µĹ���Ա�����˲���Ϊ��Ӱ�ť�����¼���Ӧ
		final MButton addManTypeButton = new MButton();
		addManTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����¹���Ա����
				String name = addTreeNode(ManInfoTreeRoot, "����Ա");
				// ��ȡ���½�ʱ����Ϊ��
				if (name != null) {
					// ��õ�ǰӵ�й���Ա������
					int childCount = ManInfoTreeRoot.getChildCount();
					// �ڹ���Ա�б�������󴴽��µĹ���Ա
					ManInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), ManInfoTreeRoot, childCount);
					// ˢ�¹���Ա��ģ��
					ManInfoTreeModel.reload();
					// �����½�����ԱΪѡ��״̬
					ManInfoTree.setSelectionRow(childCount);
             		// ���½�����Ա���浽���ݿ���
//					dao.iType(name, "card");
				}
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL ManInfoTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addManTypeButton.setIcon(new ImageIcon(ManInfoTypeUrl));
		URL ManTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addManTypeButton.setRolloverIcon(new ImageIcon(ManTypeOverUrl));
		ManTreeButtonPanel.add(addManTypeButton);
		
		//ɾ������Ա��ť������Ӧ�¼�
		final MButton delManTypeButton = new MButton();
		delManTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ѡ�еĹ���Ա����
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) ManInfoTree
						.getLastSelectedPathComponent();
				// δѡ��Ҫɾ���Ĺ���Ա
				if (treeNode == null) {
					// ������ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���Ĺ���Ա��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					// ��ֱ�ӷ���
					return;
				}
				// �����ɾ������Ա������
				String name = treeNode.getUserObject().toString();
				// ����ɾ����ȷ����ʾ
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ������Ա��" + name
						+ "����", "������ʾ", JOptionPane.YES_NO_OPTION);
				// ȡ����ɾ������
				if (i != 0)
					// ֱ�ӷ���
					return;
			    
				// ���û��б��а����û�
//				if (dao.sPersonnelVByTypeName(name).size() > 0) {
//				// ������������Ƭ�Ĵ���ʽ	
//				String options[] = { "ȡ��", "����������Ƭ��", "ɾ��" };
//				// ������ʾ��Ϣ�����û�ѡ���ṩ�Ĵ���ʽ	
//				int optionIndex = JOptionPane.showOptionDialog(null,
//							"��ѡ��Ը���Ƭ������Ա�Ĵ���ʽ��", "������ʾ",
//							JOptionPane.YES_NO_CANCEL_OPTION,
//							JOptionPane.QUESTION_MESSAGE, null, options,
//							options[0]);
//				// �û�ȡ����ɾ������	
//				if (optionIndex == 0)
//				// ֱ�ӷ���		
//				return;
//				// �����Ƭ�е�����ID	
//				int typeId = dao.sTypeIdByUsedAndName("card", name);
//				// �û�ѡ������������Ƭ��	
//				if (optionIndex == 1) {
//				// ��ѯ���������Ƭ��		
//				Vector<Vector> cardV = dao.sTypeByUsedExcept("card",
//								typeId);
//				// ����һ��ѡ��������		
//				String[] cards = new String[cardV.size() + 1];
//				// ���һ����ʾѡ����		
//				cards[0] = "��ѡ��";
//						for (int j = 0; j < cardV.size(); j++) {// ��ʼ��ѡ��������
//							cards[j + 1] = cardV.get(j).get(2).toString();// ��ӿ��������Ƭ��
//						}
//						Object card = "��ѡ��";// Ĭ��Ϊѡ�С���ѡ��
//						while (card.equals("��ѡ��")) {// ��ѡ�е�Ϊ����ѡ��ʱִ��ѭ��
//							card = JOptionPane.showInputDialog(null,
//									"��ѡ��Ҫ�������Ƭ�У�", "������ʾ",
//									JOptionPane.INFORMATION_MESSAGE, null,
//									cards, cards[0]);// �����Ի������û�ѡ�����������Ƭ��
//							if (card == null)// �û�ȡ����ɾ������
//								return;// ֱ�ӷ���
//						}
//						int newTypeId = dao.sTypeIdByUsedAndName("card", card
//								.toString());// �����������Ƭ�е�����ID
//						dao.uPersonnelTypeIdByTypeId(typeId, newTypeId);// �޸���Ƭ�����
//					}
//					if (optionIndex == 2) {// �û�ѡ��ɾ�����������Ƭ
//						dao.dPersonnelByTypeId(typeId);// �����ݿ�ɾ�����������Ƭ
//					}
//				}
				
				// �ӹ���Ա����ɾ������Ա
				ManInfoTreeModel.removeNodeFromParent(treeNode);
//				// �����ݿ���ɾ������Ա
//				dao.dTypeByName("card", name);
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL delManTypeUrl = this.getClass().getResource("/img/del_tree.png");
		delManTypeButton.setIcon(new ImageIcon(delManTypeUrl));
		URL delManTypeOverUrl = this.getClass().getResource(
				"/img/del_tree_over.png");
		delManTypeButton.setRolloverIcon(new ImageIcon(delManTypeOverUrl));
		ManTreeButtonPanel.add(delManTypeButton);
//------------------------------------------------------------------------------
		
		//��ʾѡ�й���Ա��Ϣ���ּ�������ť
		final JPanel ManInfoListPanel = new JPanel();
		ManInfoSplitPane.setRightComponent(ManInfoListPanel);
		ManInfoListPanel.setLayout(new BorderLayout());

		final JScrollPane ManInfoListScrollPane = new JScrollPane();
		ManInfoListPanel.add(ManInfoListScrollPane);

		ManInfoListTableColumnV = new Vector<String>();
		String ManInfoListTableColumns[] = {"id","����", "�ϴε�¼ip"};
		for (int i = 0; i < ManInfoListTableColumns.length; i++) {
			ManInfoListTableColumnV.add(ManInfoListTableColumns[i]);
		}

		ManInfoListTableValueV = new Vector<Vector>();

		ManInfoListTableModel = new DefaultTableModel(ManInfoListTableValueV,
				ManInfoListTableColumnV);

		ManInfoListTable = new MTable(ManInfoListTableModel);
		initManInfoListTable();
		ManInfoListScrollPane.setViewportView(ManInfoListTable);

		final JPanel ManInfoButtonPanel = new JPanel();
		ManInfoButtonPanel.setLayout(new BoxLayout(ManInfoButtonPanel,
				BoxLayout.Y_AXIS));
		ManInfoListPanel.add(ManInfoButtonPanel, BorderLayout.EAST);
		
		//���ѡ�й���Ա��Ϣ��ť������Ӧ�¼�
		final MButton addManButton = new MButton();
		URL addManUrl = this.getClass().getResource("/img/add_info.png");
		addManButton.setIcon(new ImageIcon(addManUrl));
		URL addManOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addManButton.setRolloverIcon(new ImageIcon(addManOverUrl));
		addManButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �ж��Ƿ���ڹ���Ա
				if (ManInfoTreeRoot.getChildCount() == 0) {
					// ������������Ա����ʾ
					JOptionPane.showMessageDialog(null, "�����½�����Ա��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// ��õ�ǰѡ�еĹ���Ա
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) ManInfoTree
							.getLastSelectedPathComponent();
					// ��ù���Ա����
					String ManName = treeNode.getUserObject().toString();
					// ������ӹ���Ա��Ϣ�ĶԻ������
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"��ӹ���Ա��Ϣ", ManName, -1);
					// ������ӹ���Ա��Ϣ�ĶԻ���Ϊ�ɼ�
					personnelDialog.setVisible(true);
					// ˢ�¹���Ա��Ϣ�б�
					initManInfoListTable();
				}
			}
		});
		ManInfoButtonPanel.add(addManButton);
		
		//�޸Ĺ���Ա��Ϣ��ť������Ӧ�¼�
		final MButton updManInfoButton = new MButton();
		URL updManInfoUrl = this.getClass().getResource("/img/upd_info.png");
		updManInfoButton.setIcon(new ImageIcon(updManInfoUrl));
		URL updManInfoOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updManInfoButton.setRolloverIcon(new ImageIcon(updManInfoOverUrl));
		updManInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ù���Ա��ϸ��Ϣ�б��е�ѡ����
				int[] selectedRows = ManInfoListTable.getSelectedRows();
				// ��ѡ����һ������Ա
				if (selectedRows.length == 1) {
					// ���ѡ�й���Ա��id
					int id = (Integer) ManInfoListTable.getValueAt(
							selectedRows[0], 1);
					// �����޸Ĺ���Ա��Ϣ�ĶԻ������
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"�޸Ĺ���Ա��Ϣ", "", id);
					// �����޸Ĺ���Ա��Ϣ�ĶԻ���Ϊ�ɼ�
					personnelDialog.setVisible(true);
					initManInfoListTable();
				} else {
					// δѡ��Ҫ�޸ĵĹ���Ա
					if (selectedRows.length == 0) {
						// ������ʾ��Ϣ
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵĹ���Ա��",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					} 
					// ѡ���˶������Ա
					else {
						// ������ʾ��Ϣ
						JOptionPane.showMessageDialog(null, "һ��ֻ���޸�һ������Ա��",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		ManInfoButtonPanel.add(updManInfoButton);
	}
	
	//���ð�ť��Ӧ�¼�
	private class HandsetButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Manager_SetDialog dialog = new Manager_SetDialog();
			dialog.setVisible(true);
		}
	}
	
	/**
	 * @description ��ʼ�����panel�����ķ���
	 * @param treeRoot DefaultMutableTreeNode ���ĸ��ڵ�,
	 *        used String ָ����������
	 */
	private void initTree(DefaultMutableTreeNode treeRoot, String used) {
		_System.getServe().addWork(Init_Controller.class, "userlist_init", null, null);
//		Vector typeV = dao.sTypeByUsed(used);// ��ѯ����ָ����������
//		Vector typeV = new Vector();
//		typeV.add("UserInfo");
//		// ��������
//		for (int i = 0; i < typeV.size(); i++) {
//			// �����������
//			Vector type = (Vector) typeV.get(i);
//			// ��������ӵ�����
//		UserInfoTreeRoot.add(new DefaultMutableTreeNode(type.get(2)));
//		}
	}
	
	/**
	 * @description ��ʼ��UserInfoList����Ϣ�����ķ���
	 */
	private void initUserInfoListTable() {
		
		
//		// ����û���Ϣ�б�
//		UserInfoListTableValueV.removeAllElements();
//		// ����û���Ϣ����ѡ�нڵ����
//		DefaultMutableTreeNode cardTreeNode = (DefaultMutableTreeNode) UserInfoTree
//				.getLastSelectedPathComponent();
//		// �ж��Ƿ����ѡ�еĽڵ�
//		if (cardTreeNode != null) {
//			// ���ѡ���û���Ϣ������
//			String cardName = cardTreeNode.getUserObject().toString();
//			// �����û���Ϣ��������Ϣ
////			cardListTableValueV.addAll(dao.sPersonnelVByTypeName(cardName));
//		}
//		// ˢ���û��б���ģ��
//		UserInfoListTableModel.setDataVector(UserInfoListTableValueV,
//				UserInfoListTableColumnV);
	}
	
	/**
	 * @description ��ʼ��optinfolist����Գ��ö������Ϣ�����ķ���
	 */
	private void initOptInfoListTable() {
		OptInfoListTableValueV.removeAllElements();
		DefaultMutableTreeNode infoTreeNode = (DefaultMutableTreeNode) OptInfoTree
				.getLastSelectedPathComponent();
		if (infoTreeNode != null) {
			String infoName = infoTreeNode.getUserObject().toString();
//			infoListTableValueV.addAll(dao.sInfoVByTypeName(infoName));
		}
		OptInfoListTableModel.setDataVector(OptInfoListTableValueV,
				OptInfoListTableColumnV);
	}
	
	/**
	 * @description ��ʼ��maninfolist����Գ��ö������Ϣ�����ķ���
	 */
	private void initManInfoListTable() {
		ManInfoListTableValueV.removeAllElements();
		DefaultMutableTreeNode infoTreeNode = (DefaultMutableTreeNode) ManInfoTree
				.getLastSelectedPathComponent();
		if (infoTreeNode != null) {
			String infoName = infoTreeNode.getUserObject().toString();
//			infoListTableValueV.addAll(dao.sInfoVByTypeName(infoName));
		}
		ManInfoListTableModel.setDataVector(ManInfoListTableValueV,
				ManInfoListTableColumnV);
	}
	
	 /**
	 * @description �������ӽڵ㣨�û�������Ա�͹���Ա��������
	 * @param treeNode DefaultMutableTreeNode ���Ľڵ�����
	 *        typeName String ��ӵ�����
	 * @return String ����ӽڵ������
	 */
    public String addTreeNode(DefaultMutableTreeNode treeNode, String typeName) {
		// �����ڵ�����Ϊ���ַ���
		String nodeName = "";
		// �жϽڵ����Ƶĳ����Ƿ�Ϊ0
		while (nodeName.length() == 0) {
			// �����������������
			nodeName = JOptionPane.showInputDialog(null, "������" + typeName
					+ "���ƣ�", "�½�" + typeName, JOptionPane.INFORMATION_MESSAGE);
			// �жϽڵ������Ƿ�Ϊ��ֵ
			if (nodeName == null) {
				// Ϊ��ֵ���û�ȡ�����½���������ѭ��
				break;
			} else {
				// ȥ����β�ո�
				nodeName = nodeName.trim();
				// �жϽڵ����Ƶĳ����Ƿ�Ϊ0
				if (nodeName.length() > 0) {
					// �����Ϊ0���жϸ������Ƿ��Ѿ�����
					if (isHad(treeNode, nodeName))
						// ������������ýڵ�����Ϊ���ַ���
						nodeName = "";
				}
			}
		}
		// ���ؽڵ�����
		return nodeName;
	}
    
    /**
	 * @description ��֤��ӵĽڵ��Ƿ��Ѿ�����
	 * @param treeNode DefaultMutableTreeNode ���Ľڵ�����
	 *        newChildName String �µ��ӽڵ�����
	 */
    private boolean isHad(DefaultMutableTreeNode treeNode, String newChildName) {
		// Ĭ��Ϊ������
		boolean had = false;
		// ����ӽڵ������
		int childCount = treeNode.getChildCount();
		// �����ӽڵ�
		for (int i = 0; i < childCount; i++) {
			// ����ӽڵ����
			DefaultMutableTreeNode childTreeNode = (DefaultMutableTreeNode) treeNode
					.getChildAt(i);
			// �ж������Ƿ���ͬ
			if (childTreeNode.getUserObject().toString().equals(newChildName)) {
				// �����������Ѿ����ڵ���ʾ
				JOptionPane.showMessageDialog(null, "�������Ѿ����ڣ�", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				// �������Ѿ�����
				had = true;
				// ����ѭ����ֹͣ����������ӽڵ�
				break;
			}
		}
		return had;// ���ؽ��
	}
}
