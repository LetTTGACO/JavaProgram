package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Table01 extends JFrame {
	private JTable table;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	private JScrollPane scrollPane;// 给窗体添加一个JScrollPane容器,并将table入该容器

	public Table01() {
		this.setTitle("测试表格");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		// 设置表头
		columnNames = new Vector<String>();
		columnNames.add("姓名");
		columnNames.add("年龄");
		columnNames.add("家庭住址");

		// 添加数据
		rowData = new Vector<Vector<String>>();
		// 设置第一行数据
		Vector<String> row1 = new Vector<String>();
		row1.add("张三");
		row1.add("20");
		row1.add("西安");
		rowData.add(row1);
		// 设置第二行数据
		Vector<String> row2 = new Vector<String>();
		row2.add("李四");
		row2.add("25");
		row2.add("北京");
		rowData.add(row2);
		// new表格,将设置的集合装入表格
		table = new JTable(rowData, columnNames);
		// 创建scrollPane时,将table传入JScorePane容器中
		scrollPane = new JScrollPane(table);
		// 将表格加入窗体
		// this.add(table);
		// 将容器加入窗体
		this.add(scrollPane);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Table01();
	}
}
