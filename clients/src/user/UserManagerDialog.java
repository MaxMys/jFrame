package user;

//package com.mwq.frame.user;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//import com.mwq.dao.Dao;
import mpd.app.mwing.MTable;

public class UserManagerDialog extends JDialog {

	private MTable table;
	private Vector<String> tableColumnV;
	private Vector<Vector> tableValueV;
	private DefaultTableModel tableModel;
	private JTextField passwordTextField;
	private JTextField nameTextField;
	//private Dao dao = Dao.getInstance();
	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			UserManagerDialog dialog = new UserManagerDialog();
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
	public UserManagerDialog() {
		super();
		setModal(true);
		setTitle("用户管理");
		setBounds(400, 200, 400, 350);
		final JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		getContentPane().add(inputPanel, BorderLayout.NORTH);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("姓　　名：");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.insets = new Insets(10, 0, 0, 0);
		inputPanel.add(nameLabel, gridBagConstraints_13);

		nameTextField = new JTextField();
		nameTextField.setColumns(12);
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 1;
		inputPanel.add(nameTextField, gridBagConstraints);

        final JLabel passwordLabel = new JLabel();
		passwordLabel.setText("登录密码：");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.gridx = 0;
		inputPanel.add(passwordLabel, gridBagConstraints_8);

		passwordTextField = new JTextField();
		passwordTextField.setColumns(12);
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.gridwidth = 4;
		gridBagConstraints_9.anchor = GridBagConstraints.WEST;
		gridBagConstraints_9.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.gridx = 1;
		inputPanel.add(passwordTextField, gridBagConstraints_9);

		final JPanel buttonPanel = new JPanel();
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.anchor = GridBagConstraints.EAST;
		gridBagConstraints_10.insets = new Insets(5, 0, 10, 0);
		gridBagConstraints_10.gridwidth = 7;
		gridBagConstraints_10.gridy = 2;
		gridBagConstraints_10.gridx = 0;
		inputPanel.add(buttonPanel, gridBagConstraints_10);

		final JButton subButton = new JButton();
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String values[] = new String[6];
				values[0] = nameTextField.getText();
				values[1] = passwordTextField.getText();
				values[2] = "正常";
                if (values[0].length() == 0) {
					JOptionPane.showMessageDialog(null, "请输入姓名！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (values[0].length() > 4) {
					JOptionPane.showMessageDialog(null, "姓名最多只能为 4 个汉字！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					nameTextField.setText("");
					nameTextField.requestFocus();
					return;
				}
             
                if (values[1].length() ==0) {
					JOptionPane.showMessageDialog(null, "请输入密码！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (values[1].length() > 20) {
					JOptionPane.showMessageDialog(null, "密码最长不能超过 20 个字符！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					passwordTextField.setText("");
					passwordTextField.requestFocus();
					return;
				}
				Vector rowV = new Vector();
				int row = table.getRowCount();
				rowV.add(row + 1);
				for (int i = 0; i < values.length - 1; i++) {
					rowV.add(values[i]);
				}
				rowV.add("正常");
				tableModel.addRow(rowV);
				table.setRowSelectionInterval(row);
				//dao.iUser(values);
				JOptionPane.showMessageDialog(null, "用户添加完成！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);
				nameTextField.setText("");
				passwordTextField.setText("");
			}
		});
		subButton.setText("添加");
		buttonPanel.add(subButton);

		final JButton delButton = new JButton();
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String name = table.getValueAt(selectedRow, 1).toString();
				int i = JOptionPane.showConfirmDialog(null, "确定要删除用户“" + name
						+ "”?", "友情提示", JOptionPane.YES_NO_OPTION);
				if (i == 0) {
					tableModel.removeRow(selectedRow);
					//dao.uFreezeByName(name, "禁用");
					JOptionPane.showMessageDialog(null, "删除用户成功！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		delButton.setText("删除");
		buttonPanel.add(delButton);

		final JButton exitButton = new JButton();
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setText("退出");
		buttonPanel.add(exitButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		tableColumnV = new Vector<String>();
		String tableColumns[] = { "序    号", "姓    名"};
		for (int column = 0; column < tableColumns.length; column++) {
			tableColumnV.add(tableColumns[column]);
		}

		tableValueV = new Vector<Vector>();
		//tableValueV.addAll(dao.sUser());

		tableModel = new DefaultTableModel(tableValueV, tableColumnV);

		table = new MTable(tableModel);
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0);
		scrollPane.setViewportView(table);
		//
	}
}

