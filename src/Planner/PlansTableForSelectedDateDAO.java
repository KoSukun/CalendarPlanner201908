package Planner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlansTableForSelectedDateDAO {
	public ArrayList<PlansTableForSelectedDateDTO> getPlansForSelectedDate(String userId, Date selectedDate) throws Exception {
//	public int getPlansCountForSelectedDate(Date selectedDate) throws Exception {
		ArrayList<PlansTableForSelectedDateDTO> plansForSelectedDate = new ArrayList<PlansTableForSelectedDateDTO>();
		
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 드라이버 설정 성공...");
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calendarplanner", "root", "1234");
		System.out.println("2. 데이터베이스 연결 성공...");
		
		String sql = "Select PlanId, EffectiveDate, TimeCreated, Place, Note From plans Where UserId = ? and EffectiveDate = ? Order by TimeCreated";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userId);
		ps.setDate(2, selectedDate);
		System.out.println("3. SQL문 객체화 성공...");
		
		ResultSet resultSet = ps.executeQuery();
		System.out.println("4. SQL문 전송 요청 성공...");
			
		while(resultSet.next()) {
			PlansTableForSelectedDateDTO dto = new PlansTableForSelectedDateDTO();
			dto.setPlanId(resultSet.getInt(1));
			dto.setEffectiveDate(resultSet.getDate(2));
			dto.setTimeCreated(resultSet.getTime(3));
			dto.setPlace(resultSet.getString(4));
			dto.setNote(resultSet.getString(5));
			
			plansForSelectedDate.add(dto);
		}
		
		ps.close();
		conn.close();
		
		return plansForSelectedDate;
	}
	
	public void updatePlanForSelectedPlan(int planId, PlansTableForSelectedDateDTO dto) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 드라이버 설정 성공...");
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calendarplanner", "root", "1234");
		System.out.println("2. 데이터베이스 연결 성공...");
		
		String sql = "Update plans Set TimeCreated = ?, Place = ?, Note = ? Where PlanId = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
//		ps.setDate(1, dto.effectiveDate);
		ps.setTime(1, dto.timeCreated);
		ps.setString(2, dto.place);
		ps.setString(3, dto.note);
		ps.setInt(4, planId);
		System.out.println("3. SQL문 객체화 성공...");
		
		ps.executeUpdate();
		System.out.println("4. SQL문 전송 요청 성공...");
		
		ps.close();
		conn.close();
	}

	public void deletePlanForSelectedPlan(int planId) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 드라이버 설정 성공...");
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calendarplanner", "root", "1234");
		System.out.println("2. 데이터베이스 연결 성공...");
		
		String sql = "Delete from plans Where PlanId = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, planId);
		System.out.println("3. SQL문 객체화 성공...");
		
		ps.executeUpdate();
		System.out.println("4. SQL문 전송 요청 성공...");
		
		ps.close();
		conn.close();
	}
	
}