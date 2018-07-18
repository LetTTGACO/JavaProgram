package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MyTableTest extends JFrame{
	private JTable table;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	private JScrollPane scrollPane;
	public MyTableTest(){
		this.setTitle("������");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);//���ô������
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//���õ�����ر�ʱ�����˳�
		table = new JTable(new MyTable());
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new MyTableTest();
	}
}
