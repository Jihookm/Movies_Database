package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.dao.DepartmentDAO;
import examples.dto.Department;

/**
 * Servlet implementation class DepartmentTest1
 */
@WebServlet("/DepartmentTest1")
public class DepartmentTest1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartmentTest1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DAO and doGet() 호출!");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out= response.getWriter();
		DepartmentDAO dao=new DepartmentDAO();
		
		Department department=null;
		department=dao.getDepartment(1);//Dnumber=1인 department를 가져옴.
		if(department==null)
			out.println("<h1> My department is null </h1>");
		else
			out.println("<h1> My name is"+ department.getDname()+"</h1>"+
		"<h1> My number is "+department.getDnumber()+"!!!</h1>"+
		"<h1> My mgr_ssn is "+department.getMgr_ssn()+"!!!</h1>"+
		"<h1> My mgr_start_date is "+department.getMgr_start_date()+"!!!</h1>"
		);
		
		//add 테스트
		Department department1=new Department("GraduateStudent", 20, "123456789","2020-09-24");
		//위에서 선언한 새로운 튜플을 add함
		int addCount= dao.addDepartment(department1);
		out.println("<h1> insert : "+addCount+" row(s) </h1>");
		
		//delete 테스트
		//dnumber=1인 튜플을 삭제함
		int delCount= dao.deleteDepartment(1);
		out.println("<h1> delete : "+delCount+" row(s) </h1>");//삭제 완료 시 delcount=1
		
		//update 테스트
		Department department2 = new Department("UpdateStudent", 20, "123456788","2020-09-22");
		int updateCount= dao.updateDepartment(department2);
		out.println("<h1> update : "+updateCount+" row(s) </h1>");
		
		//모두 출력하기
		List<Department> departmentList = dao.getDepartments();
		
		for(Department department3 : departmentList) {
			out.println("<h1> My name is"+ department3.getDname()+"</h1>"+
					"<h1> My number is "+department3.getDnumber()+"!!!</h1>"+
					"<h1> My mgr_ssn is "+department3.getMgr_ssn()+"!!!</h1>"+
					"<h1> My mgr_start_date is "+department3.getMgr_start_date()+"!!!</h1>");
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
