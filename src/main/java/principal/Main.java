package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		try {
			getEnderecoRede();
			serverSocket();
			connectServer();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void serverSocket() throws IOException {
		ServerSocket servidor = new ServerSocket(8081);
		while (servidor.isBound()) {
			Socket cliente = servidor.accept();
			InputStream comandos = cliente.getInputStream();
			OutputStream dados = cliente.getOutputStream();
			dados.flush();
			BufferedReader is = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			String linha = "";
			imprimeLinhas(is);
			cliente.close();
		}
	}

	private static void getEnderecoRede() throws UnknownHostException {
		InetAddress endereco = InetAddress.getLocalHost();
		String ip = endereco.getHostAddress();
		String nome = endereco.getHostName();
		String nomeCompleto = endereco.getCanonicalHostName();
		JOptionPane.showMessageDialog(null,
				"O nome da minha maquina é : " + nome + "\nNome completo: " + nomeCompleto + "\nE o meu ip é: " + ip,
				"Informações da máquina", JOptionPane.PLAIN_MESSAGE, null);
	}

	private static void connectServer() throws UnknownHostException, IOException {
		Socket soc = new Socket("127.0.0.1", 8081);
		PrintWriter os = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()));
		BufferedReader is = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		os.println("GET / HTTP/1.0\n\n");
		os.flush();
		imprimeLinhas(is);
		soc.close();
	}

	private static void imprimeLinhas(BufferedReader is) throws IOException {
		String linha;
		while ((linha = is.readLine()) != null) {
			System.out.println(linha);
		}
	}
}