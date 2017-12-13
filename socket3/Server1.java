package socket3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
	public static void main(String[] args) {
		ServerSocket server = null;									//1 객채생성
		Socket socket = null;
		try {
			server = new ServerSocket(9000);						//2 포트지정+예외처리
			System.out.println(" Server : 접속 대기중 .... ");
			socket = server.accept();								//3 accept문으로 대기상태, 수락하면 클라이언츠 정보를 가진 socket 객채 생성됨.
			System.out.println(socket.getInetAddress()+" 접속함");	//4 클라이언츠 정보 출력 (ip주소)
			
			OutputStream out = socket.getOutputStream();			//6 보내기 위한 스트림 생성.
			
			BufferedWriter bw =
					new BufferedWriter(new OutputStreamWriter(out));//7 byte타입을 OutputStreamWriter로 래핑 char으로 처리.
			
			bw.write("접속을 환영합니다");								//8 메세지 보내기 (write)
			bw.flush();												//9 buffer를 활용시 flush를 해야지만 비워진 값까지 제대로 전송.
			
			
			
			InputStream in = socket.getInputStream();		
			BufferedReader br =
					new BufferedReader(new InputStreamReader(in));
			
			System.out.println(br.readLine());

			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null) socket.close();					//5 자원반납
				if(server != null) server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}											
		}
	}
}
