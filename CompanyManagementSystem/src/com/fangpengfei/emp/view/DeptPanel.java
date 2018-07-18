package com.fangpengfei.emp.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.fangpengfei.emp.dao.DeptDao;
import com.fangpengfei.emp.entity.Dept;

public class DeptPanel extends JPanel {
	private static final long serialVersionUID = 73020147643756164L;
	private JPanel panel1, panel2;
	private JLabel label;// ��ǩ(����)
	private JTable table;// ���
	private JButton button1, button2, button3;
	private JScrollPane scrollPane;
	private DeptTableModel depttableModel;
	private AddDeptDialog addDeptDialog;
	private UpdateDeptDialog updateDeptDialog;
	
	private JPopupMenu popupMenu;// ����ʽ�˵�
	private JMenuItem delete, update, refresh;// ����ʽ�˵��ϵ�ɾ�����޸İ�ť

	public DeptPanel() {
		this.setLayout(new BorderLayout());
		panel1 = new JPanel();
		panel2 = new JPanel();
		label = new JLabel("������Ϣ����");

		button1 = new JButton("��Ӳ���");
		button2 = new JButton("ɾ������");
		button3 = new JButton("�޸Ĳ���");
		button1.addActionListener(new DeptPanelClick());
		button2.addActionListener(new DeptPanelClick());
		button3.addActionListener(new DeptPanelClick());

		popupMenu = new JPopupMenu();
		delete = new JMenuItem("ɾ��");
		update = new JMenuItem("�޸�");
		refresh = new JMenuItem("ˢ��");
		delete.addActionListener(new RightClick());
		update.addActionListener(new RightClick());
		refresh.addActionListener(new RightClick());
		
		table = new JTable(new DeptTableModel());// ��tableModel�е�������ӽ�table
		table.addMouseListener(new TableClick());// ���ñ��ļ����¼�
		
		scrollPane = new JScrollPane(table);
		addDeptDialog = new AddDeptDialog();
		updateDeptDialog = new UpdateDeptDialog();

		panel1.add(label);
		panel2.add(button1);
		panel2.add(button2);
		panel2.add(button3);

		// ��ӵ���ʽ�˵��еļ����¼�
		popupMenu.add(delete);
		popupMenu.add(update);
		popupMenu.add(refresh);
		
		

		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.SOUTH);
		this.add(scrollPane);
	}

	public void refreshDeptTable(){
		table.setModel(new DeptTableModel());
	}
	
	// ���ü����Ҽ����ĵ���¼�
	class TableClick implements MouseListener {


		@Override
		public void mousePressed(MouseEvent e) {
			// ����1:
			// �������¼�ָ���ұ���갴�����򷵻� true��
			if (SwingUtilities.isRightMouseButton(e)) {
				// table.rowAtPoint(Point point): ���� point���ڵ������������������� [0, getRowCount()-1]
				// ��Χ�ڣ��򷵻� -1��
				// Point ���� e.getPoint ��ʾ��������������
				// ͨ�����λ���ҵ����Ϊ����е���
				int row = table.rowAtPoint(e.getPoint());
				if (row == -1) {
					return;
				}
				// ��ȡ��ѡ�е���
				int[] rows = table.getSelectedRows();
				boolean inSelected = false;
				// �жϵ�ǰ�Ҽ��������Ƿ���ѡ��
				for (int r : rows) {
					if (row == r) {
						inSelected = true;
						break;
					}
				}
				// ��ǰ����Ҽ���������в���ѡ���������ʾѡ����
				if (!inSelected) {
					table.setRowSelectionInterval(row, row);
				}

				// ������ڳ�ʼ����� x��y λ������ʾ����ʽ�˵���
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}

			// ����2:
			// ���������ıȽ�:
			// (1)��ȡ�Ҽ���ʽ�Ĳ�ͬ
			// ����A: e.getButton() == MouseEvent.BUTTON3
			// ����B: SwingUtilities.isRightMouseButton(e)
			// ����A ���п��Ըı����������
			// ����B ʹ����λ���������Ƚ�
			// ��swingutility��jdk������javax��һ���֣������ұȽ�ϲ�����������ί�и����utility(����)�࣬��������������£�SwingUtilities��Ӯ�ҡ�
			// (2)
			// if (e.getButton() == MouseEvent.BUTTON3) {
			// // ͨ�����λ���ҵ����Ϊ����е���
			// int focusedRowIndex = table.rowAtPoint(e.getPoint());
			// if (focusedRowIndex == -1) {
			// return;
			// }
			// // �������ѡ����Ϊ��ǰ�Ҽ��������
			// table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
			// // �����˵�
			// popupMenu.show(table, e.getX(), e.getY());
			// }
			// e.getButton() == MouseEvent.BUTTON3

		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	// ���ü����Ҽ���ĵ�������¼�
	class RightClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == delete) {
				// System.out.println("ɾ����ť�������");
				deleteDeptClick();
			} else if (evt.getSource() == update) {
				// System.out.println("�޸İ�ť�������");
				updateDeptClick();
			} else if (evt.getSource() == refresh) {
//				System.out.println("ˢ�°�ť�������");
				table.setModel(new DeptTableModel());
			}
		}

	}

	// ����һ���ڲ��� ���ڼ����������û��ĵ���¼�
	class DeptPanelClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {
				
				addDeptDialog.setVisible(true);
				addDeptDialog.reset();// ˢ���ı���
			} else if (e.getSource() == button2) {
				deleteDeptClick();
			} else if (e.getSource() == button3) {
				updateDeptClick();
			}
		}
	}

	// ���� ��Ӳ��ŵĶԻ���
	class AddDeptDialog extends JDialog {
		private static final long serialVersionUID = -2678009932492130284L;
		private JPanel panel1, panel2, panel3;
		private JLabel label1, label2;
		private JTextField textField1, textField2;
		private JButton button1, button2;

		public AddDeptDialog() {
			this.setTitle("��Ӳ���");
			this.setSize(350, 180);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(3, 1));
			this.setVisible(false);// Ĭ�ϼ�Ϊ���岻�ɼ�,�����Ӱ�ťʱ����
			this.setModal(true);// ����ģ̬�Ի���,�������Ի���ʱ���ܲ�������
			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			label1 = new JLabel("����ID");
			label2 = new JLabel("��������");
			textField1 = new JTextField(20);
			// ����textField1ֻ��������
			// �Լ��̵ĵ���¼�������Ӧ�����������û��ļ�ֵ�����жϣ����������Ҫ��ֵ�����룬���Ǿ����ε�
			textField1.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					int keyChar = e.getKeyChar();
					if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

					} else {
						e.consume(); // �ؼ������ε��Ƿ�����
					}
				}
			});
			textField2 = new JTextField(20);
			button1 = new JButton("ȷ�����");
			button2 = new JButton("����");
			button1.addActionListener(new AddDialogClick());
			button2.addActionListener(new AddDialogClick());
			// ������ӹ���
			panel1.add(label1);
			panel1.add(textField1);
			panel2.add(label2);
			panel2.add(textField2);
			panel3.add(button1);
			panel3.add(button2);
			this.add(panel1);
			this.add(panel2);
			this.add(panel3);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}

		class AddDialogClick implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					// System.out.println("ȷ����Ӱ�ť�������");
					// ����û�˼·:
					// 1.����û����������
					String deptId = textField1.getText().trim();
					String deptName = textField2.getText().trim();
					// 2.�ж��û����������
					// (1) �жϲ���ID�Ͳ������Ʋ���Ϊ��
					if (null == deptId || "".equals(deptId)) {
						JOptionPane.showMessageDialog(panel1, "����ID����Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == deptName || "".equals(deptName)) {
						JOptionPane.showMessageDialog(panel1, "�������Ʋ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// (2) �жϲ���ID��1~100����,����������1~20λ֮��
					int id;
					try {
						id = Integer.parseInt(deptId);// ���ܻ����For input ""�� ���ָ�ʽ�쳣
						int deptNameLength = deptName.length();
						if (id > 100 || id < 1) {
							JOptionPane.showMessageDialog(panel1, "����IDӦ��1-100����,����������", "����",
									JOptionPane.ERROR_MESSAGE);
							return;
						} else if (deptNameLength > 20) {
							JOptionPane.showMessageDialog(panel1, "�������Ƴ���Ӧ��20������������,����������", "����",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (NumberFormatException e1) {
						// String errorMessage = e1.getMessage();
						// JOptionPane.showMessageDialog(panel1, errorMessage, "����",
						// JOptionPane.ERROR_MESSAGE);
						return;
					}

					// 3.�������ݿ�,������UserDao.addUser()
					DeptDao deptDao = new DeptDao();
					try {// �����Ӳ���ʱ,���ܻ��׳��쳣,��Ҫ�����쳣��������ʾ�����öԻ�����
						deptDao.addDpet(new Dept(id, deptName));
//						ManagerFrame managerFrame = new ManagerFrame();
					} catch (Exception e2) {
						String errorMessage = e2.getMessage();
						JOptionPane.showMessageDialog(panel1, errorMessage, "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 4.���ضԻ���
					addDeptDialog.setVisible(false);
					// 5.ˢ���û��������,���´���DeptModel����,�����ݴ�����
					table.setModel(new DeptTableModel());
					reset();
				} else if (e.getSource() == button2) {
					// System.out.println("���ð�ť�������");
					reset();
				}
			}

		}

		// ��������ı���ķ���
		private void reset() {
			textField1.setText("");
			textField2.setText("");
		}
	}

	// ���õ��ɾ�����ź�Ĳ���
	private void deleteDeptClick() {
		// ˼·:1.����û�ѡ�������
		int row = table.getSelectedRow();// �����û�ѡ�������,���û��ѡ��,����-1
		if (-1 == row) {
			JOptionPane.showMessageDialog(panel1, "��Ҫѡ��һ��", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// ɾ��ʱӦ����ȷ�Ͽ�,��ֹ��ɾ��.
		// ���"��"ʱ����0;���"��"ʱ����1;���"ȡ��"ʱ,����2
		int choose = JOptionPane.showConfirmDialog(panel1, "�Ƿ�ȷ��ɾ��");
		if (choose == 0) {
			// 2.ͨ���û�ѡ��������ò���ID
			// ���Ը�DeptTableModel���getRowData����,��rowData�з�װ�������з�������������
			DeptTableModel tableModel = new DeptTableModel();
			Vector<Vector<String>> rowData = tableModel.getRowData();
			
			try {
				Vector<String> vector = rowData.get(row);// �����û�ѡ�����,����ѡ���е�����
				// ѡ������0������ID(String),1����������,���ز���ID������ת��int����
				int deptId = Integer.parseInt(vector.get(0));
				// 3.�������ݿ�
				DeptDao deptDao = new DeptDao();
				deptDao.deleteDept(deptId);
			} catch(ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(panel1, "���ݴ���,������ѡ��");
				table.setModel(new DeptTableModel());
				return;
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(panel1, e2.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 4.ˢ�±��
			table.setModel(new DeptTableModel());
		}
	}

	// ���õ���޸Ĳ��ź�Ĳ���
	private void updateDeptClick() {
		// ˼·:
		// 1.����û�ѡ�������
		int row = table.getSelectedRow();// �����û�ѡ�������,���û��ѡ��,����-1
		if (-1 == row) {
			JOptionPane.showMessageDialog(panel1, "��Ҫѡ��һ��", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 2.����û�ѡ��������еĲ���ID,��rowData�з�װ�������з��������������еĲ���ID
		DeptTableModel tableModel = new DeptTableModel();
		Vector<Vector<String>> rowData = tableModel.getRowData();
		try {
			Vector<String> vector = rowData.get(row);// �����û�ѡ�����,����ѡ���е�����
			// ѡ������0������ID(String),1����������,���ز���ID������ת��int����
			int deptId = Integer.parseInt(vector.get(0));
			// 3.�������ݿ�,ͨ������ID���Ҳ���
			DeptDao deptDao = new DeptDao();
			Dept dept = deptDao.getOneDept(deptId);// �ö������ڻ�������
			// 4.�����û����洰��,�����Ե����ݽ�����ʾ
			// updateDialog.setVisible(true);
			updateDeptDialog.show(dept);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(panel1, "���ݴ���,������ѡ��");
			table.setModel(new DeptTableModel());
			return;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(panel1, e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	// �����޸� ���ŵĶԻ���
	class UpdateDeptDialog extends JDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4784385059475141306L;
		private JPanel panel1, panel2;
		private JLabel label;
		private JTextField textField;
		private JButton button1, button2;

		private Dept dept;// ȫ�ֱ���,Ϊ�����������������ڲ�����ʹ��Dept����,���ݿ��е�����

		// �÷������ڻ�������
		public void show(Dept dept) {
			this.dept = dept;// ��ͨ��rowData�д�������dept��ֵ��UpdateDialog�е�dept
			this.setTitle("�����޸�" + " " + dept.getName() + " " + "����Ϣ");
			textField.setText(dept.getName());
			updateDeptDialog.setVisible(true);
		}

		public UpdateDeptDialog() {
			// this.setTitle("�޸Ĳ���");
			this.setSize(350, 180);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(2, 1));
			this.setVisible(false);// Ĭ�ϼ�Ϊ���岻�ɼ�,�����Ӱ�ťʱ����
			this.setModal(true);// ����ģ̬�Ի���,�������Ի���ʱ���ܲ�������
			panel1 = new JPanel();
			panel1 = new JPanel();
			panel2 = new JPanel();
			label = new JLabel("��������");

			textField = new JTextField(20);
			button1 = new JButton("ȷ���޸�");
			button2 = new JButton("����");
			button1.addActionListener(new UpdateDialogClick());
			button2.addActionListener(new UpdateDialogClick());
			// ������ӹ���
			panel1.add(label);
			panel1.add(textField);
			panel2.add(button1);
			panel2.add(button2);
			this.add(panel1);
			this.add(panel2);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}

		class UpdateDialogClick implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					// System.out.println("ȷ���޸İ�ť�������");
					// ˼·:(1)���ı����л���û��޸�֮�������
					String deptName = textField.getText().trim();
					int deptId = dept.getId();
					// (2)�������ݿ�
					DeptDao deptDao = new DeptDao();
					try {
						deptDao.updateDept(new Dept(deptId, deptName));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(panel1, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// (3)���ضԻ���
					updateDeptDialog.setVisible(false);
					// (4)ˢ������
					table.setModel(new DeptTableModel());
				} else if (e.getSource() == button2) {
					// System.out.println("���ð�ť�������");
					// ע��:��������ʱ,���õ�û�б��޸ĵ�״̬
					textField.setText(dept.getName());
				}

			}

		}

	}

}
