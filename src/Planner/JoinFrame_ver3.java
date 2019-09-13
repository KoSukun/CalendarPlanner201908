package Planner;

//버튼 DB연동 입력 추가
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class JoinFrame_ver3 {

	// 입력 정보 선언
	static String joinid;
	static String joinpw;
	static String joinname;
	static String joinemail;
	static String jointel;
	static String joindate;
	static String joinedid; // 가입된 회원id
	static int check; // 중복확인 ok or no

//	public static void main(String[] args) { // 단독테스트
//		JoinFrame_ver3 name = new JoinFrame_ver3();
//	}

	public JoinFrame_ver3() { // main에서 변경
		JFrame f = new JFrame();
		f.setTitle("회원가입");
		f.setSize(387, 322);
		MemberDbConnect_ver1 dao = new MemberDbConnect_ver1();
		MemberDTO dto = new MemberDTO();

		// 현재날짜출력?
		Date d = new Date();
		int jy = d.getYear() + 1900;
		int jm = d.getMonth() + 1;
		int jd = d.getDate();
		String joinyear = Integer.toString(jy);
		String joinmonth = Integer.toString(jm);
		String joinday = Integer.toString(jd);
		String joindate = joinyear + "-" + joinmonth + "-" + joinday;

		JLabel l1 = new JLabel("ID");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setBounds(15, 5, 85, 30);
		JLabel l2 = new JLabel("PW");
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setBounds(15, 45, 85, 30);
		JLabel l3 = new JLabel("이름");
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		l3.setBounds(15, 85, 85, 30);
		JLabel l4 = new JLabel("e-mail");
		l4.setHorizontalAlignment(SwingConstants.CENTER);
		l4.setBounds(15, 125, 85, 30);
		JLabel l5 = new JLabel("전화번호");
		l5.setHorizontalAlignment(SwingConstants.CENTER);
		l5.setBounds(15, 165, 85, 30);

		JTextField t1 = new JTextField();
		t1.setFont(new Font("Dialog", Font.PLAIN, 20));
		t1.setBounds(120, 5, 230, 30);
		JTextField t2 = new JTextField();
		t2.setFont(new Font("Dialog", Font.PLAIN, 20));
		t2.setBounds(120, 45, 230, 30);
		JTextField t3 = new JTextField();
		t3.setFont(new Font("Dialog", Font.PLAIN, 20));
		t3.setBounds(120, 85, 230, 30);
		JTextField t4 = new JTextField();
		t4.setFont(new Font("Dialog", Font.PLAIN, 20));
		t4.setBounds(120, 125, 230, 30);
		JTextField t5 = new JTextField();
		t5.setFont(new Font("Dialog", Font.PLAIN, 20));
		t5.setBounds(120, 165, 230, 30);

		t1.setColumns(20);
		t2.setColumns(20);
		t3.setColumns(20);
		t4.setColumns(20);
		t5.setColumns(20);

		l1.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l2.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l3.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l4.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l5.setFont(new Font("함초롬바탕", Font.BOLD, 20));

		f.getContentPane().setLayout(null);
		f.getContentPane().add(l1);
		f.getContentPane().add(t1);
		f.getContentPane().add(l2);
		f.getContentPane().add(t2);
		f.getContentPane().add(l3);
		f.getContentPane().add(t3);
		f.getContentPane().add(l4);
		f.getContentPane().add(t4);
		f.getContentPane().add(l5);
		f.getContentPane().add(t5);

		JPanel panel = new JPanel();
		panel.setBounds(28, 220, 310, 40);
		f.getContentPane().add(panel);
		JButton bj1 = new JButton("확인");
		JButton bj2 = new JButton("취소");

		JButton btId = new JButton("ID 중복 확인");
		btId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 입력 data 변수에 받아오기
				joinid = t1.getText();
//				회원id중복확인
				ArrayList<MemberDTO> list = new ArrayList();
				try {
					list = dao.SelectAll();
				} catch (Exception e2) {
					System.out.println(e2);
				}

				for (int i = 0; i < list.size(); i++) {
					check=0;
					joinedid = list.get(i).getId();
					if (joinid.equals(joinedid)) {
						JOptionPane.showMessageDialog(null, "ID가 중복됩니다. 새 ID를 설정해주세요.");
						check++;
						break;
					}
				}
				if (check == 0) {
					JOptionPane.showMessageDialog(null, "ID를 사용하여도 좋습니다. 회원가입을 진행해주세요.");
					check--;
				}
			}
		});
		btId.setFont(new Font("함초롬바탕", Font.BOLD, 15));
		panel.add(btId);
		panel.add(bj1);
		panel.add(bj2);
		bj1.setFont(new Font("함초롬바탕", Font.BOLD, 15));
		bj2.setFont(new Font("함초롬바탕", Font.BOLD, 15));

		bj1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 입력 data 변수에 받아오기
				joinid = t1.getText();
				joinpw = t2.getText();
				joinname = t3.getText();
				joinemail = t4.getText();
				jointel = t5.getText();

				// id 중복확인 유무 확인
				if (check==0) {
					JOptionPane.showMessageDialog(null, "ID 중복확인을 진행해주세요.");
				}else if (check==1) {
					JOptionPane.showMessageDialog(null, "ID를 변경해주세요.");
					check=0;
				}
				// 회원가입 내용 null 확인 및 각 확인 별 문구출력
				else if (joinid.equals("")) {
					JOptionPane.showMessageDialog(null, "ID를 입력해주세요.");
				} else if (joinpw.equals("")) {
					JOptionPane.showMessageDialog(null, "PW를 입력해주세요.");
				} else if (joinname.equals("")) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요.");
				} else if (joinemail.equals("")) {
					JOptionPane.showMessageDialog(null, "이메일을 입력해주세요.");
				} else if (!joinid.equals("") && !joinpw.equals("") && !joinname.equals("") && !joinemail.equals("")) {
					// db 저장용 변수에 입력값 저장
					dto.setId(joinid);
					dto.setPw(joinpw);
					dto.setName(joinname);
					dto.setEmail(joinemail);
					dto.setTel(jointel);
					dto.setJoindate(joindate);
					// db처리로 보내기
					try {
						dao.insert(dto);
					} catch (Exception e1) {
//						e1.printStackTrace(); //에러확인란
					}

					JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다.");
					f.dispose();// 회원가입 창 닫기
					// 완료 save data 및 window 닫기
				}

			}
		});

		bj2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
		});

		f.setVisible(true);

	}
}
