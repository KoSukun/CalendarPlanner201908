package Planner;
// open calendar or 회원 정보/수정 탈퇴

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MemberSearchFrame_ver3 {
	private JTable jtable1;
	static ArrayList<MemberDTO> list = new ArrayList();
	static final int CHECK_COL = 0;
	static int delt; // 회원정보 삭제시 사라질 rownum
	static DefaultTableModel dtm;

//	public static void main(String[] args) { // 일단 메인, 추후 합칠 예정
//		// ~~1해당 줄 적용하여 테이블 입력 완료
//		EventQueue.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {
//				new MemberSearchFrame_ver3();
//			}
//		});
//		// 1~~
//	}

	public MemberSearchFrame_ver3() {
		JFrame f = new JFrame();
		f.setTitle("전체 회원정보 조회");
		f.setSize(550, 440);

		JLabel l1 = new JLabel("전체 회원 정보");
		l1.setBounds(30, 10, 220, 30);
		l1.setHorizontalAlignment(SwingConstants.LEFT);

		l1.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		f.getContentPane().setLayout(null);

		MemberDbConnect_ver1 dao = new MemberDbConnect_ver1();

		try {
			list = dao.SelectAll();
		} catch (Exception e1) {
		}

		// 체크박스 구현

		Object[][] o1 = new Object[list.size()][];// row data
		Object[] o2 = { "삭제", "ID", "PW", "NAME", "E-MAIL", "TEL", "JoinDate" };// data column name

		for (int i = 0; i < list.size(); i++) {
			MemberDTO dto = list.get(i);

			Object[] o = { new Boolean(false), dto.getId(), dto.getPw(), dto.getName(), dto.getEmail(), dto.getTel(),
					dto.getJoindate() };
			o1[i] = o;
		}

		// 테이블 및 스크롤 기능 추가

		dtm = new DefaultTableModel(o1, o2) {

			@Override
			public Class getColumnClass(int col) {
				return getValueAt(0, col).getClass();
			}

			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return (colIndex == CHECK_COL);
			}
		};
		jtable1 = new JTable(dtm);
		JScrollPane scroll1 = new JScrollPane(jtable1);

		scroll1.setBounds(30, 50, 470, 290);
		f.getContentPane().add(l1);
		f.getContentPane().add(scroll1);

		JPanel panel = new JPanel();
		panel.setBounds(120, 350, 260, 35);
		f.getContentPane().add(panel);

		JButton b1 = new JButton("확인");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
		});

		JButton b2 = new JButton("닫기");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
		});

		JButton bd = new JButton("회원 삭제");
		bd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("------------");
				for (int row = 0; row < list.size(); row++) { // 전체 row의
					Boolean b = ((Boolean) jtable1.getValueAt(row, 0));
					String deleteid = ((String) jtable1.getValueAt(row, 1));
					System.out.println("------------");

					if (b.booleanValue() == true && deleteid.equals(AfterLoginFrame_ver1.admin)) { // **관리자 정보 삭제 불가능 ->
																									// 오류발생
						jtable1.setValueAt(false, row, CHECK_COL);
						System.out.println("관리자아이디 삭제 불가능.");
						JOptionPane.showMessageDialog(null, "관리자 아이디는 삭제가 불가능합니다.");

					} else if (b.booleanValue() == true && !deleteid.equals(AfterLoginFrame_ver1.admin)) { // 체크표시한
//						MemberDTO dto = new MemberDTO();
						try {
//							dto = dao.delete(deleteid);
							dao.delete(deleteid);
							PlansTableUpdateWhenUserUpdatedOrDeletedDAO daoForPlansTableUpdate = new PlansTableUpdateWhenUserUpdatedOrDeletedDAO();
							daoForPlansTableUpdate.deletePlanForDeletedUser(deleteid);
						} catch (Exception e1) {
							System.out.println(e1);
						}
						JOptionPane.showMessageDialog(null, deleteid + " 회원의 정보 삭제가 완료되었습니다.");
					}
				}
				// 창 새로 띄워 table reset
				f.dispose();
				new MemberSearchFrame_ver3();
			}
		});

		panel.add(b1);
		panel.add(bd);
		panel.add(b2);
		f.setResizable(false);
		f.setVisible(true);

	}

} // public class LoginFrame 종료
