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
		List<Socket> list = new Vector<>(); 	 			//1 ������ ��� List
//		List<EchoThread> list2 = new Vector<>(); 			//1 ������ ��� List
		try {
			server = new ServerSocket(9001);
			System.out.println("Ŭ���̾�Ʈ�� ������ �����");

			while (true) {
				Socket socket = server.accept();
				list.add(socket);							//3 ���������� list���
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

	public EchoThread(Socket socket,List<Socket> list) {	//4 ������ �߰�
		super();
		this.socket = socket;
		this.list = list;									//5
	}

	@Override 															
	public void run() {
		InetAddress address = socket.getInetAddress(); 					
		System.out.println(address.getHostAddress() + " �κ��� �����߽��ϴ�.");
		try {
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			OutputStream out = socket.getOutputStream();					//10 ��ġ�̵�
//			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			// DataInputStream di = new DataInputStream(in);
			// di.readUTF();

			String message = null;
			while ((message = br.readLine()) != null) {
				broadcast(message);											//11
//				System.out.println("Ŭ���̾�Ʈ --> ������ : " + message);
//				pw.println(message);										//8 ����(�Ѹ�)���Ը� �޼��� ������ ���	//10��ġ�̵�
//				pw.flush();
			}
//			pw.close();														//12 ����
			br.close();														//
		} catch (Exception e) {

		} finally {
			try {
				list.remove(socket);										//14 �ڿ��ݳ�. ���ϻӸ� �ƴ϶� ����Ʈ������ �������.
				System.out.println(socket+"��������");
				System.out.println(list);
				if(socket != null) socket.close();							//13 ����ó��
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	public synchronized void broadcast(String msg) throws IOException{					//7 broadcast �޼ҵ� ����. list ���� ���Ƽ� client�� �ѷ��ֱ�.
		for(Socket socket:list){											//9
			OutputStream out = socket.getOutputStream();					//10 �̵� �� ����ó��
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(msg);							
			pw.flush();
		}
	}
}