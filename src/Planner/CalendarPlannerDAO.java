package Planner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class CalendarPlannerDAO {

	public Date[] getPlansDatesForUser(String userId) throws Exception {
		// ArrayList<CalendarPlannerDTO> planDatesList = new
		// ArrayList<CalendarPlannerDTO>();

		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 드라이버 설정 성공...");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calendarplanner", "root", "1234");
		System.out.println("2. 데이터베이스 연결 성공...");

		String sql = "Select EffectiveDate from plans where UserId = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userId);
		System.out.println("3. SQL문 객체화 성공...");

		ResultSet resultSet = ps.executeQuery();
		System.out.println("4. SQL문 전송 요청 성공...");

//		System.out.println(resultSet.getRow());
//		System.out.println(rMeta.getColumnCount() + "--------------------");

		int rowCount = 0;
		if (resultSet != null) {
			resultSet.last(); // moves cursor to the last row
			rowCount = resultSet.getRow(); // get row id
		}

		Date[] plansDatesList = new Date[rowCount];
		System.out.println(rowCount);

		resultSet.first();
		int i = 0;
		plansDatesList[i] = resultSet.getDate(1);
		System.out.println(plansDatesList[i]);
		i++;

		while (resultSet.next()) {
//			CalendarPlannerDTO dto = new CalendarPlannerDTO();
//			dto.setEffectiveDate(resultSet.getDate(1));
//			planDatesList.add(dto);

			plansDatesList[i] = resultSet.getDate(1);
			System.out.println(plansDatesList[i]);
			i++;
		}

		System.out.println("로그인 유저: " + userId);

		ps.close();
		conn.close();

		return plansDatesList;
	}

//	public CalendarPlannerDTO getPlansCountForSelectedDate(Date selectedDate) throws Exception {
	public int getPlansCountForSelectedDate(String userId, Date selectedDate) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("1. 드라이버 설정 성공...");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calendarplanner", "root", "1234");
		System.out.println("2. 데이터베이스 연결 성공...");

		String sql = "Select PlanId from plans where UserId = ? and EffectiveDate = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userId);
		ps.setDate(2, selectedDate);
		System.out.println("3. SQL문 객체화 성공...");

		ResultSet resultSet = ps.executeQuery();
		System.out.println("4. SQL문 전송 요청 성공...");

		int rowCount = 0;
		if (resultSet != null) {
			resultSet.last(); // moves cursor to the last row
			rowCount = resultSet.getRow(); // get row id
		}

//		Date[] plansDatesList = new Date[rowCount];
//		System.out.println(rowCount);

//		resultSet.first();
//		int i = 0;
//		while(resultSet.next()) {
////			CalendarPlannerDTO dto = new CalendarPlannerDTO();
////			dto.setEffectiveDate(resultSet.getDate(1));
////			planDatesList.add(dto);
//			
//			plansDatesList[i] = resultSet.getDate(1);
//			System.out.println(plansDatesList[i]);
//			i++;
//		}

		ps.close();
		conn.close();

		return rowCount;

//		CalendarPlannerDTO dto = new CalendarPlannerDTO();
//		if(resultSet.next()) {
//			dto.setPlanId(resultSet.getInt(1));
//			dto.setUserId(resultSet.getString(2));
//			dto.setEffectiveDate(resultSet.getDate(3));
//			dto.setTimeCreated(resultSet.getTime(4));
//			dto.setPlace(resultSet.getString(5));
//			dto.setNote(resultSet.getString(6));
//		}
//		
//		ps.close();
//		conn.close();
//		
//		return dto;
	}

}