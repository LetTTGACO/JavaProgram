package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	private Vector<String> columnNames;// ����
	private Vector<Vector<String>> rowData;// ÿһ�е�����

	public TableModel() {
		columnNames = new Vector<>();
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("��ͥסַ");

		rowData = new Vector<Vector<String>>();
		Vector<String> row1 = new Vector<String>();
		row1.add("����");
		row1.add("25");
		row1.add("����");
		rowData.add(row1);

		Vector<String> row2 = new Vector<String>();
		row2.add("����");
		row2.add("30");
		row2.add("����");
		rowData.add(row2);

		Vector<String> row3 = new Vector<String>();
		row3.add("����");
		row3.add("30");
		row3.add("����");
		rowData.add(row3);
	}

	@Override
	public int getColumnCount() {// ���ر�������
		return columnNames.size();
	}

	@Override
	public int getRowCount() {// ���ر�������
		return rowData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {// ����ָ���к�ָ���е�����
		return rowData.get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

}
