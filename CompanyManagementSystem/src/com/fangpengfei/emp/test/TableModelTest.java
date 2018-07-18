package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableModelTest extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	public TableModelTest(){
		this.setTitle("表格测试");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);//设置窗体居中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//设置当窗体关闭时程序退出
		table = new JTable(new TableModel());
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new TableModelTest();
	}
}
