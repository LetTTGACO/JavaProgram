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
		this.setTitle("������");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);//���ô������
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//���õ�����ر�ʱ�����˳�
		//����rowDataָ����ÿһ�е����ݣ�ÿһ�е����ݶ�Ϊһ�����ϣ���������ݼ��Ϲ���һ��rowData���ϣ�columnNamesָ��������
		
		columnNames = new Vector<String>();
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("��ͥסַ");
		rowData = new Vector<Vector<String>>();
		//���õ�һ�е�����
		Vector<String> row1 = new Vector<String>();
		row1.add("����");
		row1.add("20");
		row1.add("����");
		//�����ݼ��ϼ��뵽rowData������
		rowData.add(row1);
		//���õڶ��е�����
		Vector<String> row2 = new Vector<String>();
		row2.add("����");
		row2.add("24");
		row2.add("����");
		rowData.add(row2);
		//���õ����е�����
		Vector<String> row3 = new Vector<String>();
		row3.add("����");
		row3.add("30");
		row3.add("����");
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
