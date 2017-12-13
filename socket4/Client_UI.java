package socket4;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client_UI {
	
	JFrame f = new JFrame("채팅 클라이언트");				//2 생성
	Button b1, b2;					// 버튼 선언
	TextField ip, port, id, msg;	// 텍스트 필드
	TextArea ta;					// 텍스트에어리어
	Socket socket = null;
	BufferedReader br;
	PrintWriter pw;
	
	
	Client_UI() {								//3 생성자함수
		Panel p1 = new Panel();							//패널 생성
		p1.add(ip = new TextField("70.12.109.68"));		//ip 패널에 붙이기
		p1.add(port = new TextField("9001"));			//port 패널에 붙이기
		p1.add(b1 = new Button("연결"));					//연결 버튼 붙이기
		p1.add(b2 = new Button("종료"));					//종료 버튼 붙이기
		f.add(p1, BorderLayout.NORTH);
		f.add(ta = new TextArea(25,30));
		
		Panel p2 = new Panel();
		p2.add(id = new TextField(10));
		p2.add(msg = new TextField(30));
		f.add(p2, BorderLayout.SOUTH);
		f.setSize(400, 350);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		//연결버튼
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				String name = id.getText();
				try {
					socket = new Socket(ip.getText(),9001);
					System.out.println("접속됨..");
					InputStream in = socket.getInputStream();
					br = new BufferedReader(new InputStreamReader(in));
					OutputStream out = socket.getOutputStream();
					pw = new PrintWriter(new OutputStreamWriter(out));
					
					new ClientThread2(socket,br,ta).start();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		//종료
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(socket !=null) socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 채팅창
		msg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pw.println(id.getText() +" : "+ msg.getText());
				pw.flush();
				msg.setText("");
			}
		});
		
	}
	
	
	
	public static void main(String args[]) {
		new Client_UI();
////		Socket socket = null;
//		try {
////			String name = "[" + args[0] + "] ";
////			Socket socket = new Socket("70.12.109.68", 9000);
////			System.out.println("접속됨..");
//
////			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));		//필요x
//
////			InputStream in = socket.getInputStream();
////			BufferedReader br = new BufferedReader(new InputStreamReader(in));
////			OutputStream out = socket.getOutputStream();
////			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
//
//			// new ClientThread(socket, br).start(); 								//2 주석처리
//
//			String message = null;
//			while ((message = keyboard.readLine()) != null) {
//				if (message.equals("quit"))
//					break;
////				pw.println(name + message);
////				pw.flush();
//			}
//			br.close();
//			pw.close();
//			socket.close();
//
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
	class ClientThread2 extends Thread {			// 1 이너클래스로 변경
		Socket socket;
		BufferedReader br;
		TextArea ta;

		public ClientThread2() { }
		public ClientThread2(Socket socket, BufferedReader br,TextArea ta) {
			this.socket = socket;
			this.br = br;
			this.ta = ta;
		}

		@Override
		public void run() {
			try {
				String msg = null;
				while ((msg = br.readLine()) != null) {
					ta.append(msg+"\n");
				}
			} catch (Exception e) {
				e.getMessage();
			} finally {

			}
		}
	}
}
