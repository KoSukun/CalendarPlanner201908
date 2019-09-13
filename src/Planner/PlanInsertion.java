package Planner;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PlanInsertion {
	private JTextField t1;
	private JTextField t2;
	private JTextField t3;
	private JTextField t4;
	private JTextField t5;
	private JTextField t6;

	public PlanInsertion(Calendar selectedDate) {
		JFrame planInsertionFrame = new JFrame();
		planInsertionFrame.setTitle("새 일정 추가");
		planInsertionFrame.setSize(650, 500);
		planInsertionFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_2 = new JLabel("날짜:");
		planInsertionFrame.getContentPane().add(lblNewLabel_2);

		t1 = new JTextField();
		planInsertionFrame.getContentPane().add(t1);
		t1.setColumns(57);
		t1.setText(selectedDate.get(Calendar.YEAR) + "년 " + (selectedDate.get(Calendar.MONTH) + 1) + "월 " + selectedDate.get(Calendar.DAY_OF_MONTH) + "일");
		// Calendar's month value should be displayed after -1 since 

		JLabel lblNewLabel_3 = new JLabel("시간:");
		planInsertionFrame.getContentPane().add(lblNewLabel_3);

		t2 = new JTextField();
		planInsertionFrame.getContentPane().add(t2);
		t2.setText(selectedDate.get(Calendar.HOUR_OF_DAY) + "시 "+selectedDate.get(Calendar.MINUTE) + "분 "+selectedDate.get(Calendar.SECOND) + "초");
		t2.setColumns(57);

		JLabel lblNewLabel_4 = new JLabel("장소:");
		planInsertionFrame.getContentPane().add(lblNewLabel_4);

		t3 = new JTextField();
		planInsertionFrame.getContentPane().add(t3);
		t3.setColumns(57);

		JLabel lblNewLabel_5 = new JLabel("내용: ");
		planInsertionFrame.getContentPane().add(lblNewLabel_5);

		t4 = new JTextField();
		planInsertionFrame.getContentPane().add(t4);
		t4.setColumns(57);

		JButton btnNewButton = new JButton("일정 추가");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					PlanInsertionDAO dao = new PlanInsertionDAO();
					PlanInsertionDTO dto = new PlanInsertionDTO();
					
					String effectiveDateString = t1.getText();
					String[] effectiveDate = effectiveDateString.split("년 |월 |일|-");
					int plannedYear = Integer.parseInt(effectiveDate[0]);
					int plannedMonth = (Integer.parseInt(effectiveDate[1]) - 1); // displayed after adding 1 to month value. so should substract 1 before store to be stored correctly
					int plannedDate = Integer.parseInt(effectiveDate[2]);
					
					String timeCreatedString = t2.getText();
					String[] timeCreated = timeCreatedString.split("시 |분 |초|-|:");
					int plannedHour = Integer.parseInt(timeCreated[0]);
					int plannedMinute = Integer.parseInt(timeCreated[1]);
					int plannedSecond = Integer.parseInt(timeCreated[2]);
					
					Calendar plannedDateTime = Calendar.getInstance();
					plannedDateTime.set(plannedYear, plannedMonth, plannedDate, plannedHour, plannedMinute, plannedSecond);
					Date sqlPlannedDate = new Date(plannedDateTime.getTimeInMillis());
					Time sqlPlannedTime = new Time(plannedDateTime.getTimeInMillis());
					
					//dto.setPlanId(Integer.parseInt(t5.getText())); // getText
					dto.setUserId(CalendarPlanner.userIdStatic); // getText no, always insert by already logined user
					dto.setEffectiveDate(sqlPlannedDate);
					dto.setTimeCreated(sqlPlannedTime);
					dto.setPlace(t3.getText());
					dto.setNote(t4.getText());
					
					dao.insertPlan(dto);

					JOptionPane.showMessageDialog(null, "일정 추가가 완료되었습니다.");
					planInsertionFrame.dispose();
//					CalendarPlanner.f.dispose();
					new CalendarPlanner(CalendarPlanner.userIdStatic);
					
				} catch (Exception exception) {	}
			}
		});
		planInsertionFrame.getContentPane().add(btnNewButton);

		planInsertionFrame.setVisible(true);
	}
}
