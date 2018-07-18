package com.fangpengfei.emp.view;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.fangpengfei.emp.dao.DeptDao;
import com.fangpengfei.emp.dao.EmpDao;
import com.fangpengfei.emp.entity.Dept;
import com.fangpengfei.emp.entity.Emp;

public class EmpTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8075806576372705900L;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	
	public Vector<Vector<String>> getRowData() {
		return rowData;
	}

	public EmpTableModel() {
		columnNames = new Vector<String>();
		columnNames.add("员工ID");
		columnNames.add("姓名");
		columnNames.add("性别");
		columnNames.add("家庭住址");
		columnNames.add("工资");
		columnNames.add("所在部门");
		
		rowData = new Vector<Vector<String>>();
		
		
		//从数据库中取出数据
		EmpDao empDao = new EmpDao();
		List<Emp> allEmp = empDao.getAllEmp();
		DeptDao deptDao = new DeptDao();
		for (Emp emp : allEmp) {
			Vector<String> emps = new Vector<String>();
			emps.add(Integer.toString(emp.getId()));
			emps.add(emp.getName());
			emps.add(emp.getGender());
			emps.add(emp.getAddress());
			emps.add(Double.toString(emp.getSalary()));
			Dept dept = deptDao.getOneDept(emp.getDetpId());
			emps.add(dept.getName());
			rowData.add(emps);
		}
		
		
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
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
