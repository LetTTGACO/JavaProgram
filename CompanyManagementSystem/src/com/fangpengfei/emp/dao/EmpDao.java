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
	// ����Ա��
	public void addEmp(Emp emp) {
		//������,�Ա�,��ͥסַ���зǿ��ж�
		if(null == emp.getName() || "".equals(emp.getName().trim())){
			throw new EmpException("��������Ϊ��");
		}else if (null == emp.getGender() || "".equals(emp.getGender().trim())){
			throw new EmpException("�Ա���Ϊ��");
		}else if (null == emp.getAddress() || "".equals(emp.getAddress().trim())){
			throw new EmpException("��ͥסַ����Ϊ��");
		}
		//�Թ����Ĳ��Ž����ж�,���Ŵ��ڲ������Ա��
		int detpId = emp.getDetpId();
		DeptDao deptDao = new DeptDao();
		Dept dept = deptDao.getOneDept(detpId);
		if (dept == null) {
			throw new EmpException("��ӵĲ��Ų�����");
		}
		//��Ա�������ظ��ж�
		Emp emp2 = this.getOneEmp(emp.getId());
		if (emp2 != null) {
			throw new EmpException("��Ա���Ѵ���");
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

	// ���ݲ���ID��ѯһ��Ա��
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
	
	//ɾ��Ա��
	public void deleteEmp(int id) {
		Emp emp = this.getOneEmp(id);
		if (emp == null) {
			throw new EmpException("��Ҫɾ����Ա��������");
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

	// �޸�Ա��
	public void updateEmp(Emp emp) {
		//�ж���Ҫ�޸ĵĲ����ǹ�����
		DeptDao deptDao = new DeptDao();
		Dept dept = deptDao.getOneDept(emp.getDetpId());
		if (dept == null) {
			throw new EmpException("��Ҫ�޸ĵĲ��Ų�����");
		}
		//�ж���Ҫ�޸ĵ�Ա���Ƿ����
		Emp emp2 = this.getOneEmp(emp.getId());
		if (emp2 == null) {
			throw new EmpException("��Ҫ�޸ĵ�Ա��������");
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
	
	//��ѯ����Ա��
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
