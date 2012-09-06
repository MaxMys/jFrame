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

	//有关用户信息列表
	private MTable UserInfoListTable;
	public DefaultMutableTreeNode UserInfoTreeRoot;
	public DefaultTreeModel UserInfoTreeModel;
	public JTree UserInfoTree;
	private Vector<Vector> UserInfoListTableValueV;
	private Vector<String> UserInfoListTableColumnV;
	private DefaultTableModel UserInfoListTableModel;
	
	//有关操作员信息列表
	private MTable OptInfoListTable;
	public DefaultMutableTreeNode OptInfoTreeRoot;
	public DefaultTreeModel OptInfoTreeModel;
	public JTree OptInfoTree;
	private Vector<Vector> OptInfoListTableValueV;
	private Vector<String> OptInfoListTableColumnV;
	private DefaultTableModel OptInfoListTableModel;
	
	//有关管理员信息列表
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
	 * @param user 用户名
	 */
	public ManagerFrame(final Vector v){
		super();
		setTitle("操作员&管理员信息管理界面");
		setBounds(100, 100, 900, 690);
//		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 创建工具栏对象
		final JToolBar toolBar = new JToolBar();
		// 设置工具栏的边框样式
		toolBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		// 设置工具栏不可移动
		toolBar.setFloatable(false);
		// 将工具栏添加到面板中
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		//设置按钮及其事件响应
		final MButton handsetButton = new MButton();
		handsetButton.addActionListener(new HandsetButtonActionListener());
		URL infoUrl = this.getClass().getResource("/img/info.png");
		handsetButton.setIcon(new ImageIcon(infoUrl));
		URL infoOverUrl = this.getClass().getResource("/img/info_over.png");
		handsetButton.setRolloverIcon(new ImageIcon(infoOverUrl));
		toolBar.add(handsetButton);
		
		//退出按钮及其事件响应
		final MButton exitButton = new MButton();
		URL exitUrl = this.getClass().getResource("/img/exit.png");
		exitButton.setIcon(new ImageIcon(exitUrl));
		URL exitOverUrl = this.getClass().getResource("/img/exit_over.png");
		exitButton.setRolloverIcon(new ImageIcon(exitOverUrl));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null,"确定退出？", "友情提示",
							JOptionPane.YES_NO_OPTION);
					if (i == 0) System.exit(0);
			}
		});
		toolBar.add(exitButton);
		
//--------------------------------------------------------------------------------
		//此部分为列表面板（包括用户列表信息，操作员列表信息和管理员列表信息）
		final JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
		
		//1、用户信息列表部分
		final JSplitPane UserInfoSplitPane = new JSplitPane();
		UserInfoSplitPane.setOneTouchExpandable(true);
		UserInfoSplitPane.setDividerSize(12);
		UserInfoSplitPane.setDividerLocation(170);
		tabbedPane.addTab("用户信息列表", null, UserInfoSplitPane, null);
		
		//在tab面板中添加显示用户信息列表的面板
		final JPanel UserInfoTreePane = new JPanel();
		UserInfoSplitPane.setLeftComponent(UserInfoTreePane);
		UserInfoTreePane.setLayout(new BorderLayout());

		// 创建显示用户信息树的滚动面板
		final JScrollPane UserInfoTreeScrollPane = new JScrollPane();
		// 将滚动面板添加到上级面板中
		UserInfoTreePane.add(UserInfoTreeScrollPane);
		
		// 创建用户信息树的根节点
		UserInfoTreeRoot = new DefaultMutableTreeNode("root");
		// 初始化用户信息树
		initTree(UserInfoTreeRoot, "UserInfo");


		// 创建用户信息树模型
		UserInfoTreeModel = new DefaultTreeModel(UserInfoTreeRoot);

		// 创建用户信息树
		UserInfoTree = new JTree(UserInfoTreeModel);
		// 设置用户信息树的根节点不可见
		UserInfoTree.setRootVisible(false);
		//System.out.println(UserInfoTree.getSelectionModel().getSelectionMode());
		UserInfoTree.getSelectionModel().setSelectionMode(
				// 设置用户信息树的选择模式为单选
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// 如果用户信息树存在子节点，则设置选中第一个子节点
		if (UserInfoTreeRoot.getChildCount() > 0)
			UserInfoTree.setSelectionRow(0);
		// 为用户信息树添加接点选中事件监听器
		UserInfoTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						// 初始化用户信息列表
						initUserInfoListTable();
					}
				});
		// 将用户信息树添加到滚动面板中
		UserInfoTreeScrollPane.setViewportView(UserInfoTree);
		
		final JPanel UserTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		UserTreeButtonPanel.setLayout(flowLayout_1);
		UserInfoTreePane.add(UserTreeButtonPanel, BorderLayout.SOUTH);
		//在用户信息列表树最后添加新的用户，即此部分为添加按钮及其事件响应
		final MButton addUserTypeButton = new MButton();
		addUserTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得新用户名称
				String name = addTreeNode(UserInfoTreeRoot, "用户");
				// 当取消新建时名称为空
				if (name != null) {
					// 获得当前拥有用户的数量
					int childCount = UserInfoTreeRoot.getChildCount();
					// 在用户列表树的最后创建新的用户
					UserInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), UserInfoTreeRoot, childCount);
					// 刷新用户树模型
					UserInfoTreeModel.reload();
					// 设置新建用户为选中状态
					UserInfoTree.setSelectionRow(childCount);
             		// 将新建用户保存到数据库中
//					dao.iType(name, "card");
				}
			}
		});
		//把按钮换成自定义图片
		URL UserInfoTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addUserTypeButton.setIcon(new ImageIcon(UserInfoTypeUrl));
		URL UserTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addUserTypeButton.setRolloverIcon(new ImageIcon(UserTypeOverUrl));
		UserTreeButtonPanel.add(addUserTypeButton);
		
		//删除用户按钮及其响应事件
		final MButton delUserTypeButton = new MButton();
		delUserTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得选中的用户对象
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) UserInfoTree
						.getLastSelectedPathComponent();
				// 未选择要删除的用户
				if (treeNode == null) {
					// 弹出提示信息
					JOptionPane.showMessageDialog(null, "请选择要删除的用户！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					// 并直接返回
					return;
				}
				// 获得欲删除用户的名称
				String name = treeNode.getUserObject().toString();
				// 弹出删除的确认提示
				int i = JOptionPane.showConfirmDialog(null, "确定要删除用户“" + name
						+ "”？", "友情提示", JOptionPane.YES_NO_OPTION);
				// 取消了删除操作
				if (i != 0)
					// 直接返回
					return;
			    
				// 该用户列表中包含用户
//				if (dao.sPersonnelVByTypeName(name).size() > 0) {
//				// 定义对其包含名片的处理方式	
//				String options[] = { "取消", "移入其他名片夹", "删除" };
//				// 弹出提示信息，令用户选择提供的处理方式	
//				int optionIndex = JOptionPane.showOptionDialog(null,
//							"请选择对该名片夹下人员的处理方式：", "友情提示",
//							JOptionPane.YES_NO_CANCEL_OPTION,
//							JOptionPane.QUESTION_MESSAGE, null, options,
//							options[0]);
//				// 用户取消了删除操作	
//				if (optionIndex == 0)
//				// 直接返回		
//				return;
//				// 获得名片夹的主键ID	
//				int typeId = dao.sTypeIdByUsedAndName("card", name);
//				// 用户选择移入其他名片夹	
//				if (optionIndex == 1) {
//				// 查询可移入的名片夹		
//				Vector<Vector> cardV = dao.sTypeByUsedExcept("card",
//								typeId);
//				// 创建一个选择项数组		
//				String[] cards = new String[cardV.size() + 1];
//				// 添加一个提示选择项		
//				cards[0] = "请选择";
//						for (int j = 0; j < cardV.size(); j++) {// 初始化选择项数组
//							cards[j + 1] = cardV.get(j).get(2).toString();// 添加可移入的名片夹
//						}
//						Object card = "请选择";// 默认为选中“请选择”
//						while (card.equals("请选择")) {// 当选中的为“请选择”时执行循环
//							card = JOptionPane.showInputDialog(null,
//									"请选择要移入的名片夹：", "友情提示",
//									JOptionPane.INFORMATION_MESSAGE, null,
//									cards, cards[0]);// 弹出对话框令用户选择欲移入的名片夹
//							if (card == null)// 用户取消了删除操作
//								return;// 直接返回
//						}
//						int newTypeId = dao.sTypeIdByUsedAndName("card", card
//								.toString());// 获得欲移入名片夹的主键ID
//						dao.uPersonnelTypeIdByTypeId(typeId, newTypeId);// 修改名片的外键
//					}
//					if (optionIndex == 2) {// 用户选择删除其包含的名片
//						dao.dPersonnelByTypeId(typeId);// 从数据库删除其包含的名片
//					}
//				}
				
				// 从用户树中删除用户
				UserInfoTreeModel.removeNodeFromParent(treeNode);
//				// 从数据库中删除名片夹
//				dao.dTypeByName("card", name);
			}
		});
		//把按钮换成自定义图片
		URL delUserTypeUrl = this.getClass().getResource("/img/del_tree.png");
		delUserTypeButton.setIcon(new ImageIcon(delUserTypeUrl));
		URL delUserTypeOverUrl = this.getClass().getResource(
				"/img/del_tree_over.png");
		delUserTypeButton.setRolloverIcon(new ImageIcon(delUserTypeOverUrl));
		UserTreeButtonPanel.add(delUserTypeButton);
		
//-----------------------------------------------------------------------------
		
		//显示选中用户信息部分及操作按钮
		final JPanel UserInfoListPanel = new JPanel();
		UserInfoSplitPane.setRightComponent(UserInfoListPanel);
		UserInfoListPanel.setLayout(new BorderLayout());

		final JScrollPane UserInfoListScrollPane = new JScrollPane();
		UserInfoListPanel.add(UserInfoListScrollPane);

		UserInfoListTableColumnV = new Vector<String>();
		String UserInfoListTableColumns[] = {"id","姓名", "移动电话","注册时间" ,"是否开通天气","上次登录IP","已用话费","余额"};
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
		
		//添加选中用户信息按钮及其响应事件
		final MButton addUserButton = new MButton();
		URL addUserUrl = this.getClass().getResource("/img/add_info.png");
		addUserButton.setIcon(new ImageIcon(addUserUrl));
		URL addUserOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addUserButton.setRolloverIcon(new ImageIcon(addUserOverUrl));
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 判断是否存在用户
				if (UserInfoTreeRoot.getChildCount() == 0) {
					// 弹出建立用户的提示
					JOptionPane.showMessageDialog(null, "请先新建用户！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 获得当前选中的用户
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) UserInfoTree
							.getLastSelectedPathComponent();
					// 获得用户名称
					String UserName = treeNode.getUserObject().toString();
					// 创建添加用户信息的对话框对象
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"添加用户信息", UserName, -1);
					// 设置添加用户信息的对话框为可见
					personnelDialog.setVisible(true);
					// 刷新用户信息列表
					initUserInfoListTable();
				}
			}
		});
		UserInfoButtonPanel.add(addUserButton);
		
		//修改用户信息按钮及其响应事件
		final MButton updUserInfoButton = new MButton();
		URL updUserInfoUrl = this.getClass().getResource("/img/upd_info.png");
		updUserInfoButton.setIcon(new ImageIcon(updUserInfoUrl));
		URL updUserInfoOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updUserInfoButton.setRolloverIcon(new ImageIcon(updUserInfoOverUrl));
		updUserInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得用户详细信息列表中的选中行
				int[] selectedRows = UserInfoListTable.getSelectedRows();
				// 仅选中了一个用户
				if (selectedRows.length == 1) {
					// 获得选中用户的id
					int id = (Integer) UserInfoListTable.getValueAt(
							selectedRows[0], 1);
					// 创建修改用户信息的对话框对象
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"修改用户信息", "", id);
					// 设置修改用户信息的对话框为可见
					personnelDialog.setVisible(true);
					initUserInfoListTable();
				} else {
					// 未选中要修改的用户
					if (selectedRows.length == 0) {
						// 弹出提示信息
						JOptionPane.showMessageDialog(null, "请选择要修改的用户！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);
					} 
					// 选中了多个用户
					else {
						// 弹出提示信息
						JOptionPane.showMessageDialog(null, "一次只能修改一个用户！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		UserInfoButtonPanel.add(updUserInfoButton);
//-----------------------------------------------------------------------------
		//2、操作员列表部分
		final JSplitPane OptInfoSplitPane = new JSplitPane();
		OptInfoSplitPane.setOneTouchExpandable(true);
		OptInfoSplitPane.setDividerSize(12);
		OptInfoSplitPane.setDividerLocation(170);
		tabbedPane.addTab("操作员信息列表", null, OptInfoSplitPane, null);
		
		//在tab面板中添加显示操作员信息列表的面板
		final JPanel OptInfoTreePane = new JPanel();
		OptInfoSplitPane.setLeftComponent(OptInfoTreePane);
		OptInfoTreePane.setLayout(new BorderLayout());
		// 创建显示操作员信息树的滚动面板
		final JScrollPane OptInfoTreeScrollPane = new JScrollPane();
		// 将滚动面板添加到上级面板中
		OptInfoTreePane.add(OptInfoTreeScrollPane);
		
		// 创建操作员信息树的根节点
		OptInfoTreeRoot = new DefaultMutableTreeNode("root");
		// 初始化操作员信息树
		initTree(OptInfoTreeRoot, "OptInfo");

		// 创建操作员信息树模型
		OptInfoTreeModel = new DefaultTreeModel(OptInfoTreeRoot);

		// 创建操作员信息树
		OptInfoTree = new JTree(OptInfoTreeModel);
		// 设置操作员信息树的根节点不可见
		OptInfoTree.setRootVisible(false);
		//System.out.println(UserInfoTree.getSelectionModel().getSelectionMode());
		OptInfoTree.getSelectionModel().setSelectionMode(
				// 设置操作员信息树的选择模式为单选
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// 如果操作员信息树存在子节点，则设置选中第一个子节点
		if (OptInfoTreeRoot.getChildCount() > 0)
			OptInfoTree.setSelectionRow(0);
		// 为操作员信息树添加接点选中事件监听器
		OptInfoTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						// 初始化操作员信息列表
						initOptInfoListTable();
					}
				});
		// 将操作员信息树添加到滚动面板中
		OptInfoTreeScrollPane.setViewportView(OptInfoTree);
		
		final JPanel OptTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_2 = new FlowLayout();
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		OptTreeButtonPanel.setLayout(flowLayout_2);
		OptInfoTreePane.add(OptTreeButtonPanel, BorderLayout.SOUTH);
		//在操作员信息列表树最后添加新的操作员，即此部分为添加按钮及其事件响应
		final MButton addOptTypeButton = new MButton();
		addOptTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得新用户名称
				String name = addTreeNode(OptInfoTreeRoot, "操作员");
				// 当取消新建时名称为空
				if (name != null) {
					// 获得当前拥有操作员的数量
					int childCount = OptInfoTreeRoot.getChildCount();
					// 在操作员列表树的最后创建新的操作员
					OptInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), OptInfoTreeRoot, childCount);
					// 刷新操作员树模型
					OptInfoTreeModel.reload();
					// 设置新建操作员为选中状态
					OptInfoTree.setSelectionRow(childCount);
             		// 将新建用户保存到数据库中
//					dao.iType(name, "card");
				}
			}
		});
		//把按钮换成自定义图片
		URL OptInfoTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addOptTypeButton.setIcon(new ImageIcon(OptInfoTypeUrl));
		URL OptTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addOptTypeButton.setRolloverIcon(new ImageIcon(OptTypeOverUrl));
		OptTreeButtonPanel.add(addOptTypeButton);
		
		//删除操作员按钮及其响应事件
		final MButton delOptTypeButton = new MButton();
		delOptTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得选中的操作员对象
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) OptInfoTree
						.getLastSelectedPathComponent();
				// 未选择要删除的操作员
				if (treeNode == null) {
					// 弹出提示信息
					JOptionPane.showMessageDialog(null, "请选择要删除的操作员！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					// 并直接返回
					return;
				}
				// 获得欲删除操作员的名称
				String name = treeNode.getUserObject().toString();
				// 弹出删除的确认提示
				int i = JOptionPane.showConfirmDialog(null, "确定要删除操作员“" + name
						+ "”？", "友情提示", JOptionPane.YES_NO_OPTION);
				// 取消了删除操作
				if (i != 0)
					// 直接返回
					return;
			    
				// 该用户列表中包含用户
//				if (dao.sPersonnelVByTypeName(name).size() > 0) {
//				// 定义对其包含名片的处理方式	
//				String options[] = { "取消", "移入其他名片夹", "删除" };
//				// 弹出提示信息，令用户选择提供的处理方式	
//				int optionIndex = JOptionPane.showOptionDialog(null,
//							"请选择对该名片夹下人员的处理方式：", "友情提示",
//							JOptionPane.YES_NO_CANCEL_OPTION,
//							JOptionPane.QUESTION_MESSAGE, null, options,
//							options[0]);
//				// 用户取消了删除操作	
//				if (optionIndex == 0)
//				// 直接返回		
//				return;
//				// 获得名片夹的主键ID	
//				int typeId = dao.sTypeIdByUsedAndName("card", name);
//				// 用户选择移入其他名片夹	
//				if (optionIndex == 1) {
//				// 查询可移入的名片夹		
//				Vector<Vector> cardV = dao.sTypeByUsedExcept("card",
//								typeId);
//				// 创建一个选择项数组		
//				String[] cards = new String[cardV.size() + 1];
//				// 添加一个提示选择项		
//				cards[0] = "请选择";
//						for (int j = 0; j < cardV.size(); j++) {// 初始化选择项数组
//							cards[j + 1] = cardV.get(j).get(2).toString();// 添加可移入的名片夹
//						}
//						Object card = "请选择";// 默认为选中“请选择”
//						while (card.equals("请选择")) {// 当选中的为“请选择”时执行循环
//							card = JOptionPane.showInputDialog(null,
//									"请选择要移入的名片夹：", "友情提示",
//									JOptionPane.INFORMATION_MESSAGE, null,
//									cards, cards[0]);// 弹出对话框令用户选择欲移入的名片夹
//							if (card == null)// 用户取消了删除操作
//								return;// 直接返回
//						}
//						int newTypeId = dao.sTypeIdByUsedAndName("card", card
//								.toString());// 获得欲移入名片夹的主键ID
//						dao.uPersonnelTypeIdByTypeId(typeId, newTypeId);// 修改名片的外键
//					}
//					if (optionIndex == 2) {// 用户选择删除其包含的名片
//						dao.dPersonnelByTypeId(typeId);// 从数据库删除其包含的名片
//					}
//				}
				
				// 从操作员树中删除操作员
				OptInfoTreeModel.removeNodeFromParent(treeNode);
//				// 从数据库中删除操作员
//				dao.dTypeByName("card", name);
			}
		});
		//把按钮换成自定义图片
		URL delOptTypeUrl = this.getClass().getResource("/img/del_tree.png");
		delOptTypeButton.setIcon(new ImageIcon(delOptTypeUrl));
		URL delOptTypeOverUrl = this.getClass().getResource(
				"/img/del_tree_over.png");
		delOptTypeButton.setRolloverIcon(new ImageIcon(delOptTypeOverUrl));
		OptTreeButtonPanel.add(delOptTypeButton);
		
//------------------------------------------------------------------------------
		
		//显示选中操作员信息部分及操作按钮
		final JPanel OptInfoListPanel = new JPanel();
		OptInfoSplitPane.setRightComponent(OptInfoListPanel);
		OptInfoListPanel.setLayout(new BorderLayout());

		final JScrollPane OptInfoListScrollPane = new JScrollPane();
		OptInfoListPanel.add(OptInfoListScrollPane);

		OptInfoListTableColumnV = new Vector<String>();
		String OptInfoListTableColumns[] = {"id","姓名", "上次登录ip"};
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
		
		//添加选中操作员信息按钮及其响应事件
		final MButton addOptButton = new MButton();
		URL addOptUrl = this.getClass().getResource("/img/add_info.png");
		addOptButton.setIcon(new ImageIcon(addOptUrl));
		URL addOptOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addOptButton.setRolloverIcon(new ImageIcon(addOptOverUrl));
		addOptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 判断是否存在操作员
				if (OptInfoTreeRoot.getChildCount() == 0) {
					// 弹出建立操作员的提示
					JOptionPane.showMessageDialog(null, "请先新建操作员！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 获得当前选中的操作员
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) OptInfoTree
							.getLastSelectedPathComponent();
					// 获得操作员名称
					String OptName = treeNode.getUserObject().toString();
					// 创建添加操作员信息的对话框对象
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"添加操作员信息", OptName, -1);
					// 设置添加操作员信息的对话框为可见
					personnelDialog.setVisible(true);
					// 刷新操作员信息列表
					initOptInfoListTable();
				}
			}
		});
		OptInfoButtonPanel.add(addOptButton);
		
		//修改操作员信息按钮及其响应事件
		final MButton updOptInfoButton = new MButton();
		URL updOptInfoUrl = this.getClass().getResource("/img/upd_info.png");
		updOptInfoButton.setIcon(new ImageIcon(updOptInfoUrl));
		URL updOptInfoOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updOptInfoButton.setRolloverIcon(new ImageIcon(updOptInfoOverUrl));
		updOptInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得操作员详细信息列表中的选中行
				int[] selectedRows = OptInfoListTable.getSelectedRows();
				// 仅选中了一个操作员
				if (selectedRows.length == 1) {
					// 获得选中操作员的id
					int id = (Integer) OptInfoListTable.getValueAt(
							selectedRows[0], 1);
					// 创建修改操作员信息的对话框对象
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"修改操作员信息", "", id);
					// 设置修改操作员信息的对话框为可见
					personnelDialog.setVisible(true);
					initOptInfoListTable();
				} else {
					// 未选中要修改的操作员
					if (selectedRows.length == 0) {
						// 弹出提示信息
						JOptionPane.showMessageDialog(null, "请选择要修改的操作员！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);
					} 
					// 选中了多个操作员
					else {
						// 弹出提示信息
						JOptionPane.showMessageDialog(null, "一次只能修改一个操作员！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		OptInfoButtonPanel.add(updOptInfoButton);
//-----------------------------------------------------------------------------
		//3、管理员列表部分
		final JSplitPane ManInfoSplitPane = new JSplitPane();
		ManInfoSplitPane.setOneTouchExpandable(true);
		ManInfoSplitPane.setDividerSize(12);
		ManInfoSplitPane.setDividerLocation(170);
		tabbedPane.addTab("管理员信息列表", null, ManInfoSplitPane, null);
		
		//在tab面板中添加显示管理员信息列表的面板
		final JPanel ManInfoTreePane = new JPanel();
		ManInfoSplitPane.setLeftComponent(ManInfoTreePane);
		ManInfoTreePane.setLayout(new BorderLayout());
		// 创建显示管理员信息树的滚动面板
		final JScrollPane ManInfoTreeScrollPane = new JScrollPane();
		// 将滚动面板添加到上级面板中
		ManInfoTreePane.add(ManInfoTreeScrollPane);
		
		// 创建管理员信息树的根节点
		ManInfoTreeRoot = new DefaultMutableTreeNode("root");
		// 初始化管理员信息树
		initTree(ManInfoTreeRoot, "ManInfo");

		// 创建管理员信息树模型
		ManInfoTreeModel = new DefaultTreeModel(ManInfoTreeRoot);

		// 创建管理员信息树
		ManInfoTree = new JTree(ManInfoTreeModel);
		// 设置管理员信息树的根节点不可见
		ManInfoTree.setRootVisible(false);
		ManInfoTree.getSelectionModel().setSelectionMode(
				// 设置管理员信息树的选择模式为单选
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// 如果管理员信息树存在子节点，则设置选中第一个子节点
		if (ManInfoTreeRoot.getChildCount() > 0)
			ManInfoTree.setSelectionRow(0);
		// 为管理员信息树添加接点选中事件监听器
		ManInfoTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						// 初始化管理员信息列表
						initManInfoListTable();
					}
				});
		// 将管理员信息树添加到滚动面板中
		ManInfoTreeScrollPane.setViewportView(ManInfoTree);
		
		final JPanel ManTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_3 = new FlowLayout();
		flowLayout_3.setVgap(0);
		flowLayout_3.setHgap(0);
		ManTreeButtonPanel.setLayout(flowLayout_3);
		ManInfoTreePane.add(ManTreeButtonPanel, BorderLayout.SOUTH);
		//在管理员信息列表树最后添加新的管理员，即此部分为添加按钮及其事件响应
		final MButton addManTypeButton = new MButton();
		addManTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得新管理员名称
				String name = addTreeNode(ManInfoTreeRoot, "管理员");
				// 当取消新建时名称为空
				if (name != null) {
					// 获得当前拥有管理员的数量
					int childCount = ManInfoTreeRoot.getChildCount();
					// 在管理员列表树的最后创建新的管理员
					ManInfoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), ManInfoTreeRoot, childCount);
					// 刷新管理员树模型
					ManInfoTreeModel.reload();
					// 设置新建管理员为选中状态
					ManInfoTree.setSelectionRow(childCount);
             		// 将新建管理员保存到数据库中
//					dao.iType(name, "card");
				}
			}
		});
		//把按钮换成自定义图片
		URL ManInfoTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addManTypeButton.setIcon(new ImageIcon(ManInfoTypeUrl));
		URL ManTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addManTypeButton.setRolloverIcon(new ImageIcon(ManTypeOverUrl));
		ManTreeButtonPanel.add(addManTypeButton);
		
		//删除管理员按钮及其响应事件
		final MButton delManTypeButton = new MButton();
		delManTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得选中的管理员对象
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) ManInfoTree
						.getLastSelectedPathComponent();
				// 未选择要删除的管理员
				if (treeNode == null) {
					// 弹出提示信息
					JOptionPane.showMessageDialog(null, "请选择要删除的管理员！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					// 并直接返回
					return;
				}
				// 获得欲删除管理员的名称
				String name = treeNode.getUserObject().toString();
				// 弹出删除的确认提示
				int i = JOptionPane.showConfirmDialog(null, "确定要删除管理员“" + name
						+ "”？", "友情提示", JOptionPane.YES_NO_OPTION);
				// 取消了删除操作
				if (i != 0)
					// 直接返回
					return;
			    
				// 该用户列表中包含用户
//				if (dao.sPersonnelVByTypeName(name).size() > 0) {
//				// 定义对其包含名片的处理方式	
//				String options[] = { "取消", "移入其他名片夹", "删除" };
//				// 弹出提示信息，令用户选择提供的处理方式	
//				int optionIndex = JOptionPane.showOptionDialog(null,
//							"请选择对该名片夹下人员的处理方式：", "友情提示",
//							JOptionPane.YES_NO_CANCEL_OPTION,
//							JOptionPane.QUESTION_MESSAGE, null, options,
//							options[0]);
//				// 用户取消了删除操作	
//				if (optionIndex == 0)
//				// 直接返回		
//				return;
//				// 获得名片夹的主键ID	
//				int typeId = dao.sTypeIdByUsedAndName("card", name);
//				// 用户选择移入其他名片夹	
//				if (optionIndex == 1) {
//				// 查询可移入的名片夹		
//				Vector<Vector> cardV = dao.sTypeByUsedExcept("card",
//								typeId);
//				// 创建一个选择项数组		
//				String[] cards = new String[cardV.size() + 1];
//				// 添加一个提示选择项		
//				cards[0] = "请选择";
//						for (int j = 0; j < cardV.size(); j++) {// 初始化选择项数组
//							cards[j + 1] = cardV.get(j).get(2).toString();// 添加可移入的名片夹
//						}
//						Object card = "请选择";// 默认为选中“请选择”
//						while (card.equals("请选择")) {// 当选中的为“请选择”时执行循环
//							card = JOptionPane.showInputDialog(null,
//									"请选择要移入的名片夹：", "友情提示",
//									JOptionPane.INFORMATION_MESSAGE, null,
//									cards, cards[0]);// 弹出对话框令用户选择欲移入的名片夹
//							if (card == null)// 用户取消了删除操作
//								return;// 直接返回
//						}
//						int newTypeId = dao.sTypeIdByUsedAndName("card", card
//								.toString());// 获得欲移入名片夹的主键ID
//						dao.uPersonnelTypeIdByTypeId(typeId, newTypeId);// 修改名片的外键
//					}
//					if (optionIndex == 2) {// 用户选择删除其包含的名片
//						dao.dPersonnelByTypeId(typeId);// 从数据库删除其包含的名片
//					}
//				}
				
				// 从管理员树中删除管理员
				ManInfoTreeModel.removeNodeFromParent(treeNode);
//				// 从数据库中删除管理员
//				dao.dTypeByName("card", name);
			}
		});
		//把按钮换成自定义图片
		URL delManTypeUrl = this.getClass().getResource("/img/del_tree.png");
		delManTypeButton.setIcon(new ImageIcon(delManTypeUrl));
		URL delManTypeOverUrl = this.getClass().getResource(
				"/img/del_tree_over.png");
		delManTypeButton.setRolloverIcon(new ImageIcon(delManTypeOverUrl));
		ManTreeButtonPanel.add(delManTypeButton);
//------------------------------------------------------------------------------
		
		//显示选中管理员信息部分及操作按钮
		final JPanel ManInfoListPanel = new JPanel();
		ManInfoSplitPane.setRightComponent(ManInfoListPanel);
		ManInfoListPanel.setLayout(new BorderLayout());

		final JScrollPane ManInfoListScrollPane = new JScrollPane();
		ManInfoListPanel.add(ManInfoListScrollPane);

		ManInfoListTableColumnV = new Vector<String>();
		String ManInfoListTableColumns[] = {"id","姓名", "上次登录ip"};
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
		
		//添加选中管理员信息按钮及其响应事件
		final MButton addManButton = new MButton();
		URL addManUrl = this.getClass().getResource("/img/add_info.png");
		addManButton.setIcon(new ImageIcon(addManUrl));
		URL addManOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addManButton.setRolloverIcon(new ImageIcon(addManOverUrl));
		addManButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 判断是否存在管理员
				if (ManInfoTreeRoot.getChildCount() == 0) {
					// 弹出建立管理员的提示
					JOptionPane.showMessageDialog(null, "请先新建管理员！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 获得当前选中的管理员
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) ManInfoTree
							.getLastSelectedPathComponent();
					// 获得管理员名称
					String ManName = treeNode.getUserObject().toString();
					// 创建添加管理员信息的对话框对象
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"添加管理员信息", ManName, -1);
					// 设置添加管理员信息的对话框为可见
					personnelDialog.setVisible(true);
					// 刷新管理员信息列表
					initManInfoListTable();
				}
			}
		});
		ManInfoButtonPanel.add(addManButton);
		
		//修改管理员信息按钮及其响应事件
		final MButton updManInfoButton = new MButton();
		URL updManInfoUrl = this.getClass().getResource("/img/upd_info.png");
		updManInfoButton.setIcon(new ImageIcon(updManInfoUrl));
		URL updManInfoOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updManInfoButton.setRolloverIcon(new ImageIcon(updManInfoOverUrl));
		updManInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得管理员详细信息列表中的选中行
				int[] selectedRows = ManInfoListTable.getSelectedRows();
				// 仅选中了一个管理员
				if (selectedRows.length == 1) {
					// 获得选中管理员的id
					int id = (Integer) ManInfoListTable.getValueAt(
							selectedRows[0], 1);
					// 创建修改管理员信息的对话框对象
					Manager_PersonnelDialog personnelDialog = new Manager_PersonnelDialog(
							"修改管理员信息", "", id);
					// 设置修改管理员信息的对话框为可见
					personnelDialog.setVisible(true);
					initManInfoListTable();
				} else {
					// 未选中要修改的管理员
					if (selectedRows.length == 0) {
						// 弹出提示信息
						JOptionPane.showMessageDialog(null, "请选择要修改的管理员！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);
					} 
					// 选中了多个管理员
					else {
						// 弹出提示信息
						JOptionPane.showMessageDialog(null, "一次只能修改一个管理员！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		ManInfoButtonPanel.add(updManInfoButton);
	}
	
	//设置按钮响应事件
	private class HandsetButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Manager_SetDialog dialog = new Manager_SetDialog();
			dialog.setVisible(true);
		}
	}
	
	/**
	 * @description 初始化左边panel中树的方法
	 * @param treeRoot DefaultMutableTreeNode 树的根节点,
	 *        used String 指定树的类型
	 */
	private void initTree(DefaultMutableTreeNode treeRoot, String used) {
		_System.getServe().addWork(Init_Controller.class, "userlist_init", null, null);
//		Vector typeV = dao.sTypeByUsed(used);// 查询用于指定树的类型
//		Vector typeV = new Vector();
//		typeV.add("UserInfo");
//		// 遍历向量
//		for (int i = 0; i < typeV.size(); i++) {
//			// 获得类型向量
//			Vector type = (Vector) typeV.get(i);
//			// 将类型添加到树中
//		UserInfoTreeRoot.add(new DefaultMutableTreeNode(type.get(2)));
//		}
	}
	
	/**
	 * @description 初始化UserInfoList中信息的树的方法
	 */
	private void initUserInfoListTable() {
		
		
//		// 清空用户信息列表
//		UserInfoListTableValueV.removeAllElements();
//		// 获得用户信息树的选中节点对象
//		DefaultMutableTreeNode cardTreeNode = (DefaultMutableTreeNode) UserInfoTree
//				.getLastSelectedPathComponent();
//		// 判断是否存在选中的节点
//		if (cardTreeNode != null) {
//			// 获得选中用户信息的名称
//			String cardName = cardTreeNode.getUserObject().toString();
//			// 检索用户信息包含的信息
////			cardListTableValueV.addAll(dao.sPersonnelVByTypeName(cardName));
//		}
//		// 刷新用户列表表格模型
//		UserInfoListTableModel.setDataVector(UserInfoListTableValueV,
//				UserInfoListTableColumnV);
	}
	
	/**
	 * @description 初始化optinfolist（针对常用短语）中信息的树的方法
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
	 * @description 初始化maninfolist（针对常用短语）中信息的树的方法
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
	 * @description 获得新添加节点（用户，操作员和管理员）的名称
	 * @param treeNode DefaultMutableTreeNode 树的节点名，
	 *        typeName String 添加的类型
	 * @return String 新添加节点的名称
	 */
    public String addTreeNode(DefaultMutableTreeNode treeNode, String typeName) {
		// 创建节点名称为空字符串
		String nodeName = "";
		// 判断节点名称的长度是否为0
		while (nodeName.length() == 0) {
			// 弹出输入框输入名称
			nodeName = JOptionPane.showInputDialog(null, "请输入" + typeName
					+ "名称：", "新建" + typeName, JOptionPane.INFORMATION_MESSAGE);
			// 判断节点名称是否为空值
			if (nodeName == null) {
				// 为空值即用户取消了新建，则跳出循环
				break;
			} else {
				// 去掉首尾空格
				nodeName = nodeName.trim();
				// 判断节点名称的长度是否为0
				if (nodeName.length() > 0) {
					// 如果不为0则判断该名称是否已经存在
					if (isHad(treeNode, nodeName))
						// 如果存在则设置节点名称为空字符串
						nodeName = "";
				}
			}
		}
		// 返回节点名称
		return nodeName;
	}
    
    /**
	 * @description 验证添加的节点是否已经存在
	 * @param treeNode DefaultMutableTreeNode 树的节点名，
	 *        newChildName String 新的子节点名称
	 */
    private boolean isHad(DefaultMutableTreeNode treeNode, String newChildName) {
		// 默认为不存在
		boolean had = false;
		// 获得子节点的数量
		int childCount = treeNode.getChildCount();
		// 遍历子节点
		for (int i = 0; i < childCount; i++) {
			// 获得子节点对象
			DefaultMutableTreeNode childTreeNode = (DefaultMutableTreeNode) treeNode
					.getChildAt(i);
			// 判断名称是否相同
			if (childTreeNode.getUserObject().toString().equals(newChildName)) {
				// 弹出该名称已经存在的提示
				JOptionPane.showMessageDialog(null, "该名称已经存在！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);
				// 该名称已经存在
				had = true;
				// 跳出循环，停止遍历后面的子节点
				break;
			}
		}
		return had;// 返回结果
	}
}
