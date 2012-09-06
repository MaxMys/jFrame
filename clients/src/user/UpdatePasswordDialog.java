package user;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

//import com.mwq.dao.Dao;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class UpdatePasswordDialog extends JDialog {
	private JPasswordField oldPasswordField;
	private JPasswordField repeatPasswordField;
	private JPasswordField newPasswordField;
	//private final Dao dao = Dao.getInstance();
	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			UpdatePasswordDialog dialog = new UpdatePasswordDialog(null);
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
	public UpdatePasswordDialog(final Vector user) {
		super();
		setModal(true);
		setTitle("修改密码");
        setBounds(400, 200, 400, 200);
		final JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		getContentPane().add(inputPanel, BorderLayout.CENTER);

		final JLabel oldPasswordLabel = new JLabel();
		oldPasswordLabel.setText("原 密 码：");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
        gridBagConstraints_7.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_7.gridx = 0;
		gridBagConstraints_7.gridy = 0;
		inputPanel.add(oldPasswordLabel, gridBagConstraints_7);

		oldPasswordField = new JPasswordField();
		oldPasswordField.setColumns(15);
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.gridwidth = 3;
        gridBagConstraints_8.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.gridx = 1;
		inputPanel.add(oldPasswordField, gridBagConstraints_8);

		final JLabel newPasswordLabel = new JLabel();
		newPasswordLabel.setText("新 密 码：");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		inputPanel.add(newPasswordLabel, gridBagConstraints);

		newPasswordField = new JPasswordField();
		newPasswordField.setText("");
        newPasswordField.setColumns(15);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_1.gridwidth = 3;
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 1;
		inputPanel.add(newPasswordField, gridBagConstraints_1);

		final JLabel repeatPasswordLabel = new JLabel();
		repeatPasswordLabel.setText("重新输入：");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_2.gridy = 2;
		gridBagConstraints_2.gridx = 0;
		inputPanel.add(repeatPasswordLabel, gridBagConstraints_2);

		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setColumns(15);
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridwidth = 3;
		gridBagConstraints_3.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_3.gridy = 2;
		gridBagConstraints_3.gridx = 1;
		inputPanel.add(repeatPasswordField, gridBagConstraints_3);

		final JButton submitButton = new JButton();
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] oldPasswords = oldPasswordField.getPassword();
				String oldPassword = turnCharsToString(oldPasswords);
				char[] newPasswords = newPasswordField.getPassword();
				String newPassword = turnCharsToString(newPasswords);
				char[] repeatPasswords = repeatPasswordField.getPassword();
				String repeatPassword = turnCharsToString(repeatPasswords);
				if (oldPassword.length() == 0 || newPassword.length() == 0
						|| repeatPassword.length() == 0) {
					JOptionPane.showMessageDialog(null, "请输入密码！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					oldPasswordField.setText("");
					newPasswordField.setText("");
					repeatPasswordField.setText("");
					return;
				}
				String password = user.get(2).toString();
				if (oldPassword.equals(password)) {
					if (newPassword.equals(repeatPassword)) {
						String username = user.get(1).toString();
						user.set(2, newPassword);
						//dao.uPasswordByName(username, newPassword);
						JOptionPane.showMessageDialog(null, "密码修改成功！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null,
								"您两次输入的新密码不一致，请确认后重新输入！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);
						newPasswordField.setText("");
						repeatPasswordField.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(null, "您输入的原密码错误，请确认后重新输入！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					oldPasswordField.setText("");
					newPasswordField.setText("");
					repeatPasswordField.setText("");
				}
			}
		});
		submitButton.setText("确定");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_4.gridy = 3;
		gridBagConstraints_4.gridx = 2;
		inputPanel.add(submitButton, gridBagConstraints_4);

		final JButton exitButton = new JButton();
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setText("退出");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_5.gridy = 3;
		gridBagConstraints_5.gridx = 3;
		inputPanel.add(exitButton, gridBagConstraints_5);
		//
	}

	private String turnCharsToString(char[] chars) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			strBuf.append(chars[i]);
		}
		return strBuf.toString().trim();
	}

}
