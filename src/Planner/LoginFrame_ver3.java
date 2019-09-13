package Planner;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginFrame_ver3 {

	private static JTextField t1;
	private static JPasswordField t2;	
//	private static JTextField t2;
	static String lid;
	// 버튼 전역변수 설정
	static JButton b1, b2, b3;

	public static void main(String[] args) {	//일단 메인, 추후 합칠 예정
		LoginFrame_ver3 name = new LoginFrame_ver3();
	}
	
	
		public LoginFrame_ver3() { 
		// 프레임 셋팅
		JFrame f = new JFrame();
		f.setTitle("Calendar Planner.");
		f.setSize(235, 200);
		FlowLayout flow = new FlowLayout();
		f.getContentPane().setLayout(flow);


//		test용 id pw db 부분
//		String dbid = "jak1234";
//		String dbpw = "kk1234";

		JLabel l1 = new JLabel(" I D: ");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel l2 = new JLabel("P W: ");
		l2.setHorizontalAlignment(SwingConstants.CENTER);

		t1 = new JTextField(); // id입력창
//		t2 = new JTextField(); // pw입력창
		
		//pw *로 출력
		t2 = new JPasswordField();
		t2.getEchoChar();
		
		// 버튼명 선언
		b1 = new JButton("Login");
		b2 = new JButton("회원가입");
		b3 = new JButton("PW 변경");


		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lid = t1.getText();
				String lpw = t2.getText();
				
				MemberDbConnect_ver1 dao = new MemberDbConnect_ver1();
				MemberDTO dto;
				
				try {
					dto = dao.select(lid);
					
				
				
				// 팝업창 발생 및 버튼 클릭시 팝업 close. 또는 로그인프레임 내 라벨에 이미지와 함께 아래 문구 출력
				if (lid.equals(dto.getId()) && lpw.equals(dto.getPw())) {
					JOptionPane.showMessageDialog(null, "로그인이 성공하였습니다.");
					AfterLoginFrame_ver1 name = new AfterLoginFrame_ver1(lid);
					f.dispose(); // close LoginFrame
					//-----------------------//
					//로그인 시 calendar open
				} 
				else {
					if (!lid.equals(dto.getId())) { // ID 일치 확인
						JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다. ID를 확인해주세요.");
					} else if (!lpw.equals(dto.getPw())) { // ID일치 확인 후 PW일치확인
						JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다. PW를 확인해주세요.");
					}
				}
				
				} catch (Exception e1) {
					System.out.println(e1);
				}
				
				
				// 팝업창 종료
				// 로그인 완료에 따른 로그인 버튼 -> "name님이 접속하였습니다." 노출

			}
		});

		b2.addActionListener(new ActionListener() {	//회원가입창 열기
			
			public void actionPerformed(ActionEvent e) {
				JoinFrame_ver3 name = new JoinFrame_ver3();
				
			}
		});
		
		b3.addActionListener(new ActionListener() {	//ID PW찾기 창 열기
			public void actionPerformed(ActionEvent e) {
				FindFrame_ver1 name = new FindFrame_ver1();
			}
		});
		
		
		l1.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		t1.setFont(new Font("굴림", Font.PLAIN, 20));
		l2.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		t2.setFont(new Font("굴림", Font.PLAIN, 20));
		b1.setFont(new Font("함초롬돋움", Font.PLAIN, 20));
		b2.setFont(new Font("함초롬돋움", Font.PLAIN, 20));
		b3.setFont(new Font("함초롬돋움", Font.PLAIN, 20));

		t1.setColumns(10);
		t2.setColumns(10);

		f.getContentPane().add(l1);
		f.getContentPane().add(t1);
		f.getContentPane().add(l2);
		f.getContentPane().add(t2);
		f.getContentPane().add(b1);
		f.getContentPane().add(b2);
		f.getContentPane().add(b3);
		f.setResizable(false);
		f.setVisible(true);

	} // void main 종료

} // public class LoginFrame 종료
