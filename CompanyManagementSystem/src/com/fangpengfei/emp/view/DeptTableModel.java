package com.fangpengfei.emp.view;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.fangpengfei.emp.dao.DeptDao;
import com.fangpengfei.emp.entity.Dept;

public class DeptTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4258374532840277361L;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	
	public Vector<Vector<String>> getRowData() {
		return rowData;
	}

	public DeptTableModel() {
		// ���ñ������
		columnNames = new Vector<String>();
		columnNames.add("����ID");
		columnNames.add("��������");
		columnNames.add("Ա������");
		// ���ñ������
		rowData = new Vector<Vector<String>>();

		// �������ݿ�,�����ݿ���ȡ��ÿһ�е�����,Ȼ���ÿһ�е�������ӵ�rowData��
		DeptDao deptDao = new DeptDao();
		List<Dept> allDept = deptDao.getAllDept();
		for (Dept dept : allDept) {
			Vector<String> depts = new Vector<String>();
			depts.add(Integer.toString(dept.getId()));
			depts.add(dept.getName());
			depts.add(Integer.toString(deptDao.getDeptCount(dept.getId())));
			rowData.add(depts);
		}

	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		return rowData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rowData.get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

}
