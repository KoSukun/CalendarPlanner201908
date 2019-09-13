package Planner;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ChangeDeleteFrame_ver1 {

	static String lid; // after login frame에서 넘어오는 id
	// label 입력 정보
	static String upw;
	static String uname;
	static String uemail;
	static String utel;

//	public static void main(String[] args) throws Exception {	//단독 테스트
//		ChangeDeleteFrame_ver1 name = new ChangeDeleteFrame_ver1(LoginFrame_ver3.lid);
//	}
	public ChangeDeleteFrame_ver1(String lid) throws Exception {
		JFrame f = new JFrame();
		f.setTitle("회원 정보 수정 및 탈퇴");
		f.setSize(387, 340);

		// 로그인 된 회원 id 확인 후 수정 창 접속
		// 회원 id[pk, 수정불가능][label 작성]
		// 그외 정보 수정가능 [textbox]

		// test용 pk 입력

		// 창 열릴때 pk이용 textfield에 정보 출력
		MemberDbConnect_ver1 dao = new MemberDbConnect_ver1();
		MemberDTO dto;
		dto = dao.selectu(LoginFrame_ver3.lid);

		JLabel l1 = new JLabel("I D");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setBounds(15, 5, 85, 30);
		JLabel l2 = new JLabel("P W");
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setBounds(15, 45, 85, 30);
		JLabel l3 = new JLabel("이 름");
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		l3.setBounds(15, 85, 85, 30);
		JLabel l4 = new JLabel("e-mail");
		l4.setHorizontalAlignment(SwingConstants.CENTER);
		l4.setBounds(15, 125, 85, 30);
		JLabel l5 = new JLabel("전화번호");
		l5.setHorizontalAlignment(SwingConstants.CENTER);
		l5.setBounds(15, 165, 85, 30);
		JLabel lj = new JLabel("가입일");
		lj.setHorizontalAlignment(SwingConstants.CENTER);
		lj.setBounds(15, 205, 85, 30);

		JLabel tpk = new JLabel(lid); // pk dbid 값 출력
		tpk.setFont(new Font("Dialog", Font.PLAIN, 20));
		tpk.setBounds(120, 5, 230, 30);
		JTextField t2 = new JTextField(dto.getPw());
		t2.setFont(new Font("Dialog", Font.PLAIN, 20));
		t2.setBounds(120, 45, 230, 30);
		JTextField t3 = new JTextField(dto.getName());
		t3.setFont(new Font("Dialog", Font.PLAIN, 20));
		t3.setBounds(120, 85, 230, 30);
		JTextField t4 = new JTextField(dto.getEmail());
		t4.setFont(new Font("Dialog", Font.PLAIN, 20));
		t4.setBounds(120, 125, 230, 30);
		JTextField t5 = new JTextField(dto.getTel());
		t5.setFont(new Font("Dialog", Font.PLAIN, 20));
		t5.setBounds(120, 165, 230, 30);
		JLabel ljd = new JLabel(dto.getJoindate());
		ljd.setBounds(120, 205, 230, 30);

		t2.setColumns(20);
		t3.setColumns(20);
		t4.setColumns(20);
		t5.setColumns(20);

		l1.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l2.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l3.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l4.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		l5.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		lj.setFont(new Font("함초롬바탕", Font.BOLD, 20));
		ljd.setFont(new Font("Dialog", Font.PLAIN, 20));

		f.getContentPane().setLayout(null);
		f.getContentPane().add(l1);
		f.getContentPane().add(tpk);
		f.getContentPane().add(l2);
		f.getContentPane().add(t2);
		f.getContentPane().add(l3);
		f.getContentPane().add(t3);
		f.getContentPane().add(l4);
		f.getContentPane().add(t4);
		f.getContentPane().add(l5);
		f.getContentPane().add(t5);
		f.getContentPane().add(lj);
		f.getContentPane().add(ljd);

		JPanel panel = new JPanel();
		panel.setBounds(70, 251, 239, 40);
		f.getContentPane().add(panel);
		JButton bj1 = new JButton("수정");
		bj1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upw = t2.getText();
				uname = t3.getText();
				uemail = t4.getText();
				utel = t5.getText();

				MemberDbConnect_ver1 dao = new MemberDbConnect_ver1();
				MemberDTO dto;

				try {
					dto = dao.update(LoginFrame_ver3.lid, upw, uname, uemail, utel);
				} catch (Exception e1) {
					System.out.println(e1);
				}

				JOptionPane.showMessageDialog(null, "개인정보 변경이 완료되었습니다.");
				new AfterLoginFrame_ver1(LoginFrame_ver3.lid);
				f.dispose();
			}

		});

		JButton bj2 = new JButton("취소");
		bj2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
		});
		panel.add(bj1);

		JButton bjd = new JButton("탈퇴");
		bjd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// db내 pk id의 row 전체 삭제
				MemberDbConnect_ver1 dao = new MemberDbConnect_ver1();
//				MemberDTO dto;
				
				int i = JOptionPane.showConfirmDialog(null, "정말로 탈퇴를 진행하시겠습니까?");
				if (i == 0) {
					try {
						dao.delete(LoginFrame_ver3.lid);
						PlansTableUpdateWhenUserUpdatedOrDeletedDAO plansTableUpdateDAO = new PlansTableUpdateWhenUserUpdatedOrDeletedDAO();
						try {
							plansTableUpdateDAO.deletePlanForDeletedUser(LoginFrame_ver3.lid);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
//						dto = dao.delete(LoginFrame_ver3.lid);
					} catch (Exception e1) {
						System.out.println(e1);
					}
					JOptionPane.showMessageDialog(null, "회원 탈퇴가 완료되었습니다.");
					// 이후 로그아웃 어떻게???**
					LoginFrame_ver3.lid = null;
					new LoginFrame_ver3();
					f.dispose();
				}
			}
		});

		bjd.setFont(new Font("함초롬바탕", Font.BOLD, 15));
		panel.add(bjd);
		panel.add(bj2);
		bj1.setFont(new Font("함초롬바탕", Font.BOLD, 15));
		bj2.setFont(new Font("함초롬바탕", Font.BOLD, 15));

		f.setVisible(true);
	}
}
