package Planner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.IDateEvaluator;
import com.toedter.calendar.JCalendar;

public class CalendarPlanner {

	static JCalendar jCalendar = new JCalendar();
	static String userIdStatic;
	public static Calendar selectedDate = Calendar.getInstance();
//	public static Calendar[] validDates;
	static JFrame f;
	static PlansTableForSelectedDate plansTableInstance = null; // to open only one time
	static String formerDate = "";
	CalendarPlannerDAO dao = new CalendarPlannerDAO();


	public CalendarPlanner(String userId) throws Exception {
		userIdStatic = userId;
		f = new JFrame(userIdStatic + "님의 Calendar Planner");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		HighlightEvaluator evaluator = new HighlightEvaluator();
		try {
			// CalendarPlannerDTO dto = dao.getPlansDatesForUser(userId);
			// log-in user info transfer - make calendar constructor get parameter of userId
			Date[] plansDatesForUser = dao.getPlansDatesForUser(userId);

//			if (dto.effectiveDate != null) {
//			int validDateNum = 0;
			if (plansDatesForUser.length > 0) {
//				validDates = new Calendar[plansDatesForUser.length];
//				Date datesHavingPlans = dto.effectiveDate;
				for (int i = 0; i < plansDatesForUser.length; i++) {
					Calendar c = Calendar.getInstance();
//					c.set((datesHavingPlans.getYear() + 1900), datesHavingPlans.getMonth(), datesHavingPlans.getDate());
					c.set((plansDatesForUser[i].getYear() + 1900), plansDatesForUser[i].getMonth(),
							plansDatesForUser[i].getDate());
					c.set(Calendar.HOUR_OF_DAY, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MILLISECOND, 0);

//					validDates[validDateNum] = c;
//					validDateNum++;
					java.util.Date date = c.getTime();

//					System.out.println(date);

					// add evaluator highlighting date when the date has plan - by for each, mark
					// all selected date
					evaluator.add(date);

					jCalendar.getDayChooser().addDateEvaluator(evaluator); // evaluator deletion need
//					jCalendar.getDayChooser().setBackground(Color.bl; - here make evaluated date set final
//					jCalendar.getDayChooser().setIgnoreRepaint(false);

				}
			} else {
				JOptionPane.showMessageDialog(null, userId + "님 일정이 없으시군요. 일정들을 추가하며 CalendarPlanner를 만들어가세요 :)");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

//		dto = dao.getPlansDatesForUser(userId);
//		dto = dao.getPlansDatesForUser("user1"); // not plan - make this as plans list - check if the date has value
		// while iteration

		jCalendar.getDayChooser().addPropertyChangeListener(
				// property sliderListener detects change of date in date chooser
				(PropertyChangeEvent evt) -> {
//					formerDate = evt.getOldValue().toString();
//					formerDate.getClass().
//					System.out.println(formerDate);
//					formerDate.
					dateChooserPropertChanged(evt);
				});
		
		jCalendar.getDayChooser().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	
//            	f.dispose();
//            	try {
//					new CalendarPlanner(userIdStatic);
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
        		try {
        			// CalendarPlannerDTO dto = dao.getPlansDatesForUser(userId);
        			// log-in user info transfer - make calendar constructor get parameter of userId
        			
        			Date[] plansDatesForUser = dao.getPlansDatesForUser(userId);

//        			if (dto.effectiveDate != null) {
//        			int validDateNum = 0;
        			if (plansDatesForUser.length > 0) {
//        				validDates = new Calendar[plansDatesForUser.length];
//        				Date datesHavingPlans = dto.effectiveDate;
        				for (int i = 0; i < plansDatesForUser.length; i++) {
        					HighlightEvaluator evaluator = new HighlightEvaluator();
        					Calendar c = Calendar.getInstance();
//        					c.set((datesHavingPlans.getYear() + 1900), datesHavingPlans.getMonth(), datesHavingPlans.getDate());
        					c.set((plansDatesForUser[i].getYear() + 1900), plansDatesForUser[i].getMonth(),
        							plansDatesForUser[i].getDate());
        					c.set(Calendar.HOUR_OF_DAY, 0);
        					c.set(Calendar.MINUTE, 0);
        					c.set(Calendar.SECOND, 0);
        					c.set(Calendar.MILLISECOND, 0);

//        					validDates[validDateNum] = c;
//        					validDateNum++;
        					java.util.Date date = c.getTime();

//        					System.out.println(date);

        					// add evaluator highlighting date when the date has plan - by for each, mark
        					// all selected date
        					evaluator.add(date);

        					jCalendar.getDayChooser().addDateEvaluator(evaluator);
//        					jCalendar.getDayChooser().setBackground(Color.bl; - here make evaluated date set final
//        					jCalendar.getDayChooser().setIgnoreRepaint(false);

        				}
        			} 
        		} catch (Exception e1) {
        			// TODO: handle exception
        		}
            }
        });

		jCalendar.setCalendar(jCalendar.getCalendar());
		jCalendar.setPreferredSize(new Dimension(800, 500));
		f.getContentPane().add(jCalendar);

		JPanel panel = new JPanel();
		jCalendar.getDayChooser().add(panel, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("일정 추가");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PlanInsertion(selectedDate);
				f.dispose();
				// newly create with marked new plan when plan is
			}
		});
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(btnNewButton);

		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

//	@Override
//	public void JCalendar.getDayChooser().setBackground(Color bg) {
//		super.setBackground(bg);
//
//		if (dayChooser != null) {
//			dayChooser.setBackground(bg);
//		}
//	}


	private static void dateChooserPropertChanged(PropertyChangeEvent evt) {
		selectedDate.set(jCalendar.getYearChooser().getYear(), (jCalendar.getMonthChooser().getMonth()),
				jCalendar.getDayChooser().getDay());

		System.out.println("오늘의 날짜는 " + jCalendar.getYearChooser().getYear() + "년 "
				+ (jCalendar.getMonthChooser().getMonth() + 1) + "월 " + jCalendar.getDayChooser().getDay() + "일 입니다.");
		// define checking list for selected date
		// then display by edit/deleteable Jtable with scrollbar
		// when plan date is updated, get new Calendar..? when other fields are
				
		// modified, define what to do

		Date selectedSQLDate = new Date(selectedDate.getTimeInMillis());

		CalendarPlannerDAO dao = new CalendarPlannerDAO();
		try {
//			selectedDate.set(Calendar.HOUR_OF_DAY, 0);
//			selectedDate.set(Calendar.MINUTE, 0);
//			selectedDate.set(Calendar.SECOND, 0);
//			selectedDate.set(Calendar.MILLISECOND, 0);

//			validDates[validDateNum] = c;
//			validDateNum++;
//			java.util.Date selectedDateDate = selectedDate.getTime();
//			if (evaluator.list.contains(selectedDateDate)) {
//				jCalendar.getDayChooser().setBackground(Color.blue);
//				jCalendar.getDayChooser().setForeground(Color.red.darker());
//				System.out.println(jCalendar.getDayChooser().getIgnoreRepaint());
//				jCalendar.getDayChooser().setIgnoreRepaint(true);
//				System.out.println(jCalendar.getDayChooser().getIgnoreRepaint());
//			}
			if (dao.getPlansCountForSelectedDate(userIdStatic, selectedSQLDate) > 0) {
				System.out.println(dao.getPlansCountForSelectedDate(userIdStatic, selectedSQLDate));
				System.out.println("checking me?");
//				plansAppearIfValid(selectedDate);
				jCalendar.getDayChooser().setBackground(Color.blue);
				jCalendar.getDayChooser().setForeground(Color.red.darker());
//				jCalendar.getDayChooser().setIgnoreRepaint(false);
//				jCalendar.getDayChooser().getIgnoreRepaint();
//				System.out.println(jCalendar.getDayChooser().getIgnoreRepaint());

//				jCalendar.getDayChooser().isBackgroundSet()
//				jCalendar.getDayChooser().isForegroundSet() try to utilize these two
				try {
					if (plansTableInstance == null)
						plansTableInstance = new PlansTableForSelectedDate(selectedDate);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		evt.equals(validDates)
//		for (Calendar validDate : validDates) {
//			if ((selectedDate.get(Calendar.YEAR) == validDate.get(Calendar.YEAR))
//					&& (selectedDate.get(Calendar.MONTH) == validDate.get(Calendar.MONTH))
//					&& (selectedDate.get(Calendar.DATE) == validDate.get(Calendar.DATE))) {
//			}
//		}
	}

//	public static void plansAppearIfValid(Calendar selectedDate) {
//		try {
//			new PlansTableForSelectedDate(selectedDate);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}

	private static class HighlightEvaluator implements IDateEvaluator {

		private final List<java.util.Date> list = new ArrayList<>();

		public void add(java.util.Date date) {
			list.add(date);
		}

		@Override
		public boolean isSpecial(java.util.Date date) {
			return list.contains(date);
		}

		@Override
		public Color getSpecialForegroundColor() {
			return Color.red.darker();
		}

		@Override
		public Color getSpecialBackroundColor() {
			return Color.blue;
		}

		@Override
		public String getSpecialTooltip() {
			return "Highlighted event.";
		}

		@Override
		public boolean isInvalid(java.util.Date date) {
			return false;
		}

		@Override
		public Color getInvalidForegroundColor() {
			return null;
		}

		@Override
		public Color getInvalidBackroundColor() {
			return null;
		}

		@Override
		public String getInvalidTooltip() {
			return null;
		}
	}

//	public static void main(String[] args) throws Exception {
//		CalendarPlanner calendarPlanner = new CalendarPlanner();
//	}
}
