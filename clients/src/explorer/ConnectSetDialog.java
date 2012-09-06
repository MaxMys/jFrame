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
		setTitle("短信设备连接");
		setBounds(450, 200, 240, 230);
		final JPanel setPanel = new JPanel();
		setPanel.setBorder(new EmptyBorder(20, 0, 10, 10));
		final GridLayout gridLayout = new GridLayout(0, 1);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		setPanel.setLayout(gridLayout);
		getContentPane().add(setPanel, BorderLayout.CENTER);

		
		JLabel label_1 = new JLabel("端口号 ："+ device);
		JLabel label_2 = new JLabel("波特率 ："+ baud);
        JLabel label_3 = new JLabel("短信中心号码："+center);

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
		connectButton.setText("连接");
		connectButton.addActionListener(new ConnectActionListener());
		buttonPanel.add(connectButton);

		disconnectButton = new JButton();
		disconnectButton.setText("断开");
		disconnectButton.addActionListener(new DisconnectActionListener());
		buttonPanel.add(disconnectButton);

		final JButton exitButton = new JButton();
		exitButton.setText("退出");
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
//        { state.setText("当前设备状态：已连接");connectButton.setEnabled(false);
//		}else
//        { state.setText("当前设备状态：已断开");disconnectButton.setEnabled(false);
//		}
    }

	class ConnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//设置端口号
//			if(device.equals("COM1"))Dispatch.put(ob,"Commport","1");else
//			if(device.equals("COM2"))Dispatch.put(ob,"Commport","2");else
//			if(device.equals("COM3"))Dispatch.put(ob,"Commport","3");else
//			if(device.equals("COM4"))Dispatch.put(ob,"Commport","4");
//			int OpenComm = Dispatch.call(ob,"OpenComm").toInt();
//			if (OpenComm == 0){
//				// 设置短信中心号码
//				Dispatch.call(ob,"setMsgCenterNo",center);
//                state.setText("当前设备状态：已连接");
//                connectButton.setEnabled(false);
//                disconnectButton.setEnabled(true);
//                JOptionPane.showMessageDialog(ConnectSetDialog.this, "已连接");
//            }else
//                {JOptionPane.showMessageDialog(ConnectSetDialog.this, "连接失败");
//            }
		}
	}
	class DisconnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			int CloseComm = Dispatch.call(ob,"CloseComm").toInt();
//			if(CloseComm == 0){
//                state.setText("当前设备状态：已断开");
//                disconnectButton.setEnabled(false);
//                connectButton.setEnabled(true);
//                JOptionPane.showMessageDialog(ConnectSetDialog.this, "已断开");
//			}else
//                {JOptionPane.showMessageDialog(ConnectSetDialog.this, "断开失败");
//			}
//        }
	}
    class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
}}

