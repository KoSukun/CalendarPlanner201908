package Planner;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FindFrame_ver1 {

//	// 조회할? 정보 선언
//	static String dbid;
//	static String dbpw;
//	static String dbname;
//	static String dbemail;
//	static String dbtel;
	// db 바꿀 정보 선언
	static String newpw;
	// 사용자 입력 정보 선언
	static String fid;
	static String fpw;
	static String fname;
	static String femail;
	static String ftel;

	private static JTextField t1;
	private static JTextField t3;
	private static JTextField t4;
	private static JTextField t5;
	
	// 사용자 입력 정보의 맞는 횟수 확인, 2회 이상시 새로운 비밀번호 제공
	static int correct;

//	public static void main(String[] args) { // 단독테스트
//	FindFrame_ver1 name = new FindFrame_ver1();
//	}
	public FindFrame_ver1() {
		JFrame f = new JFrame();
		f.setTitle("PW 변경");
		f.setSize(423, 300);

		JLabel lt = new JLabel("ID를 포함한 두개 이상의 정확한 정보를 입력해주세요.");
		lt.setForeground(Color.RED);
		lt.setHorizontalAlignment(SwingConstants.CENTER);
		lt.setFont(new Font("함초롬바탕", Font.BOLD, 15));
		lt.setSize(380, 45);
		lt.setLocation(15, 160);

		JLabel l1 = new JLabel("I D");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setBounds(15, 5, 85, 30);
		JLabel l3 = new JLabel("이 름");
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		l3.setBounds(15, 45, 85, 30);
		JLabel l4 = new JLabel("e-mail");
		l4.setHorizontalAlignment(SwingConstants.CENTER);
		l4.setBounds(15, 85, 85, 30);
		JLabel l5 = new JLabel("전화번호");
		l5.setHorizontalAlignment(SwingConstants.CENTER);
		l5.setBounds(15, 125, 85, 30);

		t1 = new JTextField();
		t1.setFont(new Font("Dialog", Font.PLAIN, 20));
		t1.setBounds(120, 5, 275, 30);
		t3 = new JTextField();
		t3.setFont(new Font("Dialog", Font.PLAIN, 20));
		t3.setBounds(120, 45, 275, 30);
		t4 = new JTextField();
		t4.setFont(new Font("Dialog", Font.PLAIN, 20));
		t4.setBounds(120, 85, 275, 30);
		t5 = new JTextField();
		t5.setFont(new Font("Dialog", Font.PLAIN, 20));
		t5.setBounds(120, 125, 275, 30);

		t1.setColumns(20);
		t3.setColumns(20);
		t4.setColumns(20);
		t5.setColumns(20);

		l1.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l3.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l4.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l5.setFont(new Font("함초롬바탕", Font.BOLD, 20));

		f.getContentPane().setLayout(null);
		f.getContentPane().add(lt);
		f.getContentPane().add(l1);
		f.getContentPane().add(t1);
		f.getContentPane().add(l3);
		f.getContentPane().add(t3);
		f.getContentPane().add(l4);
		f.getContentPane().add(t4);
		f.getContentPane().add(l5);
		f.getContentPane().add(t5);

		JPanel panel = new JPanel();
		panel.setBounds(130, 215, 150, 40);
		f.getContentPane().add(panel);

		// 랜덤 비밀번호 부여, 6자리수
		newpw = "";
		Random r = new Random();
		for (int i = 0; i < 6; i++) {
			int pwn = r.nextInt(9);
			String pwp = Integer.toString(pwn);
			newpw = newpw + pwp;
		}
//		System.out.println(newpw);

//		// 작동확인용 data
//		dbid = "jak1234";
//		dbpw = "kk1234";
//		dbname = "강민재";
//		dbemail = "gun";
//		dbtel = "010";

		JButton bj1 = new JButton("확인");
		bj1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 입력 data 변수에 받아오기
				MemberDbConnect_ver1 dao = new MemberDbConnect_ver1();
				MemberDTO dto;
				//입력 값 변수로 받아오기
				fid = t1.getText();
				fname = t3.getText();
				femail = t4.getText();
				ftel = t5.getText();

				//회원정보 일치 확인
				try {	//ID 일치 정보 불러오기
					dto = dao.selectf(fid);

					correct = 0;

					if (fid.equals(dto.getId())) {	//ID 일치확인
						correct++;
					}
					;
					if (fname.equals(dto.getName())) { //NAME 일치 확인
						correct++;
					}
					;
					if (femail.equals(dto.getEmail())) {	//EMAIL 일치 확인
						correct++;
					}
					;
					if (ftel.equals(dto.getTel())) {	//TEL 일치 확인
						correct++;
					}
					;
					if (correct >= 2) { //ID포함 2개 이상 일치 확인
						//비밀번호 랜덤값 변경.
						fid = dto.getId();
						fpw = newpw;
						//db 랜덤비밀번호로 변경
						dto = dao.updatec(fid, fpw);

						JOptionPane.showMessageDialog(null, "아래 새 비밀번호를 확인하세요.\n" + newpw);
						f.dispose();
					} else {	//id 불일치 혹은 id 포함 2개 이상 일치하지 않음
						JOptionPane.showMessageDialog(null, "정보를 다시 입력해주세요.");
					}
					;

				} catch (Exception e1) {
					System.out.println(e1);
				}

			}
		});

		JButton bj2 = new JButton("취소");
		bj2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
		});

		panel.add(bj1);
		panel.add(bj2);
		bj1.setFont(new Font("함초롬바탕", Font.BOLD, 15));
		bj2.setFont(new Font("함초롬바탕", Font.BOLD, 15));
		f.setVisible(true);

	}
}
