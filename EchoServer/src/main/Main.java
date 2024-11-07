package main;

import java.util.Scanner;

import client.Client;
import client.gui.ClientStartupGUI;
import gui.ClientGUI;
import server.Server;
import server.gui.ServerGUI;
import server.gui.ServerStartupGUI;

public class Main {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			int input = 0;
			
			System.out.println("[1] Criar servidor");
			System.out.println("[2] Criar cliente");
			input = scanner.nextInt();
			
			switch(input) {
			case 1:
				new ServerStartupGUI(new Server()).setVisible(true);
				break;
			case 2:
				new ClientStartupGUI(new Client()).setVisible(true);
				break;
			case 0:
				System.out.println("Encerrando");
				break;
			default: 
				System.err.println("Entrada inválida, digite uma opção conforme o menu");
			}
		}
	}

}
