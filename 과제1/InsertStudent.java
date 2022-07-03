package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertStudent
 */
@WebServlet("/InsertStudent")
public class InsertStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();//프린트하기전에 out 선언해줘야함.
		
		String sname=request.getParameter("name");
		int sid=Integer.parseInt(request.getParameter("sid"));
		String scl=request.getParameter("cl");
		String smajor=request.getParameter("major");
		out.println("<h1> Hello!!!! </h1>");
		Connection conn=null;
		
		try {
			//1.드라이버 로딩
			//드라이버 인터페이스 구현한 클래스를 로딩
			//mysql은 밑에있는내용
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//연결하기
			//드라이버 매니저에게 connection 객체를 달라고 요청
			//커넥션을 얻기 위해 필요한 url도 벤더사마다 다름
			
			String url="jdbc:mysql://localhost/dbdesign?serverTimezone=Asia/Seoul";
			
			conn=DriverManager.getConnection(url, "dbuser","db1234");
			System.out.println("연결 성공!!~");
			
			String sql= "insert into student values (?,?,?,?)";
			PreparedStatement stmt= conn.prepareStatement(sql);
			
			stmt.setString(1, sname);
			stmt.setInt(2, sid);
			stmt.setString(3, scl);
			stmt.setString(4, smajor);
				
			int count = stmt.executeUpdate();
			if( count == 0 ){
			System.out.println("데이터 입력 실패");
			}
			else{
			System.out.println("데이터 입력 성공");
			}
			
		}
		catch(ClassNotFoundException e){
			System.out.println("드라이버 로딩 실패");
		}
		catch(SQLException e){
			System.out.println("에러 "+e);
		}
		finally {
			try{
				if(conn!=null && !conn.isClosed()) {
					conn.close();
				}
				
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
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
