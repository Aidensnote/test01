package socket3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static void main(String args[]) {
		ServerSocket server = null;											//5 객체 생성 위로
		try {
			server = new ServerSocket(9005);
			System.out.println("클라이언트의 접속을 대기중");

			while (true) {
				Socket socket = server.accept();
				new EchoThread(socket).start();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (server != null) server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class EchoThread extends Thread { 											// 1 쓰레드로 동작할 메세지를 주고받기
	Socket socket; 															// 3 socket정보를 받아와야 동작한다.

	public EchoThread() { 													// 4 디폴트 + 생성자
		super();
	}

	public EchoThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override 																// 2 run 오버라이딩
	public void run() {
		InetAddress address = socket.getInetAddress(); 						// 4 쓰레드로 넘거야할 코드
		System.out.println(address.getHostAddress() + " 로부터 접속했습니다.");
		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			// DataInputStream di = new DataInputStream(in);
			// di.readUTF();

			String message = null;
			while ((message = br.readLine()) != null) {
//				System.out.println("클라이언트 --> 서버로 : " + message);
				pw.println(message);
				pw.flush();
			}
			br.close();
			pw.close();
			socket.close();
		} catch (Exception e) {

		} finally {

		}
	}
}