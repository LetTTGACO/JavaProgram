package com.fangpengfei.emp.test;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	private Vector<String> columnNames;// 列名
	private Vector<Vector<String>> rowData;// 每一行的数据

	public TableModel() {
		columnNames = new Vector<>();
		columnNames.add("姓名");
		columnNames.add("年龄");
		columnNames.add("家庭住址");

		rowData = new Vector<Vector<String>>();
		Vector<String> row1 = new Vector<String>();
		row1.add("张三");
		row1.add("25");
		row1.add("西安");
		rowData.add(row1);

		Vector<String> row2 = new Vector<String>();
		row2.add("李四");
		row2.add("30");
		row2.add("北京");
		rowData.add(row2);

		Vector<String> row3 = new Vector<String>();
		row3.add("李四");
		row3.add("30");
		row3.add("北京");
		rowData.add(row3);
	}

	@Override
	public int getColumnCount() {// 返回表格的行数
		return columnNames.size();
	}

	@Override
	public int getRowCount() {// 返回表格的列数
		return rowData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {// 返回指定行和指定列的数据
		return rowData.get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

}
