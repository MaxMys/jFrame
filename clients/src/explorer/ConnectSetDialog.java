package explorer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import com.jacob.com.Dispatch;
//import com.jacob.com.DispatchEvents;
//import com.jacob.activeX.ActiveXComponent;

//import com.mwq.dao.ReceiveEvents;

public class ConnectSetDialog extends JDialog {

	private final JButton connectButton, disconnectButton;
	private final Preferences perf=Preferences.userRoot();

//	private static ActiveXComponent comx=new ActiveXComponent("alasun.alasunsms");
//	public static Dispatch ob = comx.getObject();

//    private static ReceiveEvents re = new ReceiveEvents();
//    private DispatchEvents ore = new DispatchEvents(ob,re,"alasun.alasunsms");

    private final String device = perf.get("device","COM1");
    private final String center = perf.get("center","+8613800311500");
    private final String baud = perf.get("baud","9600");
    private final JLabel state = new JLabel();
	public ConnectSetDialog() {
		super();
		setModal(true);
		setTitle("�����豸����");
		setBounds(450, 200, 240, 230);
		final JPanel setPanel = new JPanel();
		setPanel.setBorder(new EmptyBorder(20, 0, 10, 10));
		final GridLayout gridLayout = new GridLayout(0, 1);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		setPanel.setLayout(gridLayout);
		getContentPane().add(setPanel, BorderLayout.CENTER);

		
		JLabel label_1 = new JLabel("�˿ں� ��"+ device);
		JLabel label_2 = new JLabel("������ ��"+ baud);
        JLabel label_3 = new JLabel("�������ĺ��룺"+center);

		setPanel.add(label_1);
		setPanel.add(label_2);
		setPanel.add(label_3);
		setPanel.add(state);

		final JPanel buttonPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		buttonPanel.setLayout(flowLayout);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		connectButton = new JButton();
		connectButton.setText("����");
		connectButton.addActionListener(new ConnectActionListener());
		buttonPanel.add(connectButton);

		disconnectButton = new JButton();
		disconnectButton.setText("�Ͽ�");
		disconnectButton.addActionListener(new DisconnectActionListener());
		buttonPanel.add(disconnectButton);

		final JButton exitButton = new JButton();
		exitButton.setText("�˳�");
//		exitButton.addActionListener(new ExitActionListener());
		buttonPanel.add(exitButton);

		final JLabel leftLabel = new JLabel();
		leftLabel.setPreferredSize(new Dimension(10, 0));
		getContentPane().add(leftLabel, BorderLayout.WEST);

		final JLabel rightLabel = new JLabel();
		rightLabel.setPreferredSize(new Dimension(10, 0));
		getContentPane().add(rightLabel, BorderLayout.EAST);

//		boolean  IsOpen = Dispatch.get(ob,"IsOpen").toBoolean();
//		if(IsOpen == true)
//        { state.setText("��ǰ�豸״̬��������");connectButton.setEnabled(false);
//		}else
//        { state.setText("��ǰ�豸״̬���ѶϿ�");disconnectButton.setEnabled(false);
//		}
    }

	class ConnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//���ö˿ں�
//			if(device.equals("COM1"))Dispatch.put(ob,"Commport","1");else
//			if(device.equals("COM2"))Dispatch.put(ob,"Commport","2");else
//			if(device.equals("COM3"))Dispatch.put(ob,"Commport","3");else
//			if(device.equals("COM4"))Dispatch.put(ob,"Commport","4");
//			int OpenComm = Dispatch.call(ob,"OpenComm").toInt();
//			if (OpenComm == 0){
//				// ���ö������ĺ���
//				Dispatch.call(ob,"setMsgCenterNo",center);
//                state.setText("��ǰ�豸״̬��������");
//                connectButton.setEnabled(false);
//                disconnectButton.setEnabled(true);
//                JOptionPane.showMessageDialog(ConnectSetDialog.this, "������");
//            }else
//                {JOptionPane.showMessageDialog(ConnectSetDialog.this, "����ʧ��");
//            }
		}
	}
	class DisconnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			int CloseComm = Dispatch.call(ob,"CloseComm").toInt();
//			if(CloseComm == 0){
//                state.setText("��ǰ�豸״̬���ѶϿ�");
//                disconnectButton.setEnabled(false);
//                connectButton.setEnabled(true);
//                JOptionPane.showMessageDialog(ConnectSetDialog.this, "�ѶϿ�");
//			}else
//                {JOptionPane.showMessageDialog(ConnectSetDialog.this, "�Ͽ�ʧ��");
//			}
//        }
	}
    class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
}}

