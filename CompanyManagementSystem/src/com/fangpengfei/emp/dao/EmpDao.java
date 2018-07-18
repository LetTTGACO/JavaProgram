package com.fangpengfei.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fangpengfei.emp.entity.Dept;
import com.fangpengfei.emp.entity.Emp;
import com.fangpengfei.emp.exception.EmpException;
import com.fangpengfei.emp.utils.JDBCUtils;

public class EmpDao {
	// 增加员工
	public void addEmp(Emp emp) {
		//对姓名,性别,家庭住址进行非空判断
		if(null == emp.getName() || "".equals(emp.getName().trim())){
			throw new EmpException("姓名不能为空");
		}else if (null == emp.getGender() || "".equals(emp.getGender().trim())){
			throw new EmpException("性别不能为空");
		}else if (null == emp.getAddress() || "".equals(emp.getAddress().trim())){
			throw new EmpException("家庭住址不能为空");
		}
		//对关联的部门进行判断,部门存在才能添加员工
		int detpId = emp.getDetpId();
		DeptDao deptDao = new DeptDao();
		Dept dept = deptDao.getOneDept(detpId);
		if (dept == null) {
			throw new EmpException("添加的部门不存在");
		}
		//对员工进行重复判断
		Emp emp2 = this.getOneEmp(emp.getId());
		if (emp2 != null) {
			throw new EmpException("该员工已存在");
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "insert into emp values (null,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, emp.getId());
			pstmt.setString(1, emp.getName());
			pstmt.setString(2, emp.getGender());
			pstmt.setString(3, emp.getAddress());
			pstmt.setDouble(4, emp.getSalary());
			pstmt.setInt(5, emp.getDetpId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// 根据部门ID查询一个员工
	public Emp getOneEmp(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from emp where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int empid = rs.getInt("id");
				String empname = rs.getString("name");
				String empgender = rs.getString("gender");
				String empaddress = rs.getString("address");
				double empsalary = rs.getDouble("salary");
				int deptid = rs.getInt("dept_id");
				return new Emp(empid, empname, empgender, empaddress, empsalary, deptid);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}
		return null;
	}
	
	//删除员工
	public void deleteEmp(int id) {
		Emp emp = this.getOneEmp(id);
		if (emp == null) {
			throw new EmpException("需要删除的员工不存在");
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "delete from emp where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// 修改员工
	public void updateEmp(Emp emp) {
		//判断需要修改的部门是够存在
		DeptDao deptDao = new DeptDao();
		Dept dept = deptDao.getOneDept(emp.getDetpId());
		if (dept == null) {
			throw new EmpException("需要修改的部门不存在");
		}
		//判断需要修改的员工是否存在
		Emp emp2 = this.getOneEmp(emp.getId());
		if (emp2 == null) {
			throw new EmpException("需要修改的员工不存在");
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "update emp set name = ?,gender = ?, address = ?, salary = ?, dept_id = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emp.getName());
			pstmt.setString(2, emp.getGender());
			pstmt.setString(3, emp.getAddress());
			pstmt.setDouble(4, emp.getSalary());
			pstmt.setInt(5, emp.getDetpId());
			pstmt.setInt(6, emp.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}
	
	//查询所有员工
	public List<Emp> getAllEmp(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from emp";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<Emp> emps = new ArrayList<Emp>();
			while(rs.next()) {
				int empid = rs.getInt("id");
				String empname = rs.getString("name");
				String empgender = rs.getString("gender");
				String empaddress = rs.getString("address");
				double empsalary = rs.getDouble("salary");
				int deptid = rs.getInt("dept_id");
				emps.add(new Emp(empid, empname, empgender, empaddress, empsalary, deptid));
			}
			return emps;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt,rs);
		}
	}
	
	
	

}
