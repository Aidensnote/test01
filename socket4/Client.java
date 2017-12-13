package socket4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String args[]){
		 try {
			String name = "["+args[0]+"] ";
			Socket socket = new Socket("70.12.109.68",9000);
			System.out.println("접속됨..");
			
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			
			new ClientThread(socket, br).start();							//8 생성.
			
			String message = null;
			while((message = keyboard.readLine())!= null){
				if(message.equals("quit")) break;
				pw.println(name+message);
				pw.flush();
//				String echoMessage = br.readLine();							//9 중복
//				System.out.println("서버 --> 클라이언트로  : "+echoMessage);
			}
			br.close();
			pw.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
class ClientThread extends Thread {				//1
	Socket socket;								//3
	BufferedReader br;
	
	
	public ClientThread() {	}					//4
	public ClientThread(Socket socket,BufferedReader br) {		//5 브로드캐스팅으로 들어오는 data만 처리
		this.socket = socket;
		this.br = br;
	}
	


	@Override
	public void run() {								//2
		try {										//6 브로드케스팅 메세지만 읽기
			String msg = null;
			while((msg = br.readLine()) != null ){	//7 메세지가 null이 아닐떄까지 출력
				System.out.println(msg);
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {

		}
	}
}
