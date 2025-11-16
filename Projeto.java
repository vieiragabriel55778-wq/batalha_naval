/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;

import java.util.Scanner;

public class Projeto {
    public static void main(String[] args) {
        Scanner tecla = new Scanner(System.in);
        Banco_Do_Jogo banco = new Banco_Do_Jogo();

        
        banco.criarTabelaPlacar();

        MeuTabuleiro tabuleiroJogador = new MeuTabuleiro(10, 10);
        MeuTabuleiro tabuleiroInimigo = new MeuTabuleiro(10, 10);

        tabuleiroJogador.inicio();
        tabuleiroInimigo.inicio();

        System.out.println("Ola, digite o seu nome:");
        String nomeJogador = tecla.nextLine();

        
        if (!banco.jogadorExiste(nomeJogador)) {
            banco.registrarJogador(nomeJogador);
            System.out.println("Novo jogador registrado: " + nomeJogador);
        } else {
            System.out.println("Bem-vindo de volta, " + nomeJogador);
            banco.mostrarPlacarIndividual(nomeJogador);
        }

        Jogador1 jogador = new Jogador1(nomeJogador, tabuleiroInimigo, tabuleiroJogador);
        Maquina maquina = new Maquina("Maquina", tabuleiroJogador, tabuleiroInimigo);

        jogador.posicao();
        maquina.posicao();

        boolean startGame = false;

        while (!startGame) {
            System.out.println("==== Tabuleiro do jogador ====");
            tabuleiroJogador.mostrarTabuleiro(true);
            System.out.println("==== Tabuleiro da maquina ====");
            tabuleiroInimigo.mostrarTabuleiro(false);

            jogador.ataque();

            if (!tabuleiroInimigo.Status_do_navio()) {
                System.out.println("\nVoce venceu!");
                banco.adicionarVitoria(nomeJogador);
                banco.adicionarDerrota("Maquina");
                startGame = true;
                break;
            }

            maquina.ataque();

            if (!tabuleiroJogador.Status_do_navio()) {
                System.out.println("\nA maquina venceu!");
                banco.adicionarDerrota(nomeJogador);
                banco.adicionarVitoria("Maquina");
                startGame = true;
                break;
            }

            System.out.println("Tabuleiro do jogador");
            tabuleiroJogador.mostrarTabuleiro(true);
            System.out.println("Tabuleiro da maquina");
            tabuleiroInimigo.mostrarTabuleiro(false);
        }

        System.out.println("Fim de jogo");

        System.out.println("\nSeu placar atualizado:");
        banco.mostrarPlacarIndividual(nomeJogador);

        System.out.println("\nPlacar geral:");
        banco.mostrarPlacarGeral();

        tecla.close();
    }
}
