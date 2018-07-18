package com.fangpengfei.emp.view;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.fangpengfei.emp.dao.UserDao;

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = -3118075453887531635L;
	private Panel panel1, panel2, panel3;
	private JLabel label1, label2;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton button1, button2;
	private UserDao userDao;
	
	public LoginFrame() {
		this.setTitle("请登録北斗企鵝管理系统");
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(3, 1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		userDao = new UserDao();
		panel1 = new Panel();
		panel2 = new Panel();
		panel3 = new Panel();
		label1 = new JLabel("用戶名");
		label2 = new JLabel("密碼");
		textField = new JTextField(20);
		passwordField = new JPasswordField(20);
		button1 = new JButton("登録");
		button2 = new JButton("退出");
		button1.addActionListener(new ButtonClick());
		button2.addActionListener(new ButtonClick());

		panel1.add(label1);
		panel1.add(textField);
		panel2.add(label2);
		panel2.add(passwordField);
		panel3.add(button1);
		panel3.add(button2);
		
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.setVisible(true);

	}

	class ButtonClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button1) {
//				System.out.println("登録按鈕被點擊");
				String username = textField.getText().trim();
				String password = passwordField.getText().trim();
				try {
					userDao.login(username, password);
					LoginFrame.this.setVisible(false);
					new ManagerFrame();
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(panel1, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				}
			}else if (e.getSource() == button2) {
//				System.out.println("退出按鈕被點擊");
				System.exit(0);
			}
		}

	}
}
