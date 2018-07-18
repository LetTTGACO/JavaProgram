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
	private JMenuBar menuBar;// �˵���
	private JMenu menu1, menu2, menu3, menu4;// �˵�
	private JMenuItem menuItem1, menuItem2, menuItem3, menuItem4;// �˵���
	private UserPanel userPanel;
	private DeptPanel deptPanel;
	private EmpPanel empPanel;
	private JPanel panel;// Ƕ��ʽ���
	private JLabel label;
	private ImageIcon img;

	public ManagerFrame() {

		this.setTitle("��ӭʹ�ñ���������ϵͳ");
		this.setSize(650, 630);
		this.setLocationRelativeTo(null);// ���ô������
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);// ���õ�����ر�ʱ�����˳�
		menuBar = new JMenuBar();
		menu1 = new JMenu("��ҳ");
		menu2 = new JMenu("���Ź���");
		menu3 = new JMenu("Ա������");
		menu4 = new JMenu("�û�����");
		menuItem1 = new JMenuItem("��ҳ");
		menuItem2 = new JMenuItem("������Ϣ����");
		menuItem3 = new JMenuItem("Ա����Ϣ����");
		menuItem4 = new JMenuItem("�û���Ϣ����");
		userPanel = new UserPanel();
		empPanel = new EmpPanel();
		deptPanel = new DeptPanel();
		panel = new JPanel();
		label = new JLabel();
		// ��menuItem��Ӽ����¼�
		menuItem1.addActionListener(new MenuItemClick());
		menuItem2.addActionListener(new MenuItemClick());
		menuItem3.addActionListener(new MenuItemClick());
		menuItem4.addActionListener(new MenuItemClick());
		// �Ѳ˵�����ӵ��˵��У��Ѳ˵���ӵ��˵�����
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
		// �������˵�����ӵ�����ı���
		this.add(menuBar, BorderLayout.NORTH);
		this.setVisible(true);
	}

	class MenuItemClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// ÿ�ε��ǰ�������panel
			panel.removeAll();
			// ��������menuItem�ͻ�ִ����Ӧ����
			if (e.getSource() == menuItem1) {
				panel.add(label);
			} else if (e.getSource() == menuItem2) {
				// System.out.println("������Ϣ����ť�������");
				panel.add(deptPanel);
				deptPanel.refreshDeptTable();
			} else if (e.getSource() == menuItem3) {
				// System.out.println("Ա����Ϣ����ť�������");
				panel.add(empPanel);
				empPanel.refreshEmpTable();
			} else if (e.getSource() == menuItem4) {
				// System.out.println("�û���Ϣ����ť�������");
				panel.add(userPanel);
				userPanel.refreshUserTable();
			}
			// �����ť�� ʹ��ֵΪ1,ʹ�ñ���ͼƬ��panel������
			panel.updateUI();

		}

	}
}
