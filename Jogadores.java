/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;
import java.util.*;

public abstract class Jogadores {
    public String nome;
    protected int frotas[];
    MeuTabuleiro inimigo;
    MeuTabuleiro tabuleiro;

    public Jogadores(String nome, MeuTabuleiro inimigo, MeuTabuleiro tabuleiro) {
        this.nome = nome;
        this.frotas = new int[]{2, 3, 4};
        this.tabuleiro = tabuleiro;
        this.inimigo = inimigo;
    }

    abstract void ataque();
    abstract void posicao();
}

// ============================ JOGADOR HUMANO ============================

class Jogador1 extends Jogadores {

    public Jogador1(String nome, MeuTabuleiro inimigo, MeuTabuleiro tabuleiro) {
        super(nome, inimigo, tabuleiro);
    }

    @Override
    void posicao() {
        Scanner tecla = new Scanner(System.in);
        System.out.println("Vamos posicionar as frotas!");
        System.out.println("Essas sao as suas frotas: " + frotas.length + " navios para posicionar.");

        for (int i = 0; i < frotas.length; i++) {
            int tamanhoN = frotas[i];
            System.out.println("\nPosicione o navio de tamanho " + tamanhoN);

            for (int j = 0; j < tamanhoN; j++) {
                boolean posicaoValida = false;
                String entrada;

                do {
                    System.out.println("Digite a coordenada " + (j + 1) + " (Ex: A1, B5):");
                    entrada = tecla.nextLine().toUpperCase().trim();

                    // valida formato Letra+Número
                    if (!entrada.matches("^[A-J](10|[1-9])$")) {
                        System.out.println("Formato invalido! Use formato Letra+Numero (ex: A1, B10).");
                        continue;
                    }

                    int linha = entrada.charAt(0) - 'A';
                    int coluna = Integer.parseInt(entrada.substring(1)) - 1;

                    if (linha < 0 || linha >= tabuleiro.gettamanhoX() || coluna < 0 || coluna >= tabuleiro.gettamanhoY()) {
                        System.out.println("Posicao fora do limite!");
                        continue;
                    }

                    if (tabuleiro.posicaoDisponivel(linha, coluna)) {
                        tabuleiro.getcampo()[linha][coluna] = 'N';
                        posicaoValida = true;
                    } else {
                        System.out.println("Posicao ja ocupada. Digite novamente.");
                    }

                } while (!posicaoValida);
            }
        }
    }

    @Override
    void ataque() {
        Scanner tecla = new Scanner(System.in);
        boolean tiroValido = false;

        while (!tiroValido) {
            System.out.println("Digite a coordenada do ataque (Ex: A1, B5):");
            String entrada = tecla.nextLine().toUpperCase().trim();

            if (!entrada.matches("^[A-J](10|[1-9])$")) {
                System.out.println("Formato invalido! Use formato Letra+Número (ex: A1, B10).");
                continue;
            }

            int linha = entrada.charAt(0) - 'A';
            int coluna = Integer.parseInt(entrada.substring(1)) - 1;

            if (linha < 0 || linha >= inimigo.gettamanhoX() || coluna < 0 || coluna >= inimigo.gettamanhoY()) {
                System.out.println("Posicao fora do limite!");
                continue;
            }

            char valorAtual = inimigo.getcampo()[linha][coluna];

            if (valorAtual == 'X' || valorAtual == 'O') {
                System.out.println("Posicao ja atacada!");
                continue;
            }

            if (valorAtual == 'N') {
                System.out.println(" Acertou um navio!");
                inimigo.getcampo()[linha][coluna] = 'X';
            } else {
                System.out.println(" Acertou a agua!");
                inimigo.getcampo()[linha][coluna] = 'O';
            }

            tiroValido = true;
        }
    }
}

// ============================ MAQUINA ============================

class Maquina extends Jogadores {

    public Maquina(String nome, MeuTabuleiro inimigo, MeuTabuleiro tabuleiro) {
        super(nome, inimigo, tabuleiro);
    }

    @Override
    void posicao() {
        System.out.println("Posicionando frotas da maquina...");

        for (int i = 0; i < frotas.length; i++) {
            int tamanhoNavio = frotas[i];
            boolean posicionado = false;

            while (!posicionado) {
                int linha = (int) (Math.random() * tabuleiro.gettamanhoX());
                int coluna = (int) (Math.random() * tabuleiro.gettamanhoY());
                boolean horizontal = Math.random() < 0.5;

                if (veriEspa(tabuleiro, linha, coluna, tamanhoNavio, horizontal)) {
                    if (horizontal) {
                        for (int j = 0; j < tamanhoNavio; j++)
                            tabuleiro.getcampo()[linha][coluna + j] = 'N';
                    } else {
                        for (int j = 0; j < tamanhoNavio; j++)
                            tabuleiro.getcampo()[linha + j][coluna] = 'N';
                    }
                    posicionado = true;
                }
            }
        }
    }

    @Override
    void ataque() {
        boolean tiroValido = false;

        while (!tiroValido) {
            int linha = (int) (Math.random() * inimigo.gettamanhoX());
            int coluna = (int) (Math.random() * inimigo.gettamanhoY());
            char valorAtual = inimigo.getcampo()[linha][coluna];

            if (valorAtual == 'X' || valorAtual == 'O') continue;

            if (valorAtual == 'N') {
                System.out.println("A maquina acertou um navio!");
                inimigo.getcampo()[linha][coluna] = 'X';
            } else {
                System.out.println("A maquina acertou a agua!");
                inimigo.getcampo()[linha][coluna] = 'O';
            }
            tiroValido = true;
        }
    }

    boolean veriEspa(MeuTabuleiro tab, int linha, int coluna, int tamanhoNavio, boolean horizontal) {
        if (horizontal) {
            if (coluna + tamanhoNavio > tab.gettamanhoY()) return false;
            for (int j = 0; j < tamanhoNavio; j++)
                if (!tab.posicaoDisponivel(linha, coluna + j)) return false;
        } else {
            if (linha + tamanhoNavio > tab.gettamanhoX()) return false;
            for (int j = 0; j < tamanhoNavio; j++)
                if (!tab.posicaoDisponivel(linha + j, coluna)) return false;
        }
        return true;
    }
}
