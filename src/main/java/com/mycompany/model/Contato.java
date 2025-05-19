package com.mycompany.model;

public class Contato {
    private String nome;
    private int idade;
    private String email;

    public Contato() {}

    public Contato(String nome, int idade, String email) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getEmail() { return email; }
}
