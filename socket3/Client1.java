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
			socket = new Socket("70.12.109.68", 9000);		//2 ��ä���� + ����ó�� = ���� ��û
			System.out.println("���ӿϷ�");
			
			InputStream in = socket.getInputStream();		//4 ���� ��Ʈ�� �غ�. (Ŭ���̾�Ʈ <-> ������ ¦�� ��������Ѵ�)
			
			BufferedReader br =
					new BufferedReader(new InputStreamReader(in));	//5 ������ �����ϰ� ó��.
			
			System.out.println(br.readLine());						//6 �޼��� �б� (read)
			
			
			OutputStream out = socket.getOutputStream();
			BufferedWriter bw =
					new BufferedWriter(new OutputStreamWriter(out));
			
			
			bw.write("�ȳ��ϼ���");
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
				if(socket != null) socket.close();			//3 �ڿ��ݳ�
			} catch (IOException e) {
				e.printStackTrace();
			}											
		}
	}
}
