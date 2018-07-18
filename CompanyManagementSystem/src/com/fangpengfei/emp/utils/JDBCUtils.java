package com.fangpengfei.emp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JDBCUtils {
	//��̬���������ִ�У�����ִֻ��һ��
	static{//��Ϊ����ֻ��Ҫ����һ�Σ����԰Ѽ��������Ĵ���ŵ���̬�������
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//1.���췽��˽�л�
	private JDBCUtils(){}
	//2.������ȡConnection����ķ���
	public static Connection getConnection(){
		Connection conn = null;
		try {
			String url = "jdbc:mysql://localhost:3306/emp";
			String user = "root";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	//3.�����ر�2����Դ�ķ���
	public static void close(Connection conn, Statement stmt){
		try {
			if(null != stmt) {
				stmt.close();
			}
			if(null != conn) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//4���صķ���
	public static void close(Connection conn, Statement stmt,ResultSet rs){
		close(conn,stmt);
		try {
			if(null != rs) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
