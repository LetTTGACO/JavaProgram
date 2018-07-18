package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TestTable extends JFrame {
	private JTable table;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	private JScrollPane scrollPane;
	public TestTable(){
		this.setTitle("表格测试");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);//设置窗体居中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//设置当窗体关闭时程序退出
		//参数rowData指的是每一行的数据，每一行的数据都为一个集合，整体的数据集合构成一个rowData集合，columnNames指的是列名
		
		columnNames = new Vector<String>();
		columnNames.add("姓名");
		columnNames.add("年龄");
		columnNames.add("家庭住址");
		rowData = new Vector<Vector<String>>();
		//设置第一行的数据
		Vector<String> row1 = new Vector<String>();
		row1.add("张三");
		row1.add("20");
		row1.add("西安");
		//将数据集合加入到rowData集合中
		rowData.add(row1);
		//设置第二行的数据
		Vector<String> row2 = new Vector<String>();
		row2.add("李四");
		row2.add("24");
		row2.add("北京");
		rowData.add(row2);
		//设置第三行的数据
		Vector<String> row3 = new Vector<String>();
		row3.add("王五");
		row3.add("30");
		row3.add("湖南");
		rowData.add(row3);
//		this.add(table);
		table = new JTable(rowData, columnNames);
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new TestTable();
	}
}
