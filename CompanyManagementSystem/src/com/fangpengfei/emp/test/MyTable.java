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
		
	}
	@Override
	public int getColumnCount() {//返回表格的行数
		
		return rowData.size();
	}
	
	@Override
	public int getRowCount() {//返回表格的列数
		return columnNames.size();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {//返回每个单元格的数据
		return rowData.get(rowIndex).get(columnIndex);
	}
	
	@Override
	public String getColumnName(int column) {//返回列名
		return columnNames.get(column);
	}
}
