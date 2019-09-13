package Planner;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PlansTableForSelectedDate  {
	private JFrame jf;
	private DefaultTableModel dtm;
	private JTable jtable;
	private JScrollPane jsp;
	ArrayList<PlansTableForSelectedDateDTO> plansForSelectedDateList = new ArrayList<PlansTableForSelectedDateDTO>();

	public PlansTableForSelectedDate(Calendar selectedDate) {
		jf = new JFrame(selectedDate.get(Calendar.YEAR) + "년 " + (selectedDate.get(Calendar.MONTH) + 1) + "월 "
				+ selectedDate.get(Calendar.DATE) + "일 일정");
		jf.setLocationRelativeTo(null);
		jf.setSize(700, 300);

		Date selectedSQLDate = new Date(selectedDate.getTimeInMillis());

		PlansTableForSelectedDateDAO dao = new PlansTableForSelectedDateDAO();
		try {
			plansForSelectedDateList = dao.getPlansForSelectedDate(CalendarPlanner.userIdStatic, selectedSQLDate);

			Object[][] o1 = new Object[plansForSelectedDateList.size()][5];
//        Object[][] o1 = new Object[][4];

			for (int i = 0; i < plansForSelectedDateList.size(); i++) {
				o1[i][0] = plansForSelectedDateList.get(i).timeCreated;
				if (plansForSelectedDateList.get(i).place.isEmpty()) {
					o1[i][1] = "장소 미정";
				} else {
					o1[i][1] = plansForSelectedDateList.get(i).place;
				}
				if (plansForSelectedDateList.get(i).note.isEmpty()) {
					o1[i][2] = "노트 없음";
				} else {
					o1[i][2] = plansForSelectedDateList.get(i).note;
				}
				o1[i][3] = "일정 수정";
				o1[i][4] = "일정 삭제";
			}

			Object[] o2 = { "시간", "장소", "노트", "수정", "삭제" };

			dtm = new DefaultTableModel(o1, o2);
			jtable = new JTable(dtm);

			Action update = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					JTable table = (JTable) e.getSource();
					int modelRow = Integer.valueOf(e.getActionCommand()); // get modelRow's rowvalue - get(rowValue).
					System.out.println(modelRow);
					PlansTableForSelectedDateDTO dto = new PlansTableForSelectedDateDTO();
//					dto.setEffectiveDate((Date) table.getValueAt(modelRow, 0)); // date, time get using formatter would be solution
					dto.setTimeCreated((Time) table.getValueAt(modelRow, 0)); //
					dto.setPlace((String) table.getValueAt(modelRow, 1));
					dto.setNote((String) table.getValueAt(modelRow, 2));

					int planIdForUpdate = plansForSelectedDateList.get(modelRow).planId;
					System.out.println(planIdForUpdate);
					int check = JOptionPane.showConfirmDialog(null, "새로 입력하신대로 일정을 수정하시겠습니까??");
					if (check == 0) {
						try {
							dao.updatePlanForSelectedPlan(planIdForUpdate, dto);
							CalendarPlanner.f.dispose();
							jf.dispose();
							new CalendarPlanner(CalendarPlanner.userIdStatic);
							new PlansTableForSelectedDate(selectedDate);
							JOptionPane.showMessageDialog(null, "일정 수정이 완료되었습니다.");
						} catch (Exception error) {
							error.printStackTrace();
						}
					}
				}
			};

			Action delete = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					JTable table = (JTable) e.getSource();
					int modelRow = Integer.valueOf(e.getActionCommand());
					int planIdForDelete = plansForSelectedDateList.get(modelRow).planId;
					int check = JOptionPane.showConfirmDialog(null, "정말 일정을 삭제하시겠습니까?");
					if (check == 0) {
						try {
							((DefaultTableModel) table.getModel()).removeRow(modelRow);
							dao.deletePlanForSelectedPlan(planIdForDelete);
							CalendarPlanner.f.dispose();
							jf.dispose();
							new CalendarPlanner(CalendarPlanner.userIdStatic);
							new PlansTableForSelectedDate(selectedDate);
							JOptionPane.showMessageDialog(null, "일정 삭제가 완료되었습니다.");
						} catch (Exception error) {

						}
					}
				}
			};

			ButtonColumn buttonColumnForEdit = new ButtonColumn(jtable, update, 3);
			buttonColumnForEdit.setMnemonic(KeyEvent.VK_D);
			ButtonColumn buttonColumnForDelete = new ButtonColumn(jtable, delete, 4);
			buttonColumnForDelete.setMnemonic(KeyEvent.VK_D);
			jsp = new JScrollPane(jtable);

			jf.add(jsp);

			jf.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					if (JOptionPane.showConfirmDialog(jf, "현재 창을 닫으시겠습니까?", "일정 창 닫기", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						CalendarPlanner.plansTableInstance = null;
						jf.dispose();
					}
				}
			});

			jf.setVisible(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}