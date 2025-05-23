package com.mycompany.dao;

import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import java.util.Scanner;

public class Connection {

    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;
    private final Scanner scanner;

    public Connection() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("agenda_pessoal");
        collection = database.getCollection("contatos");
        scanner = new Scanner(System.in);
        System.out.println("Conectado ao MongoDB com sucesso!");
    }

    public void run() {
        String op;
        do {
            System.out.println("\n1) Adicionar  2) Listar  3) Atualizar  4) Deletar  5) Sair");
            op = scanner.nextLine();
            switch (op) {
                case "1":
                    adicionarContato();
                    break;
                case "2":
                    listarContatos();
                    break;
                case "3":
                    atualizarContato();
                    break;
                case "4":
                    deletarContato();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (!op.equals("5"));
        fechar();
    }

    public void adicionarContato() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        Document doc = new Document("nome", nome)
                .append("telefone", telefone);
        collection.insertOne(doc);
        System.out.println("Contato adicionado: " + doc.getObjectId("_id"));
    }

    public void listarContatos() {
        System.out.println("\n--- Contatos ---");
        for (Document doc : collection.find()) {
            String nome = doc.getString("nome");
            String telefone = doc.getString("telefone");

            System.out.printf("%s | %s%n", nome, telefone);
        }
    }

    public void atualizarContato() {
        System.out.print("Nome do contato a atualizar: ");
        String nome = scanner.nextLine();
        Document existente = collection.find(eq("nome", nome)).first();
        if (existente == null) {
            System.out.println("Contato nao encontrado.");
            return;
        }
        System.out.print("Novo nome (enter = não altera): ");
        String novoNome = scanner.nextLine();
        System.out.print("Novo telefone (enter = não altera): ");
        String novoTel = scanner.nextLine();

        Document updates = new Document();
        if (!novoNome.isBlank()) {
            updates.append("nome", novoNome);
        }
        if (!novoTel.isBlank()) {
            updates.append("telefone", novoTel);
        }
        if (updates.isEmpty()) {
            System.out.println("Nada para alterar.");
            return;
        }
        collection.updateOne(eq("nome", nome), new Document("$set", updates));
        System.out.println("Contato atualizado!");
    }

    public void deletarContato() {
        System.out.print("Nome do contato a deletar: ");
        String nome = scanner.nextLine();
        long deleted = collection.deleteOne(eq("nome", nome)).getDeletedCount();
        System.out.println(deleted > 0
                ? "Contato deletado!"
                : "Contato nao encontrado.");
    }

    public void fechar() {
        scanner.close();
        mongoClient.close();
        System.out.println("Conexao encerrada!");
    }

    public static void main(String[] args) {
        new Connection().run();
    }
}
