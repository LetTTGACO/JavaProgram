package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class MyTable extends AbstractTableModel {
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	public MyTable() {
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
		
	}
	@Override
	public int getColumnCount() {//���ر�������
		
		return rowData.size();
	}
	
	@Override
	public int getRowCount() {//���ر�������
		return columnNames.size();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {//����ÿ����Ԫ�������
		return rowData.get(rowIndex).get(columnIndex);
	}
	
	@Override
	public String getColumnName(int column) {//��������
		return columnNames.get(column);
	}
}
