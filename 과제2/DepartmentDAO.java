package examples.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import examples.dto.Department;

public class DepartmentDAO {
	private static String dburl="jdbc:mysql://localhost/dbdesign?serverTimezone=Asia/Seoul";
	private static String dbUser="dbuser";
	private static String dbpasswd="db1234";
	
	public Department getDepartment(Integer dnumber) {
		Department department =null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql= "SELECT Dnumber,Dname, Mgr_ssn, Mgr_start_date FROM department WHERE Dnumber= ?";
		
		
		try(Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps= conn.prepareStatement(sql)){
				ps.setInt(1,dnumber);
				try(ResultSet rs=ps.executeQuery()){
					if(rs.next()) {
						int id=rs.getInt(1);
						String name=rs.getString(2);
						String ssn = rs.getString(3);
						String date = rs.getString(4);
						department=new Department(name, id, ssn, date);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return department;
	}
	
	public int addDepartment(Department department) {
		int insertCount=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("연결 성공!~");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql= "INSERT INTO department (Dnumber, Dname, Mgr_ssn, Mgr_start_date) VALUES (?, ?, ?, ?)";
		
		try(Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps= conn.prepareStatement(sql)){
			ps.setInt(1, department.getDnumber());
			ps.setString(2, department.getDname());
			
			ps.setString(3, department.getMgr_ssn());
			ps.setString(4, department.getMgr_start_date());
			
			insertCount=ps.executeUpdate();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return insertCount;
	}
	
	public int deleteDepartment(Integer dnumber) {
		int deleteCount=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql="DELETE FROM department WHERE Dnumber=?";
		
		try(Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps= conn.prepareStatement(sql)){
			ps.setInt(1, dnumber);
			deleteCount=ps.executeUpdate();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return deleteCount;
	}
	
	public int updateDepartment(Department department) {
		int updateCount=0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql="update department set Dname = ?, mgr_ssn=?, mgr_start_date=? where Dnumber = ?";
		
		try(Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
			PreparedStatement ps= conn.prepareStatement(sql)){
			ps.setString(1, department.getDname());
			ps.setString(2, department.getMgr_ssn());
			ps.setString(3, department.getMgr_start_date());
			ps.setInt(4, department.getDnumber());
			
			updateCount=ps.executeUpdate();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return updateCount;
	}
	
	public List<Department> getDepartments(){
		List<Department> list= new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String sql="SELECT Dname, Mgr_ssn, Mgr_start_date, Dnumber FROM department order by Dnumber desc";
		
		try(Connection conn=DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps=conn.prepareStatement(sql)){
			try(ResultSet rs=ps.executeQuery()){
				while(rs.next()) {
					String name=rs.getString(1);
					String ssn =rs.getString(2);
					String date =rs.getString(3);
					int id= rs.getInt("Dnumber");
					Department department=new Department(name, id, ssn, date);
					list.add(department);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
		
	}
}