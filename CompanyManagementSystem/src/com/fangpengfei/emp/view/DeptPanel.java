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
	private JLabel label;// 标签(文字)
	private JTable table;// 表格
	private JButton button1, button2, button3;
	private JScrollPane scrollPane;
	private DeptTableModel depttableModel;
	private AddDeptDialog addDeptDialog;
	private UpdateDeptDialog updateDeptDialog;
	
	private JPopupMenu popupMenu;// 弹出式菜单
	private JMenuItem delete, update, refresh;// 弹出式菜单上的删除和修改按钮

	public DeptPanel() {
		this.setLayout(new BorderLayout());
		panel1 = new JPanel();
		panel2 = new JPanel();
		label = new JLabel("部门信息管理");

		button1 = new JButton("添加部门");
		button2 = new JButton("删除部门");
		button3 = new JButton("修改部门");
		button1.addActionListener(new DeptPanelClick());
		button2.addActionListener(new DeptPanelClick());
		button3.addActionListener(new DeptPanelClick());

		popupMenu = new JPopupMenu();
		delete = new JMenuItem("删除");
		update = new JMenuItem("修改");
		refresh = new JMenuItem("刷新");
		delete.addActionListener(new RightClick());
		update.addActionListener(new RightClick());
		refresh.addActionListener(new RightClick());
		
		table = new JTable(new DeptTableModel());// 将tableModel中的数据添加进table
		table.addMouseListener(new TableClick());// 设置表格的监听事件
		
		scrollPane = new JScrollPane(table);
		addDeptDialog = new AddDeptDialog();
		updateDeptDialog = new UpdateDeptDialog();

		panel1.add(label);
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

	public void refreshDeptTable(){
		table.setModel(new DeptTableModel());
	}
	
	// 设置监听右键表格的点击事件
	class TableClick implements MouseListener {


		@Override
		public void mousePressed(MouseEvent e) {
			// 方法1:
			// 如果鼠标事件指定右边鼠标按键，则返回 true。
			if (SwingUtilities.isRightMouseButton(e)) {
				// table.rowAtPoint(Point point): 返回 point所在的行索引；如果结果不在 [0, getRowCount()-1]
				// 范围内，则返回 -1。
				// Point 坐标 e.getPoint 表示获得鼠标点击的坐标
				// 通过点击位置找到点击为表格中的行
				int row = table.rowAtPoint(e.getPoint());
				if (row == -1) {
					return;
				}
				// 获取已选中的行
				int[] rows = table.getSelectedRows();
				boolean inSelected = false;
				// 判断当前右键所在行是否已选中
				for (int r : rows) {
					if (row == r) {
						inSelected = true;
						break;
					}
				}
				// 当前鼠标右键点击所在行不被选中则高亮显示选中行
				if (!inSelected) {
					table.setRowSelectionInterval(row, row);
				}

				// 在相对于初始组件的 x、y 位置上显示弹出式菜单。
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}

			// 方法2:
			// 两个方法的比较:
			// (1)获取右键方式的不同
			// 方法A: e.getButton() == MouseEvent.BUTTON3
			// 方法B: SwingUtilities.isRightMouseButton(e)
			// 方法A 中有可以改变的神奇数字
			// 方法B 使用了位操作数来比较
			// 而swingutility是jdk附带的javax的一部分，所以我比较喜欢将这个责任委托给这个utility(助手)类，所以在这种情况下，SwingUtilities是赢家。
			// (2)
			// if (e.getButton() == MouseEvent.BUTTON3) {
			// // 通过点击位置找到点击为表格中的行
			// int focusedRowIndex = table.rowAtPoint(e.getPoint());
			// if (focusedRowIndex == -1) {
			// return;
			// }
			// // 将表格所选项设为当前右键点击的行
			// table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
			// // 弹出菜单
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

	// 设置监听右键后的点击处理事件
	class RightClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == delete) {
				// System.out.println("删除按钮被点击了");
				deleteDeptClick();
			} else if (evt.getSource() == update) {
				// System.out.println("修改按钮被点击了");
				updateDeptClick();
			} else if (evt.getSource() == refresh) {
//				System.out.println("刷新按钮被点击了");
				table.setModel(new DeptTableModel());
			}
		}

	}

	// 定义一个内部类 用于监听并处理用户的点击事件
	class DeptPanelClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {
				
				addDeptDialog.setVisible(true);
				addDeptDialog.reset();// 刷新文本框
			} else if (e.getSource() == button2) {
				deleteDeptClick();
			} else if (e.getSource() == button3) {
				updateDeptClick();
			}
		}
	}

	// 设置 添加部门的对话框
	class AddDeptDialog extends JDialog {
		private static final long serialVersionUID = -2678009932492130284L;
		private JPanel panel1, panel2, panel3;
		private JLabel label1, label2;
		private JTextField textField1, textField2;
		private JButton button1, button2;

		public AddDeptDialog() {
			this.setTitle("添加部门");
			this.setSize(350, 180);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(3, 1));
			this.setVisible(false);// 默认即为窗体不可见,点击添加按钮时出现
			this.setModal(true);// 设置模态对话框,当操作对话框时不能操作窗体
			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			label1 = new JLabel("部门ID");
			label2 = new JLabel("部门名称");
			textField1 = new JTextField(20);
			// 限制textField1只输入数字
			// 对键盘的点击事件进行响应，进而对所敲击的键值进行判断，如果是所需要的值则输入，不是就屏蔽掉
			textField1.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					int keyChar = e.getKeyChar();
					if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

					} else {
						e.consume(); // 关键，屏蔽掉非法输入
					}
				}
			});
			textField2 = new JTextField(20);
			button1 = new JButton("确认添加");
			button2 = new JButton("重置");
			button1.addActionListener(new AddDialogClick());
			button2.addActionListener(new AddDialogClick());
			// 设置添加过程
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
					// System.out.println("确认添加按钮被点击了");
					// 添加用户思路:
					// 1.获得用户输入的数据
					String deptId = textField1.getText().trim();
					String deptName = textField2.getText().trim();
					// 2.判断用户输入的数据
					// (1) 判断部门ID和部门名称不能为空
					if (null == deptId || "".equals(deptId)) {
						JOptionPane.showMessageDialog(panel1, "部门ID不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (null == deptName || "".equals(deptName)) {
						JOptionPane.showMessageDialog(panel1, "部门名称不能为空", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// (2) 判断部门ID在1~100以内,部门名称在1~20位之间
					int id;
					try {
						id = Integer.parseInt(deptId);// 可能会出现For input ""的 数字格式异常
						int deptNameLength = deptName.length();
						if (id > 100 || id < 1) {
							JOptionPane.showMessageDialog(panel1, "部门ID应在1-100以内,请重新输入", "错误",
									JOptionPane.ERROR_MESSAGE);
							return;
						} else if (deptNameLength > 20) {
							JOptionPane.showMessageDialog(panel1, "部门名称长度应在20个字以内以内,请重新输入", "错误",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (NumberFormatException e1) {
						// String errorMessage = e1.getMessage();
						// JOptionPane.showMessageDialog(panel1, errorMessage, "错误",
						// JOptionPane.ERROR_MESSAGE);
						return;
					}

					// 3.连接数据库,调用用UserDao.addUser()
					DeptDao deptDao = new DeptDao();
					try {// 在增加部门时,可能会抛出异常,需要捕获异常并将其显示在内置对话框中
						deptDao.addDpet(new Dept(id, deptName));
//						ManagerFrame managerFrame = new ManagerFrame();
					} catch (Exception e2) {
						String errorMessage = e2.getMessage();
						JOptionPane.showMessageDialog(panel1, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// 4.隐藏对话框
					addDeptDialog.setVisible(false);
					// 5.刷新用户管理界面,重新创建DeptModel对象,把数据传入表格
					table.setModel(new DeptTableModel());
					reset();
				} else if (e.getSource() == button2) {
					// System.out.println("重置按钮被点击了");
					reset();
				}
			}

		}

		// 定义清空文本框的方法
		private void reset() {
			textField1.setText("");
			textField2.setText("");
		}
	}

	// 设置点击删除部门后的操作
	private void deleteDeptClick() {
		// 思路:1.获得用户选择的行数
		int row = table.getSelectedRow();// 返回用户选择的行数,如果没有选择,返回-1
		if (-1 == row) {
			JOptionPane.showMessageDialog(panel1, "需要选择一行", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 删除时应设置确认框,防止误删除.
		// 点击"是"时返回0;点击"否"时返回1;点击"取消"时,返回2
		int choose = JOptionPane.showConfirmDialog(panel1, "是否确认删除");
		if (choose == 0) {
			// 2.通过用户选择行数获得部门ID
			// 可以给DeptTableModel添加getRowData方法,从rowData中封装的数据中返回所有行数据
			DeptTableModel tableModel = new DeptTableModel();
			Vector<Vector<String>> rowData = tableModel.getRowData();
			
			try {
				Vector<String> vector = rowData.get(row);// 根据用户选择的行,返回选中行的数据
				// 选中行中0代表部门ID(String),1代表部门名称,返回部门ID并进行转成int类型
				int deptId = Integer.parseInt(vector.get(0));
				// 3.连接数据库
				DeptDao deptDao = new DeptDao();
				deptDao.deleteDept(deptId);
			} catch(ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(panel1, "数据错误,请重新选择");
				table.setModel(new DeptTableModel());
				return;
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(panel1, e2.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 4.刷新表格
			table.setModel(new DeptTableModel());
		}
	}

	// 设置点击修改部门后的操作
	private void updateDeptClick() {
		// 思路:
		// 1.获得用户选择的行数
		int row = table.getSelectedRow();// 返回用户选择的行数,如果没有选择,返回-1
		if (-1 == row) {
			JOptionPane.showMessageDialog(panel1, "需要选择一行", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 2.获得用户选择的行数中的部门ID,从rowData中封装的数据中返回所有行数据中的部门ID
		DeptTableModel tableModel = new DeptTableModel();
		Vector<Vector<String>> rowData = tableModel.getRowData();
		try {
			Vector<String> vector = rowData.get(row);// 根据用户选择的行,返回选中行的数据
			// 选中行中0代表部门ID(String),1代表部门名称,返回部门ID并进行转成int类型
			int deptId = Integer.parseInt(vector.get(0));
			// 3.连接数据库,通过部门ID查找部门
			DeptDao deptDao = new DeptDao();
			Dept dept = deptDao.getOneDept(deptId);// 该对象用于回显数据
			// 4.弹出用户界面窗口,将回显的数据进行显示
			// updateDialog.setVisible(true);
			updateDeptDialog.show(dept);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(panel1, "数据错误,请重新选择");
			table.setModel(new DeptTableModel());
			return;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(panel1, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	// 设置修改 部门的对话框
	class UpdateDeptDialog extends JDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4784385059475141306L;
		private JPanel panel1, panel2;
		private JLabel label;
		private JTextField textField;
		private JButton button1, button2;

		private Dept dept;// 全局变量,为了在其他方法或者内部内中使用Dept对象,数据库中的数据

		// 该方法用于回显数据
		public void show(Dept dept) {
			this.dept = dept;// 让通过rowData中传回来的dept赋值给UpdateDialog中的dept
			this.setTitle("正在修改" + " " + dept.getName() + " " + "的信息");
			textField.setText(dept.getName());
			updateDeptDialog.setVisible(true);
		}

		public UpdateDeptDialog() {
			// this.setTitle("修改部门");
			this.setSize(350, 180);
			this.setLocationRelativeTo(null);
			this.setLayout(new GridLayout(2, 1));
			this.setVisible(false);// 默认即为窗体不可见,点击添加按钮时出现
			this.setModal(true);// 设置模态对话框,当操作对话框时不能操作窗体
			panel1 = new JPanel();
			panel1 = new JPanel();
			panel2 = new JPanel();
			label = new JLabel("部门名称");

			textField = new JTextField(20);
			button1 = new JButton("确认修改");
			button2 = new JButton("重置");
			button1.addActionListener(new UpdateDialogClick());
			button2.addActionListener(new UpdateDialogClick());
			// 设置添加过程
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
					// System.out.println("确认修改按钮被点击了");
					// 思路:(1)从文本框中获得用户修改之后的数据
					String deptName = textField.getText().trim();
					int deptId = dept.getId();
					// (2)连接数据库
					DeptDao deptDao = new DeptDao();
					try {
						deptDao.updateDept(new Dept(deptId, deptName));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(panel1, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// (3)隐藏对话框
					updateDeptDialog.setVisible(false);
					// (4)刷新数据
					table.setModel(new DeptTableModel());
				} else if (e.getSource() == button2) {
					// System.out.println("重置按钮被点击了");
					// 注意:重置数据时,重置到没有被修改的状态
					textField.setText(dept.getName());
				}

			}

		}

	}

}
