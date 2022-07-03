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
 * Servlet implementation class JDBCServlet
 */
@WebServlet("/JDBCServlet")
public class JDBCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JDBCServlet() {
        super();

        System.out.println("JDBCServlet 생성!!");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet() 호출!");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<h1> Hello world!!</h1>");
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			//1.드라이버 로딩
			//드라이버 인터페이스 구현한 클래스를 로딩
			//mysql은 밑에있는내용
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//연결하기
			//드라이버 매니저에게 connection 객체를 달라고 요청
			//커넥션을 얻기 위해 필요한 url도 벤더사마다 다름
			
			String url="jdbc:mysql://localhost/dbdesign";
			
			conn=DriverManager.getConnection(url, "dbuser","db1234");
			System.out.println("연결 성공!!~");
			//3.쿼리 수행을 위한 statement 객체 생성
			stmt=conn.createStatement(); //<-select문 할때
			
			//4. sql 쿼리 작성
			// 1) JDBC에서 쿼리 작성할때는 세미콜론 빼고 작성
			// 2) SELECT 할때 *으로 다 가져오는거보다 가져올 칼럼을 직접 명시하는게 좋음
			String sql= "select sid, name, major, classname, submajor, admission, email from newstudent";
			
			
			//5. 쿼리 수행
			
			//레코드들은 ResultSet 객체에 추가됨.
			rs=stmt.executeQuery(sql); //sql문을 넘겨서 수행한다음에 그걸 다시 rs에 집어넣는거
			
			while(rs.next()) {//값이 존재하는 한 다음 값에 접근
				//레코드의 칼럼은 배열과 달리 1부터 시작함
				//디비에서 가져오는 데이터타입에 맞게 getString, getInt 등을 호출함
				int sid=rs.getInt(1);//컬럼 번호. 필드순서임
				String name=rs.getString(2);
				String major=rs.getString(3);
				int classname=rs.getInt(4);
				String submajor=rs.getString(5);
				String admission=rs.getString(6);
				String email=rs.getString(7);
				out.println("<h1>"+sid+" "+ name+ " "+ major+" "+ classname+" "+submajor
						+ " "+ admission+ " "+email+ "</h1>");
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
