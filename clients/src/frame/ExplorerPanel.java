package frame;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import MOB.app.Controller.Message_Controller;
import MOB.sys.Thread.ReceiveMessage_Thread;

//import com.mwq.dao.Dao;
import explorer.InfoDialog;
import explorer.PersonnelDialog;
import mpd.app.mwing.MButton;
import mpd.app.mwing.MTable;



import java.io.File;
import jxl.Workbook;
import jxl.write.WritableSheet;

public class ExplorerPanel extends JPanel {

	private MTable msgListTable;
	private Vector<String> msgListTableColumnV;
	private Vector<Vector> msgListTableValueV;
	private DefaultTableModel msgListTableModel;
	private JTree msgTree;
	private DefaultMutableTreeNode msgTreeRoot;
	private DefaultTreeModel msgTreeModel;

    private MTable infoListTable;
	private Vector<String> infoListTableColumnV;
	private Vector<Vector> infoListTableValueV;
	private DefaultTableModel infoListTableModel;
	private JTree infoTree;
	private DefaultMutableTreeNode infoTreeRoot;
	private DefaultTreeModel infoTreeModel;

    private MTable cardListTable;
	private Vector<String> cardListTableColumnV;
	private Vector<Vector> cardListTableValueV;
	private DefaultTableModel cardListTableModel;
	private JTree cardTree;
	private DefaultMutableTreeNode cardTreeRoot;
	private DefaultTreeModel cardTreeModel;

    public JPanel msgListPanel = null;
    public JPanel msgTreeButtonPanel = null;
//	private final Dao dao = Dao.getInstance();
	/**
	 * @description Create the panel
	 * @param sendListTableModel DefaultTableModel 发送列表模型,
	 *        infoTabbedPane JTabbedPane 短信板的tabbedpane,
	 *        infoTextArea JTextArea 短信的区域
	 */
	public ExplorerPanel(final DefaultTableModel sendListTableModel,
			final JTabbedPane infoTabbedPane,final JTextArea infoTextArea) {
		super();
		setLayout(new BorderLayout());

		final JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
        
//---------------------------------------------------------------------------
		//这里开始是名片的panel
		final JSplitPane cardSplitPane = new JSplitPane();
		cardSplitPane.setOneTouchExpandable(true);
		cardSplitPane.setDividerSize(12);
		cardSplitPane.setDividerLocation(244);
		tabbedPane.addTab("名片夹", null, cardSplitPane, null);

		final JPanel cardTreePanel = new JPanel();
		cardSplitPane.setLeftComponent(cardTreePanel);
		cardTreePanel.setLayout(new BorderLayout());

		// 创建显示名片夹树的滚动面板
		final JScrollPane cardTreeScrollPane = new JScrollPane();
		// 将滚动面板添加到上级面板中
		cardTreePanel.add(cardTreeScrollPane);

		// 创建名片夹树的根节点
		cardTreeRoot = new DefaultMutableTreeNode("root");
		// 初始化名片夹树
		initTree(cardTreeRoot, "card");

		// 创建名片夹树模型
		cardTreeModel = new DefaultTreeModel(cardTreeRoot);

		// 创建名片夹树
		cardTree = new JTree(cardTreeModel);
		// 设置名片夹树的根节点不可见
		cardTree.setRootVisible(false);
		//System.out.println(cardTree.getSelectionModel().getSelectionMode());
		cardTree.getSelectionModel().setSelectionMode(
				// 设置名片夹树的选择模式为单选
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// 如果名片夹树存在子节点，则设置选中第一个子节点
		if (cardTreeRoot.getChildCount() > 0)
			cardTree.setSelectionRow(0);
		// 为名片夹树添加接点选中事件监听器
		cardTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						// 初始化名片夹列表
						initCardListTable();
					}
				});
		// 将名片夹树添加到滚动面板中
		cardTreeScrollPane.setViewportView(cardTree);
		
		final JPanel cardTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		cardTreeButtonPanel.setLayout(flowLayout_1);
		cardTreePanel.add(cardTreeButtonPanel, BorderLayout.SOUTH);
//---------------------------------------------------------------------------
		//在名片夹树最后添加新的名片夹，即此部分为添加按钮及其事件响应
		final MButton addCardTypeButton = new MButton();
		addCardTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得新名片夹名称
				String name = addTreeNode(cardTreeRoot, "名片夹");
				// 当用户取消新建时名称为空
				if (name != null) {
					// 获得当前拥有名片夹的数量
					int childCount = cardTreeRoot.getChildCount();
					// 在名片夹树的最后创建新的名片夹
					cardTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), cardTreeRoot, childCount);
					// 刷新名片夹树模型
					cardTreeModel.reload();
					// 设置新建名片夹为选中状态
					cardTree.setSelectionRow(childCount);
             		// 将新建名片夹保存到数据库中
//					dao.iType(name, "card");
				}
			}
		});
		//把按钮换成自定义图片
		URL creCardTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addCardTypeButton.setIcon(new ImageIcon(creCardTypeUrl));
		URL creCardTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addCardTypeButton.setRolloverIcon(new ImageIcon(creCardTypeOverUrl));
		cardTreeButtonPanel.add(addCardTypeButton);
//---------------------------------------------------------------------------		
		//修改名片夹的名称，即此部分为修改按钮及其事件响应
		final MButton updCardTypeButton = new MButton();
		updCardTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得选中节点的路径对象
				TreePath selectionPath = cardTree.getSelectionPath();
				// 判断路径是否为空
				if (selectionPath == null) {
					// 如果为空则弹出提示
					JOptionPane.showMessageDialog(null, "请选择要修改的名片夹！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 获得选中节点对象
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selectionPath
							.getLastPathComponent();
					// 获得选中节点的名称
					String nowName = treeNode.getUserObject().toString();
					// 弹出确认修改提示框
					int i = JOptionPane.showConfirmDialog(null, "确定要修改名片夹“"
							+ nowName + "”的名称？", "友情提示",
							JOptionPane.YES_NO_OPTION);
					// 如果为0则修改
					if (i == 0) {
						// 获得修改后的名称
						String newName = updateTreeNode(treeNode);
						// 判断修改后的名称是否为空，如果为空则用户取消了修改
						if (newName != null) {
							// 修改节点名称
							treeNode.setUserObject(newName);
							// 刷新树
							cardTreeModel.reload();
							// 设置修改的节点为选中节点
							cardTree.setSelectionPath(selectionPath);
							// 将修改后的名称保存到数据库
//							dao.uTypeNameByName("card", nowName, newName);
						}
					}
				}
			}
		});
		//把按钮换成自定义图片
		URL updCardTypeUrl = this.getClass().getResource("/img/upd_tree.png");
		updCardTypeButton.setIcon(new ImageIcon(updCardTypeUrl));
		URL updCardTypeOverUrl = this.getClass().getResource(
				"/img/upd_tree_over.png");
		updCardTypeButton.setRolloverIcon(new ImageIcon(updCardTypeOverUrl));
		cardTreeButtonPanel.add(updCardTypeButton);
//---------------------------------------------------------------------------
		//删除名片夹，即此部分为删除按钮及其响应事件
		final MButton delCardTypeButton = new MButton();
		delCardTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得选中的名片夹对象
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) cardTree
						.getLastSelectedPathComponent();
				// 未选择要删除的名片夹
				if (treeNode == null) {
					// 弹出提示信息
					JOptionPane.showMessageDialog(null, "请选择要删除的名片夹！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					// 并直接返回
					return;
				}
				// 获得欲删除名片夹的名称
				String name = treeNode.getUserObject().toString();
				// 弹出删除的确认提示
				int i = JOptionPane.showConfirmDialog(null, "确定要删除名片夹“" + name
						+ "”？", "友情提示", JOptionPane.YES_NO_OPTION);
				// 用户取消了删除操作
				if (i != 0)
					// 直接返回
					return;
			    
				// 该名片夹中包含名片
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
				
				// 从名片夹树中删除名片夹
				cardTreeModel.removeNodeFromParent(treeNode);
//				// 从数据库中删除名片夹
//				dao.dTypeByName("card", name);
			}
		});
		//把按钮换成自定义图片
		URL delCardTypeUrl = this.getClass().getResource("/img/del_tree.png");
		delCardTypeButton.setIcon(new ImageIcon(delCardTypeUrl));
		URL delCardTypeOverUrl = this.getClass().getResource(
				"/img/del_tree_over.png");
		delCardTypeButton.setRolloverIcon(new ImageIcon(delCardTypeOverUrl));
		cardTreeButtonPanel.add(delCardTypeButton);
//---------------------------------------------------------------------------
		//此部分为名片夹信息列表及操作按钮面板
		final JPanel cardListPanel = new JPanel();
		cardSplitPane.setRightComponent(cardListPanel);
		cardListPanel.setLayout(new BorderLayout());

		final JScrollPane cardListScrollPane = new JScrollPane();
		cardListPanel.add(cardListScrollPane);

		cardListTableColumnV = new Vector<String>();
		String cardListTableColumns[] = {"姓名", "性别", "移动电话"};
		for (int i = 0; i < cardListTableColumns.length; i++) {
			cardListTableColumnV.add(cardListTableColumns[i]);
		}

		cardListTableValueV = new Vector<Vector>();

		cardListTableModel = new DefaultTableModel(cardListTableValueV,
				cardListTableColumnV);

		cardListTable = new MTable(cardListTableModel);
		initCardListTable();
		cardListScrollPane.setViewportView(cardListTable);

		final JPanel cardButtonPanel = new JPanel();
		cardButtonPanel.setLayout(new BoxLayout(cardButtonPanel,
				BoxLayout.Y_AXIS));
		cardListPanel.add(cardButtonPanel, BorderLayout.EAST);

		//全选按钮及其响应事件
		final MButton selAllButton = new MButton();
		URL selAllUrl = this.getClass().getResource("/img/select_all.png");
		selAllButton.setIcon(new ImageIcon(selAllUrl));
		URL selAllOverUrl = this.getClass().getResource(
				"/img/select_all_over.png");
		selAllButton.setRolloverIcon(new ImageIcon(selAllOverUrl));
		selAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 选中表格中的所有行
				cardListTable.selectAll();
			}
		});
		cardButtonPanel.add(selAllButton);

		//加入列表按钮及其响应事件
		final MButton addToSendListButton = new MButton();
		URL addToSendListUrl = this.getClass().getResource(
				"/img/add_to_list.png");
		addToSendListButton.setIcon(new ImageIcon(addToSendListUrl));
		URL addToSendListOverUrl = this.getClass().getResource(
				"/img/add_to_list_over.png");
		addToSendListButton
				.setRolloverIcon(new ImageIcon(addToSendListOverUrl));
		addToSendListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得当前收信人列表中的收信人个数
				int rowCount = sendListTableModel.getRowCount();
				// 获得名片列表中的选中行
				int[] selectedRows = cardListTable.getSelectedRows();
				// 初始化收信人列表的序号
				int index = rowCount + 1;
				// 遍历选中行
				for (int selectedRow = 0; selectedRow < selectedRows.length; selectedRow++) {
					// 获得名片电话
					String newPhone = cardListTable.getValueAt(
							selectedRows[selectedRow], 8).toString();
					// 默认为未加入收信人列表
					boolean had = false;
					// 遍历收信人列表
					for (int row = 0; row < rowCount; row++) {
						// 获得收信人的电话
						String nowPhone = sendListTableModel.getValueAt(
								row, 2).toString();
						// 判断名片编号和收信人编号是否相同
						if (newPhone.equals(nowPhone) ) {
							// 已经加入收信人列表
							had = true;
							break;
						}
					}
					// 未加入收信人列表
					if (!had) {
						// 创建一个代表收信人的向量
						Vector rowV = new Vector();
						// 添加序号
						rowV.add(index++);
						// 添加编号
						//rowV.add(newNum);
						// 添加姓名
						rowV.add(cardListTable.getValueAt(
								selectedRows[selectedRow], 2));
						// 添加号码
						rowV.add(cardListTable.getValueAt(
								selectedRows[selectedRow], 8));
						//加入收信人列表
						sendListTableModel.addRow(rowV);
					}
				}
				// 取消名片列表中的选中行
				cardListTable.clearSelection();
			}
		});
		cardButtonPanel.add(addToSendListButton);

		//添加名片信息进名片夹按钮及其响应事件
		final MButton addCardButton = new MButton();
		URL addCardUrl = this.getClass().getResource("/img/add_info.png");
		addCardButton.setIcon(new ImageIcon(addCardUrl));
		URL addCardOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addCardButton.setRolloverIcon(new ImageIcon(addCardOverUrl));
		addCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 判断是否存在名片夹
				if (cardTreeRoot.getChildCount() == 0) {
					// 弹出建立名片夹的提示
					JOptionPane.showMessageDialog(null, "请先建立名片夹！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 获得当前选中的名片夹
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) cardTree
							.getLastSelectedPathComponent();
					// 获得名片夹名称
					String cardName = treeNode.getUserObject().toString();
					// 创建添加名片的对话框对象
					PersonnelDialog personnelDialog = new PersonnelDialog(
							"添加名片", cardName, -1);
					// 设置添加名片的对话框为可见
					personnelDialog.setVisible(true);
					// 刷新名片列表
					initCardListTable();
				}
			}
		});
		cardButtonPanel.add(addCardButton);

		//修改名片信息按钮及其响应事件
		final MButton updCardButton = new MButton();
		URL updCardUrl = this.getClass().getResource("/img/upd_info.png");
		updCardButton.setIcon(new ImageIcon(updCardUrl));
		URL updCardOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updCardButton.setRolloverIcon(new ImageIcon(updCardOverUrl));
		updCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得名片列表中的选中行
				int[] selectedRows = cardListTable.getSelectedRows();
				// 仅选中了一个名片
				if (selectedRows.length == 1) {
					// 获得选中名片的编号
					int num = (Integer) cardListTable.getValueAt(
							selectedRows[0], 1);
					// 创建修改名片的对话框对象
					PersonnelDialog personnelDialog = new PersonnelDialog(
							"修改名片", "", num);
					// 设置修改名片的对话框为可见
					personnelDialog.setVisible(true);
					initCardListTable();
				} else {
					// 未选中要修改的名片
					if (selectedRows.length == 0) {
						// 弹出提示信息
						JOptionPane.showMessageDialog(null, "请选择要修改的人员！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);
					} 
					// 选中了多个名片
					else {
						// 弹出提示信息
						JOptionPane.showMessageDialog(null, "一次只能修改一个人员！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		cardButtonPanel.add(updCardButton);

		//删除名片信息按钮及其响应事件
		final MButton delCardButton = new MButton();
		URL delCardUrl = this.getClass().getResource("/img/del_info.png");
		delCardButton.setIcon(new ImageIcon(delCardUrl));
		URL delCardOverUrl = this.getClass().getResource(
				"/img/del_info_over.png");
		delCardButton.setRolloverIcon(new ImageIcon(delCardOverUrl));
		delCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得名片列表中的选中行
				int[] selectedRows = cardListTable.getSelectedRows();
				// 未选中要删除的名片
				if (selectedRows.length == 0) {
					// 弹出提示信息
					JOptionPane.showMessageDialog(null, "请选择要删除的人员！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 组织提示信息
					String[] infos = new String[selectedRows.length + 1];
					// 添加提示信息
					infos[0] = "确定要删除以下人员：";
					// 遍历选中的名片
					for (int i = 0; i < selectedRows.length; i++) {
						infos[i + 1] = "    "
								+ cardListTable.getValueAt(selectedRows[i], 1)
								+ "  "
								+ cardListTable.getValueAt(selectedRows[i], 2);
					}
					// 弹出提示信息
					int i = JOptionPane.showConfirmDialog(null, infos, "友情提示",
							JOptionPane.YES_NO_OPTION);
					// 确定删除
					if (i == 0) {
						// 遍历选中的名片
						for (int j = 0; j < selectedRows.length; j++) {
							// 获得名片编号
							int num = (Integer) cardListTable.getValueAt(
									selectedRows[j], 1);
//							// 从数据库删除
//							dao.dPersonnelByNum(num);
						}
						// 刷新名片列表
						initCardListTable();
					}
				}
			}
		});
		cardButtonPanel.add(delCardButton);
//--------------------------------------------------------------------------------
		//这里开始为常用短语panel
		final JSplitPane infoSplitPane = new JSplitPane();
		infoSplitPane.setOneTouchExpandable(true);
		infoSplitPane.setDividerSize(12);
		infoSplitPane.setDividerLocation(244);
		tabbedPane.addTab("常用短语", null, infoSplitPane, null);

		final JPanel infoTreePanel = new JPanel();
		infoSplitPane.setLeftComponent(infoTreePanel);
		infoTreePanel.setLayout(new BorderLayout());

		final JScrollPane infoTreeScrollPane = new JScrollPane();
		infoTreePanel.add(infoTreeScrollPane);

		infoTreeRoot = new DefaultMutableTreeNode("root");
		initTree(infoTreeRoot, "info");

		infoTreeModel = new DefaultTreeModel(infoTreeRoot);

		infoTree = new JTree(infoTreeModel);
		infoTree.setRootVisible(false);
		if (infoTreeRoot.getChildCount() > 0)
			infoTree.setSelectionRow(0);
		infoTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				initInfoListTable();
			}
		});
		infoTreeScrollPane.setViewportView(infoTree);

	    
		final JPanel infoTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		infoTreeButtonPanel.setLayout(flowLayout);
		infoTreePanel.add(infoTreeButtonPanel, BorderLayout.SOUTH);

		//新建常用短语信息库按钮及其响应事件
		final MButton addInfoTypeButton = new MButton();
		addInfoTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = addTreeNode(infoTreeRoot, "信息库");
				if (name != null) {
					int childCount = infoTreeRoot.getChildCount();
					infoTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), infoTreeRoot, childCount);
					infoTreeModel.reload();
					infoTree.setSelectionRow(childCount);
//					dao.iType(name, "info");
				}
			}
		});
		URL creInfoTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addInfoTypeButton.setIcon(new ImageIcon(creInfoTypeUrl));
		URL creInfoTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addInfoTypeButton.setRolloverIcon(new ImageIcon(creInfoTypeOverUrl));
		infoTreeButtonPanel.add(addInfoTypeButton);

		//修改常用短语信息库名称按钮及其响应事件
		final MButton updInfoTypeButton = new MButton();
		updInfoTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath selectionPath = infoTree.getSelectionPath();
				if (selectionPath == null) {
					JOptionPane.showMessageDialog(null, "请选择要修改的信息库！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selectionPath
							.getLastPathComponent();
					String nowName = treeNode.getUserObject().toString();
					int i = JOptionPane.showConfirmDialog(null, "确定要修改信息库“"
							+ nowName + "”的名称？", "友情提示",
							JOptionPane.YES_NO_OPTION);
					if (i == 0) {
						String newName = updateTreeNode(treeNode);
						if (newName != null) {
							treeNode.setUserObject(newName);
							infoTreeModel.reload();
							infoTree.setSelectionPath(selectionPath);
//							dao.uTypeNameByName("info", nowName, newName);
						}
					}
				}
			}
		});
		URL updInfoTypeUrl = this.getClass().getResource("/img/upd_tree.png");
		updInfoTypeButton.setIcon(new ImageIcon(updInfoTypeUrl));
		URL updInfoTypeOverUrl = this.getClass().getResource(
				"/img/upd_tree_over.png");
		updInfoTypeButton.setRolloverIcon(new ImageIcon(updInfoTypeOverUrl));
		infoTreeButtonPanel.add(updInfoTypeButton);

//		final MButton delInfoTypeButton = new MButton();
//		delInfoTypeButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) infoTree
//						.getLastSelectedPathComponent();
//				// 未选择要删除的信息库
//				if (treeNode == null) {
//					JOptionPane.showMessageDialog(null, "请选择要删除的信息库！", "友情提示",
//							JOptionPane.INFORMATION_MESSAGE);
//					return;
//				}
//				String name = treeNode.getUserObject().toString();
//				int i = JOptionPane.showConfirmDialog(null, "确定要删除信息库“" + name
//						+ "”？", "友情提示", JOptionPane.YES_NO_OPTION);
//				// 用户取消了删除操作
//				if (i != 0)
//					return;
////				if (dao.sInfoVByTypeName(name).size() > 0) {
////					String options[] = { "取消", "移入其他信息库", "删除" };
////					int optionIndex = JOptionPane.showOptionDialog(null,
////							"请选择对该信息库下信息的处理方式：", "友情提示",
////							JOptionPane.YES_NO_CANCEL_OPTION,
////							JOptionPane.QUESTION_MESSAGE, null, options,
////							options[0]);
////					int typeId = dao.sTypeIdByUsedAndName("info", name);
////					if (optionIndex == 0)// 用户取消了删除操作
////						return;
////					if (optionIndex == 1) {// 移入其他信息库
////						Vector infoV = dao.sTypeByUsedExcept("info", typeId);
////						String[] infos = new String[infoV.size() + 1];
////						infos[0] = "请选择";
////						for (int j = 0; j < infoV.size(); j++) {
////							infos[j + 1] = ((Vector) infoV.get(j)).get(2)
////									.toString();
////						}
////						Object info = "请选择";
////						while (info.equals("请选择")) {
////							info = JOptionPane.showInputDialog(null,
////									"请选择要移入的信息库：", "友情提示",
////									JOptionPane.INFORMATION_MESSAGE, null,
////									infos, infos[0]);
////							if (info == null)// 用户取消了删除操作
////								return;
////						}
////						int newTypeId = dao.sTypeIdByUsedAndName("info", info
////								.toString());
////						dao.uPersonnelTypeIdByTypeId(typeId, newTypeId);
////					}
////					if (optionIndex == 2) {// 删除
////						dao.dPersonnelByTypeId(typeId);
////					}
////				}
//				DefaultMutableTreeNode selectedNode = treeNode.getNextNode();
//				if (selectedNode == null)
//					selectedNode = treeNode.getPreviousNode();
//				infoTreeModel.removeNodeFromParent(treeNode);
//				infoTree.setSelectionRow(selectedNode.getDepth());
////				dao.dTypeByName("info", name);
//			}
//		});
//		URL delInfoTypeUrl = this.getClass().getResource("/img/del_tree.png");
//		delInfoTypeButton.setIcon(new ImageIcon(delInfoTypeUrl));
//		URL delInfoTypeOverUrl = this.getClass().getResource(
//				"/img/del_tree_over.png");
//		delInfoTypeButton.setRolloverIcon(new ImageIcon(delInfoTypeOverUrl));
//		infoTreeButtonPanel.add(delInfoTypeButton);

		//常用短语内容面板
		final JPanel infoListPanel = new JPanel();
		infoSplitPane.setRightComponent(infoListPanel);
		infoListPanel.setLayout(new BorderLayout());

		final JScrollPane infoListScrollPane = new JScrollPane();
		infoListPanel.add(infoListScrollPane);

		infoListTableColumnV = new Vector<String>();
		infoListTableColumnV.add("序号");
		infoListTableColumnV.add("编号");
		infoListTableColumnV.add("信息内容");

		infoListTableValueV = new Vector<Vector>();

		infoListTableModel = new DefaultTableModel(infoListTableValueV,
				infoListTableColumnV);

		infoListTable = new MTable(infoListTableModel);
		infoListTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		initInfoListTable();
		infoListScrollPane.setViewportView(infoListTable);

		//编辑常用短语内容按钮面板
		final JPanel infoButtonPanel = new JPanel();
		infoButtonPanel.setLayout(new BoxLayout(infoButtonPanel,
				BoxLayout.Y_AXIS));
		infoListPanel.add(infoButtonPanel, BorderLayout.EAST);

		//编辑内容按钮及其响应事件
		final MButton addToSendInfoButton = new MButton();
		URL addToSendInfoUrl = this.getClass().getResource(
				"/img/add_to_info.png");
		addToSendInfoButton.setIcon(new ImageIcon(addToSendInfoUrl));
		URL addToSendInfoOverUrl = this.getClass().getResource(
				"/img/add_to_info_over.png");
		addToSendInfoButton
				.setRolloverIcon(new ImageIcon(addToSendInfoOverUrl));
		addToSendInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得信息列表的选中行
				int selectedRow = infoListTable.getSelectedRow();
				// 未选择任何行
				if (selectedRow < 0) {
					// 弹出提示信息
					JOptionPane.showMessageDialog(null, "请选择要编辑的信息！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 获得信息内容
					String info = infoListTable.getValueAt(selectedRow, 2).toString();
					// 当前被选中的是“短信内容”面板
					//if (infoTabbedPane.getSelectedIndex() == 0)
					// 添加信息到短信内容文本区域	
//					infoTextArea.setText(info);
					// 当前被选中的是“E-mail内容”面板
					//else
					// 添加信息到E-mail内容文本区域
					// emailTextArea.setText(info);
				}
			}
		});
		infoButtonPanel.add(addToSendInfoButton);

		//添加内容按钮及其响应事件
		final MButton addInfoButton = new MButton();
		URL addInfoUrl = this.getClass().getResource("/img/add_info.png");
		addInfoButton.setIcon(new ImageIcon(addInfoUrl));
		URL addInfoOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addInfoButton.setRolloverIcon(new ImageIcon(addInfoOverUrl));
		addInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) infoTree
						.getLastSelectedPathComponent();
				InfoDialog infoDialog = new InfoDialog("添加信息", treeNode
						.getUserObject().toString(), -1, null);
				infoDialog.setVisible(true);
				initInfoListTable();
			}
		});
		infoButtonPanel.add(addInfoButton);

		//修改内容按钮及其响应事件
		final MButton updInfoButton = new MButton();
		URL updInfoUrl = this.getClass().getResource("/img/upd_info.png");
		updInfoButton.setIcon(new ImageIcon(updInfoUrl));
		URL updInfoOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updInfoButton.setRolloverIcon(new ImageIcon(updInfoOverUrl));
		updInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = infoListTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "请选择要修改的信息！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) infoTree
							.getLastSelectedPathComponent();
					int num = (Integer) infoListTable
							.getValueAt(selectedRow, 1);
					String content = infoListTable.getValueAt(selectedRow, 2)
							.toString();
					InfoDialog infoDialog = new InfoDialog("修改信息", treeNode
							.getUserObject().toString(), num, content);
					infoDialog.setVisible(true);
					initInfoListTable();
				}
			}
		});
		infoButtonPanel.add(updInfoButton);

		//删除内容信息及其响应事件
		final MButton delInfoButton = new MButton();
		URL delInfoUrl = this.getClass().getResource("/img/del_info.png");
		delInfoButton.setIcon(new ImageIcon(delInfoUrl));
		URL delInfoOverUrl = this.getClass().getResource(
				"/img/del_info_over.png");
		delInfoButton.setRolloverIcon(new ImageIcon(delInfoOverUrl));
		delInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = infoListTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "请选择要删除的信息！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int num = (Integer) infoListTable
							.getValueAt(selectedRow, 1);
					int i = JOptionPane.showConfirmDialog(null, "确定要删除信息" + num
							+ "？", "友情提示", JOptionPane.YES_NO_OPTION);
					if (i == 0) {
//						dao.dInfoByNum(num);
						initInfoListTable();
					}
				}
			}
		});
		infoButtonPanel.add(delInfoButton);
//------------------------------------------------------------------------
		//这里开始为短信箱panel
		final JSplitPane msgSplitPane = new JSplitPane();
		msgSplitPane.setOneTouchExpandable(true);
		msgSplitPane.setDividerSize(12);
		msgSplitPane.setDividerLocation(244);
		tabbedPane.addTab("短信箱", null, msgSplitPane, null);

		final JPanel msgTreePanel = new JPanel();
		msgSplitPane.setLeftComponent(msgTreePanel);
		msgTreePanel.setLayout(new BorderLayout());

		final JScrollPane msgTreeScrollPane = new JScrollPane();
		msgTreePanel.add(msgTreeScrollPane);

		msgTreeRoot = new DefaultMutableTreeNode("root");
        msgTreeRoot.add(new DefaultMutableTreeNode("收件箱"));
        msgTreeRoot.add(new DefaultMutableTreeNode("发件箱"));
		msgTreeModel = new DefaultTreeModel(msgTreeRoot);
		msgTree = new JTree(msgTreeModel);
		msgTree.setRootVisible(false);
		if (msgTreeRoot.getChildCount() > 0)msgTree.setSelectionRow(0);
		msgTreeScrollPane.setViewportView(msgTree);

		//短信箱内容显示面板
		this.msgTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_3 = new FlowLayout();
		flowLayout_3.setVgap(0);
		flowLayout_3.setHgap(0);
		msgTreeButtonPanel.setLayout(flowLayout_3);
		msgTreePanel.add(msgTreeButtonPanel, BorderLayout.SOUTH);
        
		this.msgListPanel = new JPanel();
		msgSplitPane.setRightComponent(msgListPanel);
		msgListPanel.setLayout(new BorderLayout());

		final JScrollPane msgListScrollPane = new JScrollPane();
		msgListPanel.add(msgListScrollPane);

		msgListTableColumnV = new Vector<String>();

		msgListTableColumnV.add("手机号");
		msgListTableColumnV.add("短信内容");
		msgListTableColumnV.add("发送时间");


		msgListTableValueV = new Vector<Vector>();
		msgListTableModel = new DefaultTableModel(msgListTableValueV,
				msgListTableColumnV);
		msgListTable = new MTable(msgListTableModel);
		msgListTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		initMsgListTable();
		msgListScrollPane.setViewportView(msgListTable);

        //把四个按钮加在msgButtonPanel上；
		final JPanel msgButtonPanel = new JPanel();
        msgButtonPanel.setLayout(new BoxLayout(msgButtonPanel,BoxLayout.Y_AXIS));
		msgListPanel.add(msgButtonPanel, BorderLayout.EAST);

        //发件箱转发按钮及其响应事件
        final MButton forwMsgButton = new MButton();
		URL forwMsgUrl = this.getClass().getResource("/img/forward_msg.jpg");//刷新
		forwMsgButton.setIcon(new ImageIcon(forwMsgUrl));
		URL forwMsgOverUrl = this.getClass().getResource(
				"/img/forward_msg_over.jpg");
		forwMsgButton.setRolloverIcon(new ImageIcon(forwMsgOverUrl));
		forwMsgButton.addActionListener(new ActionListener() {
			//把当前信息放在短信内容里
			public void actionPerformed(ActionEvent e) {
				// 获得信息列表的选中行
				int selectedRow = msgListTable.getSelectedRow();
				// 未选择任何行
				if (selectedRow < 0) {
					// 弹出提示信息
					JOptionPane.showMessageDialog(null, "请选择要转发的信息！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 获得信息内容
					String info = msgListTable.getValueAt(selectedRow, 5).toString();
					// 添加信息到短信内容文本区域
					infoTextArea.setText(info);
                }
			}
		});
        forwMsgButton.setVisible(false);
		msgButtonPanel.add(forwMsgButton);

        //收件箱短信回复的按钮及其响应事件
		final MButton replyMsgButton = new MButton();
		//短信转发
		URL replyMsgUrl = this.getClass().getResource(
				"/img/reply_msg.jpg");
		replyMsgButton.setIcon(new ImageIcon(replyMsgUrl));
		URL replyMsgOverUrl = this.getClass().getResource(
				"/img/reply_msg_over.jpg");
		replyMsgButton
				.setRolloverIcon(new ImageIcon(replyMsgOverUrl));
		replyMsgButton.addActionListener(new ActionListener() {
			//得到那个这条记录的手机号，把他加入收信人列表
			public void actionPerformed(ActionEvent e) {   
				// 获得信息列表的选中行
				int selectedRow = msgListTable.getSelectedRow();
				// 未选择任何行
				if (selectedRow < 0) {
					// 弹出提示信息
					JOptionPane.showMessageDialog(null, "请选择要回复的联系人！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 获得当前收信人列表中的收信人个数
					int rowCount = sendListTableModel.getRowCount();
					//当收信人不为空时，清空收信人列表的询问
					if(rowCount > 0)
					{
						JOptionPane.showMessageDialog(null, "收信人不为空，请先清空收信人列表！", 
								"友情提示",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// 创建一个代表收信人的向量
					String info = msgListTable.getValueAt(selectedRow,4).toString();// 获得回复信息手机号
					Vector rowV = new Vector();
					// 添加序号
					rowV.add(1);
					// 添加姓名
					rowV.add(msgListTable.getValueAt(selectedRow,3));
					// 添加手机号
					rowV.add(msgListTable.getValueAt(selectedRow,4));
					// 加入收信人列表
					sendListTableModel.addRow(rowV);
                }
            }
		});
//        replyMsgButton.setVisible(false);
		msgButtonPanel.add(replyMsgButton);

		//删除单个信息的按钮及其响应事件（收发共用）
		final MButton delMsgButton = new MButton();
		URL delMsgUrl = this.getClass().getResource("/img/del_info.png");
		delMsgButton.setIcon(new ImageIcon(delMsgUrl));
		URL delMsgOverUrl = this.getClass().getResource(
				"/img/del_info_over.png");
		delMsgButton.setRolloverIcon(new ImageIcon(delMsgOverUrl));
          delMsgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = msgListTable.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "请选择要删除的信息！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int num = (Integer) msgListTable.getValueAt(selectedRow, 1);
					int i = JOptionPane.showConfirmDialog(null, "确定要删除信息" + num
							+ "？", "友情提示", JOptionPane.YES_NO_OPTION);
					if (i == 0) {
//						dao.dMsgByNum(num);
						initMsgListTable();
					}
				}
			}
		});
		msgButtonPanel.add(delMsgButton);

		//清空的按钮及其响应事件（收发共用）
		final MButton claMsgButton = new MButton();
		URL claMsgUrl = this.getClass().getResource("/img/cancel_msg.png");
		claMsgButton.setIcon(new ImageIcon(claMsgUrl));
		URL claMsgOverUrl = this.getClass().getResource(
				"/img/cancel_msg_over.png");
		claMsgButton.setRolloverIcon(new ImageIcon(claMsgOverUrl));
		claMsgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "确定要清空该短信箱?",
						"友情提示", JOptionPane.YES_NO_OPTION);
                				if (i == 0) {
                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) msgTree.getLastSelectedPathComponent();
                 // 获得欲删除短信箱的名称    
                    String name = treeNode.getUserObject().toString();
//                        int typeId = dao.sTypeIdByUsedAndName("msg", name);
//                    	dao.dMsgByTypeId(typeId);
                        msgListTableValueV.removeAllElements();
                        initMsgListTable();
                }
			}
		});
		msgButtonPanel.add(claMsgButton);
        
        //刷新的按钮及其响应事件（收发共用）
		final MButton refMsgButton = new MButton();
		URL refMsgUrl = this.getClass().getResource("/img/refresh_msg.jpg");
		refMsgButton.setIcon(new ImageIcon(refMsgUrl));
		URL refMsgOverUrl = this.getClass().getResource(
				"/img/refresh_msg_over.jpg");
		refMsgButton.setRolloverIcon(new ImageIcon(refMsgOverUrl));
		refMsgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//这里还要运行收信的操作    
                initMsgListTable();
			}
		});
		msgButtonPanel.add(refMsgButton);

		//收件箱导出数据按钮及其响应事件
        final MButton exptMsgButton = new MButton();
        URL exptMsgUrl = this.getClass().getResource("/img/export_msg.jpg");
		exptMsgButton.setIcon(new ImageIcon(exptMsgUrl));
		URL exptMsgOverUrl = this.getClass().getResource(
				"/img/export_msg_over.jpg");
		exptMsgButton.setRolloverIcon(new ImageIcon(exptMsgOverUrl));
		exptMsgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog();
				JFileChooser jfc=new JFileChooser();//JFileChooser.SAVE_DIALOG
				jfc.setFileFilter(new FileNameExtensionFilter("*.xls", "xls"));

				if(JFileChooser.APPROVE_OPTION == jfc.showSaveDialog(jd))
				{
					String path=jfc.getSelectedFile().getPath();
					SaveFile(path.replaceAll("\\\\", "\\\\\\\\"));
					}
			}
		});
        //exptMsgButton.setVisible(false);
		msgButtonPanel.add(exptMsgButton);


//------------------------------------------------------------------------
	msgTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
                   DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) msgTree.getLastSelectedPathComponent();
                   // 获得欲删除短信箱的名称
                   String name = treeNode.getUserObject().toString();
                   if (name.equals("收件箱"))
                   {   forwMsgButton.setVisible(false);
                       //exptMsgButton.setVisible(false);
                       exptMsgButton.setVisible(true);
                       replyMsgButton.setVisible(true);
                       //refMsgButton.setVisible(true);
                   }
                   else
                   {
                       replyMsgButton.setVisible(false);
                       //refMsgButton.setVisible(false);
                       forwMsgButton.setVisible(true);
                       exptMsgButton.setVisible(false);
                   }
                initMsgListTable();
			}
		});
}
//-------------------------------------------------------------------------
	// 初始化树的方法
	
	/**
	 * @description 初始化左边panel中树的方法
	 * @param treeRoot DefaultMutableTreeNode 树的根节点,
	 *        used String 指定树的类型
	 */
	private void initTree(DefaultMutableTreeNode treeRoot, String used) {
//		Vector typeV = dao.sTypeByUsed(used);// 查询用于指定树的类型
//		for (int i = 0; i < typeV.size(); i++) {// 遍历向量
//			Vector type = (Vector) typeV.get(i);// 获得类型向量
//			treeRoot.add(new DefaultMutableTreeNode(type.get(2)));// 将类型添加到树中
//		}
	}

	/**
	 * @description 初始化cardlist中信息的树的方法
	 */
	private void initCardListTable() {
		// 清空名片列表
		cardListTableValueV.removeAllElements();
		// 获得名片夹树的选中节点对象
		DefaultMutableTreeNode cardTreeNode = (DefaultMutableTreeNode) cardTree
				.getLastSelectedPathComponent();
		// 判断是否存在选中的节点
		if (cardTreeNode != null) {
			// 获得选中名片夹的名称
			String cardName = cardTreeNode.getUserObject().toString();
			// 检索名片夹包含的名片
//			cardListTableValueV.addAll(dao.sPersonnelVByTypeName(cardName));
		}
		// 刷新名片列表表格模型
		cardListTableModel.setDataVector(cardListTableValueV,
				cardListTableColumnV);
	}

	/**
	 * @description 初始化infolist（针对常用短语）中信息的树的方法
	 */
	private void initInfoListTable() {
		infoListTableValueV.removeAllElements();
		DefaultMutableTreeNode infoTreeNode = (DefaultMutableTreeNode) infoTree
				.getLastSelectedPathComponent();
		if (infoTreeNode != null) {
			String infoName = infoTreeNode.getUserObject().toString();
//			infoListTableValueV.addAll(dao.sInfoVByTypeName(infoName));
		}
		infoListTableModel.setDataVector(infoListTableValueV,
				infoListTableColumnV);
	}
	
	/**
	 * @description 接收短信内容的方法
	 * @param m String[] 接收的短信内容
	 * @access public
	 */
    public void insertMsgListTable(String[] m) {
    	msgListTableColumnV = new Vector<String>();
        msgListTableModel.addRow(m);
	}
    
    /**
	 * @description 初始化信息树的方法
	 * @access public
	 */
    public void initMsgListTable() {
		msgListTableValueV.removeAllElements();
		DefaultMutableTreeNode msgTreeNode = (DefaultMutableTreeNode) msgTree
				.getLastSelectedPathComponent();
		if (msgTreeNode != null) {
			String msgName = msgTreeNode.getUserObject().toString();
//			msgListTableValueV.addAll(dao.sMsgVByTypeName(msgName));
		}
		msgListTableModel.setDataVector(msgListTableValueV,
				msgListTableColumnV);
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

    /**
	 * @description 获得新添加节点（名片夹或常用短语）的名称
	 * @param treeNode DefaultMutableTreeNode 树的节点名，
	 *        typeName String 添加的类型
	 * @return String 新添加节点的名称
	 */
    private String addTreeNode(DefaultMutableTreeNode treeNode, String typeName) {
		// 创建节点名称为空字符串
		String nodeName = "";
		// 判断节点名称的长度是否为0
		while (nodeName.length() == 0) {
			// 弹出输入框令用户输入名称
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
	 * @description 获得更改后节点（名片夹或常用短语）的名称
	 * @param treeNode DefaultMutableTreeNode 树的节点名，
	 * @return String 更改后节点的名称
	 */
	private String updateTreeNode(DefaultMutableTreeNode treeNode) {
		// 获得欲修改节点的父节点
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeNode
				.getParent();
		// 创建节点名称为空字符串
		String newNodeName = ""; 
		// 判断节点名称的长度是否为0
		while (newNodeName.length() == 0) { 
			// 接受用户输入名称
			newNodeName = JOptionPane.showInputDialog(null, "请输入新名称：", "修改名称",
					JOptionPane.INFORMATION_MESSAGE);
			// 判断节点名称是否为空值
			if (newNodeName == null) { 
				 // 为空值即用户取消了修改，则跳出循环
				break;
			} else {
				// 去掉首尾空格
				newNodeName = newNodeName.trim();
				// 判断节点名称的长度是否为0
				if (newNodeName.length() > 0) {
					// 如果不为0则判断该名称是否已经存在
					if (isHad(parentNode, newNodeName))
						// 如果存在则设置节点名称为空字符串
						newNodeName = ""; 
				}
			}
		}
		// 返回节点名称
		return newNodeName; 
	}
	
	/**
	 * @description 将导出的数据保存到文件
	 * @param path String 保存的路径
	 */
    private void SaveFile(String path)
    {
//        Vector<Vector> Data =new Vector<Vector>(dao.sExportInfo());
        Vector head = new Vector();
//        int length=Data.size() * (Data.get(0).size()), i=0;
        try{
            jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(path+".xls"));
            WritableSheet ws = wwb.createSheet("Sheet1",0);
            head.add("序号");head.add("发送时间");head.add("发信人");
            head.add("电话号码");head.add("短信内容");
//            for (i=0;i<5;i++){
//                jxl.write.Label labelC=new jxl.write.Label(i,0,head.get(i).toString());
//                ws.addCell(labelC);}
//            for(i=0;i<length;i++)
//            {
//                jxl.write.Label labelC=new jxl.write.Label(i%5,i/5+1,(Data.get(i/5)).get(i%5).toString());
//                ws.addCell(labelC);
//            }
            wwb.write();
            wwb.close();
            JOptionPane.showMessageDialog(null, "文件导出完成！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e)
        {
           e.printStackTrace();
        }
    }//
}