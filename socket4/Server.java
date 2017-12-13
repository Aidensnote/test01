package socket4;

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
import java.util.List;
import java.util.Vector;

public class Server {
	public static void main(String args[]) {
		ServerSocket server = null;
		List<Socket> list = new Vector<>(); 	 			//1 공유할 목록 List
//		List<EchoThread> list2 = new Vector<>(); 			//1 공유할 목록 List
		try {
			server = new ServerSocket(9001);
			System.out.println("클라이언트의 접속을 대기중");

			while (true) {
				Socket socket = server.accept();
				list.add(socket);							//3 소켓정보를 list담기
				new EchoThread(socket,list).start();		//6 
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

class EchoThread extends Thread { 											
	List<Socket> list;										//2
	Socket socket;
	

	public EchoThread() { 													
		super();
	}

	public EchoThread(Socket socket,List<Socket> list) {	//4 생성자 추가
		super();
		this.socket = socket;
		this.list = list;									//5
	}

	@Override 															
	public void run() {
		InetAddress address = socket.getInetAddress(); 					
		System.out.println(address.getHostAddress() + " 로부터 접속했습니다.");
		try {
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			OutputStream out = socket.getOutputStream();					//10 위치이동
//			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			// DataInputStream di = new DataInputStream(in);
			// di.readUTF();

			String message = null;
			while ((message = br.readLine()) != null) {
				broadcast(message);											//11
//				System.out.println("클라이언트 --> 서버로 : " + message);
//				pw.println(message);										//8 에코(한명)에게만 메세지 보내던 기능	//10위치이동
//				pw.flush();
			}
//			pw.close();														//12 삭제
			br.close();														//
		} catch (Exception e) {

		} finally {
			try {
				list.remove(socket);										//14 자원반납. 소켓뿐만 아니라 리스트에서도 빼줘야함.
				System.out.println(socket+"접속해제");
				System.out.println(list);
				if(socket != null) socket.close();							//13 예외처리
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	public synchronized void broadcast(String msg) throws IOException{					//7 broadcast 메소드 생성. list 루프 돌아서 client에 뿌려주기.
		for(Socket socket:list){											//9
			OutputStream out = socket.getOutputStream();					//10 이동 후 예외처리
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(msg);							
			pw.flush();
		}
	}
}