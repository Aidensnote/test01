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
		ServerSocket server = null;									//1 ��ä����
		Socket socket = null;
		try {
			server = new ServerSocket(9000);						//2 ��Ʈ����+����ó��
			System.out.println(" Server : ���� ����� .... ");
			socket = server.accept();								//3 accept������ ������, �����ϸ� Ŭ���̾��� ������ ���� socket ��ä ������.
			System.out.println(socket.getInetAddress()+" ������");	//4 Ŭ���̾��� ���� ��� (ip�ּ�)
			
			OutputStream out = socket.getOutputStream();			//6 ������ ���� ��Ʈ�� ����.
			
			BufferedWriter bw =
					new BufferedWriter(new OutputStreamWriter(out));//7 byteŸ���� OutputStreamWriter�� ���� char���� ó��.
			
			bw.write("������ ȯ���մϴ�");								//8 �޼��� ������ (write)
			bw.flush();												//9 buffer�� Ȱ��� flush�� �ؾ����� ����� ������ ����� ����.
			
			
			
			InputStream in = socket.getInputStream();		
			BufferedReader br =
					new BufferedReader(new InputStreamReader(in));
			
			System.out.println(br.readLine());

			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null) socket.close();					//5 �ڿ��ݳ�
				if(server != null) server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}											
		}
	}
}
