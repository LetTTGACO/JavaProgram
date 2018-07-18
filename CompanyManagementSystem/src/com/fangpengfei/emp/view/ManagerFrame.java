package com.fangpengfei.emp.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ManagerFrame extends JFrame {
	private static final long serialVersionUID = 11409754064525018L;
	private JMenuBar menuBar;// 菜单条
	private JMenu menu1, menu2, menu3, menu4;// 菜单
	private JMenuItem menuItem1, menuItem2, menuItem3, menuItem4;// 菜单项
	private UserPanel userPanel;
	private DeptPanel deptPanel;
	private EmpPanel empPanel;
	private JPanel panel;// 嵌入式面板
	private JLabel label;
	private ImageIcon img;

	public ManagerFrame() {

		this.setTitle("欢迎使用北斗企鹅管理系统");
		this.setSize(650, 630);
		this.setLocationRelativeTo(null);// 设置窗体居中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);// 设置当窗体关闭时程序退出
		menuBar = new JMenuBar();
		menu1 = new JMenu("主页");
		menu2 = new JMenu("部门管理");
		menu3 = new JMenu("员工管理");
		menu4 = new JMenu("用户管理");
		menuItem1 = new JMenuItem("主页");
		menuItem2 = new JMenuItem("部门信息管理");
		menuItem3 = new JMenuItem("员工信息管理");
		menuItem4 = new JMenuItem("用户信息管理");
		userPanel = new UserPanel();
		empPanel = new EmpPanel();
		deptPanel = new DeptPanel();
		panel = new JPanel();
		label = new JLabel();
		// 给menuItem添加监听事件
		menuItem1.addActionListener(new MenuItemClick());
		menuItem2.addActionListener(new MenuItemClick());
		menuItem3.addActionListener(new MenuItemClick());
		menuItem4.addActionListener(new MenuItemClick());
		// 把菜单项添加到菜单中，把菜单添加到菜单条中
		menu1.add(menuItem1);
		menu2.add(menuItem2);
		menu3.add(menuItem3);
		menu4.add(menuItem4);
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		menuBar.add(menu4);

		img = new ImageIcon(".\\src\\com\\fangpengfei\\emp\\images\\beidou600x600.jpg");
		label.setIcon(img);
		panel.add(label);
		this.add(panel);
		// 把整个菜单天添加到窗体的北边
		this.add(menuBar, BorderLayout.NORTH);
		this.setVisible(true);
	}

	class MenuItemClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 每次点击前清空其他panel
			panel.removeAll();
			// 如果点击了menuItem就会执行相应代码
			if (e.getSource() == menuItem1) {
				panel.add(label);
			} else if (e.getSource() == menuItem2) {
				// System.out.println("部门信息管理按钮被电击了");
				panel.add(deptPanel);
				deptPanel.refreshDeptTable();
			} else if (e.getSource() == menuItem3) {
				// System.out.println("员工信息管理按钮被电击了");
				panel.add(empPanel);
				empPanel.refreshEmpTable();
			} else if (e.getSource() == menuItem4) {
				// System.out.println("用户信息管理按钮被电击了");
				panel.add(userPanel);
				userPanel.refreshUserTable();
			}
			// 点击按钮后 使其值为1,使得背景图片的panel不加载
			panel.updateUI();

		}

	}
}
