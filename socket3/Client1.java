package socket3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client1 {
	public static void main(String[] args) {
		Socket socket = null;								//1
		
		try {
			socket = new Socket("70.12.109.68", 9000);		//2 객채생성 + 예외처리 = 접속 요청
			System.out.println("접속완료");
			
			InputStream in = socket.getInputStream();		//4 받을 스트림 준비. (클라이언트 <-> 서버의 짝을 맞춰줘야한다)
			
			BufferedReader br =
					new BufferedReader(new InputStreamReader(in));	//5 서버와 동일하게 처리.
			
			System.out.println(br.readLine());						//6 메세지 읽기 (read)
			
			
			OutputStream out = socket.getOutputStream();
			BufferedWriter bw =
					new BufferedWriter(new OutputStreamWriter(out));
			
			
			bw.write("안녕하세요");
			bw.flush();
			
//			
//			while(bw.equals(null)){
//				System.out.println("--");
//			}
//			
			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null) socket.close();			//3 자원반납
			} catch (IOException e) {
				e.printStackTrace();
			}											
		}
	}
}
