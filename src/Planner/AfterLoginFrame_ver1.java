package Planner;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AfterLoginFrame_ver1 {
//	private static JTextField t2;
	// 버튼 전역변수 설정
	static JButton b2, b3;
//	static String lid; //login frame에서 넘어오는 id
	static String admin = "admin";

//	public static void main(String[] args) { // 일단 메인, 추후 합칠 예정
//		AfterLoginFrame_ver1 name = new AfterLoginFrame_ver1(LoginFrame_ver3.lid);
//	}

	public AfterLoginFrame_ver1(String lid) {
		// 프레임 셋팅
		JFrame f = new JFrame();
		f.setTitle("Next");
		f.setSize(260, 180);

//		test용 id pw db 부분
//		String dbid = "jak1234";
//		String dbpw = "kk1234";

		JLabel l1 = new JLabel(LoginFrame_ver3.lid + "님 환영합니다.");
		l1.setBounds(20, 10, 220, 30);
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		b2 = new JButton("Calendar 열기");
		b2.setBounds(25, 50, 210, 35);
		b3 = new JButton("회원 정보 수정/탈퇴");
		b3.setBounds(25, 100, 210, 35);

		b2.addActionListener(new ActionListener() { // 캘린더 열기

			public void actionPerformed(ActionEvent e) {
				// JoinFrame_ver3 name = new JoinFrame_ver3();
				// 캘린더 열기 및 창 닫기
				try {
					new CalendarPlanner(LoginFrame_ver3.lid);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				f.dispose();

			}
		});

		b3.addActionListener(new ActionListener() { // 회원정보 수정 및 탈퇴 열기
			public void actionPerformed(ActionEvent e) {

				if (LoginFrame_ver3.lid.equals(admin)) { // 관리자계정일 시 회원정보 조회 및 탈퇴 화면 열기
					// 관리자 계정일 시 회원정보 조회 및 탈퇴화면 열기
					MemberSearchFrame_ver3 name = new MemberSearchFrame_ver3();
					f.dispose();
				} else {

					// 일반 회원이면 회원정보 수정 및 탈퇴 열기
					try {
						ChangeDeleteFrame_ver1 name = new ChangeDeleteFrame_ver1(LoginFrame_ver3.lid);
						f.dispose();
					} catch (Exception e1) {
					}
//				

				}

			}
		});

		l1.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		b2.setFont(new Font("함초롬돋움", Font.PLAIN, 20));
		b3.setFont(new Font("함초롬돋움", Font.PLAIN, 20));
		f.getContentPane().setLayout(null);

		f.getContentPane().add(l1);
		f.getContentPane().add(b2);
		f.getContentPane().add(b3);
		f.setResizable(false);
		f.setVisible(true);

	} // void main 종료

} // public class LoginFrame 종료
