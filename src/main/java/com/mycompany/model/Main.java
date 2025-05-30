package com.mycompany.model;

import com.mycompany.dao.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Connection dao = new Connection();
        Scanner inputOpcao = new Scanner(System.in); 
        int opcao = -1;

        do { 
            exibirMenu();
            try {
                opcao = inputOpcao.nextInt();

                switch (opcao) {
                    case 1:
                        dao.adicionarContato();
                        break;
                    case 2:
                        dao.listarContatos();
                        break;
                    case 3:
                        dao.atualizarContato();
                        break;
                    case 4:
                        dao.deletarContato();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção invalida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Por favor, digite um numero.");
                inputOpcao.next(); // Limpa o buffer do scanner
                opcao = -1; // Reseta a opção para continuar no loop
            } catch (Exception e) {
                 System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }

        } while (opcao != 0);

        dao.fechar();
        inputOpcao.close();
    }

    public static void exibirMenu() {
        System.out.println("\n--- Menu Agenda MongoDB ---");
        System.out.println("1. Adicionar Contato");
        System.out.println("2. Listar Contatos");
        System.out.println("3. Atualizar Contato");
        System.out.println("4. Deletar Contato");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opcao: ");
    }
}