package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Table01 extends JFrame {
	private JTable table;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	private JScrollPane scrollPane;// ���������һ��JScrollPane����,����table�������

	public Table01() {
		this.setTitle("���Ա��");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		// ���ñ�ͷ
		columnNames = new Vector<String>();
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("��ͥסַ");

		// �������
		rowData = new Vector<Vector<String>>();
		// ���õ�һ������
		Vector<String> row1 = new Vector<String>();
		row1.add("����");
		row1.add("20");
		row1.add("����");
		rowData.add(row1);
		// ���õڶ�������
		Vector<String> row2 = new Vector<String>();
		row2.add("����");
		row2.add("25");
		row2.add("����");
		rowData.add(row2);
		// new���,�����õļ���װ����
		table = new JTable(rowData, columnNames);
		// ����scrollPaneʱ,��table����JScorePane������
		scrollPane = new JScrollPane(table);
		// �������봰��
		// this.add(table);
		// ���������봰��
		this.add(scrollPane);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Table01();
	}
}
