package Planner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDbConnect_ver1 {

//	public static void main(String[] args) throws Exception{ //단독테스트용

	// 회원가입 부분
	public void insert(MemberDTO dto) throws Exception {

		// connector 설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("설정완료");

		// DB연결
		String url = "jdbc:mysql://localhost:3306/calendarplanner";
		String user = "root";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("연결완료");

		// ? 1~5는 join frame의 textbox값 사용.

		String sql = "insert into memberinfo values(?,?,?,?,?,?)"; // 6번째 ?는 db생성 년월일
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, dto.getId());
		ps.setString(2, dto.getPw());
		ps.setString(3, dto.getName());
		ps.setString(4, dto.getEmail());
		ps.setString(5, dto.getTel()); // 1~5 textbox
		ps.setString(6, dto.getJoindate()); // sever time year month date 자동입력

		System.out.println("SQL문 설정 완료");

		ps.executeUpdate();

		System.out.println("SQL문 전송 완료");

		ps.close();
		con.close();

	}

	// 로그인 부분, id-pw 확인 완료
	public MemberDTO select(String lid) throws Exception {
		// connector 설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 설정완료..");

		// DB연결
		String url = "jdbc:mysql://localhost:3306/calendarplanner";
		String user = "root";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("연결완료");

		// if문 사용으로 각 경우의 수 별 update sql문 입력.

		String sql = "Select id, pw from memberinfo where id = ?"; // id 입력 값 확인 -> pw 찾기,
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, lid);

		System.out.println("3. SQL문 설정 완료..");

		ResultSet rs = ps.executeQuery();
		System.out.println("4. SQL문 전송 요청 완료..");

		MemberDTO dto = new MemberDTO();

		// 정보출력

		if (rs.next()) {
			String id = rs.getString(1);
			String pw = rs.getString(2);

			dto.setId(id);
			dto.setPw(pw);

		}

		System.out.println("SQL문 전송 완료");

		ps.close();
		con.close();
		return dto;
	}

//회원정보 수정 창 출력
	public MemberDTO selectu(String lid) throws Exception {
		// connector 설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 설정완료..");

		// DB연결
		String url = "jdbc:mysql://localhost:3306/calendarplanner";
		String user = "root";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("연결완료");

		String sql = "select * from memberinfo where id = ?"; // id 입력 값 확인 -> pw 찾기,
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, lid);

		System.out.println("3. SQL문 설정 완료..");

		ResultSet rs = ps.executeQuery();
		System.out.println("4. SQL문 전송 요청 완료..");

		MemberDTO dto = new MemberDTO();

		// 정보출력

		if (rs.next()) {
			String id = rs.getString(1);
			String pw = rs.getString(2);
			String name = rs.getString(3);
			String email = rs.getString(4);
			String tel = rs.getString(5);
			String joindate = rs.getString(6);

			dto.setId(id);
			dto.setPw(pw);
			dto.setName(name);
			dto.setEmail(email);
			dto.setTel(tel);
			dto.setJoindate(joindate);

		}

		System.out.println("SQL문 전송 완료");

		ps.close();
		con.close();
		return dto;
	}

//	 회원정보 수정
	public MemberDTO update(String lid, String upw, String uname, String uemail, String utel) throws Exception {
		// connector 설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 설정완료..");

		// DB연결
		String url = "jdbc:mysql://localhost:3306/calendarplanner";
		String user = "root";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("연결완료");

		String sql = "update memberinfo set pw=?, name=?, email=?, tel=? where id = ?";
		// id 입력으로 나머지 정보 update.
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, upw);
		ps.setString(2, uname);
		ps.setString(3, uemail);
		ps.setString(4, utel);
		ps.setString(5, lid);

		System.out.println("3. SQL문 설정 완료..");

		ps.executeUpdate();
		System.out.println("4. SQL문 전송 요청 완료..");

		ps.close();
		con.close();
		return null;
	}

	// 회원 탈퇴
	public MemberDTO delete(String lid) throws Exception {

		// connector 설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 설정완료..");

		// DB연결
		String url = "jdbc:mysql://localhost:3306/calendarplanner";
		String user = "root";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("2. 연결완료..");

		String sql = "delete from memberinfo where id = ?";
		// id 입력으로 나머지 정보 update.
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, lid);

		System.out.println("3. SQL문 설정 완료..");

		ps.executeUpdate();
		System.out.println("4. SQL문 전송 요청 완료..");

		ps.close();
		con.close();
		return null;

	}

//pw변경에서 입력정보 비교위한 정보 찾기 부분
	public MemberDTO selectf(String fid) throws Exception {
		// connector 설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 설정완료..");

		// DB연결
		String url = "jdbc:mysql://localhost:3306/calendarplanner";
		String user = "root";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("2. 연결완료..");

		String sql = "select id,name,email,tel from memberinfo where id = ?"; // id 입력 값 확인 -> pw 찾기,
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, fid);

		System.out.println("3. SQL문 설정 완료..");

		ResultSet rs = ps.executeQuery();
		System.out.println("4. SQL문 전송 요청 완료..");

		MemberDTO dto = new MemberDTO();

		// 정보출력

		if (rs.next()) {
			String id = rs.getString(1);
			String name = rs.getString(2);
			String email = rs.getString(3);
			String tel = rs.getString(4);

			dto.setId(id);
			dto.setName(name);
			dto.setEmail(email);
			dto.setTel(tel);

		}

		System.out.println("SQL문 전송 완료");

		ps.close();
		con.close();
		return dto;
	}
	
	// pw 변경 중 pw를 random 숫자로 변경하는 부분
	public MemberDTO updatec(String fid, String upw) throws Exception {
		// connector 설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 설정완료..");

		// DB연결
		String url = "jdbc:mysql://localhost:3306/calendarplanner";
		String user = "root";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("2. 연결완료..");

		String sql = "update memberinfo set pw=? where id = ?";
		// id 입력으로 나머지 정보 update.
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, upw);
		ps.setString(2, fid);

		System.out.println("3. SQL문 설정 완료..");

		ps.executeUpdate();
		System.out.println("4. SQL문 전송 요청 완료..");

		ps.close();
		con.close();
		return null;
	}

	// selectAll 회원전체정보 조회
	public ArrayList<MemberDTO> SelectAll() throws Exception {
		ArrayList<MemberDTO> list = new ArrayList();

		// connector 설정
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 설정완료");

		// DB연결
		String url = "jdbc:mysql://localhost:3306/calendarplanner";
		String user = "root";
		String password = "1234";
		Connection con = DriverManager.getConnection(url, user, password);
		System.out.println("2. 연결완료");

		// ? 1~5는 join frame의 textbox값 사용.

		String sql = "select * from memberinfo"; //전체 조회
		PreparedStatement ps = con.prepareStatement(sql);
		System.out.println("3. SQL문 설정 완료");

		ResultSet rs = ps.executeQuery();

		System.out.println("4. SQL문 전송 완료");
		
		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			String id = rs.getString(1);
			String pw = rs.getString(2);
			String name = rs.getString(3);
			String email = rs.getString(4);
			String tel = rs.getString(5);
			String joindate = rs.getString(6);
			
			dto.setId(id);
			dto.setPw(pw);
			dto.setName(name);
			dto.setEmail(email);
			dto.setTel(tel);
			dto.setJoindate(joindate);
			
			list.add(dto);
						
		}

		con.close();
		ps.close();
		return list;
	}
}
