package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SelectStudent
 */
@WebServlet("/SelectStudent")
public class SelectStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet() 호출!");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<h1> Hello world!!</h1>");
		out.println("<h1>"+"학번 "+" 이름 "+" 전공 "+" 반 </h1>");
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			//1.드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//연결
			String url="jdbc:mysql://localhost/dbdesign?serverTimezone=Asia/Seoul";
			conn=DriverManager.getConnection(url, "dbuser","db1234");
			System.out.println("연결 성공!!~");
			//3.쿼리 수행을 위한 statement 객체 생성
			stmt=conn.createStatement(); //<-select문 할때
			
			//4. sql 쿼리 작성
			String sql= "select name, studentnumber, class, major from student";
			
			//5. 쿼리 수행
			//레코드들은 ResultSet 객체에 추가됨.
			rs=stmt.executeQuery(sql); //sql문을 넘겨서 수행한다음에 그걸 다시 rs에
			
			while(rs.next()) {//값이 존재하는 한 다음 값에 접근
				//레코드의 칼럼은 배열과 달리 1부터 시작함
				//디비에서 가져오는 데이터타입에 맞게 getString, getInt 등을 호출함
				String name=rs.getString(1);
				int sid=rs.getInt(2);//컬럼 번호. 필드순서임
				int classname=rs.getInt(3);
				String major=rs.getString(4);
				
				out.println("<h1>"+sid+" "+ name+ " "+ major+" "+ classname+ " </h1>");
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
				if(stmt!=null && !stmt.isClosed()) {
					conn.close();
				}
				if(rs!=null && !rs.isClosed()) {
					rs.close();
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
