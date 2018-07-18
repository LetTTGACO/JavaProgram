package com.fangpengfei.emp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JDBCUtils {
	//静态代码块最先执行，而且只执行一次
	static{//因为驱动只需要加载一次，可以把加载驱动的代码放到静态代码块中
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//1.构造方法私有化
	private JDBCUtils(){}
	//2.创建获取Connection对象的方法
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
	//3.创建关闭2个资源的方法
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
	//4重载的方法
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
