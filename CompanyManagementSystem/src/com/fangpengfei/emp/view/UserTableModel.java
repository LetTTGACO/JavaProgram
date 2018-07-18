package com.fangpengfei.emp.view;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.fangpengfei.emp.dao.UserDao;
import com.fangpengfei.emp.entity.User;

public class UserTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4165460376149739432L;
	private Vector<String> columnNames;
	private Vector<Vector<String>> rowData;
	public Vector<Vector<String>> getRowData() {
		return rowData;
	}
	public UserTableModel() {
		columnNames = new Vector<String>();
		columnNames.add("用户昵称");
		columnNames.add("用户名");
		columnNames.add("密码");
		rowData = new Vector<Vector<String>>();
		//连接数据库
		UserDao userDao = new UserDao();
		List<User> users = userDao.getAllUser();
		for (User user : users) {
			Vector<String> row = new Vector<String>();
			row.add(user.getNickname());
			row.add(user.getUsername());
			row.add(user.getPassword());
			rowData.add(row);
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
