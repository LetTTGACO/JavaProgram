package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableModelTest extends JFrame{
	private JTable table;
	private JScrollPane scrollPane;
	public TableModelTest(){
		this.setTitle("������");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);//���ô������
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//���õ�����ر�ʱ�����˳�
		table = new JTable(new TableModel());
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new TableModelTest();
	}
}
