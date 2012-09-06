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
	 * @param user �û���
	 */
	public MainFrame(final Vector user) {
		super();
		setTitle("�û���Ϣ�������");
		setBounds(100, 100, 900, 690);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ��������������
		final JToolBar toolBar = new JToolBar();
		// ���ù������ı߿���ʽ
		toolBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		// ���ù����������ƶ�
		toolBar.setFloatable(false);
		// ����������ӵ������
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		//�������ð�ť�����¼���Ӧ
		final MButton handsetButton = new MButton();
		handsetButton.addActionListener(new HandsetButtonActionListener());
		URL infoUrl = this.getClass().getResource("/img/info.png");
		handsetButton.setIcon(new ImageIcon(infoUrl));
		URL infoOverUrl = this.getClass().getResource("/img/info_over.png");
		handsetButton.setRolloverIcon(new ImageIcon(infoOverUrl));
		toolBar.add(handsetButton);
		
		
		//sim������ť�����¼���Ӧ
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
		
		//�˳���ť�����¼���Ӧ
		final MButton exitButton = new MButton();
		URL exitUrl = this.getClass().getResource("/img/exit.png");
		exitButton.setIcon(new ImageIcon(exitUrl));
		URL exitOverUrl = this.getClass().getResource("/img/exit_over.png");
		exitButton.setRolloverIcon(new ImageIcon(exitOverUrl));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null,"��������?", "������ʾ",
							JOptionPane.YES_NO_OPTION);
					if (i != 0) System.exit(0);
			}
		});
		toolBar.add(exitButton);
		
		// �����ָ�������
		final JSplitPane workaroundSplitPane = new JSplitPane();
		// ���÷ָ����Ŀ��
		workaroundSplitPane.setDividerSize(12);
		// ����Ϊ֧�ֿ���չ��/�۵��ָ���
		workaroundSplitPane.setOneTouchExpandable(true);
		// �������Ĭ�ϵķָ�λ��
		workaroundSplitPane.setDividerLocation(310);
		// �������Ĭ�ϵķָ�λ��
		workaroundSplitPane.setPreferredSize(new Dimension(0, 590));
		// ����Ϊ��ֱ�ָ�
		workaroundSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(workaroundSplitPane);
		
		final JSplitPane sendSplitPane = new JSplitPane();
		// ����Ϊ֧�ֿ���չ��/�۵��ָ���
		sendSplitPane.setOneTouchExpandable(true);
		// ���÷ָ����Ŀ��
		sendSplitPane.setDividerSize(12);
		// �������Ĭ�ϵķָ�λ��
		sendSplitPane.setDividerLocation(244);
		// ����Ϊˮƽ�ָ�
		sendSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		workaroundSplitPane.setLeftComponent(sendSplitPane);
			
		final JPanel sendListPanel = new JPanel();
		sendListPanel.setBorder(new TitledBorder(null, "�������б�",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		sendListPanel.setLayout(new BorderLayout());
		sendSplitPane.setLeftComponent(sendListPanel);

		final JScrollPane scrollPane = new JScrollPane();
		sendListPanel.add(scrollPane, BorderLayout.CENTER);

		sendListTableColumnV = new Vector<String>();
		sendListTableColumnV.add("���");
		sendListTableColumnV.add("����");
        sendListTableColumnV.add("�ֻ���");

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

		//ȡ�����Ű�ť�����¼���Ӧ
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
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫȡ������Ա��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					String[] infos = new String[selectedRows.length + 1];
					infos[0] = "ȷ��Ҫȡ��������Ա��";
					for (int i = 0; i < selectedRows.length; i++) {
						infos[i + 1] = "    "
								+ sendListTable.getValueAt(selectedRows[i], 0)
								+ "  "
								+ sendListTable.getValueAt(selectedRows[i], 2);
					}
					int i = JOptionPane.showConfirmDialog(null, infos, "������ʾ",
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
		
		//����б�ť�����¼���Ӧ
		final MButton clearButton = new MButton();
		URL cancelListUrl = this.getClass().getResource("/img/cancel_list.png");
		clearButton.setIcon(new ImageIcon(cancelListUrl));
		URL cancelListOverUrl = this.getClass().getResource(
				"/img/cancel_list_over.png");
		clearButton.setRolloverIcon(new ImageIcon(cancelListOverUrl));
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ����������б�",
						"������ʾ", JOptionPane.YES_NO_OPTION);
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
