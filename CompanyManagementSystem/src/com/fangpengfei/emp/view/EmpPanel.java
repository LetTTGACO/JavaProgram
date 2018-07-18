package com.fangpengfei.emp.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
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

import com.fangpengfei.emp.dao.DeptDao;
import com.fangpengfei.emp.dao.EmpDao;
import com.fangpengfei.emp.entity.Dept;
import com.fangpengfei.emp.entity.Emp;

public class EmpPanel extends JPanel {
	private static final long serialVersionUID = 4246140135713253444L;
	private JPanel panel1, panel2;
	private JLabel label;// ��ǩ(����)
	private JTable table;// ���
	private JScrollPane scrollPane;
	private JButton button1, button2, button3;
	private EmpTableModel empTableModel;
	private AddEmpDialog addEmpDialog;
	private UpdateEmpDialog updateEmpDialog;
	private TableClick tableClick;
	private RightClick rightClick;

	private JPopupMenu popupMenu;
	private JMenuItem update, delete, refresh;
	
	public EmpPanel() {
		this.setLayout(new BorderLayout());
		panel1 = new JPanel();
		panel2 = new JPanel();
		label = new JLabel("Ա����Ϣ����");

		button1 = new JButton("���Ա��");
		button2 = new JButton("ɾ��Ա��");
		button3 = new JButton("�޸�Ա��");
		button1.addActionListener(new EmpPanelClick());
		button2.addActionListener(new EmpPanelClick());
		button3.addActionListener(new EmpPanelClick());

		popupMenu = new JPopupMenu();
		update = new JMenuItem("�޸�");
		delete = new JMenuItem("ɾ��");
		refresh = new JMenuItem("ˢ��");
		rightClick = new RightClick();
		update.addActionListener(rightClick);
		delete.addActionListener(rightClick);
		refresh.addActionListener(rightClick);
		table = new JTable(new EmpTableModel());
		tableClick = new TableClick();
		table.addMouseListener(tableClick);

		scrollPane = new JScrollPane(table);
		addEmpDialog = new AddEmpDialog();
		updateEmpDialog = new UpdateEmpDialog();

		panel1.add(label);// ��������ӵ�panel1��
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

	public void refreshEmpTable(){
		table.setModel(new EmpTableModel());
	}
	
	
	// ����һ���ڲ���,���ڴ�����ĵ���¼�,����������
	class TableClick implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				// ͨ��������������Ҽ�ѡ�е�����,������ڷ���-1
				int selectRow = table.rowAtPoint(e.getPoint());
				if (-1 == selectRow) {
					return;
				}
				// ��ȡ��ѡ�е�����
				int[] selectedRows = table.getSelectedRows();
				boolean isSelected = false;
				for (int i : selectedRows) {
					if (i == selectRow) {
						isSelected = true;
						break;
					}
				}
				// ��ǰ����Ҽ���������в���ѡ���������ʾѡ����
				if (!isSelected) {
					table.setRowSelectionInterval(selectRow, selectRow);
				}
				// ���ֵ���ʽ����
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
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

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	// �����ڲ���,���ڴ�����ʽ���ڵĵ���¼�
	class RightClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == delete) {
//				System.out.println("ɾ����ť�������");
				deleteEmpClick();
			} else if (e.getSource() == update) {
//				System.out.println("�޸İ�ť�����");
				updateEmpClick();
			} else if (e.getSource() == refresh) {
				//System.out.println("ˢ�°�ť�����");
				table.setModel(new EmpTableModel());
			}
		}

	}

	// ����һ���ڲ���,���ڼ����������û��İ�ť����¼�
	class EmpPanelClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {
				// System.out.println("���Ա���������");
				addEmpDialog.setVisible(true);
				addEmpDialog.reset();
			} else if (e.getSource() == button2) {
				// System.out.println("ɾ��Ա���������");
				deleteEmpClick();
			} else if (e.getSource() == button3) {
				// System.out.println("�޸�Ա���������");
				updateEmpClick();
			}
		}
	}

	// ����һ��ɾ������,�����������ڲ������
	private void deleteEmpClick() {
		// System.out.println("ɾ��Ա���������");
		// ˼·:
		// 1.����table�е�table.getSelectedRow()������������
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(panel1, "��ѡ��һ��", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 2.emp������ȫ������װ��EmpTableModel�е�rowDataǶ�׼�����,ͨ����EmpTableModel������getRowData()����
		EmpTableModel tableModel = new EmpTableModel();
		Vector<Vector<String>> data = tableModel.getRowData();
		int choose = JOptionPane.showConfirmDialog(panel1, "�Ƿ�ɾ��");
		if (choose == 0) {
			try {
				int empId = Integer.parseInt(data.get(row).get(0));
				EmpDao empDao = new EmpDao();
				empDao.deleteEmp(empId);
				table.setModel(new DeptTableModel());
			} catch (ArrayIndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(panel1, "���ݴ���,������ѡ��");
				table.setModel(new DeptTableModel());
				return;
			} catch (Exception e) {
				String errorMessage = e.getMessage();
				JOptionPane.showMessageDialog(panel1, errorMessage, "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		table.setModel(new EmpTableModel());
	}

	// ����һ���޸ķ���,�����������ڲ������
	private void updateEmpClick() {
		// System.out.println("�޸�Ա���������");
		// ˼·:
		// 1.ͨ���û�ѡ���������ȡԱ��id,�������ж�
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(panel1, "��ѡ��һ��", "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 2.��ȡrowData�з�װ��emp����,������get�������Ա����id
		empTableModel = new EmpTableModel();
		Vector<Vector<String>> rowData = empTableModel.getRowData();
		EmpDao empDao = new EmpDao();
		try {
			int empId = Integer.parseInt(rowData.get(row).get(0));
			// 3.����Ա����id���Ա������Ϣ,������һ��Ա��
			Emp emp = empDao.getOneEmp(empId);
			updateEmpDialog.show(emp);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(panel1, "���ݴ���,������ѡ��");
			table.setModel(new DeptTableModel());
			return;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	// ����һ���ڲ���,�����������Ա�������Ĵ���
	class AddEmpDialog extends JDialog {
		private static final long serialVersionUID = -7622238543162578310L;
		private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
		private JLabel label1, label2, label3, label4, label5;
		private JTextField textField1, textField2, textField3, textField4, textField5;
		private JButton button1, button2;

		public AddEmpDialog() {
			this.setTitle("���Ա��");
			this.setSize(400, 400);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(6, 1));
			this.setModal(true);

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();
			panel6 = new JPanel();

			label1 = new JLabel("Ա������");
			label2 = new JLabel("Ա���Ա�");
			label3 = new JLabel("��ͥסַ");
			label4 = new JLabel("Ա������");
			label5 = new JLabel("��������");

			textField1 = new JTextField(20);
			textField2 = new JTextField(20);
			textField3 = new JTextField(20);
			textField4 = new JTextField(20);
			textField5 = new JTextField(20);

			button1 = new JButton("ȷ�����");
			button2 = new JButton("����");
			button1.addActionListener(new AddEmpClick());
			button2.addActionListener(new AddEmpClick());

			panel1.add(label1);
			panel1.add(textField1);
			panel2.add(label2);
			panel2.add(textField2);
			panel3.add(label3);
			panel3.add(textField3);
			panel4.add(label4);
			panel4.add(textField4);
			panel5.add(label5);
			panel5.add(textField5);
			panel6.add(button1);
			panel6.add(button2);

			this.add(panel1);
			this.add(panel2);
			this.add(panel3);
			this.add(panel4);
			this.add(panel5);
			this.add(panel6);

			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		}

		class AddEmpClick implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					// System.out.println("ȷ����Ӱ�ť�����");
					// ˼·:
					// 1.��ȡ�û����������
					String empName = textField1.getText().trim();
					String empGender = textField2.getText().trim();
					String empAddress = textField3.getText().trim();
					String empSalary = textField4.getText().trim();// ���ݿ���Ϊdicmal,Ӧ��ת��Ϊdouble����
					String empDept_Name = textField5.getText().trim();
					// 2.���ı����е����ݽ����ж�
					// (1)�����ı��򶼲���Ϊnull���߿�
					if (null == empName || "".equals(empName)) {
						JOptionPane.showMessageDialog(panel1, "�û�������Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empGender || "".equals(empGender)) {
						JOptionPane.showMessageDialog(panel1, "�Ա���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empDept_Name || "".equals(empDept_Name)) {
						JOptionPane.showMessageDialog(panel1, "�������Ʋ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
						// �Ա��ַ�����Ϊ1-3λ
					} else if (empGender.length() > 3 || empGender.length() < 1) {
						JOptionPane.showMessageDialog(panel1, "�Ա�һ���������ֳ���", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empSalary || "".equals(empSalary)) {
						JOptionPane.showMessageDialog(panel1, "���ʲ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empDept_Name || "".equals(empDept_Name)) {
						JOptionPane.showMessageDialog(panel1, "�������Ʋ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// (2)���ű���ô���
					DeptDao deptDao = new DeptDao();
					List<Dept> depts = deptDao.getAllDept();
					int DeptID = 0;
					boolean isExist = false;// ������Ŵ��ڷ���1
					for (Dept dept : depts) {
						String deptName = dept.getName();
						if (deptName.equals(empDept_Name)) {
							DeptID = dept.getId();
							isExist = true;
						}
					}
					// 3.������Ŵ���,�������ݿ�,�������
					if (isExist == true) {
						EmpDao empDao = new EmpDao();
						try {
							empDao.addEmp(
									new Emp(0, empName, empGender, empAddress, Double.parseDouble(empSalary), DeptID));
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(panel1, "�Ƿ��ַ�����������", "����", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(panel1, "���Ų�����", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 4.�����Ϻ����ضԻ���
					addEmpDialog.setVisible(false);
					// 5.ˢ���ı���ͽ���
					table.setModel(new EmpTableModel());
					reset();//��Ҫ�ɲ�Ҫ
				} else if (e.getSource() == button2) {
					// System.out.println("���ð�ť�������");
					int choose = JOptionPane.showConfirmDialog(panel1, "�Ƿ�ȷ������");
					if (choose == 0) {
						reset();
					}

				}
			}

		}

		private void reset() {
			textField1.setText("");
			textField2.setText("");
			textField3.setText("");
			textField4.setText("");
			textField5.setText("");

		}
	}

	class UpdateEmpDialog extends JDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3551278442566845050L;
		private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
		private JLabel label1, label2, label3, label4, label5;
		private JTextField textField1, textField2, textField3, textField4, textField5;
		private JButton button1, button2;
		private Emp emp;

		public UpdateEmpDialog() {
			this.setTitle("�޸�Ա��");
			this.setSize(400, 400);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(6, 1));
			this.setModal(true);

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();
			panel6 = new JPanel();

			label1 = new JLabel("Ա������");
			label2 = new JLabel("Ա���Ա�");
			label3 = new JLabel("��ͥסַ");
			label4 = new JLabel("Ա������");
			label5 = new JLabel("��������");

			textField1 = new JTextField(20);
			textField2 = new JTextField(20);
			textField3 = new JTextField(20);
			textField4 = new JTextField(20);
			textField5 = new JTextField(20);

			button1 = new JButton("ȷ���޸�");
			button2 = new JButton("����");
			button1.addActionListener(new UpdateEmpClick());
			button2.addActionListener(new UpdateEmpClick());

			panel1.add(label1);
			panel1.add(textField1);
			panel2.add(label2);
			panel2.add(textField2);
			panel3.add(label3);
			panel3.add(textField3);
			panel4.add(label4);
			panel4.add(textField4);
			panel5.add(label5);
			panel5.add(textField5);
			panel6.add(button1);
			panel6.add(button2);

			this.add(panel1);
			this.add(panel2);
			this.add(panel3);
			this.add(panel4);
			this.add(panel5);
			this.add(panel6);

			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}

		class UpdateEmpClick implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					// System.out.println("ȷ���޸İ�ť�������");
					// ˼·:
					// 1.���ı���������
					String empName = textField1.getText().trim();
					String empGender = textField2.getText().trim();
					String empAddress = textField3.getText().trim();
					String empSalary = textField4.getText().trim();
					String empDept_Name = textField5.getText().trim();
					// 2.���ı����е����ݽ����ж�
					// (1)�����ı��򶼲���Ϊnull���߿�
					if (null == empName || "".equals(empName)) {
						JOptionPane.showMessageDialog(panel1, "�û�������Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empGender || "".equals(empGender)) {
						JOptionPane.showMessageDialog(panel1, "�Ա���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (empGender.length() > 3 || empGender.length() < 1) {
						JOptionPane.showMessageDialog(panel1, "�Ա�һ���������ֳ���", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empAddress || "".equals(empAddress)) {
						JOptionPane.showMessageDialog(panel1, "��ͥסַ����Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empSalary || "".equals(empSalary)) {
						JOptionPane.showMessageDialog(panel1, "���ʲ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empDept_Name || "".equals(empDept_Name)) {
						JOptionPane.showMessageDialog(panel1, "�������Ʋ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// (2)���ű���ô���
					DeptDao deptDao = new DeptDao();
					List<Dept> depts = deptDao.getAllDept();
					int DeptID = 0;
					int test = 0;// ������Ŵ��ڷ���1
					for (Dept dept : depts) {
						String deptName = dept.getName();
						if (deptName.equals(empDept_Name)) {
							DeptID = dept.getId();
							test = 1;
							break;
						}
					}

					// 3.������Ŵ���,�������ݿ�,�������
					if (test == 1) {
						EmpDao empDao = new EmpDao();
						try {
							empDao.updateEmp(new Emp(emp.getId(), empName, empGender, empAddress,
									Double.parseDouble(empSalary), DeptID));
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(panel1, "�Ƿ��ַ�����������", "����", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(panel1, "���Ų�����", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 4.�����Ϻ����ضԻ���
					updateEmpDialog.setVisible(false);

					// 5.ˢ�½���
					table.setModel(new EmpTableModel());

				} else if (e.getSource() == button2) {
					// System.out.println("���ð�ť�������");
					DeptDao deptDao = new DeptDao();
					Dept dept = deptDao.getOneDept(emp.getDetpId());
					String deptName = dept.getName();
					textField1.setText(emp.getName());
					textField2.setText(emp.getGender());
					textField3.setText(emp.getAddress());
					textField4.setText(Double.toString(emp.getSalary()));
					textField5.setText(deptName);
				}

			}

		}

		private void show(Emp emp) {
			this.emp = emp;
			DeptDao deptDao = new DeptDao();
			Dept dept = deptDao.getOneDept(emp.getDetpId());
			String deptName = dept.getName();
			textField1.setText(emp.getName());
			textField2.setText(emp.getGender());
			textField3.setText(emp.getAddress());
			textField4.setText(Double.toString(emp.getSalary()));
			textField5.setText(deptName);
			this.setVisible(true);

		}
	}
}
