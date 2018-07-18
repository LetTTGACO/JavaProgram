package com.fangpengfei.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fangpengfei.emp.entity.User;
import com.fangpengfei.emp.exception.EmpException;
import com.fangpengfei.emp.utils.JDBCUtils;

public class UserDao {
	// ��
	public void addUser(User user) {
		// �ж��û����Ƿ�Ϊ��
		String username = user.getUsername();
		if (null == username || "".equals(username.trim())) {
			throw new EmpException("�û�������Ϊ��");
		}
		if (this.getOneUser(username) != null) {
			throw new EmpException("�û����Ѵ���");
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "insert into user values (?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getNickname());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// ��һ���û���������������
	public User getOneUser(String username) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from user where username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String uname = rs.getString("username");
				String password = rs.getString("password");
				String nickname = rs.getString("nickname");
				return new User(uname, password, nickname);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}
		return null;
	}

	// ɾ
	public void deleteUser(String username) {
		User user = this.getOneUser(username);
		if (user == null) {
			throw new EmpException("ɾ�����û���������");
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "delete from user where username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// ��
	public void updateUser(User user) {
		// ���û������зǿ��ж�
		String username = user.getUsername();
		if (null == username || "".equals(username.trim())) {
			throw new EmpException("�û�������Ϊ��");
		}
		// �û��������޸�
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "update user set password = ?,nickname = ? where username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getNickname());
			pstmt.setString(3, user.getUsername());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// ��
	public List<User> getAllUser() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from user";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);
			List<User> users = new ArrayList<User>();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String nickname = rs.getString("nickname");
				User user = new User(username, password, nickname);
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}
	}
	
	public void login(String username, String password){
		//ͨ�������username���õ�һ���û�
		User user = this.getOneUser(username);
		if (null == username || "".equals(username.trim())) {
			throw new EmpException("�û�������Ϊ��");
		} else if (user == null) {
			throw new EmpException("�û�������");
		} else if (null == password || "".equals(password.trim())) {
			throw new EmpException("���벻��Ϊ��");	
		//������ �û���getPassword()����������벢�ʹ����������бȽ�
		} else if (!password.equals(user.getPassword())) {
			throw new EmpException("�������");
		}
	}

}
