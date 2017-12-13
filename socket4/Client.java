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
			System.out.println("���ӵ�..");
			
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			
			new ClientThread(socket, br).start();							//8 ����.
			
			String message = null;
			while((message = keyboard.readLine())!= null){
				if(message.equals("quit")) break;
				pw.println(name+message);
				pw.flush();
//				String echoMessage = br.readLine();							//9 �ߺ�
//				System.out.println("���� --> Ŭ���̾�Ʈ��  : "+echoMessage);
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
	public ClientThread(Socket socket,BufferedReader br) {		//5 ��ε�ĳ�������� ������ data�� ó��
		this.socket = socket;
		this.br = br;
	}
	


	@Override
	public void run() {								//2
		try {										//6 ��ε��ɽ��� �޼����� �б�
			String msg = null;
			while((msg = br.readLine()) != null ){	//7 �޼����� null�� �ƴҋ����� ���
				System.out.println(msg);
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {

		}
	}
}
