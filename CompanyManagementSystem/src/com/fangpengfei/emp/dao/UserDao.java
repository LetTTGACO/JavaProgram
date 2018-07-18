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
	// 增
	public void addUser(User user) {
		// 判断用户名是否为空
		String username = user.getUsername();
		if (null == username || "".equals(username.trim())) {
			throw new EmpException("用户名不能为空");
		}
		if (this.getOneUser(username) != null) {
			throw new EmpException("用户名已存在");
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

	// 查一个用户，方便其他功能
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

	// 删
	public void deleteUser(String username) {
		User user = this.getOneUser(username);
		if (user == null) {
			throw new EmpException("删除的用户名不存在");
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

	// 改
	public void updateUser(User user) {
		// 对用户名进行非空判断
		String username = user.getUsername();
		if (null == username || "".equals(username.trim())) {
			throw new EmpException("用户名不能为空");
		}
		// 用户名不能修改
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

	// 查
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
		//通过传入的username来得到一个用户
		User user = this.getOneUser(username);
		if (null == username || "".equals(username.trim())) {
			throw new EmpException("用户名不能为空");
		} else if (user == null) {
			throw new EmpException("用户不存在");
		} else if (null == password || "".equals(password.trim())) {
			throw new EmpException("密码不能为空");	
		//再利用 用户的getPassword()方法获得密码并和传入的密码进行比较
		} else if (!password.equals(user.getPassword())) {
			throw new EmpException("密码错误");
		}
	}

}
