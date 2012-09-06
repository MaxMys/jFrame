package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import user.UserManagerDialog;
import user.UpdatePasswordDialog;
import mpd.app.mwing.MButton;
import mpd.app.mwing.MTable;
import explorer.ConnectSetDialog;
import explorer.LetterSetDialog;
import explorer.SimCardDialog;

public class MainFrame extends JFrame{

	private MTable sendListTable;
	private Vector<String> sendListTableColumnV;
	private Vector<Vector> sendListTableValueV;
    private DefaultTableModel sendListTableModel;
    public InfoPanel infoPanel = null; 
    public ExplorerPanel  explorerPanel = null;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Vector v = new Vector();
			v.addElement("adm");
			MainFrame frame = new MainFrame(v);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @description Create the frame
	 * @param user 用户名
	 */
	public MainFrame(final Vector user) {
		super();
		setTitle("用户信息管理界面");
		setBounds(100, 100, 900, 690);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 创建工具栏对象
		final JToolBar toolBar = new JToolBar();
		// 设置工具栏的边框样式
		toolBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		// 设置工具栏不可移动
		toolBar.setFloatable(false);
		// 将工具栏添加到面板中
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		//短信设置按钮及其事件响应
		final MButton handsetButton = new MButton();
		handsetButton.addActionListener(new HandsetButtonActionListener());
		URL infoUrl = this.getClass().getResource("/img/info.png");
		handsetButton.setIcon(new ImageIcon(infoUrl));
		URL infoOverUrl = this.getClass().getResource("/img/info_over.png");
		handsetButton.setRolloverIcon(new ImageIcon(infoOverUrl));
		toolBar.add(handsetButton);
		
		
		//sim卡管理按钮及其事件响应
		final MButton simcardButton = new MButton();
		URL simcardUrl = this.getClass().getResource("/img/simcard.png");
		simcardButton.setIcon(new ImageIcon(simcardUrl));
		URL simcardOverUrl = this.getClass().getResource("/img/simcard_over.png");
		simcardButton.setRolloverIcon(new ImageIcon(simcardOverUrl));
		simcardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	          //      dialog.setVisible(true);
	                SimCardDialog dialog=new SimCardDialog();
	                dialog.setVisible(true);
				}
		});
		toolBar.add(simcardButton);
		
		//退出按钮及其事件响应
		final MButton exitButton = new MButton();
		URL exitUrl = this.getClass().getResource("/img/exit.png");
		exitButton.setIcon(new ImageIcon(exitUrl));
		URL exitOverUrl = this.getClass().getResource("/img/exit_over.png");
		exitButton.setRolloverIcon(new ImageIcon(exitOverUrl));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null,"再玩会儿呗?", "友情提示",
							JOptionPane.YES_NO_OPTION);
					if (i != 0) System.exit(0);
			}
		});
		toolBar.add(exitButton);
		
		// 创建分割面板对象
		final JSplitPane workaroundSplitPane = new JSplitPane();
		// 设置分割条的宽度
		workaroundSplitPane.setDividerSize(12);
		// 设置为支持快速展开/折叠分割条
		workaroundSplitPane.setOneTouchExpandable(true);
		// 设置面版默认的分割位置
		workaroundSplitPane.setDividerLocation(310);
		// 设置面版默认的分割位置
		workaroundSplitPane.setPreferredSize(new Dimension(0, 590));
		// 设置为垂直分割
		workaroundSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(workaroundSplitPane);
		
		final JSplitPane sendSplitPane = new JSplitPane();
		// 设置为支持快速展开/折叠分割条
		sendSplitPane.setOneTouchExpandable(true);
		// 设置分割条的宽度
		sendSplitPane.setDividerSize(12);
		// 设置面版默认的分割位置
		sendSplitPane.setDividerLocation(244);
		// 设置为水平分割
		sendSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		workaroundSplitPane.setLeftComponent(sendSplitPane);
			
		final JPanel sendListPanel = new JPanel();
		sendListPanel.setBorder(new TitledBorder(null, "收信人列表",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		sendListPanel.setLayout(new BorderLayout());
		sendSplitPane.setLeftComponent(sendListPanel);

		final JScrollPane scrollPane = new JScrollPane();
		sendListPanel.add(scrollPane, BorderLayout.CENTER);

		sendListTableColumnV = new Vector<String>();
		sendListTableColumnV.add("序号");
		sendListTableColumnV.add("姓名");
        sendListTableColumnV.add("手机号");

		sendListTableValueV = new Vector<Vector>();
		sendListTableModel = new DefaultTableModel(sendListTableValueV,
				sendListTableColumnV);

		sendListTable = new MTable(sendListTableModel);
		scrollPane.setViewportView(sendListTable);

		final JPanel buttonPanel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		buttonPanel.setLayout(flowLayout_1);
		sendListPanel.add(buttonPanel, BorderLayout.SOUTH);

		//取消发信按钮及其事件响应
		final MButton cancelButton = new MButton();
		URL cancelUrl = this.getClass()
				.getResource("/img/cancel_personnel.png");
		cancelButton.setIcon(new ImageIcon(cancelUrl));
		URL cancelOverUrl = this.getClass().getResource(
				"/img/cancel_personnel_over.png");
		cancelButton.setRolloverIcon(new ImageIcon(cancelOverUrl));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = sendListTable.getSelectedRows();
				if (selectedRows.length == 0) {
					JOptionPane.showMessageDialog(null, "请选择要取消的人员！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					String[] infos = new String[selectedRows.length + 1];
					infos[0] = "确定要取消以下人员：";
					for (int i = 0; i < selectedRows.length; i++) {
						infos[i + 1] = "    "
								+ sendListTable.getValueAt(selectedRows[i], 0)
								+ "  "
								+ sendListTable.getValueAt(selectedRows[i], 2);
					}
					int i = JOptionPane.showConfirmDialog(null, infos, "友情提示",
							JOptionPane.YES_NO_OPTION);
					if (i == 0) {
						for (int j = selectedRows.length - 1; j >= 0; j--) {
							sendListTableModel.removeRow(selectedRows[j]);
						}
						for (int row = selectedRows[0]; row < sendListTable
								.getRowCount(); row++) {
							sendListTable.setValueAt(row + 1, row, 0);
						}
					}
				}
			}
		});
		buttonPanel.add(cancelButton);
		
		//清空列表按钮及其事件响应
		final MButton clearButton = new MButton();
		URL cancelListUrl = this.getClass().getResource("/img/cancel_list.png");
		clearButton.setIcon(new ImageIcon(cancelListUrl));
		URL cancelListOverUrl = this.getClass().getResource(
				"/img/cancel_list_over.png");
		clearButton.setRolloverIcon(new ImageIcon(cancelListOverUrl));
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "确定要清空收信人列表？",
						"友情提示", JOptionPane.YES_NO_OPTION);
				if (i == 0) {
					sendListTableValueV.removeAllElements();
					sendListTableModel.setDataVector(sendListTableValueV,
							sendListTableColumnV);
				}
			}
		});
		buttonPanel.add(clearButton);
		
		this.infoPanel = new InfoPanel(sendListTable);
		sendSplitPane.setRightComponent(infoPanel);

		this.explorerPanel = new ExplorerPanel(sendListTableModel, infoPanel.getTabbedPane(), infoPanel.getInfoTextArea());
		workaroundSplitPane.setRightComponent(explorerPanel);
	}

	private class HandsetButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LetterSetDialog dialog = new LetterSetDialog();
			dialog.setVisible(true);
		}
	}
}
