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

public class DeptDao {
	// 增加部门
	public void addDpet(Dept dept) {
		// 对部门名称进行非空判断
		if (null == dept.getName() || "".equals(dept.getName().trim())) {
			throw new EmpException("部门名称不能为空");
		}
		// 对部门名称进行重复判断
		List<Dept> allDept = this.getAllDept();
		for (Dept depts : allDept) {
			if (dept.getId() ==depts.getId()) {
				throw new EmpException("部门ID不能重复");
			} else if (dept.getName().equals(depts.getName())) {
				throw new EmpException("部门名称不能重复");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "insert into dept values (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dept.getId());
			pstmt.setString(2, dept.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// 查询一个部门
	public Dept getOneDept(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from dept where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int deptid = rs.getInt("id");
				String deptname = rs.getString("name");
				return new Dept(deptid, deptname);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}
		return null;
	}

	// 删除部门
	public void deleteDept(int id) {
		// 判断删除的部门是否存在
		Dept dept = this.getOneDept(id);
		if (dept == null) {
			throw new EmpException("删除的部门不存在");
		}
		// 判断部门中还有员工,如果还有员工则不能删除
		EmpDao empDao = new EmpDao();
		List<Emp> allEmp = empDao.getAllEmp();
		for (Emp emp : allEmp) {
			if (emp.getDetpId() == id) {
				throw new EmpException("删除的部门还有员工");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "delete from dept where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// 修改部门
	public void updateDept(Dept dept) {
		// 判断部门名是否为空
		String name = dept.getName();
		if (name == null || "".equals(name.trim())) {
			throw new EmpException("部门名不能为空");
		}
		// 判断部门是否已存在
		Dept oneDept = this.getOneDept(dept.getId());
		if (null == oneDept) {
			throw new EmpException("修改的部门不存在");
		}
		List<Dept> depts = this.getAllDept();
		for (Dept dept2 : depts) {
			if (dept2.getName().equals(dept.getName())) {
				throw new EmpException("修改的部门已存在");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "update dept set name = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dept.getName());
			pstmt.setInt(2, dept.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// 查询所有部门
	public List<Dept> getAllDept() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 准备一个空集合
		List<Dept> depts;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from dept";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			depts = new ArrayList<Dept>();
			while (rs.next()) {
				int deptid = rs.getInt("id");
				String deptname = rs.getString("name");
				depts.add(new Dept(deptid, deptname));
			}
			return depts;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}

	}

	// 查询部门员工数量
	public int getDeptCount(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select count(*) as empcount from emp where dept_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int empcount = rs.getInt("empcount");
				return empcount;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}
		return 0;
	}
}
