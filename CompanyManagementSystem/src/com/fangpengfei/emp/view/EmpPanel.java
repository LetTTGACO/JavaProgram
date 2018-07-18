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
	private JLabel label;// 标签(文字)
	private JTable table;// 表格
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
		label = new JLabel("员工信息管理");

		button1 = new JButton("添加员工");
		button2 = new JButton("删除员工");
		button3 = new JButton("修改员工");
		button1.addActionListener(new EmpPanelClick());
		button2.addActionListener(new EmpPanelClick());
		button3.addActionListener(new EmpPanelClick());

		popupMenu = new JPopupMenu();
		update = new JMenuItem("修改");
		delete = new JMenuItem("删除");
		refresh = new JMenuItem("刷新");
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

		panel1.add(label);// 把文字添加到panel1中
		panel2.add(button1);
		panel2.add(button2);
		panel2.add(button3);

		// 添加弹出式菜单中的监听事件
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
	
	
	// 定义一个内部类,用于处理表格的点击事件,并弹出窗口
	class TableClick implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				// 通过点击的坐标获得右键选中的行数,如果不在返回-1
				int selectRow = table.rowAtPoint(e.getPoint());
				if (-1 == selectRow) {
					return;
				}
				// 获取已选中的行数
				int[] selectedRows = table.getSelectedRows();
				boolean isSelected = false;
				for (int i : selectedRows) {
					if (i == selectRow) {
						isSelected = true;
						break;
					}
				}
				// 当前鼠标右键点击所在行不被选中则高亮显示选中行
				if (!isSelected) {
					table.setRowSelectionInterval(selectRow, selectRow);
				}
				// 出现弹出式窗口
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

	// 定义内部类,用于处理弹出式窗口的点击事件
	class RightClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == delete) {
//				System.out.println("删除按钮被点击了");
				deleteEmpClick();
			} else if (e.getSource() == update) {
//				System.out.println("修改按钮被点击");
				updateEmpClick();
			} else if (e.getSource() == refresh) {
				//System.out.println("刷新按钮被点击");
				table.setModel(new EmpTableModel());
			}
		}

	}

	// 定义一个内部类,用于监听并处理用户的按钮点击事件
	class EmpPanelClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {
				// System.out.println("添加员工被点击了");
				addEmpDialog.setVisible(true);
				addEmpDialog.reset();
			} else if (e.getSource() == button2) {
				// System.out.println("删除员工被点击了");
				deleteEmpClick();
			} else if (e.getSource() == button3) {
				// System.out.println("修改员工被点击了");
				updateEmpClick();
			}
		}
	}

	// 定义一个删除方法,方便在其他内部类调用
	private void deleteEmpClick() {
		// System.out.println("删除员工被点击了");
		// 思路:
		// 1.调用table中的table.getSelectedRow()方法返回行数
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(panel1, "请选择一行", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 2.emp的数据全部被封装在EmpTableModel中的rowData嵌套集合中,通过在EmpTableModel中设置getRowData()方法
		EmpTableModel tableModel = new EmpTableModel();
		Vector<Vector<String>> data = tableModel.getRowData();
		int choose = JOptionPane.showConfirmDialog(panel1, "是否删除");
		if (choose == 0) {
			try {
				int empId = Integer.parseInt(data.get(row).get(0));
				EmpDao empDao = new EmpDao();
				empDao.deleteEmp(empId);
				table.setModel(new DeptTableModel());
			} catch (ArrayIndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(panel1, "数据错误,请重新选择");
				table.setModel(new DeptTableModel());
				return;
			} catch (Exception e) {
				String errorMessage = e.getMessage();
				JOptionPane.showMessageDialog(panel1, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		table.setModel(new EmpTableModel());
	}

	// 定义一个修改方法,方便在其他内部类访问
	private void updateEmpClick() {
		// System.out.println("修改员工被点击了");
		// 思路:
		// 1.通过用户选择的行数获取员工id,并进行判断
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(panel1, "请选择一行", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 2.获取rowData中封装的emp数据,根据其get方法获得员工的id
		empTableModel = new EmpTableModel();
		Vector<Vector<String>> rowData = empTableModel.getRowData();
		EmpDao empDao = new EmpDao();
		try {
			int empId = Integer.parseInt(rowData.get(row).get(0));
			// 3.根据员工的id获得员工的信息,并返回一个员工
			Emp emp = empDao.getOneEmp(empId);
			updateEmpDialog.show(emp);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(panel1, "数据错误,请重新选择");
			table.setModel(new DeptTableModel());
			return;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	// 定义一个内部类,用于设置添加员工弹出的窗口
	class AddEmpDialog extends JDialog {
		private static final long serialVersionUID = -7622238543162578310L;
		private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
		private JLabel label1, label2, label3, label4, label5;
		private JTextField textField1, textField2, textField3, textField4, textField5;
		private JButton button1, button2;

		public AddEmpDialog() {
			this.setTitle("添加员工");
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

			label1 = new JLabel("员工姓名");
			label2 = new JLabel("员工性别");
			label3 = new JLabel("家庭住址");
			label4 = new JLabel("员工工资");
			label5 = new JLabel("部门名称");

			textField1 = new JTextField(20);
			textField2 = new JTextField(20);
			textField3 = new JTextField(20);
			textField4 = new JTextField(20);
			textField5 = new JTextField(20);

			button1 = new JButton("确认添加");
			button2 = new JButton("重置");
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
					// System.out.println("确认添加按钮被点击");
					// 思路:
					// 1.获取用户输入的数据
					String empName = textField1.getText().trim();
					String empGender = textField2.getText().trim();
					String empAddress = textField3.getText().trim();
					String empSalary = textField4.getText().trim();// 数据库中为dicmal,应该转换为double类型
					String empDept_Name = textField5.getText().trim();
					// 2.对文本框中的数据进行判断
					// (1)所有文本框都不能为null或者空
					if (null == empName || "".equals(empName)) {
						JOptionPane.showMessageDialog(panel1, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empGender || "".equals(empGender)) {
						JOptionPane.showMessageDialog(panel1, "性别不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empDept_Name || "".equals(empDept_Name)) {
						JOptionPane.showMessageDialog(panel1, "部门名称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
						// 性别字符长度为1-3位
					} else if (empGender.length() > 3 || empGender.length() < 1) {
						JOptionPane.showMessageDialog(panel1, "性别一栏超出文字长度", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empSalary || "".equals(empSalary)) {
						JOptionPane.showMessageDialog(panel1, "工资不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empDept_Name || "".equals(empDept_Name)) {
						JOptionPane.showMessageDialog(panel1, "部门名称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// (2)部门必须得存在
					DeptDao deptDao = new DeptDao();
					List<Dept> depts = deptDao.getAllDept();
					int DeptID = 0;
					boolean isExist = false;// 如果部门存在返回1
					for (Dept dept : depts) {
						String deptName = dept.getName();
						if (deptName.equals(empDept_Name)) {
							DeptID = dept.getId();
							isExist = true;
						}
					}
					// 3.如果部门存在,连接数据库,添加数据
					if (isExist == true) {
						EmpDao empDao = new EmpDao();
						try {
							empDao.addEmp(
									new Emp(0, empName, empGender, empAddress, Double.parseDouble(empSalary), DeptID));
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(panel1, "非法字符请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(panel1, "部门不存在", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 4.添加完毕后隐藏对话框
					addEmpDialog.setVisible(false);
					// 5.刷新文本框和界面
					table.setModel(new EmpTableModel());
					reset();//可要可不要
				} else if (e.getSource() == button2) {
					// System.out.println("重置按钮被点击了");
					int choose = JOptionPane.showConfirmDialog(panel1, "是否确认重置");
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
			this.setTitle("修改员工");
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

			label1 = new JLabel("员工姓名");
			label2 = new JLabel("员工性别");
			label3 = new JLabel("家庭住址");
			label4 = new JLabel("员工工资");
			label5 = new JLabel("部门名称");

			textField1 = new JTextField(20);
			textField2 = new JTextField(20);
			textField3 = new JTextField(20);
			textField4 = new JTextField(20);
			textField5 = new JTextField(20);

			button1 = new JButton("确认修改");
			button2 = new JButton("重置");
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
					// System.out.println("确认修改按钮被点击了");
					// 思路:
					// 1.从文本框获得数据
					String empName = textField1.getText().trim();
					String empGender = textField2.getText().trim();
					String empAddress = textField3.getText().trim();
					String empSalary = textField4.getText().trim();
					String empDept_Name = textField5.getText().trim();
					// 2.对文本框中的数据进行判断
					// (1)所有文本框都不能为null或者空
					if (null == empName || "".equals(empName)) {
						JOptionPane.showMessageDialog(panel1, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empGender || "".equals(empGender)) {
						JOptionPane.showMessageDialog(panel1, "性别不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (empGender.length() > 3 || empGender.length() < 1) {
						JOptionPane.showMessageDialog(panel1, "性别一栏超出文字长度", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empAddress || "".equals(empAddress)) {
						JOptionPane.showMessageDialog(panel1, "家庭住址不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empSalary || "".equals(empSalary)) {
						JOptionPane.showMessageDialog(panel1, "工资不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == empDept_Name || "".equals(empDept_Name)) {
						JOptionPane.showMessageDialog(panel1, "部门名称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// (2)部门必须得存在
					DeptDao deptDao = new DeptDao();
					List<Dept> depts = deptDao.getAllDept();
					int DeptID = 0;
					int test = 0;// 如果部门存在返回1
					for (Dept dept : depts) {
						String deptName = dept.getName();
						if (deptName.equals(empDept_Name)) {
							DeptID = dept.getId();
							test = 1;
							break;
						}
					}

					// 3.如果部门存在,连接数据库,添加数据
					if (test == 1) {
						EmpDao empDao = new EmpDao();
						try {
							empDao.updateEmp(new Emp(emp.getId(), empName, empGender, empAddress,
									Double.parseDouble(empSalary), DeptID));
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(panel1, "非法字符请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(panel1, "部门不存在", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 4.添加完毕后隐藏对话框
					updateEmpDialog.setVisible(false);

					// 5.刷新界面
					table.setModel(new EmpTableModel());

				} else if (e.getSource() == button2) {
					// System.out.println("重置按钮被点击了");
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
