package com.fangpengfei.emp.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.fangpengfei.emp.dao.UserDao;
import com.fangpengfei.emp.entity.User;

public class UserPanel extends JPanel {
	private static final long serialVersionUID = 6665300946451832219L;
	private JPanel panel1, panel2;
	private JLabel label;
	private JTable table;// 表格
	private JScrollPane scrollPane;
	private UserTableModel userTableModel;
	private JButton button1, button2, button3;
	private AddDialog addDialog;
	private UpdateDialog updateDialog;
	private JPopupMenu popupMenu;
	private JMenuItem delete, update,refresh;

	public UserPanel() {
		this.setLayout(new BorderLayout());
		panel1 = new JPanel();
		panel2 = new JPanel();
		label = new JLabel("用户管理界面");
		button1 = new JButton("添加用户");
		button2 = new JButton("删除用户");
		button3 = new JButton("修改用户");
		button1.addActionListener(new UsePanelClick());
		button2.addActionListener(new UsePanelClick());
		button3.addActionListener(new UsePanelClick());
		
		popupMenu = new JPopupMenu();
		delete = new JMenuItem("删除");
		update = new JMenuItem("修改");
		refresh = new JMenuItem("刷新");
		delete.addActionListener(new RightClick());
		update.addActionListener(new RightClick());
		refresh.addActionListener(new RightClick());
		
		userTableModel = new UserTableModel();
		table = new JTable(userTableModel);
		table.addMouseListener(new TableClick());
		scrollPane = new JScrollPane(table);
		addDialog = new AddDialog();
		updateDialog = new UpdateDialog();

		popupMenu.add(delete);
		popupMenu.add(update);
		popupMenu.add(refresh);
		
		panel1.add(label);
		panel2.add(button1);
		panel2.add(button2);
		panel2.add(button3);
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.SOUTH);
		this.add(scrollPane);
	}
	
	//定义一个刷新表格的方法
	public void refreshUserTable(){
		table.setModel(new UserTableModel());
	}
	
	// 定义一个类,用于处理表格的点击事件
	class TableClick implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				int selectRow = table.rowAtPoint(e.getPoint());
				if (selectRow == -1) {
					return;
				}
				int[] rows = table.getSelectedRows();
				boolean inSelected = false;
				// 判断当前右键所在行是否已选中
				for (int r : rows) {
					if (selectRow == r) {
						inSelected = true;
						break;
					}
				}
				if (!inSelected) {
					table.setRowSelectionInterval(selectRow, selectRow);
				}
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}

	}

	// 定义一个内部类,处理右键点击后的删除和修改事件
	class RightClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == delete) {
				deleteUserClick();
			} else if (e.getSource() == update) {
				updateUserClick();
			}
		}

	}

	// 定义一个内部类,用于处理用户点击按钮的事件
	class UsePanelClick implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {
				// System.out.println("添加用户按钮被点击");
				addDialog.setVisible(true);
				addDialog.reset();// 在弹出添加对话框后,清空对话框中的内容
			} else if (e.getSource() == button2) {
				deleteUserClick();
			} else if (e.getSource() == button3) {
				updateUserClick();
			}
		}
	}

	// 定义一个删除方法
	private void deleteUserClick() {
		// System.out.println("删除用户按钮被点击");
		// 1.获得用户选择的行数
		int row = table.getSelectedRow();// 的返回用户选择的行数，没有选择则为-1
		if (row == -1) {
			JOptionPane.showMessageDialog(panel1, "需要选择一行", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 删除的时候防止误删，有个确认对话框
		int choose = JOptionPane.showConfirmDialog(panel1, "是否确认删除");
		if (choose == 0) {
			// 2.通过用户选择的行来获得用户名；
			// 可以给UseTablemodel 添加getrowdata方法，返回所有行
			userTableModel = new UserTableModel();
			Vector<Vector<String>> rows = userTableModel.getRowData();
			Vector<String> vector;
			try {
				vector = rows.get(row);
				String username = vector.get(1);
				// 3.链接数据库，删除用户
				UserDao userDao = new UserDao();
				userDao.deleteUser(username);
			} catch (ArrayIndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(panel1, "数据错误,请重新选择");
				table.setModel(new UserTableModel());
				return;
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(panel1, e2.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 4.刷新表格
			table.setModel(new UserTableModel());
		}
	}

	// 定义一个修改方法
	private void updateUserClick() {
		// System.out.println("修改用户按钮被点击");
		// 1.获得用户选择的行数
		int row = table.getSelectedRow();// 的返回用户选择的行数，没有选择则为-1
		if (row == -1) {
			JOptionPane.showMessageDialog(panel1, "需要选择一行", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 2.通过用户选择的行来获得用户名；
		// 可以给UseTablemodel 添加getrowdata方法，返回所有行
		UserTableModel userTableModel = new UserTableModel();
		Vector<Vector<String>> rows = userTableModel.getRowData();
		Vector<String> vector;
		try {
			vector = rows.get(row);
			String username = vector.get(1);// 取出username
			// 3.连接数据库，通過用戶名查找一個用戶
			UserDao userDao = new UserDao();
			User user = userDao.getOneUser(username);
			// 4.修改用戶界面的窗口,刷新界面
			updateDialog.show(user);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(panel1, "数据错误,请重新选择");
			table.setModel(new UserTableModel());
			return;
		}
	}

	// 内部类用于显示 增加 功能窗口
	class AddDialog extends JDialog {
		private static final long serialVersionUID = 208534481212625146L;
		private JPanel panel1, panel2, panel3, panel4;
		private JLabel label1, label2, label3;
		private JTextField textField1, textField2;
		private JPasswordField passwordField;
		private JButton button1, button2;

		public AddDialog() {
			this.setTitle("添加用户");
			this.setSize(350, 230);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(4, 1));
			this.setModal(true);
			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			label1 = new JLabel("用户名");
			label2 = new JLabel("密码");
			label3 = new JLabel("用户昵称");
			textField1 = new JTextField(20);
			textField2 = new JTextField(20);
			passwordField = new JPasswordField(20);
			button1 = new JButton("确认添加");
			button2 = new JButton("重置");
			button1.addActionListener(new AddDialogClick());
			button2.addActionListener(new AddDialogClick());
			panel1.add(label1);
			panel1.add(textField1);
			panel2.add(label2);
			panel2.add(passwordField);
			panel3.add(label3);
			panel3.add(textField2);
			panel4.add(button1);
			panel4.add(button2);
			this.add(panel1);
			this.add(panel2);
			this.add(panel3);
			this.add(panel4);

			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}

		// 内部类，用于进行对话框中按钮的的点击事件
		class AddDialogClick implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					// System.out.println("确认添加按钮被点击");
					// 添加用戶的思路
					// 1.获得用户输入的数据
					String username = textField1.getText().trim();
					@SuppressWarnings("deprecation")
					String password = passwordField.getText().trim();
					String nickname = textField2.getText().trim();
					int userLength = username.length();
					int passwordLength = password.length();
					// 2.判断用户输入的数据，，用户名不能为空
					if (null == username || "".equals(username)) {
						JOptionPane.showMessageDialog(panel1, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == password || "".equals(password)) {
						JOptionPane.showMessageDialog(panel1, "密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == nickname || "".equals(nickname)) {
						JOptionPane.showMessageDialog(panel1, "昵称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (userLength < 1 || userLength > 18) {
						JOptionPane.showMessageDialog(panel1, "用户名长度为1到18位", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (passwordLength < 6 || passwordLength > 18) {
						JOptionPane.showMessageDialog(panel1, "密码长度为6到18位", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 3、链接数据库，调用UseDao的adduser方法
					UserDao userDao = new UserDao();
					try {
						userDao.addUser(new User(username, password, nickname));
					} catch (Exception e2) {
						// 在增加用户时可能抛异常，自定义的异常，在此处处理
						String errorMessage = e2.getMessage();
						JOptionPane.showMessageDialog(panel1, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 4.隐藏对话框
					addDialog.setVisible(false);
					// 5.刷新用户管理界面，把添加的数据放到表格中
					// 重新创建usetablemodel对象
					reset();
					table.setModel(new UserTableModel());

				} else if (e.getSource() == button2) {
					// System.out.println("重置按钮被点击");
					// 设置文本框中的内容
					reset();
				}
			}

		}

		private void reset() {
			textField1.setText("");
			textField2.setText("");
			passwordField.setText("");
		}
	}

	// 内部类用于显示 修改 功能窗口
	class UpdateDialog extends JDialog {
		private static final long serialVersionUID = 2759502263494735198L;
		private JPanel panel1, panel2, panel3;
		private JLabel label1, label2;
		private JTextField textField;
		private JPasswordField passwordField;
		private JButton button1, button2;
		private User user;

		public void show(User user) {
			this.user = user;
			this.setTitle("正在修改" + user.getUsername() + "的信息");
			textField.setText(user.getNickname());
			passwordField.setText(user.getPassword());
			this.setVisible(true);

		}

		public UpdateDialog() {
			// this.setTitle("添加用户");
			this.setSize(350, 230);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(3, 1));
			this.setModal(true);
			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			label1 = new JLabel("用户昵称");
			label2 = new JLabel("密码");
			textField = new JTextField(20);
			passwordField = new JPasswordField(20);
			button1 = new JButton("确认修改");
			button2 = new JButton("重置");
			button1.addActionListener(new UpdateDialogClick());
			button2.addActionListener(new UpdateDialogClick());
			panel1.add(label1);
			panel1.add(textField);
			panel2.add(label2);
			panel2.add(passwordField);
			panel3.add(button1);
			panel3.add(button2);
			this.add(panel1);
			this.add(panel2);
			this.add(panel3);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}

		class UpdateDialogClick implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					// System.out.println("确认修改按钮被点击");
					@SuppressWarnings("deprecation")
					String password = passwordField.getText();
					String nickname = textField.getText();
					String username = user.getUsername();

					UserDao userDao = new UserDao();
					try {
						userDao.updateUser(new User(username, password, nickname));
					} catch (Exception e1) {
						JOptionPane.showConfirmDialog(panel1, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
					}
					updateDialog.setVisible(false);
					table.setModel(new UserTableModel());
				} else if (e.getSource() == button2) {
					// System.out.println("重置按钮被点击");
					textField.setText(user.getNickname());
					passwordField.setText(user.getPassword());
				}
			}

		}
	}

}
