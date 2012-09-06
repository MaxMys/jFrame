package frame;

import MOB.app.Controller.Message_Controller;
import MOB.sys.Core._System;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

//import com.mwq.dao.Dao;
import mpd.app.mwing.MButton;
import mpd.app.mwing.MTable;
import explorer.ConnectSetDialog;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class InfoPanel extends JPanel {

    private JTabbedPane tabbedPane;
	private JTextArea infoTextArea;
	private MTable sendListTable;
    private JCheckBox blink,flash;
    public JPanel infoPanel = null;

	public InfoPanel(MTable sendListTable) {
		super();
		this.sendListTable = sendListTable;
		setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		this.infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("短信内容", null, infoPanel, null);

		final JScrollPane infoScrollPane = new JScrollPane();
		infoPanel.add(infoScrollPane, BorderLayout.CENTER);

		infoTextArea = new JTextArea();
		infoTextArea.setLineWrap(true);
		infoScrollPane.setViewportView(infoTextArea);

		final JPanel infoButtonPanel = new JPanel();
		infoButtonPanel.setLayout(new BoxLayout(infoButtonPanel,
				BoxLayout.Y_AXIS));
		infoPanel.add(infoButtonPanel, BorderLayout.EAST);

		final MButton infoSendButton = new MButton();
		infoSendButton.addActionListener(new InfoSendButtonActionListener());
		URL sendUrl = this.getClass().getResource("/img/send.png");
		infoSendButton.setIcon(new ImageIcon(sendUrl));
		URL sendOverUrl = this.getClass().getResource("/img/send_over.png");
		infoSendButton.setRolloverIcon(new ImageIcon(sendOverUrl));
		infoButtonPanel.add(infoSendButton);

		final MButton infoCancelButton = new MButton();
		infoCancelButton
				.addActionListener(new InfoCancelButtonActionListener());
		URL cancelUrl = this.getClass().getResource("/img/cancel.png");
		infoCancelButton.setIcon(new ImageIcon(cancelUrl));
		URL cancelOverUrl = this.getClass().getResource("/img/cancel_over.png");
		infoCancelButton.setRolloverIcon(new ImageIcon(cancelOverUrl));
		infoButtonPanel.add(infoCancelButton);

//         JLabel blank = new JLabel(" ");infoButtonPanel.add(blank);
//        JLabel msgtype = new JLabel("发送短信类型:");infoButtonPanel.add(msgtype);
//        
//        blink = new JCheckBox();infoButtonPanel.add(blink);
//        flash = new JCheckBox();infoButtonPanel.add(flash);
//        blink.setText("闪烁");flash.setText("免提");
        
	}

	private class InfoCancelButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			infoTextArea.setText(null);
		}
	}

	private class InfoSendButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            //Vector 可以写为//真实号num,//内容//手机号//姓名//类型号
            

			int sendCount = sendListTable.getRowCount();
//            int msgtype = 0;//短信类型
//            if(blink.isSelected() == true)msgtype = msgtype + 2;
//            if(flash.isSelected() == true)msgtype = msgtype + 4;
//			for (int i = 0; i < sendCount; i++) {
//                //-----这下是把一条短信存入发件箱-----------
////                int num = dao.sMsgIdOfMax();
//				String info = infoTextArea.getText();
//                String phone = (String)sendListTable.getValueAt(i, 2);//在收件人列表中的第3列就是手机号
//                String name = (String)sendListTable.getValueAt(i, 1);
//                Vector ims = new Vector();
////                ims.add(num+1);//应该插入的num号primary key
//                ims.add("'"+info+"'");//内容
//                ims.add(phone);//手机号
//                ims.add(name);//姓名
//                ims.add('2');//类型号 发件箱为2
//                dao.iMsgSend(ims);
                //-----^这上是把一条短信存入发件箱^------
               	//Variant SendMsg = Dispatch.call(ConnectSetDialog.ob,"SendMsg","10086","101",null,"0");//;SendMsg(sNo,sCon,dSendTime,iMsgType,bAsync);
//                Variant SendMsg = Dispatch.call(ConnectSetDialog.ob,"SendMsg",phone,info,null,msgtype);
//                if ( 0 == SendMsg.getInt()){JOptionPane.showMessageDialog(InfoPanel.this, "发送成功");}
//                else {JOptionPane.showMessageDialog(InfoPanel.this, "发送失败");}
//			}
			String info = infoTextArea.getText();
			HashMap<String,Object> hm = new HashMap<String,Object>();
			hm.put("message", info);
			_System.getServe().addWork(Message_Controller.class,"SendMsg", hm, null);
		}
	}

	//写短信的区域
	public JTextArea getInfoTextArea() {
		return infoTextArea;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

}

