package Planner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PlansTableUpdateWhenUserUpdatedOrDeletedDAO {
	
	public void deletePlanForDeletedUser(String userId) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 드라이버 설정 성공...");
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calendarplanner", "root", "1234");
		System.out.println("2. 데이터베이스 연결 성공...");
		
		String sql = "Delete from plans Where UserId = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userId);
		System.out.println("3. SQL문 객체화 성공...");
		
		ps.executeUpdate();
		System.out.println("4. SQL문 전송 요청 성공...");
		
		ps.close();
		conn.close();
	}
	
}