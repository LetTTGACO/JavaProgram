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
	private JTable table;// ���
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
		label = new JLabel("�û��������");
		button1 = new JButton("����û�");
		button2 = new JButton("ɾ���û�");
		button3 = new JButton("�޸��û�");
		button1.addActionListener(new UsePanelClick());
		button2.addActionListener(new UsePanelClick());
		button3.addActionListener(new UsePanelClick());
		
		popupMenu = new JPopupMenu();
		delete = new JMenuItem("ɾ��");
		update = new JMenuItem("�޸�");
		refresh = new JMenuItem("ˢ��");
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
	
	//����һ��ˢ�±��ķ���
	public void refreshUserTable(){
		table.setModel(new UserTableModel());
	}
	
	// ����һ����,���ڴ�����ĵ���¼�
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
				// �жϵ�ǰ�Ҽ��������Ƿ���ѡ��
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

	// ����һ���ڲ���,�����Ҽ�������ɾ�����޸��¼�
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

	// ����һ���ڲ���,���ڴ����û������ť���¼�
	class UsePanelClick implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {
				// System.out.println("����û���ť�����");
				addDialog.setVisible(true);
				addDialog.reset();// �ڵ�����ӶԻ����,��նԻ����е�����
			} else if (e.getSource() == button2) {
				deleteUserClick();
			} else if (e.getSource() == button3) {
				updateUserClick();
			}
		}
	}

	// ����һ��ɾ������
	private void deleteUserClick() {
		// System.out.println("ɾ���û���ť�����");
		// 1.����û�ѡ�������
		int row = table.getSelectedRow();// �ķ����û�ѡ���������û��ѡ����Ϊ-1
		if (row == -1) {
			JOptionPane.showMessageDialog(panel1, "��Ҫѡ��һ��", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// ɾ����ʱ���ֹ��ɾ���и�ȷ�϶Ի���
		int choose = JOptionPane.showConfirmDialog(panel1, "�Ƿ�ȷ��ɾ��");
		if (choose == 0) {
			// 2.ͨ���û�ѡ�����������û�����
			// ���Ը�UseTablemodel ���getrowdata����������������
			userTableModel = new UserTableModel();
			Vector<Vector<String>> rows = userTableModel.getRowData();
			Vector<String> vector;
			try {
				vector = rows.get(row);
				String username = vector.get(1);
				// 3.�������ݿ⣬ɾ���û�
				UserDao userDao = new UserDao();
				userDao.deleteUser(username);
			} catch (ArrayIndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(panel1, "���ݴ���,������ѡ��");
				table.setModel(new UserTableModel());
				return;
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(panel1, e2.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 4.ˢ�±��
			table.setModel(new UserTableModel());
		}
	}

	// ����һ���޸ķ���
	private void updateUserClick() {
		// System.out.println("�޸��û���ť�����");
		// 1.����û�ѡ�������
		int row = table.getSelectedRow();// �ķ����û�ѡ���������û��ѡ����Ϊ-1
		if (row == -1) {
			JOptionPane.showMessageDialog(panel1, "��Ҫѡ��һ��", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 2.ͨ���û�ѡ�����������û�����
		// ���Ը�UseTablemodel ���getrowdata����������������
		UserTableModel userTableModel = new UserTableModel();
		Vector<Vector<String>> rows = userTableModel.getRowData();
		Vector<String> vector;
		try {
			vector = rows.get(row);
			String username = vector.get(1);// ȡ��username
			// 3.�������ݿ⣬ͨ�^�Ñ�������һ���Ñ�
			UserDao userDao = new UserDao();
			User user = userDao.getOneUser(username);
			// 4.�޸��Ñ�����Ĵ���,ˢ�½���
			updateDialog.show(user);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(panel1, "���ݴ���,������ѡ��");
			table.setModel(new UserTableModel());
			return;
		}
	}

	// �ڲ���������ʾ ���� ���ܴ���
	class AddDialog extends JDialog {
		private static final long serialVersionUID = 208534481212625146L;
		private JPanel panel1, panel2, panel3, panel4;
		private JLabel label1, label2, label3;
		private JTextField textField1, textField2;
		private JPasswordField passwordField;
		private JButton button1, button2;

		public AddDialog() {
			this.setTitle("����û�");
			this.setSize(350, 230);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(4, 1));
			this.setModal(true);
			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			label1 = new JLabel("�û���");
			label2 = new JLabel("����");
			label3 = new JLabel("�û��ǳ�");
			textField1 = new JTextField(20);
			textField2 = new JTextField(20);
			passwordField = new JPasswordField(20);
			button1 = new JButton("ȷ�����");
			button2 = new JButton("����");
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

		// �ڲ��࣬���ڽ��жԻ����а�ť�ĵĵ���¼�
		class AddDialogClick implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					// System.out.println("ȷ����Ӱ�ť�����");
					// ����Ñ���˼·
					// 1.����û����������
					String username = textField1.getText().trim();
					@SuppressWarnings("deprecation")
					String password = passwordField.getText().trim();
					String nickname = textField2.getText().trim();
					int userLength = username.length();
					int passwordLength = password.length();
					// 2.�ж��û���������ݣ����û�������Ϊ��
					if (null == username || "".equals(username)) {
						JOptionPane.showMessageDialog(panel1, "�û�������Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == password || "".equals(password)) {
						JOptionPane.showMessageDialog(panel1, "���벻��Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == nickname || "".equals(nickname)) {
						JOptionPane.showMessageDialog(panel1, "�ǳƲ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (userLength < 1 || userLength > 18) {
						JOptionPane.showMessageDialog(panel1, "�û�������Ϊ1��18λ", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (passwordLength < 6 || passwordLength > 18) {
						JOptionPane.showMessageDialog(panel1, "���볤��Ϊ6��18λ", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 3���������ݿ⣬����UseDao��adduser����
					UserDao userDao = new UserDao();
					try {
						userDao.addUser(new User(username, password, nickname));
					} catch (Exception e2) {
						// �������û�ʱ�������쳣���Զ�����쳣���ڴ˴�����
						String errorMessage = e2.getMessage();
						JOptionPane.showMessageDialog(panel1, errorMessage, "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 4.���ضԻ���
					addDialog.setVisible(false);
					// 5.ˢ���û�������棬����ӵ����ݷŵ������
					// ���´���usetablemodel����
					reset();
					table.setModel(new UserTableModel());

				} else if (e.getSource() == button2) {
					// System.out.println("���ð�ť�����");
					// �����ı����е�����
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

	// �ڲ���������ʾ �޸� ���ܴ���
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
			this.setTitle("�����޸�" + user.getUsername() + "����Ϣ");
			textField.setText(user.getNickname());
			passwordField.setText(user.getPassword());
			this.setVisible(true);

		}

		public UpdateDialog() {
			// this.setTitle("����û�");
			this.setSize(350, 230);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(3, 1));
			this.setModal(true);
			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			label1 = new JLabel("�û��ǳ�");
			label2 = new JLabel("����");
			textField = new JTextField(20);
			passwordField = new JPasswordField(20);
			button1 = new JButton("ȷ���޸�");
			button2 = new JButton("����");
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
					// System.out.println("ȷ���޸İ�ť�����");
					@SuppressWarnings("deprecation")
					String password = passwordField.getText();
					String nickname = textField.getText();
					String username = user.getUsername();

					UserDao userDao = new UserDao();
					try {
						userDao.updateUser(new User(username, password, nickname));
					} catch (Exception e1) {
						JOptionPane.showConfirmDialog(panel1, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
					}
					updateDialog.setVisible(false);
					table.setModel(new UserTableModel());
				} else if (e.getSource() == button2) {
					// System.out.println("���ð�ť�����");
					textField.setText(user.getNickname());
					passwordField.setText(user.getPassword());
				}
			}

		}
	}

}
