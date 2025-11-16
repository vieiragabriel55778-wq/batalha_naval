/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_a3;

public abstract class Tabuleiro {        
    protected char[][] campo;
    private int tamanhoX = 10;
    private int tamanhoY = 10;

    public Tabuleiro(int tamanhoX, int tamanhoY) {
        campo = new char[tamanhoX][tamanhoY];
    }

    public int gettamanhoX() { return tamanhoX; }
    public int gettamanhoY() { return tamanhoY; }

    public char[][] getcampo() { return campo; }

    abstract void inicio();

    public boolean Status_do_navio() {
        for (int i = 0; i < tamanhoX; i++) {
            for (int j = 0; j < tamanhoY; j++) {
                if (campo[i][j] == 'N') {
                    return true;
                }
            }
        }
        return false;
    }
}

class MeuTabuleiro extends Tabuleiro {

    public MeuTabuleiro(int tamanhoX, int tamanhoY) {
        super(tamanhoX, tamanhoY);
    }

    @Override
    void inicio() {
        for (int i = 0; i < gettamanhoX(); i++) {
            for (int j = 0; j < gettamanhoY(); j++) {
                campo[i][j] = '~';
            }
        }
    }

    public boolean posicaoDisponivel(int linha, int coluna) {
        return campo[linha][coluna] == '~';
    }

    public boolean posicionarN(int linha, int coluna) {
        return campo[linha][coluna] == 'N';
    }

    public void mostrarTabuleiro(boolean mostrarNavios) {
        for (int i = 0; i < gettamanhoX(); i++) {
            for (int j = 0; j < gettamanhoY(); j++) {
                if (!mostrarNavios && campo[i][j] == 'N') {
                    System.out.print("~ "); 
                } else {
                    System.out.print(campo[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}

class TabuleiroInimigo extends Tabuleiro {

    public TabuleiroInimigo(int tamanhoX, int tamanhoY) {
        super(tamanhoX, tamanhoY);
    }

    @Override
    void inicio() {
        for (int i = 0; i < gettamanhoX(); i++) {
            for (int j = 0; j < gettamanhoY(); j++) {
                campo[i][j] = '~';
            }
        }
    }
}
