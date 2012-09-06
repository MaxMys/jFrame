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
	 * @param sendListTableModel DefaultTableModel �����б�ģ��,
	 *        infoTabbedPane JTabbedPane ���Ű��tabbedpane,
	 *        infoTextArea JTextArea ���ŵ�����
	 */
	public ExplorerPanel(final DefaultTableModel sendListTableModel,
			final JTabbedPane infoTabbedPane,final JTextArea infoTextArea) {
		super();
		setLayout(new BorderLayout());

		final JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
        
//---------------------------------------------------------------------------
		//���￪ʼ����Ƭ��panel
		final JSplitPane cardSplitPane = new JSplitPane();
		cardSplitPane.setOneTouchExpandable(true);
		cardSplitPane.setDividerSize(12);
		cardSplitPane.setDividerLocation(244);
		tabbedPane.addTab("��Ƭ��", null, cardSplitPane, null);

		final JPanel cardTreePanel = new JPanel();
		cardSplitPane.setLeftComponent(cardTreePanel);
		cardTreePanel.setLayout(new BorderLayout());

		// ������ʾ��Ƭ�����Ĺ������
		final JScrollPane cardTreeScrollPane = new JScrollPane();
		// �����������ӵ��ϼ������
		cardTreePanel.add(cardTreeScrollPane);

		// ������Ƭ�����ĸ��ڵ�
		cardTreeRoot = new DefaultMutableTreeNode("root");
		// ��ʼ����Ƭ����
		initTree(cardTreeRoot, "card");

		// ������Ƭ����ģ��
		cardTreeModel = new DefaultTreeModel(cardTreeRoot);

		// ������Ƭ����
		cardTree = new JTree(cardTreeModel);
		// ������Ƭ�����ĸ��ڵ㲻�ɼ�
		cardTree.setRootVisible(false);
		//System.out.println(cardTree.getSelectionModel().getSelectionMode());
		cardTree.getSelectionModel().setSelectionMode(
				// ������Ƭ������ѡ��ģʽΪ��ѡ
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// �����Ƭ���������ӽڵ㣬������ѡ�е�һ���ӽڵ�
		if (cardTreeRoot.getChildCount() > 0)
			cardTree.setSelectionRow(0);
		// Ϊ��Ƭ������ӽӵ�ѡ���¼�������
		cardTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						// ��ʼ����Ƭ���б�
						initCardListTable();
					}
				});
		// ����Ƭ������ӵ����������
		cardTreeScrollPane.setViewportView(cardTree);
		
		final JPanel cardTreeButtonPanel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		cardTreeButtonPanel.setLayout(flowLayout_1);
		cardTreePanel.add(cardTreeButtonPanel, BorderLayout.SOUTH);
//---------------------------------------------------------------------------
		//����Ƭ�����������µ���Ƭ�У����˲���Ϊ��Ӱ�ť�����¼���Ӧ
		final MButton addCardTypeButton = new MButton();
		addCardTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �������Ƭ������
				String name = addTreeNode(cardTreeRoot, "��Ƭ��");
				// ���û�ȡ���½�ʱ����Ϊ��
				if (name != null) {
					// ��õ�ǰӵ����Ƭ�е�����
					int childCount = cardTreeRoot.getChildCount();
					// ����Ƭ��������󴴽��µ���Ƭ��
					cardTreeModel.insertNodeInto(new DefaultMutableTreeNode(
							name), cardTreeRoot, childCount);
					// ˢ����Ƭ����ģ��
					cardTreeModel.reload();
					// �����½���Ƭ��Ϊѡ��״̬
					cardTree.setSelectionRow(childCount);
             		// ���½���Ƭ�б��浽���ݿ���
//					dao.iType(name, "card");
				}
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL creCardTypeUrl = this.getClass().getResource("/img/add_tree.png");
		addCardTypeButton.setIcon(new ImageIcon(creCardTypeUrl));
		URL creCardTypeOverUrl = this.getClass().getResource(
				"/img/add_tree_over.png");
		addCardTypeButton.setRolloverIcon(new ImageIcon(creCardTypeOverUrl));
		cardTreeButtonPanel.add(addCardTypeButton);
//---------------------------------------------------------------------------		
		//�޸���Ƭ�е����ƣ����˲���Ϊ�޸İ�ť�����¼���Ӧ
		final MButton updCardTypeButton = new MButton();
		updCardTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ѡ�нڵ��·������
				TreePath selectionPath = cardTree.getSelectionPath();
				// �ж�·���Ƿ�Ϊ��
				if (selectionPath == null) {
					// ���Ϊ���򵯳���ʾ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵ���Ƭ�У�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// ���ѡ�нڵ����
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selectionPath
							.getLastPathComponent();
					// ���ѡ�нڵ������
					String nowName = treeNode.getUserObject().toString();
					// ����ȷ���޸���ʾ��
					int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�޸���Ƭ�С�"
							+ nowName + "�������ƣ�", "������ʾ",
							JOptionPane.YES_NO_OPTION);
					// ���Ϊ0���޸�
					if (i == 0) {
						// ����޸ĺ������
						String newName = updateTreeNode(treeNode);
						// �ж��޸ĺ�������Ƿ�Ϊ�գ����Ϊ�����û�ȡ�����޸�
						if (newName != null) {
							// �޸Ľڵ�����
							treeNode.setUserObject(newName);
							// ˢ����
							cardTreeModel.reload();
							// �����޸ĵĽڵ�Ϊѡ�нڵ�
							cardTree.setSelectionPath(selectionPath);
							// ���޸ĺ�����Ʊ��浽���ݿ�
//							dao.uTypeNameByName("card", nowName, newName);
						}
					}
				}
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL updCardTypeUrl = this.getClass().getResource("/img/upd_tree.png");
		updCardTypeButton.setIcon(new ImageIcon(updCardTypeUrl));
		URL updCardTypeOverUrl = this.getClass().getResource(
				"/img/upd_tree_over.png");
		updCardTypeButton.setRolloverIcon(new ImageIcon(updCardTypeOverUrl));
		cardTreeButtonPanel.add(updCardTypeButton);
//---------------------------------------------------------------------------
		//ɾ����Ƭ�У����˲���Ϊɾ����ť������Ӧ�¼�
		final MButton delCardTypeButton = new MButton();
		delCardTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ѡ�е���Ƭ�ж���
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) cardTree
						.getLastSelectedPathComponent();
				// δѡ��Ҫɾ������Ƭ��
				if (treeNode == null) {
					// ������ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ������Ƭ�У�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					// ��ֱ�ӷ���
					return;
				}
				// �����ɾ����Ƭ�е�����
				String name = treeNode.getUserObject().toString();
				// ����ɾ����ȷ����ʾ
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����Ƭ�С�" + name
						+ "����", "������ʾ", JOptionPane.YES_NO_OPTION);
				// �û�ȡ����ɾ������
				if (i != 0)
					// ֱ�ӷ���
					return;
			    
				// ����Ƭ���а�����Ƭ
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
				
				// ����Ƭ������ɾ����Ƭ��
				cardTreeModel.removeNodeFromParent(treeNode);
//				// �����ݿ���ɾ����Ƭ��
//				dao.dTypeByName("card", name);
			}
		});
		//�Ѱ�ť�����Զ���ͼƬ
		URL delCardTypeUrl = this.getClass().getResource("/img/del_tree.png");
		delCardTypeButton.setIcon(new ImageIcon(delCardTypeUrl));
		URL delCardTypeOverUrl = this.getClass().getResource(
				"/img/del_tree_over.png");
		delCardTypeButton.setRolloverIcon(new ImageIcon(delCardTypeOverUrl));
		cardTreeButtonPanel.add(delCardTypeButton);
//---------------------------------------------------------------------------
		//�˲���Ϊ��Ƭ����Ϣ�б�������ť���
		final JPanel cardListPanel = new JPanel();
		cardSplitPane.setRightComponent(cardListPanel);
		cardListPanel.setLayout(new BorderLayout());

		final JScrollPane cardListScrollPane = new JScrollPane();
		cardListPanel.add(cardListScrollPane);

		cardListTableColumnV = new Vector<String>();
		String cardListTableColumns[] = {"����", "�Ա�", "�ƶ��绰"};
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

		//ȫѡ��ť������Ӧ�¼�
		final MButton selAllButton = new MButton();
		URL selAllUrl = this.getClass().getResource("/img/select_all.png");
		selAllButton.setIcon(new ImageIcon(selAllUrl));
		URL selAllOverUrl = this.getClass().getResource(
				"/img/select_all_over.png");
		selAllButton.setRolloverIcon(new ImageIcon(selAllOverUrl));
		selAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ѡ�б���е�������
				cardListTable.selectAll();
			}
		});
		cardButtonPanel.add(selAllButton);

		//�����б�ť������Ӧ�¼�
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
				// ��õ�ǰ�������б��е������˸���
				int rowCount = sendListTableModel.getRowCount();
				// �����Ƭ�б��е�ѡ����
				int[] selectedRows = cardListTable.getSelectedRows();
				// ��ʼ���������б�����
				int index = rowCount + 1;
				// ����ѡ����
				for (int selectedRow = 0; selectedRow < selectedRows.length; selectedRow++) {
					// �����Ƭ�绰
					String newPhone = cardListTable.getValueAt(
							selectedRows[selectedRow], 8).toString();
					// Ĭ��Ϊδ�����������б�
					boolean had = false;
					// �����������б�
					for (int row = 0; row < rowCount; row++) {
						// ��������˵ĵ绰
						String nowPhone = sendListTableModel.getValueAt(
								row, 2).toString();
						// �ж���Ƭ��ź������˱���Ƿ���ͬ
						if (newPhone.equals(nowPhone) ) {
							// �Ѿ������������б�
							had = true;
							break;
						}
					}
					// δ�����������б�
					if (!had) {
						// ����һ�����������˵�����
						Vector rowV = new Vector();
						// ������
						rowV.add(index++);
						// ��ӱ��
						//rowV.add(newNum);
						// �������
						rowV.add(cardListTable.getValueAt(
								selectedRows[selectedRow], 2));
						// ��Ӻ���
						rowV.add(cardListTable.getValueAt(
								selectedRows[selectedRow], 8));
						//�����������б�
						sendListTableModel.addRow(rowV);
					}
				}
				// ȡ����Ƭ�б��е�ѡ����
				cardListTable.clearSelection();
			}
		});
		cardButtonPanel.add(addToSendListButton);

		//�����Ƭ��Ϣ����Ƭ�а�ť������Ӧ�¼�
		final MButton addCardButton = new MButton();
		URL addCardUrl = this.getClass().getResource("/img/add_info.png");
		addCardButton.setIcon(new ImageIcon(addCardUrl));
		URL addCardOverUrl = this.getClass().getResource(
				"/img/add_info_over.png");
		addCardButton.setRolloverIcon(new ImageIcon(addCardOverUrl));
		addCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �ж��Ƿ������Ƭ��
				if (cardTreeRoot.getChildCount() == 0) {
					// ����������Ƭ�е���ʾ
					JOptionPane.showMessageDialog(null, "���Ƚ�����Ƭ�У�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// ��õ�ǰѡ�е���Ƭ��
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) cardTree
							.getLastSelectedPathComponent();
					// �����Ƭ������
					String cardName = treeNode.getUserObject().toString();
					// ���������Ƭ�ĶԻ������
					PersonnelDialog personnelDialog = new PersonnelDialog(
							"�����Ƭ", cardName, -1);
					// ���������Ƭ�ĶԻ���Ϊ�ɼ�
					personnelDialog.setVisible(true);
					// ˢ����Ƭ�б�
					initCardListTable();
				}
			}
		});
		cardButtonPanel.add(addCardButton);

		//�޸���Ƭ��Ϣ��ť������Ӧ�¼�
		final MButton updCardButton = new MButton();
		URL updCardUrl = this.getClass().getResource("/img/upd_info.png");
		updCardButton.setIcon(new ImageIcon(updCardUrl));
		URL updCardOverUrl = this.getClass().getResource(
				"/img/upd_info_over.png");
		updCardButton.setRolloverIcon(new ImageIcon(updCardOverUrl));
		updCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �����Ƭ�б��е�ѡ����
				int[] selectedRows = cardListTable.getSelectedRows();
				// ��ѡ����һ����Ƭ
				if (selectedRows.length == 1) {
					// ���ѡ����Ƭ�ı��
					int num = (Integer) cardListTable.getValueAt(
							selectedRows[0], 1);
					// �����޸���Ƭ�ĶԻ������
					PersonnelDialog personnelDialog = new PersonnelDialog(
							"�޸���Ƭ", "", num);
					// �����޸���Ƭ�ĶԻ���Ϊ�ɼ�
					personnelDialog.setVisible(true);
					initCardListTable();
				} else {
					// δѡ��Ҫ�޸ĵ���Ƭ
					if (selectedRows.length == 0) {
						// ������ʾ��Ϣ
						JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵ���Ա��",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					} 
					// ѡ���˶����Ƭ
					else {
						// ������ʾ��Ϣ
						JOptionPane.showMessageDialog(null, "һ��ֻ���޸�һ����Ա��",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		cardButtonPanel.add(updCardButton);

		//ɾ����Ƭ��Ϣ��ť������Ӧ�¼�
		final MButton delCardButton = new MButton();
		URL delCardUrl = this.getClass().getResource("/img/del_info.png");
		delCardButton.setIcon(new ImageIcon(delCardUrl));
		URL delCardOverUrl = this.getClass().getResource(
				"/img/del_info_over.png");
		delCardButton.setRolloverIcon(new ImageIcon(delCardOverUrl));
		delCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �����Ƭ�б��е�ѡ����
				int[] selectedRows = cardListTable.getSelectedRows();
				// δѡ��Ҫɾ������Ƭ
				if (selectedRows.length == 0) {
					// ������ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ������Ա��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// ��֯��ʾ��Ϣ
					String[] infos = new String[selectedRows.length + 1];
					// �����ʾ��Ϣ
					infos[0] = "ȷ��Ҫɾ��������Ա��";
					// ����ѡ�е���Ƭ
					for (int i = 0; i < selectedRows.length; i++) {
						infos[i + 1] = "    "
								+ cardListTable.getValueAt(selectedRows[i], 1)
								+ "  "
								+ cardListTable.getValueAt(selectedRows[i], 2);
					}
					// ������ʾ��Ϣ
					int i = JOptionPane.showConfirmDialog(null, infos, "������ʾ",
							JOptionPane.YES_NO_OPTION);
					// ȷ��ɾ��
					if (i == 0) {
						// ����ѡ�е���Ƭ
						for (int j = 0; j < selectedRows.length; j++) {
							// �����Ƭ���
							int num = (Integer) cardListTable.getValueAt(
									selectedRows[j], 1);
//							// �����ݿ�ɾ��
//							dao.dPersonnelByNum(num);
						}
						// ˢ����Ƭ�б�
						initCardListTable();
					}
				}
			}
		});
		cardButtonPanel.add(delCardButton);
//--------------------------------------------------------------------------------
		//���￪ʼΪ���ö���panel
		final JSplitPane infoSplitPane = new JSplitPane();
		infoSplitPane.setOneTouchExpandable(true);
		infoSplitPane.setDividerSize(12);
		infoSplitPane.setDividerLocation(244);
		tabbedPane.addTab("���ö���", null, infoSplitPane, null);

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

		//�½����ö�����Ϣ�ⰴť������Ӧ�¼�
		final MButton addInfoTypeButton = new MButton();
		addInfoTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = addTreeNode(infoTreeRoot, "��Ϣ��");
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

		//�޸ĳ��ö�����Ϣ�����ư�ť������Ӧ�¼�
		final MButton updInfoTypeButton = new MButton();
		updInfoTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreePath selectionPath = infoTree.getSelectionPath();
				if (selectionPath == null) {
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵ���Ϣ�⣡", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selectionPath
							.getLastPathComponent();
					String nowName = treeNode.getUserObject().toString();
					int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�޸���Ϣ�⡰"
							+ nowName + "�������ƣ�", "������ʾ",
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
//				// δѡ��Ҫɾ������Ϣ��
//				if (treeNode == null) {
//					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ������Ϣ�⣡", "������ʾ",
//							JOptionPane.INFORMATION_MESSAGE);
//					return;
//				}
//				String name = treeNode.getUserObject().toString();
//				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����Ϣ�⡰" + name
//						+ "����", "������ʾ", JOptionPane.YES_NO_OPTION);
//				// �û�ȡ����ɾ������
//				if (i != 0)
//					return;
////				if (dao.sInfoVByTypeName(name).size() > 0) {
////					String options[] = { "ȡ��", "����������Ϣ��", "ɾ��" };
////					int optionIndex = JOptionPane.showOptionDialog(null,
////							"��ѡ��Ը���Ϣ������Ϣ�Ĵ���ʽ��", "������ʾ",
////							JOptionPane.YES_NO_CANCEL_OPTION,
////							JOptionPane.QUESTION_MESSAGE, null, options,
////							options[0]);
////					int typeId = dao.sTypeIdByUsedAndName("info", name);
////					if (optionIndex == 0)// �û�ȡ����ɾ������
////						return;
////					if (optionIndex == 1) {// ����������Ϣ��
////						Vector infoV = dao.sTypeByUsedExcept("info", typeId);
////						String[] infos = new String[infoV.size() + 1];
////						infos[0] = "��ѡ��";
////						for (int j = 0; j < infoV.size(); j++) {
////							infos[j + 1] = ((Vector) infoV.get(j)).get(2)
////									.toString();
////						}
////						Object info = "��ѡ��";
////						while (info.equals("��ѡ��")) {
////							info = JOptionPane.showInputDialog(null,
////									"��ѡ��Ҫ�������Ϣ�⣺", "������ʾ",
////									JOptionPane.INFORMATION_MESSAGE, null,
////									infos, infos[0]);
////							if (info == null)// �û�ȡ����ɾ������
////								return;
////						}
////						int newTypeId = dao.sTypeIdByUsedAndName("info", info
////								.toString());
////						dao.uPersonnelTypeIdByTypeId(typeId, newTypeId);
////					}
////					if (optionIndex == 2) {// ɾ��
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

		//���ö����������
		final JPanel infoListPanel = new JPanel();
		infoSplitPane.setRightComponent(infoListPanel);
		infoListPanel.setLayout(new BorderLayout());

		final JScrollPane infoListScrollPane = new JScrollPane();
		infoListPanel.add(infoListScrollPane);

		infoListTableColumnV = new Vector<String>();
		infoListTableColumnV.add("���");
		infoListTableColumnV.add("���");
		infoListTableColumnV.add("��Ϣ����");

		infoListTableValueV = new Vector<Vector>();

		infoListTableModel = new DefaultTableModel(infoListTableValueV,
				infoListTableColumnV);

		infoListTable = new MTable(infoListTableModel);
		infoListTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		initInfoListTable();
		infoListScrollPane.setViewportView(infoListTable);

		//�༭���ö������ݰ�ť���
		final JPanel infoButtonPanel = new JPanel();
		infoButtonPanel.setLayout(new BoxLayout(infoButtonPanel,
				BoxLayout.Y_AXIS));
		infoListPanel.add(infoButtonPanel, BorderLayout.EAST);

		//�༭���ݰ�ť������Ӧ�¼�
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
				// �����Ϣ�б��ѡ����
				int selectedRow = infoListTable.getSelectedRow();
				// δѡ���κ���
				if (selectedRow < 0) {
					// ������ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�༭����Ϣ��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// �����Ϣ����
					String info = infoListTable.getValueAt(selectedRow, 2).toString();
					// ��ǰ��ѡ�е��ǡ��������ݡ����
					//if (infoTabbedPane.getSelectedIndex() == 0)
					// �����Ϣ�����������ı�����	
//					infoTextArea.setText(info);
					// ��ǰ��ѡ�е��ǡ�E-mail���ݡ����
					//else
					// �����Ϣ��E-mail�����ı�����
					// emailTextArea.setText(info);
				}
			}
		});
		infoButtonPanel.add(addToSendInfoButton);

		//������ݰ�ť������Ӧ�¼�
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
				InfoDialog infoDialog = new InfoDialog("�����Ϣ", treeNode
						.getUserObject().toString(), -1, null);
				infoDialog.setVisible(true);
				initInfoListTable();
			}
		});
		infoButtonPanel.add(addInfoButton);

		//�޸����ݰ�ť������Ӧ�¼�
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
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵ���Ϣ��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) infoTree
							.getLastSelectedPathComponent();
					int num = (Integer) infoListTable
							.getValueAt(selectedRow, 1);
					String content = infoListTable.getValueAt(selectedRow, 2)
							.toString();
					InfoDialog infoDialog = new InfoDialog("�޸���Ϣ", treeNode
							.getUserObject().toString(), num, content);
					infoDialog.setVisible(true);
					initInfoListTable();
				}
			}
		});
		infoButtonPanel.add(updInfoButton);

		//ɾ��������Ϣ������Ӧ�¼�
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
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ������Ϣ��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int num = (Integer) infoListTable
							.getValueAt(selectedRow, 1);
					int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����Ϣ" + num
							+ "��", "������ʾ", JOptionPane.YES_NO_OPTION);
					if (i == 0) {
//						dao.dInfoByNum(num);
						initInfoListTable();
					}
				}
			}
		});
		infoButtonPanel.add(delInfoButton);
//------------------------------------------------------------------------
		//���￪ʼΪ������panel
		final JSplitPane msgSplitPane = new JSplitPane();
		msgSplitPane.setOneTouchExpandable(true);
		msgSplitPane.setDividerSize(12);
		msgSplitPane.setDividerLocation(244);
		tabbedPane.addTab("������", null, msgSplitPane, null);

		final JPanel msgTreePanel = new JPanel();
		msgSplitPane.setLeftComponent(msgTreePanel);
		msgTreePanel.setLayout(new BorderLayout());

		final JScrollPane msgTreeScrollPane = new JScrollPane();
		msgTreePanel.add(msgTreeScrollPane);

		msgTreeRoot = new DefaultMutableTreeNode("root");
        msgTreeRoot.add(new DefaultMutableTreeNode("�ռ���"));
        msgTreeRoot.add(new DefaultMutableTreeNode("������"));
		msgTreeModel = new DefaultTreeModel(msgTreeRoot);
		msgTree = new JTree(msgTreeModel);
		msgTree.setRootVisible(false);
		if (msgTreeRoot.getChildCount() > 0)msgTree.setSelectionRow(0);
		msgTreeScrollPane.setViewportView(msgTree);

		//������������ʾ���
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

		msgListTableColumnV.add("�ֻ���");
		msgListTableColumnV.add("��������");
		msgListTableColumnV.add("����ʱ��");


		msgListTableValueV = new Vector<Vector>();
		msgListTableModel = new DefaultTableModel(msgListTableValueV,
				msgListTableColumnV);
		msgListTable = new MTable(msgListTableModel);
		msgListTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		initMsgListTable();
		msgListScrollPane.setViewportView(msgListTable);

        //���ĸ���ť����msgButtonPanel�ϣ�
		final JPanel msgButtonPanel = new JPanel();
        msgButtonPanel.setLayout(new BoxLayout(msgButtonPanel,BoxLayout.Y_AXIS));
		msgListPanel.add(msgButtonPanel, BorderLayout.EAST);

        //������ת����ť������Ӧ�¼�
        final MButton forwMsgButton = new MButton();
		URL forwMsgUrl = this.getClass().getResource("/img/forward_msg.jpg");//ˢ��
		forwMsgButton.setIcon(new ImageIcon(forwMsgUrl));
		URL forwMsgOverUrl = this.getClass().getResource(
				"/img/forward_msg_over.jpg");
		forwMsgButton.setRolloverIcon(new ImageIcon(forwMsgOverUrl));
		forwMsgButton.addActionListener(new ActionListener() {
			//�ѵ�ǰ��Ϣ���ڶ���������
			public void actionPerformed(ActionEvent e) {
				// �����Ϣ�б��ѡ����
				int selectedRow = msgListTable.getSelectedRow();
				// δѡ���κ���
				if (selectedRow < 0) {
					// ������ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫת������Ϣ��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// �����Ϣ����
					String info = msgListTable.getValueAt(selectedRow, 5).toString();
					// �����Ϣ�����������ı�����
					infoTextArea.setText(info);
                }
			}
		});
        forwMsgButton.setVisible(false);
		msgButtonPanel.add(forwMsgButton);

        //�ռ�����Żظ��İ�ť������Ӧ�¼�
		final MButton replyMsgButton = new MButton();
		//����ת��
		URL replyMsgUrl = this.getClass().getResource(
				"/img/reply_msg.jpg");
		replyMsgButton.setIcon(new ImageIcon(replyMsgUrl));
		URL replyMsgOverUrl = this.getClass().getResource(
				"/img/reply_msg_over.jpg");
		replyMsgButton
				.setRolloverIcon(new ImageIcon(replyMsgOverUrl));
		replyMsgButton.addActionListener(new ActionListener() {
			//�õ��Ǹ�������¼���ֻ��ţ����������������б�
			public void actionPerformed(ActionEvent e) {   
				// �����Ϣ�б��ѡ����
				int selectedRow = msgListTable.getSelectedRow();
				// δѡ���κ���
				if (selectedRow < 0) {
					// ������ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�ظ�����ϵ�ˣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// ��õ�ǰ�������б��е������˸���
					int rowCount = sendListTableModel.getRowCount();
					//�������˲�Ϊ��ʱ������������б��ѯ��
					if(rowCount > 0)
					{
						JOptionPane.showMessageDialog(null, "�����˲�Ϊ�գ���������������б�", 
								"������ʾ",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// ����һ�����������˵�����
					String info = msgListTable.getValueAt(selectedRow,4).toString();// ��ûظ���Ϣ�ֻ���
					Vector rowV = new Vector();
					// ������
					rowV.add(1);
					// �������
					rowV.add(msgListTable.getValueAt(selectedRow,3));
					// ����ֻ���
					rowV.add(msgListTable.getValueAt(selectedRow,4));
					// �����������б�
					sendListTableModel.addRow(rowV);
                }
            }
		});
//        replyMsgButton.setVisible(false);
		msgButtonPanel.add(replyMsgButton);

		//ɾ��������Ϣ�İ�ť������Ӧ�¼����շ����ã�
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
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ������Ϣ��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int num = (Integer) msgListTable.getValueAt(selectedRow, 1);
					int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����Ϣ" + num
							+ "��", "������ʾ", JOptionPane.YES_NO_OPTION);
					if (i == 0) {
//						dao.dMsgByNum(num);
						initMsgListTable();
					}
				}
			}
		});
		msgButtonPanel.add(delMsgButton);

		//��յİ�ť������Ӧ�¼����շ����ã�
		final MButton claMsgButton = new MButton();
		URL claMsgUrl = this.getClass().getResource("/img/cancel_msg.png");
		claMsgButton.setIcon(new ImageIcon(claMsgUrl));
		URL claMsgOverUrl = this.getClass().getResource(
				"/img/cancel_msg_over.png");
		claMsgButton.setRolloverIcon(new ImageIcon(claMsgOverUrl));
		claMsgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ��ոö�����?",
						"������ʾ", JOptionPane.YES_NO_OPTION);
                				if (i == 0) {
                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) msgTree.getLastSelectedPathComponent();
                 // �����ɾ�������������    
                    String name = treeNode.getUserObject().toString();
//                        int typeId = dao.sTypeIdByUsedAndName("msg", name);
//                    	dao.dMsgByTypeId(typeId);
                        msgListTableValueV.removeAllElements();
                        initMsgListTable();
                }
			}
		});
		msgButtonPanel.add(claMsgButton);
        
        //ˢ�µİ�ť������Ӧ�¼����շ����ã�
		final MButton refMsgButton = new MButton();
		URL refMsgUrl = this.getClass().getResource("/img/refresh_msg.jpg");
		refMsgButton.setIcon(new ImageIcon(refMsgUrl));
		URL refMsgOverUrl = this.getClass().getResource(
				"/img/refresh_msg_over.jpg");
		refMsgButton.setRolloverIcon(new ImageIcon(refMsgOverUrl));
		refMsgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���ﻹҪ�������ŵĲ���    
                initMsgListTable();
			}
		});
		msgButtonPanel.add(refMsgButton);

		//�ռ��䵼�����ݰ�ť������Ӧ�¼�
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
                   // �����ɾ�������������
                   String name = treeNode.getUserObject().toString();
                   if (name.equals("�ռ���"))
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
	// ��ʼ�����ķ���
	
	/**
	 * @description ��ʼ�����panel�����ķ���
	 * @param treeRoot DefaultMutableTreeNode ���ĸ��ڵ�,
	 *        used String ָ����������
	 */
	private void initTree(DefaultMutableTreeNode treeRoot, String used) {
//		Vector typeV = dao.sTypeByUsed(used);// ��ѯ����ָ����������
//		for (int i = 0; i < typeV.size(); i++) {// ��������
//			Vector type = (Vector) typeV.get(i);// �����������
//			treeRoot.add(new DefaultMutableTreeNode(type.get(2)));// ��������ӵ�����
//		}
	}

	/**
	 * @description ��ʼ��cardlist����Ϣ�����ķ���
	 */
	private void initCardListTable() {
		// �����Ƭ�б�
		cardListTableValueV.removeAllElements();
		// �����Ƭ������ѡ�нڵ����
		DefaultMutableTreeNode cardTreeNode = (DefaultMutableTreeNode) cardTree
				.getLastSelectedPathComponent();
		// �ж��Ƿ����ѡ�еĽڵ�
		if (cardTreeNode != null) {
			// ���ѡ����Ƭ�е�����
			String cardName = cardTreeNode.getUserObject().toString();
			// ������Ƭ�а�������Ƭ
//			cardListTableValueV.addAll(dao.sPersonnelVByTypeName(cardName));
		}
		// ˢ����Ƭ�б���ģ��
		cardListTableModel.setDataVector(cardListTableValueV,
				cardListTableColumnV);
	}

	/**
	 * @description ��ʼ��infolist����Գ��ö������Ϣ�����ķ���
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
	 * @description ���ն������ݵķ���
	 * @param m String[] ���յĶ�������
	 * @access public
	 */
    public void insertMsgListTable(String[] m) {
    	msgListTableColumnV = new Vector<String>();
        msgListTableModel.addRow(m);
	}
    
    /**
	 * @description ��ʼ����Ϣ���ķ���
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

    /**
	 * @description �������ӽڵ㣨��Ƭ�л��ö��������
	 * @param treeNode DefaultMutableTreeNode ���Ľڵ�����
	 *        typeName String ��ӵ�����
	 * @return String ����ӽڵ������
	 */
    private String addTreeNode(DefaultMutableTreeNode treeNode, String typeName) {
		// �����ڵ�����Ϊ���ַ���
		String nodeName = "";
		// �жϽڵ����Ƶĳ����Ƿ�Ϊ0
		while (nodeName.length() == 0) {
			// ������������û���������
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
	 * @description ��ø��ĺ�ڵ㣨��Ƭ�л��ö��������
	 * @param treeNode DefaultMutableTreeNode ���Ľڵ�����
	 * @return String ���ĺ�ڵ������
	 */
	private String updateTreeNode(DefaultMutableTreeNode treeNode) {
		// ������޸Ľڵ�ĸ��ڵ�
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeNode
				.getParent();
		// �����ڵ�����Ϊ���ַ���
		String newNodeName = ""; 
		// �жϽڵ����Ƶĳ����Ƿ�Ϊ0
		while (newNodeName.length() == 0) { 
			// �����û���������
			newNodeName = JOptionPane.showInputDialog(null, "�����������ƣ�", "�޸�����",
					JOptionPane.INFORMATION_MESSAGE);
			// �жϽڵ������Ƿ�Ϊ��ֵ
			if (newNodeName == null) { 
				 // Ϊ��ֵ���û�ȡ�����޸ģ�������ѭ��
				break;
			} else {
				// ȥ����β�ո�
				newNodeName = newNodeName.trim();
				// �жϽڵ����Ƶĳ����Ƿ�Ϊ0
				if (newNodeName.length() > 0) {
					// �����Ϊ0���жϸ������Ƿ��Ѿ�����
					if (isHad(parentNode, newNodeName))
						// ������������ýڵ�����Ϊ���ַ���
						newNodeName = ""; 
				}
			}
		}
		// ���ؽڵ�����
		return newNodeName; 
	}
	
	/**
	 * @description �����������ݱ��浽�ļ�
	 * @param path String �����·��
	 */
    private void SaveFile(String path)
    {
//        Vector<Vector> Data =new Vector<Vector>(dao.sExportInfo());
        Vector head = new Vector();
//        int length=Data.size() * (Data.get(0).size()), i=0;
        try{
            jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(path+".xls"));
            WritableSheet ws = wwb.createSheet("Sheet1",0);
            head.add("���");head.add("����ʱ��");head.add("������");
            head.add("�绰����");head.add("��������");
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
            JOptionPane.showMessageDialog(null, "�ļ�������ɣ�", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e)
        {
           e.printStackTrace();
        }
    }//
}