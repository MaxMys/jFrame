package explorer;


//import com.jacob.com.Dispatch;
//import com.mwq.dao.ReceiveEvents;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
//import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
//import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import MOB.sys.Common.Common;

import explorer.LetterSetDialog.ExitActionListener;

import mpd.app.mwing.MTable;

public class SimCardDialog extends JDialog {

    public static void main(String args[]) {
		try {
			SimCardDialog dialog = new SimCardDialog();
			dialog.addWindowListener(new WindowAdapter() {
                @Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog
	 */
	public SimCardDialog() {
		super();
		setModal(true);
		setTitle("SIM卡管理");
		int width = 200;
		setBounds(500, 320, width, 150);

		final JPanel setPanel = new JPanel();
		setPanel.setBorder(new EmptyBorder(20, 0, 10, 10));
		final GridLayout gridLayout = new GridLayout(0, 2);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		setPanel.setLayout(new BoxLayout(setPanel,BoxLayout.Y_AXIS));
		getContentPane().add(setPanel, BorderLayout.CENTER);

		String localnum = Common.C("Sim_Config", "number");
		JLabel label_1 = new JLabel("本机号码："+localnum);
		setPanel.add(label_1);
		label_1.setVerticalAlignment(SwingConstants.CENTER);
		JLabel label2 = new JLabel("服务提供商：MMP GROUP");
		setPanel.add(label2);
		JLabel label_3 = new JLabel("短信中心号码：10086");
		setPanel.add(label_3);
		final JButton exitButton = new JButton();
		exitButton.setText("退出");
//		exitButton.setHorizontalAlignment(SwingConstants.RIGHT);
		exitButton.addActionListener(new ExitActionListener());
		setPanel.add(exitButton);
	}
	
	class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
