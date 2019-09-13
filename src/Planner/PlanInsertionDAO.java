package Planner;

import java.sql.*;

public class PlanInsertionDAO {
	
	public void insertPlan(PlanInsertionDTO dto) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 드라이버 설정 성공...");
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calendarplanner",
				"root", "1234");
		System.out.println("2. 데이터베이스 연결 성공...");
		
		String sql = "Insert into plans values (null, ?, ?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, dto.userId);
		ps.setDate(2, dto.effectiveDate);
		ps.setTime(3, dto.timeCreated);
		ps.setString(4, dto.place);
		ps.setString(5, dto.note);
		System.out.println("3. SQL문 객체화 성공...");
		
		ps.executeUpdate();
		System.out.println("4. SQL문 전송 요청 성공...");
		
		ps.close();
		conn.close();
	}

}
